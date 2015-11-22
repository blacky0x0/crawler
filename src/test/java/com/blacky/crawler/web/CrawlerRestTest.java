package com.blacky.crawler.web;

import com.blacky.crawler.model.CrawlerTask;
import com.blacky.crawler.service.CrawlerService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .andExpect(jsonPath("$.id", is(1)));
    }
    // </editor-fold>


    // <editor-fold desc="2 tests. CrawlerRest.get(long id)">
    @Test
    /**
     * Positive test.
     * Expect 200 OK.
     * Check all properties.
     * Check amount of properties.
     */
    public void get_expectOk_checkProperties() throws Exception {
        String domain = "example.com";
        String keyword = "example";

        CrawlerTask task = service.add(domain, keyword);
        Thread.sleep(500);

        mockMvc.perform(get(REST_URL + task.getId()))
                .andDo(print())
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.domain", is("http://example.com")))
                .andExpect(jsonPath("$.keyword", is("example")))
                .andExpect(jsonPath("$.title", is("Example Domain")))
                .andExpect(jsonPath("$.amountWordsInBody", is(30)))
                .andExpect(jsonPath("$.density.h1", is(50)))
                .andExpect(jsonPath("$.density.title", is(50)))
                .andExpect(jsonPath("$.density.body", is(6)))
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.density.*", hasSize(3)));
    }

    @Test
    /**
     * Negative test.
     * No items in storage, therefore expect status -1
     */
    public void get_expectStatus01() throws Exception {
        mockMvc.perform(get(REST_URL + 42))
                .andDo(print())
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(-1)));
    }
    // </editor-fold>
}