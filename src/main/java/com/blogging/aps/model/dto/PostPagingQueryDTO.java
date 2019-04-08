package com.blogging.aps.model.dto;

public class PostPagingQueryDTO {

    private Integer lastMinId = Integer.MAX_VALUE;

    private Integer pageSize = 5;

    public Integer getLastMinId() {
        return lastMinId;
    }

    public void setLastMinId(Integer lastMinId) {
        this.lastMinId = lastMinId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
