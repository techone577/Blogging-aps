package com.blogging.aps.model.dto;

import java.util.List;

public class BlogQueryRespDTO {

    private String postId;

    private String title;

    private PostInfoDTO nextPost;

    private PostInfoDTO previousPost;

    private String htmlContent;

    private String addTime;

    private List<String> tagList;

    private String TOC;

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

    public PostInfoDTO getNextPost() {
        return nextPost;
    }

    public void setNextPost(PostInfoDTO nextPost) {
        this.nextPost = nextPost;
    }

    public PostInfoDTO getPreviousPost() {
        return previousPost;
    }

    public void setPreviousPost(PostInfoDTO previousPost) {
        this.previousPost = previousPost;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getTOC() {
        return TOC;
    }

    public void setTOC(String TOC) {
        this.TOC = TOC;
    }

    public StatisticInfo getStatisticInfo() {
        return statisticInfo;
    }

    public void setStatisticInfo(StatisticInfo statisticInfo) {
        this.statisticInfo = statisticInfo;
    }
}
