package com.blogging.aps.model.entity;

/**
 * @author techoneduan
 * @date 2018/12/18
 *
 * netty 请求返回 需要同步操作时使用唯一的requestId
 */
public class NettyRespEntity {

    private String requestId;

    private String respType;

    private String response;

    public String getRequestId () {
        return requestId;
    }

    public void setRequestId (String requestId) {
        this.requestId = requestId;
    }

    public String getRespType () {
        return respType;
    }

    public void setRespType (String respType) {
        this.respType = respType;
    }

    public String getResponse () {
        return response;
    }

    public void setResponse (String response) {
        this.response = response;
    }
}
