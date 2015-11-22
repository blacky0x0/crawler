package com.blacky.crawler.service;

import com.blacky.crawler.model.CrawlerTask;
import com.blacky.crawler.model.CrawlerTaskStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;

/**
 * User: blacky
 * Date: 19.11.15
 */
@Service
public class CrawlerService {

    @Autowired
    private CrawlerTaskKeeper keeper;



    public CrawlerTask add(String domain, String keyword) {
        return keeper.add(domain, keyword);
    }


    public CrawlerTask get(Long id) {
        return keeper.get(id);
    }

    public Collection<CrawlerTask> getAll() {
        return keeper.getAll();
    }

    public void delete(CrawlerTask task) {
        keeper.delete(task);
    }

    /**
     * Method deletes old items from storage.
     * If a task was created more than a minute ago, then it is considered old.
     * This method primarily used by scheduler.
     */
    public void deleteOld() {
        Collection<CrawlerTask> items = getAll();
        Iterator<CrawlerTask> it = items.iterator();

        while (it.hasNext()) {
            CrawlerTask item = it.next();
            if (LocalDateTime.now().minusMinutes(1).isAfter(item.getCreatedTime()))
                delete(item);
        }

    }

    /**
     * The method starts a task
     */
    void compute(CrawlerTask task) {
        task.setStatus(CrawlerTaskStatus.RUN.getCode());
        crawl(task);
        task.setStatus(CrawlerTaskStatus.SUCCESS.getCode());
    }


    /**
     * The method crawls a main page of web domain.
     */
    private void crawl(CrawlerTask task) {
        Document doc;

        try { doc = Jsoup.connect(task.getDomain()).get(); }
        catch (IOException e)
        {
            task.setStatus(CrawlerTaskStatus.FAIL.getCode());

            StringBuilder sb = new StringBuilder();
            sb.append("Can't crawl the domain: ");
            sb.append(task.getDomain());
            sb.append("; Task id: ");
            sb.append(task.getId());

            throw new RuntimeException(sb.toString());
        }

        task.setTitle(doc.title());
        task.setAmountWordsInBody(countWords(doc.body().text()));
        task.addDensity("title", computeDensity(task.getKeyword(), task.getTitle()));
        task.addDensity("body", computeDensity(task.getKeyword(), doc.text()));
        task.addDensity("h1", computeDensity(task.getKeyword(), doc.getElementsByTag("h1").text()));
    }

    /**
     * Calculates approximate number of words in the text.
     * Very greedy method.
     */
    private int countWords(String text) {
        return text.split("(\\p{Punct}*\\s+)+").length;
    }

    /**
     * Calculates approximate density of keyword in the text.
     * Very greedy method.
     * @return density
     */
    private int computeDensity(String keyword, String text) {
        int keywordCounter = 0;
        String[] items = text.split("(\\p{Punct}*\\s+)+");

        for (String item : items) {
            if (keyword.equalsIgnoreCase(item))
                keywordCounter++;
        }

        return (100 * keywordCounter) / items.length;
    }

}
