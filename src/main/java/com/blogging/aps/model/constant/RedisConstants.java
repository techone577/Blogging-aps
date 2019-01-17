package com.blogging.aps.model.constant;


import com.blogging.aps.support.spring.ApplicationContextCache;

/**
 * @author techoneduan
 * @date 2018/12/18
 */
public class RedisConstants {

    /**
     * Redis key 常量值
     */

    private static PropertiesHolder propertiesHolder = ApplicationContextCache.getPropertiesHolder();

    //本地服务列表缓存
    public static final String INSTANCE_CACHE = "INSTANCE_LOCAL_CACHE:" + propertiesHolder.getName() + ":" + propertiesHolder.getPort();

}
