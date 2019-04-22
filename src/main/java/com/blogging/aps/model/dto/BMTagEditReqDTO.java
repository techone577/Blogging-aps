package com.blogging.aps.model.dto;

/**
 * @author techoneduan
 * @date 2019/4/22
 */
public class BMTagEditReqDTO {

    private Integer id;

    private String tagName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
