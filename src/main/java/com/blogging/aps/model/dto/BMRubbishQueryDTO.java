package com.blogging.aps.model.dto;

/**
 * @author techoneduan
 * @date 2019/4/18
 */
public class BMRubbishQueryDTO {

    private Integer pageNum;

    private Integer pageSize;

    private Integer delFlag;

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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
