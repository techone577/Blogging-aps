package com.blogging.aps.business;

import com.blogging.aps.model.dto.ImageReqDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.support.utils.Base64Util;
import com.blogging.aps.support.utils.JsonUtil;
import com.blogging.aps.support.utils.ResponseBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author techoneduan
 * @date 2019/5/2
 */

@Component
public class ImageBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(ImageBusiness.class);

    @Value("${server.port}")
    private String port;

    public Response uploadForPost(ImageReqDTO reqDTO, HttpServletRequest request) throws Exception {
        return uploadImage(reqDTO, "/WEB-INF/image/post/", "/imageView/showImg?imgId=", request);
    }

    public Response uploadForCover(ImageReqDTO reqDTO, HttpServletRequest request) throws Exception {
        return uploadImage(reqDTO, "/WEB-INF/image/cover/", "/imageView/showCoverImg?imgId=", request);
    }

    private Response uploadImage(ImageReqDTO reqDTO, String vPath, String route, HttpServletRequest request) throws Exception {
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
        String path = request.getServletContext().getRealPath(vPath);
        File targetFile = new File(path, file.getName());
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
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
        String imPath = route + file.getName();
        sb.append(imPath);
        resp.put("url", sb.toString());
        LOG.info("图片上传返回地址：{}", sb.toString());
        return ResponseBuilder.build(true, JsonUtil.toString(resp));
    }
}
