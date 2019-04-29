//package com.blogging.aps.support.aop;
//
//import com.blogging.aps.model.enums.ErrorCodeEnum;
//import com.blogging.aps.support.annotation.ValidAuthentication;
//import com.blogging.aps.support.exception.UnifiedException;
//import com.mysql.cj.x.protobuf.Mysqlx;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 登录状态及权限判断
// *
// * @author techoneduan
// * @date 2019/4/27
// */
//
//@Aspect
//@Component
//public class ValidAuthenticationAspect {
//
//    private static final Logger LOG = LoggerFactory.getLogger(ValidAuthenticationAspect.class);
//
//    @Pointcut(value = "@annotation(validAuthentication)",argNames = "validAuthentication")
//    public void authenticationPointcut(ValidAuthentication validAuthentication) {
//    }
//
//
//    @Before(value = "authenticationPointcut(validAuthentication)")
//    public void validAuthentication(JoinPoint joinPoint,ValidAuthentication validAuthentication) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        String permission = validAuthentication.permission();
//        //查询cookie
//        Cookie[] cookies = request.getCookies();
//        if (null == cookies || cookies.length == 0)
//            throw new UnifiedException(ErrorCodeEnum.COOKIES_NULL_ERROR);
//        //TODO 是去ams查询验证session还是共享到redis管理session
//        String sessionId = null;
//        for(Cookie c : cookies){
//            if("ams-session-id".equals(c.getName()))
//                sessionId = c.getValue();
//        }
//        if(null == sessionId)
//            throw new UnifiedException(ErrorCodeEnum.USER_NOT_LOGIN_ERROR);
//    }
//}
