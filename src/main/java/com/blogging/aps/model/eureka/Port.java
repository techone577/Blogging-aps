package com.blogging.aps.model.eureka;

/**
 * @author techoneduan
 * @date 2018/12/17
 *
 * 端口实体类 包括普通端口和安全端口
 */
public class Port {

    private String port;

    private String enabled;

    public String getPort () {
        return port;
    }

    public void setPort (String port) {
        this.port = port;
    }

    public String getEnabled () {
        return enabled;
    }

    public void setEnabled (String enabled) {
        this.enabled = enabled;
    }
}
