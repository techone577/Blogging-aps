package com.blogging.aps.service;


import com.blogging.aps.model.entity.NettyRespEntity;
import com.blogging.aps.support.strategy.MatchingBean;


/**
 * @author techoneduan
 * @date 2018/12/17
 */
public abstract class AbstractNettyService implements MatchingBean<String> {

    public abstract void dealRequest (NettyRespEntity resp);

}
