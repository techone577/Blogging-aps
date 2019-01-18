package com.blogging.aps.support.spring;

import com.blogging.aps.model.constant.BspSubscribeService;
import com.blogging.aps.model.constant.PropertiesHolder;
import com.blogging.aps.model.constant.RestMethod;
import com.blogging.aps.model.eureka.RegistryInfo;
import com.blogging.aps.model.eureka.ServiceConfig;
import com.blogging.aps.service.netty.FactoryListHolder;
import com.blogging.aps.support.annotation.ServiceInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author techoneduan
 * @date 2018/12/14
 *
 * 缓存应用上下文
 */
@Component
public class ApplicationContextCache implements ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationContextCache.class);

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String clientName;

    private static ApplicationContext applicationContextCache;

    private static List<ServiceConfig> list;

    private static RegistryInfo registryInfo;


    //TODO why synchronized?
    @Override
    public synchronized void setApplicationContext (ApplicationContext applicationContext) throws BeansException {
        applicationContextCache = applicationContext;
        list = new ArrayList<>();
        registryInfo = new RegistryInfo();
        scan(applicationContextCache, list);
        LOG.info("扫描服务结束...");
    }

    public synchronized static ApplicationContext getApplicationcontext () {
        return applicationContextCache;
    }

    public synchronized static List<ServiceConfig> getServiceConfig () {
        return list;
    }

    public static RegistryInfo getRegistryInfo () {
        return registryInfo;
    }

    public static FactoryListHolder getFactoryListHolder () {
        return null == applicationContextCache ? null : (FactoryListHolder) applicationContextCache.getBean("factoryListHolder");
    }

    public static PropertiesHolder getPropertiesHolder () {
        return null == applicationContextCache ? null : (PropertiesHolder) applicationContextCache.getBean("propertiesHolder");
    }

    private void scan (ApplicationContext ct, List<ServiceConfig> list) {
        //TODO controlelr.class
        Map<String, Object> restMap = ct.getBeansWithAnnotation(RestController.class);
        Collection<Object> c = restMap.values();
        for (Object ob : c) {
            getServiceInfoAnnotation(ob.getClass(), list);
        }
    }

    private void getServiceInfoAnnotation (Class<?> cls, List<ServiceConfig> list) {
        if (null == cls || null == list) {
            return;
        }
        Method[] methods = cls.getDeclaredMethods();
        RequestMapping rc = cls.getAnnotation(RequestMapping.class);
        for (Method m : methods) {
            ServiceConfig sc = new ServiceConfig();
            RequestMapping rm = m.getAnnotation(RequestMapping.class);
            if (rm == null || rm.value().length == 0)
                continue;
            RequestMethod[] requestMethods = rm.method();
            //请求方法类型
            if (null != requestMethods && requestMethods.length > 0) {
                sc.setMethod(requestMethods[0].toString());
            } else {
                sc.setMethod(RestMethod.GET);
            }
            String cm = null, mm = null;
            if (null != rc && rc.value().length > 0 && null != rc.value()[0]) {
                cm = formatMapping(rc.value()[0]);
            }
            if (null != rm && rm.value().length > 0 && null != rm.value()[0]) {
                mm = formatMapping(rm.value()[0]);
            }
            //路由地址
            sc.setMapping(cm + mm);
            //返回值名
            sc.setReturnValue(m.getReturnType().getName());
            //参数列表
            Class<?>[] type = m.getParameterTypes();
            StringBuilder params = new StringBuilder();
            Arrays.asList(type).stream().forEach(item -> {
                params.append(item.getName()).append("#");
            });
            String ps = params.toString();
            if (StringUtils.isNotBlank(ps))
                ps = ps.substring(0, ps.length() - 1);
            sc.setParam(ps);
            //解析服务注解
            ServiceInfo si = m.getAnnotation(ServiceInfo.class);
            if (null != si) {
                sc.setDescription(si.description());
                String sn = si.name().trim();
                if (StringUtils.isBlank(sn)) {
                    LOG.error("服务名为空,method:{}", m.getName());
                }
                if (list.stream().map(item -> item.getName()).collect(Collectors.toSet()).contains(sn)) {
                    LOG.error("服务名重复.method:{}", cls.getName() + ":" + m.getName());
                    continue;
                }
                sc.setName(sn);
            }
            list.add(sc);
        }
        registryInfo.setClientName(clientName);
        registryInfo.setPort(port);
        try {
            registryInfo.setIpaddr(InetAddress.getLocalHost().getHostAddress());
        } catch (Exception e) {
            LOG.info("获取ip异常:{}", e);
        }
        registryInfo.setServiceConfigs(list);
        registryInfo.setSubscribeServices(BspSubscribeService.subscribeList);
    }

    private String formatMapping (String mapping) {
        mapping = mapping.startsWith("/") ? mapping : "/".concat(mapping);
        return mapping.endsWith("/") ? mapping.substring(0, mapping.length() - 1) : mapping;

    }
}
