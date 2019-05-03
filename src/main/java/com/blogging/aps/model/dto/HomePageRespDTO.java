package com.blogging.aps.model.dto;

import java.util.List;

public class HomePageRespDTO {

    private List<TagInfoDTO> tagInfoList;

    private List<CategoryInfo> categoryInfoList;

    private List<HomePagePostListDTO> homePagePostList;

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

    public List<HomePagePostListDTO> getHomePagePostList() {
        return homePagePostList;
    }

    public void setHomePagePostList(List<HomePagePostListDTO> homePagePostList) {
        this.homePagePostList = homePagePostList;
    }
}
