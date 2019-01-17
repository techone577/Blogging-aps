package com.blogging.aps.model.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author techoneduan
 * @date 2018/12/20
 */
public class BspSubscribeService {

    /**
     * 注册成为该服务的消费者 统一管理
     */
    public static List<String> subscribeList = new ArrayList<String>(){
        {
            add("blogging.test");
        }
    };
}
