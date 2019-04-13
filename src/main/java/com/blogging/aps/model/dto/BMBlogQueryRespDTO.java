package com.blogging.aps.model.dto;

import java.util.List;

/**
 * @author techoneduan
 * @date 2019/4/13
 */
public class BMBlogQueryRespDTO {

    private String title;

    private String content;

    private String summary;

    private List<String> tagList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
