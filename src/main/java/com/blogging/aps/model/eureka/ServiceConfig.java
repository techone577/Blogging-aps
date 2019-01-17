package com.blogging.aps.model.eureka;


import com.blogging.aps.model.constant.RestMethod;

/**
 * @author techoneduan
 * @date 2018/12/14
 *
 * 提供的服务信息实体类
 */
public class ServiceConfig {

    /**
     * 服务名称
     */
    private String name;

    /**
     * 路由映射
     */
    private String mapping;

    /**
     * 支持的方法 默认为GET
     */
    private String method = RestMethod.GET;//POST GET

    /**
     * 返回值信息
     */
    private String returnValue;

    /**
     * 参数列表
     */
    private String param;

    /**
     * 服务描述
     */
    private String description;

    public void setMapping(String map) {
        this.mapping = map;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public void setName(String na) {
        this.name = na;
    }

    public String getName() {
        return this.name;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getReturnValue() {
        return this.returnValue;
    }

    public void setParam(String para) {
        this.param = para;
    }

    public String getParam() {
        return this.param;
    }

    public void setDescription(String descrip) {
        this.description = descrip;
    }

    public String getDescription() {
        return this.description;
    }

}
