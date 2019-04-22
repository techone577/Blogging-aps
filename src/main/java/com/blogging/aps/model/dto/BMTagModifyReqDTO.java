package com.blogging.aps.model.dto;

/**
 * @author techoneduan
 * @date 2019/4/22
 */
public class BMTagModifyReqDTO {

    private String postId;

    private String tagName;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
