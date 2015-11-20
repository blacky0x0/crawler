package com.blacky.crawler.model;

/**
 * User: blacky
 * Date: 20.11.15
 */
public enum CrawlerTaskStatus {

    NEW(0),
    RUN(1),
    SUCCESS(2),
    FAIL(3);

    private final int code;

    CrawlerTaskStatus(int code) {
        this.code = code;
    }

    /**
     * Returns an integer code value
     * @return an integer value
     */
    public int getCode() {
        return code;
    }

}
