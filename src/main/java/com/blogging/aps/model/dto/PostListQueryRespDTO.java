package com.blogging.aps.model.dto;

import java.util.List;

public class PostListQueryRespDTO {

    private List<HomePagePostListDTO> postList;

    private List<TagInfoDTO> tagInfoList;

    private Integer totalNum;

    private Integer minId;

    public List<HomePagePostListDTO> getPostList() {
        return postList;
    }

    public void setPostList(List<HomePagePostListDTO> postList) {
        this.postList = postList;
    }

    public List<TagInfoDTO> getTagInfoList() {
        return tagInfoList;
    }

    public void setTagInfoList(List<TagInfoDTO> tagInfoList) {
        this.tagInfoList = tagInfoList;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getMinId() {
        return minId;
    }

    public void setMinId(Integer minId) {
        this.minId = minId;
    }
}
