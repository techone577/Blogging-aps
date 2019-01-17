package com.blogging.aps.model.eureka;

/**
 * @author techoneduan
 * @date 2018/12/17
 *
 * eureka 注册中心注册实例实体类
 */
public class Instance {

    private String instanceId;

    private String hostName;

    private String app;

    private String ipAddr;

    private Port port;

    private Port securePort;


    public String getInstanceId () {
        return instanceId;
    }

    public void setInstanceId (String instanceId) {
        this.instanceId = instanceId;
    }

    public String getHostName () {
        return hostName;
    }

    public void setHostName (String hostName) {
        this.hostName = hostName;
    }

    public String getApp () {
        return app;
    }

    public void setApp (String app) {
        this.app = app;
    }

    public String getIpAddr () {
        return ipAddr;
    }

    public void setIpAddr (String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Port getPort () {
        return port;
    }

    public void setPort (Port port) {
        this.port = port;
    }

    public Port getSecurePort () {
        return securePort;
    }

    public void setSecurePort (Port securePort) {
        this.securePort = securePort;
    }
}
