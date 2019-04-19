package com.blogging.aps.support.aop;

import com.blogging.aps.model.enums.ErrorCodeEnum;
import com.blogging.aps.support.exception.UnifiedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author techoneduan
 * @date 2019/4/19
 */

@Aspect
@Component
public class ServiceReturnAspect {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceReturnAspect.class);

//    @Pointcut("@annotation(com.blogging.aps.support.annotation.ServiceInfo)")
//    public void servicePointcut(){}
//
//    @Around("servicePointcut()")
    public Object serviceAround(ProceedingJoinPoint pjp){
        LOG.info("around");
        try {
            Object obj = pjp.proceed();
            return obj;
        }catch (Throwable t){
            throw new UnifiedException(ErrorCodeEnum.UNKNOWN);
        }
    }
}
