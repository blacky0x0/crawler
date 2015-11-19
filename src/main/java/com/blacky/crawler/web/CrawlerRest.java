package com.blacky.crawler.web;

import com.blacky.crawler.service.CrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: blacky
 * Date: 19.11.15
 */
@RestController
@RequestMapping(value = CrawlerRest.REST_URL)
public class CrawlerRest {

    private static final Logger LOG = LoggerFactory.getLogger(CrawlerRest.class);
    public static final String REST_URL = "/rest/tasks";

    @Autowired
    private CrawlerService service;



    // TODO: /rest/tasks/new?domain=...&keyword=...   POST

    // TODO: /rest/tasks/{id}  GET
    /*
        domain = ...
        title = ...
        amount = ...
        density_title = ...
        density_h1 = ...
        density_body = ...
    */

}
