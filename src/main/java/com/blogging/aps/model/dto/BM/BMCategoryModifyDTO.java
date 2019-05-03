package com.blogging.aps.model.dto.BM;

/**
 * @author techoneduan
 * @date 2019/5/3
 */
public class BMCategoryModifyDTO {

    private Integer id;

    private String name;

    private String summary;

    private String coverUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

}
