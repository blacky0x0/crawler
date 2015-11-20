package com.blacky.crawler.service;

import com.blacky.crawler.model.CrawlerTask;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: blacky
 * Date: 19.11.15
 */
public class CrawlerServiceTest extends AbstractServiceTest {

    @Autowired
    CrawlerService service;

    @Before
    public void init() {
        // reset the id counter
        CrawlerTask.ID.set(0);
    }


    // <editor-fold desc="1 test. CrawlerService.add(String domain, String keyword)">
    @Test
    public void add_one() throws Exception {
        String domain = "example.com";
        String keyword = "example";

        CrawlerTask task = service.add(domain, keyword);

        assertEquals(Long.valueOf(1), task.getId());
        assertEquals(domain, task.getDomain());
        assertEquals(keyword, task.getKeyword());
    }
    // </editor-fold>


    // <editor-fold desc="1 test. CrawlerService.get(Long long)">
    @Test
    public void get_result() throws Exception {
        String domain = "example.com";
        String keyword = "example";

        CrawlerTask task = service.add(domain, keyword);

        assertNotNull(service.get(task.getId()));
    }
    // </editor-fold>

}