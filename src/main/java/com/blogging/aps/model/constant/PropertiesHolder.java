package com.blogging.aps.model.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author techoneduan
 * @date 2018/12/17
 *
 * 配置存储 避免static注入不可用
 */
@Component
public class PropertiesHolder {

    @Value("${server.port}")
    private Integer port;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${netty.server.ip}")
    private String nettyIp;

    @Value("${netty.server.port}")
    private Integer nettyPort;

    public Integer getPort () {
        return port;
    }

    public String getName () {
        return appName;
    }

    public String getNettyIp() {
        return nettyIp;
    }

    public Integer getNettyPort() {
        return nettyPort;
    }
}
