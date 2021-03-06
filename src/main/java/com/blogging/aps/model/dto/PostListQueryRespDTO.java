package com.blogging.aps.model.dto;

import java.util.List;

public class PostListQueryRespDTO {

    private List<HomePagePostListDTO> postList;

    private List<TagInfoDTO> tagInfoList;

    private List<CategoryInfo> categoryInfoList;

    private Long totalNum;

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

    public List<CategoryInfo> getCategoryInfoList() {
        return categoryInfoList;
    }

    public void setCategoryInfoList(List<CategoryInfo> categoryInfoList) {
        this.categoryInfoList = categoryInfoList;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getMinId() {
        return minId;
    }

    public void setMinId(Integer minId) {
        this.minId = minId;
    }
}
