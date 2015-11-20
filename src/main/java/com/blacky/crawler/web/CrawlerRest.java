package com.blacky.crawler.web;

import com.blacky.crawler.model.CrawlerTask;
import com.blacky.crawler.service.CrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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


    /**
     * The method saves a new item.
     * @return 200 OK - item was created
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ResponseEntity add(@RequestParam(value = "domain", required = true) String domain,
                              @RequestParam(value = "keyword", required = true) String keyword) {
        LOG.debug("Received parameters: domain = {} ; keyword = {}", domain, keyword);

        CrawlerTask task = service.add(domain, keyword);
        return new ResponseEntity<>(task.getId().toString(), HttpStatus.OK);
    }

    // TODO: /rest/tasks/{id}  GET
    /*
        domain = ...
        title = ...
        amount = ...
        density_title = ...
        density_h1 = ...
        density_body = ...
    */

    /**
     * The method saves a new item.
     * @return 200 OK - item was created
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable(value = "id") long id) {
        LOG.debug("Received parameter: id = {}", id);

        CrawlerTask task = service.get(id);

        if (Objects.isNull(task)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(task.toString(), HttpStatus.OK);
    }

}
