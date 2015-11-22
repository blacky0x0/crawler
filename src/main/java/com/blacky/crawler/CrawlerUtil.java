package com.blacky.crawler;

import com.blacky.crawler.model.CrawlerTask;
import com.blacky.crawler.model.CrawlerTaskStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * User: blacky
 * Date: 22.11.15
 */
public class CrawlerUtil {

    /**
     * The method crawls a domain web page and fill in a received task.
     */
    public static void crawl(CrawlerTask task) {
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
        task.setUpdatedTime(LocalDateTime.now());
        task.setStatus(CrawlerTaskStatus.SUCCESS.getCode());
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
