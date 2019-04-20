package com.blogging.aps.model.dto;

/**
 * @author techoneduan
 * @date 2019/4/20
 */
public class StatisticInfo {

    private Long pageView;

    private String readTime;

    public Long getPageView() {
        return pageView;
    }

    public void setPageView(Long pageView) {
        this.pageView = pageView;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }
}
