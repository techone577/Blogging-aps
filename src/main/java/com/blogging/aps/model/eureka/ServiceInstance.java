package com.blogging.aps.model.eureka;

import java.util.List;

/**
 * @author techoneduan
 * @date 2018/12/17
 * 系统-多个实例
 */
public class ServiceInstance {

    private String name;

    private List<Instance> instance;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public List<Instance> getInstance () {
        return instance;
    }

    public void setInstance (List<Instance> instance) {
        this.instance = instance;
    }
}
