package com.blogging.aps.model.dto;

import java.util.List;

public class HomePageRespDTO {

    private List<TagInfoDTO> tagInfoList;

    private List<HomePagePostListDTO> homePagePostList;

    public List<TagInfoDTO> getTagInfoList() {
        return tagInfoList;
    }

    public void setTagInfoList(List<TagInfoDTO> tagInfoList) {
        this.tagInfoList = tagInfoList;
    }

    public List<HomePagePostListDTO> getHomePagePostList() {
        return homePagePostList;
    }

    public void setHomePagePostList(List<HomePagePostListDTO> homePagePostList) {
        this.homePagePostList = homePagePostList;
    }
}
