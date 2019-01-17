package com.blogging.aps.config.surpport;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.FactoryBean;

/**
 * <pre>
 * 初始化ObjectMapper
 * 静态化，保证Spring容器中和用{@link #getObjectMapper()}获取到的实例为同一个
 * </pre>
 *
 * @author liuxiaobo
 */
public class ObjectMapperFactoryBean implements FactoryBean<ObjectMapper> {

    /**
     * 全局唯一实例
     */
    private volatile static ObjectMapper objectMapper;

    /**
     * 初始化
     */
    private static synchronized void init() {
        if (null == objectMapper) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }

    public static ObjectMapper getObjectMapper() {
        if (null == objectMapper) {
            init();
        }

        return objectMapper;
    }

    @Override
    public ObjectMapper getObject() throws Exception {
        if (null == objectMapper) {
            init();
        }

        return objectMapper;
    }

    @Override
    public Class<?> getObjectType() {
        return ObjectMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}