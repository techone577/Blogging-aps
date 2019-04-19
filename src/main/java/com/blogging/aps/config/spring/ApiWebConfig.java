package com.blogging.aps.config.spring;


import com.blogging.aps.config.surpport.JsonMapperArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author techoneduan
 * @date 2018/12/28
 */

/**
 * fuck aspectj 注意mvc和spring上下文容器不一样，想要拦截controller中的方法两个enable必须在一个配置文件
 */
@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@ComponentScan(basePackages = "com.blogging.aps.web.api")
public class ApiWebConfig implements WebMvcConfigurer {

    @Bean
    public JsonMapperArgumentResolver jsonMapperArgumentResolver () {
        JsonMapperArgumentResolver jsonMapperArgumentResolver = new JsonMapperArgumentResolver();
        return jsonMapperArgumentResolver;
    }

    @Override
    public void addArgumentResolvers (List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(jsonMapperArgumentResolver());

    }

    /**
     * 处理responsebody返回string 多出""
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        return new MappingJackson2HttpMessageConverter(){
            @Override
            protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                if(object instanceof String){
                    Charset charset = this.getDefaultCharset();
                    StreamUtils.copy((String)object, charset, outputMessage.getBody());
                }else{
                    super.writeInternal(object, type, outputMessage);
                }
            }
        };
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());
    }

}
