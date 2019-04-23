package com.blogging.aps.model.dto;

/**
 * @author techoneduan
 * @date 2019/4/24
 */
public class BMPostUpdateDTO {

    private String postId;

    private BMPostAddDTO addDTO;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public BMPostAddDTO getAddDTO() {
        return addDTO;
    }

    public void setAddDTO(BMPostAddDTO addDTO) {
        this.addDTO = addDTO;
    }
}
