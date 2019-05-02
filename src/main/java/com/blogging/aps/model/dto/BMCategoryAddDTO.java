package com.blogging.aps.model.dto;

/**
 * @author techoneduan
 * @date 2019/5/2
 */
public class BMCategoryAddDTO {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 封面图片链接
     */
    private String url;

    /**
     * 描述
     */
    private String summary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
