package com.blogging.aps.config.spring;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


/**
 * @author techoneduan
 * @date 2019/1/2
 */
@Configuration
@ComponentScan(basePackages = "com.blogging.aps.web.view")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver viewResolver () {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
//      viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

//    @Bean
//    public DispatcherServlet webDispatcherServlet () {
//        AnnotationConfigWebApplicationContext ctx
//                = new AnnotationConfigWebApplicationContext();
//        ctx.register(WebConfig.class);
//        DispatcherServlet dispatcherServlet = new DispatcherServlet(ctx);
//        return dispatcherServlet;
//    }
//
//    @Bean
//    public ServletRegistrationBean servletRegistration () {
//        ServletRegistrationBean registration = new ServletRegistrationBean(webDispatcherServlet(), "/");
//        registration.setName("client-dispatcher");
//        registration.setLoadOnStartup(1);
//        return registration;
//    }

    /**
     * 静态资源配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers (ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void configureDefaultServletHandling (DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
