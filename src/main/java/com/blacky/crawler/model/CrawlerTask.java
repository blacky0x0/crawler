package com.blacky.crawler.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * User: blacky
 * Date: 20.11.15
 */
public class CrawlerTask {
    private Long id;
    private String domain;
    private String keyword;

    public static AtomicLong ID = new AtomicLong(0);

    {
        // Before each object creation
        // test incremented ID value
        // and set it to 1 if it was overflowed
        if (ID.incrementAndGet() < 0) {
            ID.set(1);
        }
    }

    public CrawlerTask(String domain, String keyword) {
        this.id = ID.get();
        this.domain = domain;
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }

    public String getKeyword() {
        return keyword;
    }
}
