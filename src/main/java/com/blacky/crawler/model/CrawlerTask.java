package com.blacky.crawler.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * User: blacky
 * Date: 20.11.15
 */
public class CrawlerTask {
    public static final String DEFAULT_PROTOCOL = "http://";
    public static final AtomicLong ID = new AtomicLong(0);

    private Long id;
    private String domain;
    private String keyword;
    private Integer status = CrawlerTaskStatus.NEW.getCode();
    private String title;
    private Integer amountWordsInBody;

    private LocalDateTime updatedTime = LocalDateTime.now();
    private LocalDateTime createdTime = LocalDateTime.now();

    // keyword density for text in tags: title, h1, body
    private HashMap<String, Integer> density = new HashMap<>(8);


    {
        // Before each object creation
        // test incremented ID value
        // and set it to 1 if it was overflowed
        if (ID.incrementAndGet() < 0) {
            ID.set(1);
        }
    }

    public CrawlerTask(String domain, String keyword) {
        this.id = ID.get();

        // if no protocol is set
        if (!domain.contains("://"))
            this.domain = DEFAULT_PROTOCOL.concat(domain);
        else
            this.domain = domain;

        this.keyword = keyword;
    }


    // <editor-fold desc="Getters & Setters">
    public Long getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }

    public String getKeyword() {
        return keyword;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAmountWordsInBody() {
        return amountWordsInBody;
    }

    public void setAmountWordsInBody(Integer amountWordsInBody) {
        this.amountWordsInBody = amountWordsInBody;
    }

    public void addDensity(String key, Integer value) {
        density.put(key, value);
    }

    public HashMap<String, Integer> getDensity() {
        return density;
    }

    public void setDensity(HashMap<String, Integer> density) {
        this.density = density;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    // </editor-fold>


    @Override
    public String toString() {
        return "CrawlerTask{" +
                "id=" + id +
                ", domain='" + domain + '\'' +
                ", keyword='" + keyword + '\'' +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", amountWordsInBody=" + amountWordsInBody +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", density=" + density +
                '}';
    }
}
