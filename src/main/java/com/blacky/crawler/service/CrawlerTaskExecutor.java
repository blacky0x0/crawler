package com.blacky.crawler.service;

import com.blacky.crawler.CrawlerUtil;
import com.blacky.crawler.model.CrawlerTask;
import com.blacky.crawler.model.CrawlerTaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

public class CrawlerTaskExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlerTaskExecutor.class);

    private TaskExecutor taskExecutor;

    public CrawlerTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }


    private class Job implements Runnable {
        private CrawlerTask task;

        public Job(CrawlerTask task) {
            this.task = task;
        }

        public void run() {
            LOG.debug("Execute task #{}", task.getId());
            CrawlerUtil.crawl(task);
            LOG.trace("Task was computed: {}", task);
        }
    }


    /**
     * The method starts a task
     */
    public void compute(CrawlerTask task) {
        task.setStatus(CrawlerTaskStatus.RUN.getCode());
        taskExecutor.execute(new Job(task));
    }

}