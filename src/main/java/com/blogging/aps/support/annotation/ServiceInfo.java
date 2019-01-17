package com.blogging.aps.support.annotation;

import java.lang.annotation.*;

/**
 * @author techoneduan
 * @date 2018/12/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
//@Inherited
@Documented
public @interface ServiceInfo {

    String name () default "";

    String description () default "";
}
