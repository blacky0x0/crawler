package com.blacky.crawler.web;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: blacky
 * Date: 19.11.15
 */
public class CrawlerRestTest extends AbstractRestTest {

    public static final String REST_URL = CrawlerRest.REST_URL + '/';
    public static final String REST_URL_NEW_ONE = REST_URL + "new";


    // <editor-fold desc="1 test. CrawlerRest.add()">
    @Test
    /**
     * Positive test.
     * Expect 200 OK.
     */
    public void add_expectOk() throws Exception {
        String domain = "example.com";
        String keyword = "example";

        mockMvc.perform(post(REST_URL_NEW_ONE).param("domain", domain).param("keyword", keyword))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk());
    }
    // </editor-fold>

    // <editor-fold desc="1 test. CrawlerRest.get(int id)">
    @Test
    /**
     * Positive test.
     * Expect 200 OK.
     */
    public void get_expectOk() throws Exception {
        Integer id = 1;

        mockMvc.perform(get(REST_URL + id))
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk());
    }
    // </editor-fold>
}