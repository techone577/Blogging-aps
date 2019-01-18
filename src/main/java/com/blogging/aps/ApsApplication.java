package com.blogging.aps;

import com.blogging.aps.netty.NettyClient;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@MapperScan(basePackages = "com.blogging.aps.persistence")
public class ApsApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ApsApplication.class);

    public static void main (String[] args) {

        SpringApplication.run(ApsApplication.class, args);

        try {
            NettyClient.connect();
        } catch (Exception e) {
            LOG.info("连接netty服务器失败...");
        }
        LOG.info("netty启动结束...");
    }
}
