package com.blogging.aps.model.eureka;

import java.util.List;

/**
 * @author techoneduan
 * @date 2018/12/19
 *
 * client 注册信息
 */
public class RegistryInfo {

    /**
     * application name 系统名称
     */
    private String clientName;

    private String ipaddr;

    private String port;
    /**
     * 提供的服务信息
     */
    private List<ServiceConfig> serviceConfigs;

    /**
     * 订阅的服务名称
     */
    private List<String> subscribeServices;

    public String getClientName () {
        return clientName;
    }

    public void setClientName (String clientName) {
        this.clientName = clientName;
    }

    public String getIpaddr () {
        return ipaddr;
    }

    public void setIpaddr (String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getPort () {
        return port;
    }

    public void setPort (String port) {
        this.port = port;
    }

    public List<ServiceConfig> getServiceConfigs () {
        return serviceConfigs;
    }

    public void setServiceConfigs (List<ServiceConfig> serviceConfigs) {
        this.serviceConfigs = serviceConfigs;
    }

    public List<String> getSubscribeServices () {
        return subscribeServices;
    }

    public void setSubscribeServices (List<String> subscribeServices) {
        this.subscribeServices = subscribeServices;
    }
}
