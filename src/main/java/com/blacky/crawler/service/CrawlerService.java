package com.blacky.crawler.service;

import com.blacky.crawler.model.CrawlerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: blacky
 * Date: 19.11.15
 */
@Service
public class CrawlerService {

    @Autowired
    private CrawlerTaskKeeper executor;

    public CrawlerTask add(String domain, String keyword) {
        CrawlerTask task = executor.add(domain, keyword);
        return task;
    }


}
