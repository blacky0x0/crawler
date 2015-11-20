package com.blacky.crawler.service;

import com.blacky.crawler.model.CrawlerTask;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * User: blacky
 * Date: 20.11.15
 */
@Component
public class CrawlerTaskKeeper {

    private static final ConcurrentHashMap<Long, CrawlerTask> container = new ConcurrentHashMap<>();

    public CrawlerTask add(String domain, String keyword) {
        CrawlerTask task = new CrawlerTask(domain, keyword);
        container.put(task.getId(), task);
        return task;
    }

}
