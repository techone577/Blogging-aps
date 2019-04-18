package com.blogging.aps.model.dto;

/**
 * @author techoneduan
 * @date 2019/4/18
 */
public class BMDraftsQueryDTO {

    private Integer pageNum;

    private Integer pageSize;

    private Integer releaseFlag;

    public Integer getReleaseFlag() {
        return releaseFlag;
    }

    public void setReleaseFlag(Integer releaseFlag) {
        this.releaseFlag = releaseFlag;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
