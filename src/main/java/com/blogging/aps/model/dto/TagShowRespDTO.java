package com.blogging.aps.model.dto;

import java.util.List;

public class TagShowRespDTO {

    private List<TagInfoDTO> allTags;

    private List<TagInfoDTO> hotTags;

    public List<TagInfoDTO> getAllTags() {
        return allTags;
    }

    public void setAllTags(List<TagInfoDTO> allTags) {
        this.allTags = allTags;
    }

    public List<TagInfoDTO> getHotTags() {
        return hotTags;
    }

    public void setHotTags(List<TagInfoDTO> hotTags) {
        this.hotTags = hotTags;
    }
}
