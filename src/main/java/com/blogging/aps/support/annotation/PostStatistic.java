package com.blogging.aps.support.annotation;

import java.lang.annotation.*;

/**
 * @author techoneduan
 * @date 2019/4/20
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface PostStatistic {
    String value () default "";
}
