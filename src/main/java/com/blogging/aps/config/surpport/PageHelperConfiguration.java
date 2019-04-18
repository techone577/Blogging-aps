package com.blogging.aps.config.surpport;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author techoneduan
 * @date 2019/4/18
 */
@Configuration
public class PageHelperConfiguration {

    @Bean
    public PageHelper pageHelper() {
        //pagehelper 传入参数从1开始
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
