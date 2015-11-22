package com.blacky.crawler.service;

import com.blacky.crawler.CrawlerUtil;
import com.blacky.crawler.model.CrawlerTask;
import com.blacky.crawler.model.CrawlerTaskStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

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
        assertEquals("http://".concat(domain), task.getDomain());
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


    // <editor-fold desc="1 test. CrawlerService.delete(CrawlerTask task)">
    @Test
    public void delete_result() throws Exception {
        String domain = "example.com";
        String keyword = "example";

        CrawlerTask task = service.add(domain, keyword);
        service.delete(task);

        assertNull(service.get(task.getId()));
    }
    // </editor-fold>


    // <editor-fold desc="1 test. CrawlerService.compute(Long long)">
    @Test
    public void compute_result() throws Exception {
        String domain = "jsoup.org";
        String keyword = "jsoup";

        CrawlerTask task = new CrawlerTask(domain, keyword);
        CrawlerUtil.crawl(task);

        assertEquals(CrawlerTaskStatus.SUCCESS.getCode(), (int) task.getStatus());
        assertEquals("jsoup Java HTML Parser, with best of DOM, CSS, and jquery", task.getTitle());
        assertEquals(341, (int) task.getAmountWordsInBody());
        assertEquals(25, (int) task.getDensity().get("h1"));
        assertEquals(9, (int) task.getDensity().get("title"));
        assertEquals(4, (int) task.getDensity().get("body"));
    }
    // </editor-fold>


    // <editor-fold desc="1 test. CrawlerService.getAll()">
    @Test
    public void getAll_result() throws Exception {
        String domain = "example.com";
        String keyword = "example";

        service.add(domain, keyword);
        service.add(domain, keyword);
        service.add(domain, keyword);
        service.add(domain, keyword);

        assertEquals(4, service.getAll().size());
    }
    // </editor-fold>


    // <editor-fold desc="1 test. CrawlerService.deleteOld()">
    @Test
    public void deleteOld_result() throws Exception {
        String domain = "example.com";
        String keyword = "example";

        // Add two items
        CrawlerTask task = service.add(domain, keyword);
        task.setCreatedTime(LocalDateTime.now().minusMinutes(2));
        task.setUpdatedTime(LocalDateTime.now().minusMinutes(2));
        service.add(domain, keyword);

        service.deleteOld();

        assertEquals(1, service.getAll().size());
    }
    // </editor-fold>
}