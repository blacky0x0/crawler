package com.blacky.crawler.util;

import com.blacky.crawler.model.CrawlerTask;
import com.blacky.crawler.model.CrawlerTaskStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * User: blacky
 * Date: 22.11.15
 */
public class CrawlerUtil {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlerUtil.class);

    /**
     * The method crawls a domain web page and fill in a received task.
     */
    public static void crawl(CrawlerTask task) {
        LOG.debug("Execute task #{}", task.getId());
        Document doc;

        try { doc = Jsoup.connect(task.getDomain()).get(); }
        catch (IOException e)
        {
            task.setStatus(CrawlerTaskStatus.FAIL.getCode());
            String msg = String.format("Can't crawl the domain: %s; Task id: %s", task.getDomain(), task.getId());
            throw new RuntimeException(msg);
        }

        task.setTitle(doc.title());
        task.setAmountWordsInBody(countWords(doc.body().text()));
        task.addDensity("title", computeDensity(task.getKeyword(), task.getTitle()));
        task.addDensity("body", computeDensity(task.getKeyword(), doc.text()));
        task.addDensity("h1", computeDensity(task.getKeyword(), doc.getElementsByTag("h1").text()));
        task.setUpdatedTime(LocalDateTime.now());
        task.setStatus(CrawlerTaskStatus.SUCCESS.getCode());

        LOG.trace("Task was computed: {}", task);
    }

    /**
     * Calculates approximate number of words in the text.
     * Very greedy method.
     */
    public static int countWords(String text) {
        return text.split("(\\p{Punct}*\\s+)+").length;
    }

    /**
     * Calculates approximate density of keyword in the text.
     * Very greedy method.
     * @return density
     */
    public static int computeDensity(String keyword, String text) {
        int keywordCounter = 0;
        String[] items = text.split("(\\p{Punct}*\\s+)+");

        for (String item : items) {
            if (keyword.equalsIgnoreCase(item))
                keywordCounter++;
        }

        return (100 * keywordCounter) / items.length;
    }

}
