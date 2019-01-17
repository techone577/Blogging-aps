package com.blogging.aps.support.exception.handler;


import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.enums.ErrorCodeEnum;
import com.blogging.aps.support.exception.UnifiedException;
import com.blogging.aps.support.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * 通用异常处理
 *
 * @author xubin2
 */
@Component
public class UnifiedExceptionResolver extends SimpleMappingExceptionResolver {

    private static final Logger LOG = LoggerFactory.getLogger(UnifiedExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {
        Response responseVo = new Response();
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String name = handlerMethod.getBeanType().getName();
        responseVo.setSuccess(false);
        if (ex instanceof UnifiedException) {
            ErrorCodeEnum errorCodeEnum = ((UnifiedException) ex).getErrorCodeEnum();
            if (Objects.nonNull(errorCodeEnum)) {
                responseVo.setErrorCode(errorCodeEnum.getCode());
                String message = "";
                if (StringUtils.isNotBlank(ex.getMessage())) {
                    message = "[" + ex.getMessage() + "]";
                }
                responseVo.setMsg(errorCodeEnum.getMsg() + message);
            } else {
                responseVo.setMsg(ex.getMessage());
            }
        } else {
            LOG.error("catch by UnifiedExceptionResolver,request:{}", JsonUtil.toString(request.getParameterMap()),ex);
            String str = StringUtils.isEmpty(ex.getMessage()) ? "系统异常，稍后再试...".concat(ex.toString()) : ex.getMessage();
            responseVo.setMsg(str);
            responseVo.setErrorCode(500);
        }
        String result = JsonUtil.toString(responseVo);
        if(name.contains("api"))
            result = Base64.getEncoder().encodeToString(result.getBytes(StandardCharsets.UTF_8));
        LOG.info("response:{}", result);
        write(response, result);
        return new ModelAndView();
    }

    protected void write(HttpServletResponse response, String result) {
        PrintWriter out = null;
        String charCode = "UTF-8";
        try {
//          String resStr = Base64.getEncoder().encodeToString(result.getBytes(StandardCharsets.UTF_8));
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Methods", "*");
            out = response.getWriter();
            out.write(result);
        } catch (UnsupportedEncodingException e) {
            LOG.error("不支持字" + charCode + "符集");
        } catch (IOException e) {
            LOG.error("获取输出流失败");
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

}
