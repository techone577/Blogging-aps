package com.blogging.aps.support.strategy;

/**
 * bean匹配
 * @author xiehui
 *
 */
public interface MatchingBean<T> {

    boolean matching (T factor);
    
}
