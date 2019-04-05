package com.blogging.aps.web.api;

import com.blogging.aps.model.dto.ImageReqDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.support.annotation.Json;
import com.blogging.aps.support.annotation.ServiceInfo;
import com.blogging.aps.support.utils.Base64Util;
import com.blogging.aps.support.utils.JsonUtil;
import com.blogging.aps.support.utils.ResponseBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author techoneduan
 * @date 2019/4/4
 */

@RestController
@RequestMapping(value = "/image")
public class ImageController {

    private static final Logger LOG = LoggerFactory.getLogger(ImageController.class);

    @Value("${server.port}")
    private String port;


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.ImageController.upload", description = "图片上传")
    public Response uploadImage(@Json ImageReqDTO reqDTO, HttpServletRequest request) throws Exception {
        String imString = reqDTO.getImString();
        Map<String, Object> resp = new HashMap<>();
        String originalName = reqDTO.getOrignalName();
        if (StringUtils.isBlank(originalName)) {
            resp.put("success", 0);
            resp.put("message", "文件名为空");
            resp.put("url", null);
            return ResponseBuilder.build(true, JsonUtil.toString(resp));
        }
        MultipartFile file = Base64Util.base64ToMultipart(imString, originalName);
        String path = request.getServletContext().getRealPath("/WEB-INF/image/");
        File targetFile = new File(path, file.getName());
        //保存
        file.transferTo(targetFile);
        resp.put("success", 1);
        resp.put("message", "成功");
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("http://").append(InetAddress.getLocalHost().getHostAddress()).append(":").append(port);
        } catch (Exception e) {
            LOG.info("获取ip异常:{}", e);
        }
        String imPath = path + file.getName();
        sb.append(imPath);
        resp.put("url", sb.toString());
        return ResponseBuilder.build(true, JsonUtil.toString(resp));
    }

}
