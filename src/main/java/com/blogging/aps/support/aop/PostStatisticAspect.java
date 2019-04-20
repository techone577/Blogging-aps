package com.blogging.aps.support.aop;

import com.blogging.aps.model.dto.PostQueryReqDTO;
import com.blogging.aps.model.entity.post.StatisticEntity;
import com.blogging.aps.model.enums.ErrorCodeEnum;
import com.blogging.aps.service.post.StatisticService;
import com.blogging.aps.support.exception.UnifiedException;
import com.blogging.aps.support.lock.DistributeLock;
import com.blogging.aps.support.lock.DistributeLockImpl;
import com.blogging.aps.support.utils.BloggingThreadPool;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author techoneduan
 * @date 2019/4/19
 */

@Aspect
@Component
public class PostStatisticAspect {

    private static final Logger LOG = LoggerFactory.getLogger(PostStatisticAspect.class);

    @Autowired
    private DistributeLock distributeLock;

    @Autowired
    private StatisticService statisticService;

    @Pointcut("@annotation(com.blogging.aps.support.annotation.PostStatistic)")
    public void statisticPointcut(){}


    @AfterReturning(value = "statisticPointcut()",returning = "retVal")
    public void StatisticAfterReturning(JoinPoint pjp,Object retVal){
        try {
            //TODO 可以先缓存到redis 一段时间后统一写入数据库
            //TODO 并发
            //感觉一段时间分析日志最好
            Object[] obj = pjp.getArgs();
            PostQueryReqDTO p = (PostQueryReqDTO)obj[0];
            LOG.info("文章阅读统计开始,id:{}",p.getPostId());
            BloggingThreadPool.StatiaticPool().execute(()->StatisticPageView(p.getPostId()));

        }catch (Throwable t){
            throw new UnifiedException(ErrorCodeEnum.UNKNOWN);
        }
    }

    //不精确
    private void StatisticPageView(String postId){
        String key = "Statistic_"+postId;
        Boolean lock = distributeLock.tryLock(key);
        try {
            if (lock) {
                List<StatisticEntity> entityList = statisticService.queryByPostId(postId);
                StatisticEntity entity = new StatisticEntity();
                entity.setPostId(postId);
                if(null == entityList || entityList.size() == 0){
                    entity.setStatistic(1l);
                    entity.setDelFlag(0);
                    entity.setAddTime(new Date());
                    entity.setUpdateTime(new Date());
                    statisticService.insertSelective(entity);

                }else{
                    entity.setStatistic(entityList.get(0).getStatistic()+1);
                    entity.setUpdateTime(new Date());
                    statisticService.updatePageView(entity);
                }
            } else {
                LOG.info("获取分布式锁失败，key:{}", key);
            }
        }catch (Exception e){
            LOG.info("统计PV失败:{}",e);
        }finally {
            distributeLock.unlock(key);
        }
    }
}
