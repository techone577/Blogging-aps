package com.blogging.aps.model.entity;

/**
 * @author techoneduan
 * @date 2018/12/17
 *
 * netty 请求入参 header 为请求类型 NettyHeader
 */
public class NettyReqEntity {

    private String requestId;

    private String header;

    private Object params;

    public String getRequestId () {
        return requestId;
    }

    public void setRequestId (String requestId) {
        this.requestId = requestId;
    }

    public String getHeader () {
        return header;
    }

    public void setHeader (String header) {
        this.header = header;
    }

    public Object getParams () {
        return params;
    }

    public void setParams (Object params) {
        this.params = params;
    }
}
