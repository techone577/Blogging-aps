package com.blogging.aps.config.spring;

import ch.qos.logback.classic.ViewStatusMessagesServlet;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * @author techoneduan
 * @date 2019/1/2
 */
@Configuration
public class WebInitializer implements ServletContextInitializer {
    @Override
    public void onStartup (ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext dispatcherCtx = new AnnotationConfigWebApplicationContext();
        dispatcherCtx.register(WebConfig.class);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("web-dispatcher", new DispatcherServlet(dispatcherCtx));
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);

        //字符集过滤器
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
    }
}
