package com.blacky.crawler.web;

import com.blacky.crawler.model.CrawlerTask;
import com.blacky.crawler.service.CrawlerService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: blacky
 * Date: 19.11.15
 */
public class CrawlerRestTest extends AbstractRestTest {

    public static final String REST_URL = CrawlerRest.REST_URL + '/';
    public static final String REST_URL_NEW_ONE = REST_URL + "new";

    @Autowired
    CrawlerService service;

    @Before
    public void init() {
        // reset the id counter
        CrawlerTask.ID.set(0);
    }



    // <editor-fold desc="1 test. CrawlerRest.add(String, String)">
    @Test
    /**
     * Positive test.
     * Expect: 200 OK and id = 1 in body
     */
    public void add_expectOk() throws Exception {
        String domain = "example.com";
        String keyword = "example";

        mockMvc.perform(get(REST_URL_NEW_ONE).param("domain", domain).param("keyword", keyword))
                .andDo(print())
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
    // </editor-fold>

    // <editor-fold desc="1 test. CrawlerRest.get(long id)">
    @Test
    /**
     * Positive test.
     * Expect 200 OK.
     */
    public void get_expectOk() throws Exception {
        String domain = "example.com";
        String keyword = "example";

        CrawlerTask task = service.add(domain, keyword);

        mockMvc.perform(get(REST_URL + task.getId()))
                .andDo(print())
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk());
    }
    // </editor-fold>
}