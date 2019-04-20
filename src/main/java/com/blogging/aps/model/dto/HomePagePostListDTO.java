package com.blogging.aps.model.dto;

import java.util.List;

public class HomePagePostListDTO {

    private String postId;

    private String title;

    private String summary;

    private String addTime;

    private String updateTime;

    private Integer releaseFlag;

    private List<String> tagList;

    private StatisticInfo statisticInfo;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getReleaseFlag() {
        return releaseFlag;
    }

    public void setReleaseFlag(Integer releaseFlag) {
        this.releaseFlag = releaseFlag;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public StatisticInfo getStatisticInfo() {
        return statisticInfo;
    }

    public void setStatisticInfo(StatisticInfo statisticInfo) {
        this.statisticInfo = statisticInfo;
    }
}
