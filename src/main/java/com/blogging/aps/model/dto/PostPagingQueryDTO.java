package com.blogging.aps.model.dto;

public class PostPagingQueryDTO {

    private Integer lastMinId = Integer.MAX_VALUE;

    private Integer pageSize = 5;

    private String type;

    private String typeValue;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }
}
