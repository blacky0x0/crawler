package com.blacky.crawler.web;

import com.blacky.crawler.builder.CrawlerTaskBuilder;
import com.blacky.crawler.model.CrawlerTask;
import com.blacky.crawler.model.CrawlerTaskStatus;
import com.blacky.crawler.service.CrawlerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
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

    @InjectMocks
    private CrawlerRest rest;

    @Mock
    CrawlerService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
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
        CrawlerTask task = new CrawlerTaskBuilder().build();

        when(service.add(task.getDomain(), task.getKeyword())).thenReturn(task);

        MockMvcBuilders.standaloneSetup(rest)
                .build()
                .perform(get(REST_URL_NEW_ONE)
                        .param("domain", task.getDomain())
                        .param("keyword", task.getKeyword()))
                .andDo(print())
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(task.getId().intValue())));

    }

    // <editor-fold desc="2 tests. CrawlerRest.get(long id)">
    @Test
    /**
     * Positive test.
     * Expect 200 OK.
     * Check all properties.
     * Check amount of properties.
     */
    public void get_expectOk_checkProperties() throws Exception {

        CrawlerTask task = new CrawlerTaskBuilder()
                .withStatus(CrawlerTaskStatus.SUCCESS)
                .withTitle("Example Domain")
                .withAmountWordsInBody(30)
                .build();

        when(service.get(task.getId())).thenReturn(task);

        MockMvcBuilders.standaloneSetup(rest)
                .build()
                .perform(get(REST_URL + task.getId()))
                .andDo(print())
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(task.getId().intValue())))
                .andExpect(jsonPath("$.domain", is(task.getDomain())))
                .andExpect(jsonPath("$.keyword", is(task.getKeyword())))
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.amountWordsInBody", is(task.getAmountWordsInBody())))
                .andExpect(jsonPath("$.density.h1", is(task.getDensity().get("h1"))))
                .andExpect(jsonPath("$.density.title", is(task.getDensity().get("title"))))
                .andExpect(jsonPath("$.density.body", is(task.getDensity().get("body"))))
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


    @Test
    /**
     * Positive test.
     * Storage contains selected item, but it isn't computed yet.
     * Check status: RUN(1)
     */
    public void get_expectStatus02() throws Exception {

        CrawlerTask task = new CrawlerTaskBuilder()
                .withStatus(CrawlerTaskStatus.RUN)
                .build();

        when(service.get(task.getId())).thenReturn(task);

        MockMvcBuilders.standaloneSetup(rest)
                .build()
                .perform(get(REST_URL + task.getId()))
                .andDo(print())
                .andExpect(forwardedUrl(null))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(task.getStatus())));

    }
    // </editor-fold>
}