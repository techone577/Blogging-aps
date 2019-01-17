package com.blogging.aps.support.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author techoneduan
 * @date 2018/12/20
 */
@Component
public class RedisUtil {

    private static final Logger LOG = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void doCache (String key, String value) {
        LOG.info("缓存redis，key:{},value:{}", key, value);
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            LOG.info("redis缓存异常:{}", e);
        }
    }

    public String getString (String key) {
        String res = null;
        LOG.info("获取redis缓存,key:{}", key);
        try {
            res = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            LOG.info("redis读取异常:{}", e);
        }
        return res;
    }

    public void expire (String key, Integer n, TimeUnit unit) {
        LOG.info("设置缓存过期时间,key:{},{}{}", key, n, unit);
        redisTemplate.expire(key, n, unit);
    }
}
