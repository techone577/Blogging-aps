package com.blogging.aps.support.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author techoneduan
 * @date 2019/1/4
 */
@Service
public class DistributeLockImpl implements DistributeLock {
    private static final Logger LOG = LoggerFactory.getLogger(DistributeLockImpl.class);

    private static final int DEAD_LOCK_EXPIRED_SECONDS = 60 * 1; // 1 分钟

    private static final int DEAD_LOCK_REMOVE_SECONDS = DEAD_LOCK_EXPIRED_SECONDS + 60; //

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean tryLock(String lockKey) {
        try {
            Long time_stamp = System.currentTimeMillis();
            BoundValueOperations<String, Long> boundValueOperations = redisTemplate.boundValueOps(lockKey);
            boolean flag = boundValueOperations.setIfAbsent(time_stamp);
            if (flag) {//获得锁
                redisTemplate.expire(lockKey, DEAD_LOCK_EXPIRED_SECONDS, TimeUnit.SECONDS);
                return true;
            } else {  //没有获得锁
                //通过查看值的时间戳进行死锁检查
                Long val = boundValueOperations.get();

                if (val == null) {//如果key读取期间被删（释放锁），直接返回 false;
                    return false;
                } else if ((time_stamp - val) > DEAD_LOCK_REMOVE_SECONDS) { //已经超过时间
                    //抢占 GETSET 锁决定谁 进行删除过期锁
                    Long last_value = boundValueOperations.getAndSet(time_stamp);

                    if (val.equals(last_value)) { //相等则抢占到锁
                        redisTemplate.delete(lockKey);
                    } else {
                        return false; //中间被人删除 或 last_value为空（被释放）
                    }

                } else { //未超时
                    return false;
                }
            }
        } catch (Exception e) {
            LOG.error("获取分布式锁异常:{}", e);
            return false;
        }

        return false;
    }

    @Override
    public boolean unlock(String lockKey) {
        try {
            redisTemplate.delete(lockKey);
        } catch (Exception e) {
            LOG.error("释放分布式锁异常:{}", e);
            return false;
        }
        return true;
    }

}
