package com.blacky.crawler.builder;

import com.blacky.crawler.model.CrawlerTask;
import com.blacky.crawler.model.CrawlerTaskStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: blacky
 * Date: 22.11.15
 */
public class CrawlerTaskBuilder {

    public static final String DEFAULT_DOMAIN = "example.com";
    public static final String DEFAULT_KEYWORD = "example";
    public static final CrawlerTaskStatus DEFAULT_STATUS = CrawlerTaskStatus.NEW;
    public static final String DEFAULT_TITLE = "Example Domain";
    public static final Map<String, Integer> DEFAULT_DENSITY;

    static
    {
        HashMap<String, Integer> tmp = new HashMap<>(8);
        tmp.put("h1", 50);
        tmp.put("title", 50);
        tmp.put("body", 6);

        DEFAULT_DENSITY = Collections.unmodifiableMap(tmp);
    }

    private String domain = DEFAULT_DOMAIN;
    private String keyword = DEFAULT_KEYWORD;
    private int status = DEFAULT_STATUS.getCode();
    private String title = DEFAULT_TITLE;
    private Integer amountWordsInBody = 30;
    private Map<String, Integer> density = DEFAULT_DENSITY;

    public CrawlerTaskBuilder withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public CrawlerTaskBuilder withKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public CrawlerTaskBuilder withStatus(CrawlerTaskStatus status) {
        this.status = status.getCode();
        return this;
    }

    public CrawlerTaskBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public CrawlerTaskBuilder withAmountWordsInBody(int amountWordsInBody) {
        this.amountWordsInBody = amountWordsInBody;
        return this;
    }

    public CrawlerTaskBuilder withDensity(Map<String, Integer> density) {
        this.density = density;
        return this;
    }

    public CrawlerTask build() {
        CrawlerTask task = new CrawlerTask(domain, keyword);
        task.setStatus(status);
        task.setTitle(title);
        task.setAmountWordsInBody(amountWordsInBody);
        task.setDensity(density);

        return task;
    }

}
