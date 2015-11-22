package com.blacky.crawler.service;

import com.blacky.crawler.model.CrawlerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;

/**
 * User: blacky
 * Date: 19.11.15
 */
@Service
public class CrawlerService {

    @Autowired
    private CrawlerTaskKeeper keeper;

    @Autowired
    private CrawlerTaskExecutor executor;


    public CrawlerTask add(String domain, String keyword) {

        CrawlerTask task = keeper.add(domain, keyword);
        // Enqueue for execution
        executor.compute(task);
        return task;
    }


    public CrawlerTask get(Long id) {
        return keeper.get(id);
    }

    public Collection<CrawlerTask> getAll() {
        return keeper.getAll();
    }

    public void delete(CrawlerTask task) {
        keeper.delete(task);
    }

    /**
     * Method deletes old items from storage.
     * If a task was created more than a minute ago, then it is considered old.
     * This method primarily used by scheduler.
     */
    public void deleteOld() {
        Collection<CrawlerTask> items = getAll();
        Iterator<CrawlerTask> it = items.iterator();

        while (it.hasNext()) {
            CrawlerTask item = it.next();
            if (LocalDateTime.now().minusMinutes(1).isAfter(item.getCreatedTime()))
                delete(item);
        }

    }

}
