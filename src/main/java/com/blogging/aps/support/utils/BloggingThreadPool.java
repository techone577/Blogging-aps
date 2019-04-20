package com.blogging.aps.support.utils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author techoneduan
 * @date 2019/4/20
 */
public class BloggingThreadPool {

    private static class StatisticPool{
        private static ThreadPoolExecutor statisticPool = new ThreadPoolExecutor(5, 20, 30L,TimeUnit.SECONDS, new LinkedBlockingDeque<>(20));
    }

    public static ThreadPoolExecutor StatiaticPool(){
        return StatisticPool.statisticPool;
    }
}
