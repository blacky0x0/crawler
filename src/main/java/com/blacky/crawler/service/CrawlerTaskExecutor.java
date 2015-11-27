package com.blacky.crawler.service;

import com.blacky.crawler.util.CrawlerUtil;
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

    public void compute(CrawlerTask task) {
        LOG.trace("Task was enqueued. ID: {}", task.getId());
        task.setStatus(CrawlerTaskStatus.RUN.getCode());
        taskExecutor.execute(() -> CrawlerUtil.crawl(task));
    }

}