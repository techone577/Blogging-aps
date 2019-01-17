package com.blogging.aps.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author techoneduan
 * @date 2018/12/14
 */
public class AnnotationScanner  {

//    private static final Logger LOG = LoggerFactory.getLogger(AnnotationScanner.class);
//
//    @Override
//    public void postProcessBeanFactory (ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        ApplicationContext ct = ApplicationContextCache.getApplicationcontext();
//        if (ct != null)
//            scan(ct);
//    }
//
//    public Object postProcessAfterInitialization (Object bean, String beanName) throws BeansException {
//        ApplicationContext ct = ApplicationContextCache.getApplicationcontext();
//        if (ct != null)
//            scan(ct);
//        return bean;
//    }

//    private void scan (ApplicationContext ct) {
//        List<ServiceConfig> list = new ArrayList<>();
//        Map<String, Object> restMap = ct.getBeansWithAnnotation(RestController.class);
//        Collection<Object> c = restMap.values();
//        for (Object ob : c) {
//            getServiceInfoAnnotation(ob.getClass(), list);
//        }
//    }
//
//    private void getServiceInfoAnnotation (Class<?> cls, List<ServiceConfig> list) {
//        if (null == cls || null == list) {
//            return;
//        }
//        ServiceConfig sc = new ServiceConfig();
//        //获取类映射
//        Method[] methods = cls.getDeclaredMethods();
//        RequestMapping rc = cls.getAnnotation(RequestMapping.class);
//        for (Method m : methods) {
//            RequestMapping rm = m.getAnnotation(RequestMapping.class);
//            if (rm == null || rm.value().length == 0)
//                continue;
//            RequestMethod[] requestMethods = rm.method();
//            if (null != requestMethods && requestMethods.length > 0) {
//                sc.setMethod(requestMethods[0].toString());
//            } else {
//                sc.setMethod(RestMethod.GET);
//            }
//            String cm = null, mm = null;
//            if (null != rc && rc.value().length > 0 && null != rc.value()[0]) {
//                cm = formatMapping(rc.value()[0]);
//            }
//            if (null != rm && rm.value().length > 0 && null != rm.value()[0]) {
//                mm = formatMapping(rm.value()[0]);
//            }
//            sc.setMapping(cm + mm);
//            list.add(sc);
//        }
//    }
//
//    private String formatMapping (String mapping) {
//        mapping = mapping.startsWith("/") ? mapping : "/".concat(mapping);
//        return mapping.endsWith("/") ? mapping.substring(0, mapping.length() - 1) : mapping;
//
//    }
}
