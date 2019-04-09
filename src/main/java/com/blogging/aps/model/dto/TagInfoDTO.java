package com.blogging.aps.model.dto;

public class TagInfoDTO {
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTagNum() {
        return tagNum;
    }

    public void setTagNum(Integer tagNum) {
        this.tagNum = tagNum;
    }

    private String tagName;

    private Integer tagNum;


}
