package com.blogging.aps.web.api;

import com.blogging.aps.model.dto.ImageLoadReqDTO;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
        LOG.info("图片上传！");
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
        String route = "/imageView/showImg?imgId=";
        String imPath = route + file.getName();
        sb.append(imPath);
        resp.put("url", sb.toString());
        LOG.info("图片上传返回地址：{}",sb.toString());
        return ResponseBuilder.build(true, JsonUtil.toString(resp));
    }

    @RequestMapping(value = "/obtain",method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.ImageController.obtain")
    public Response obtainImage(@Json ImageLoadReqDTO reqDTO, HttpServletRequest request, HttpServletResponse response) throws Exception{
        String path = "WEB-INF/image/"+reqDTO.getImageId();
        File file = new File(request.getServletContext().getRealPath(path));
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {

            byte[] buffer = new byte[1024];
            int ch = 0;
            while ((ch = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer,0,ch);
            }
            BASE64Encoder encoder = new BASE64Encoder();
            String imString = encoder.encode(outputStream.toByteArray());
            return ResponseBuilder.build(true,imString);

        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            outputStream.close();
            fileInputStream.close();
        }
        return null;
    }
}
