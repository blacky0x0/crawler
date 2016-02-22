package com.blacky.crawler.web;

import com.blacky.crawler.model.CrawlerTask;
import com.blacky.crawler.model.CrawlerTaskStatus;
import com.blacky.crawler.service.CrawlerService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;


@RestController
@RequestMapping(value = CrawlerRest.REST_URL, produces = CrawlerRest.JSON_UTF8)
public class CrawlerRest {

    private static final Logger LOG = LoggerFactory.getLogger(CrawlerRest.class);
    public static final String REST_URL = "/rest/tasks";
    public static final String JSON_UTF8 = MediaType.APPLICATION_JSON_VALUE; // rfc4627.txt -> rfc7159.txt

    @Autowired
    private CrawlerService service;


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity addWithBody(@Valid @RequestBody ClientTask clientTask) {
        LOG.debug("Received parameters: domain = {} ; keyword = {}", clientTask.domain, clientTask.keyword);

        CrawlerTask task = service.add(clientTask.domain, clientTask.keyword);

        HashMap<String, Long> map = new HashMap<>(4);
        map.put("id", task.getId());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ClientTask implements Serializable {

        @JsonProperty("domain")
        public String domain;

        @NotNull(message = "Please provide a valid valid keyword")
        @Size(min = 1, max = 10, message="Please provide a valid keyword")
        @JsonProperty("keyword")
        public String keyword;

    }


    /**
     * The method saves a new item.
     * Returns id in json message. It looks like: {"id":42}
     * @return 200 OK - item was created
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ResponseEntity add(@RequestParam(value = "domain", required = true) String domain,
                              @RequestParam(value = "keyword", required = true) String keyword) {
        LOG.debug("Received parameters: domain = {} ; keyword = {}", domain, keyword);

        CrawlerTask task = service.add(domain, keyword);

        HashMap<String, Long> map = new HashMap<>(4);
        map.put("id", task.getId());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * The method returns a computed item or status.
     * Returns a status code if item not found or item not computed yet.
     * In above case it looks like:
     * <pre>{"status":-1}</pre>
     * Otherwise returns a result, that looks like:
     * <pre>
     * {
     *  "id":1,
     *  "domain":"http://example.com",
     *  "keyword":"example",
     *  "title":"Example Domain",
     *  "amountWordsInBody":30,
     *  "density":{"h1":50,"title":50,"body":6}
     *  }
     * </pre>
     * @return 200 OK
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable(value = "id") long id) {
        LOG.debug("Received parameter: id = {}", id);

        CrawlerTask task = service.get(id);

        if (Objects.isNull(task) ||
                !(task.getStatus() == CrawlerTaskStatus.SUCCESS.getCode())) {
            HashMap<String, Object> map = new HashMap<>(4);

            if (Objects.isNull(task))
                map.put("status", -1);
            else
                map.put("status", task.getStatus());

            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

}
