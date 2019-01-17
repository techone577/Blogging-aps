package com.blogging.aps.support.lock;

/**
 * @author techoneduan
 * @date 2019/1/4
 *
 * redis分布式锁
 */
public interface DistributeLock {

    boolean tryLock(String lockKey);

    boolean unlock(String lockKey);
}
