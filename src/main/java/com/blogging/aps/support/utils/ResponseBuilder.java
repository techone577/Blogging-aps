package com.blogging.aps.support.utils;


import com.blogging.aps.model.entity.Response;
import org.apache.commons.lang.StringUtils;

/**
 * @author techoneduan
 * @date 2018/11/6
 */
public class ResponseBuilder {

    public static Response build (boolean isSuccess, Object data) {
        Response response = new Response();
        response.setSuccess(isSuccess);
        response.setData(data);
        return response;
    }

    public static Response buildResult(String result) {
        Response response = null;
        if (StringUtils.isBlank(result)) {
            response = build(false, null);
        } else {
            response = JsonUtil.toBean(result, Response.class);
        }
        return response;
    }
}
