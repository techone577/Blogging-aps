package com.blogging.aps.web.api;

import com.blogging.aps.business.ImageBusiness;
import com.blogging.aps.model.dto.ImageLoadReqDTO;
import com.blogging.aps.model.dto.ImageReqDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.support.annotation.Json;
import com.blogging.aps.support.annotation.ServiceInfo;
import com.blogging.aps.support.utils.JsonUtil;
import com.blogging.aps.support.utils.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author techoneduan
 * @date 2019/4/4
 */

@RestController
@RequestMapping(value = "/image")
public class ImageController {

    private static final Logger LOG = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageBusiness imageBusiness;

    @RequestMapping(value = "/uploadForPost", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.ImageController.uploadForPost", description = "文章图片上传")
    public Response uploadImageForPost(@Json ImageReqDTO reqDTO, HttpServletRequest request) throws Exception {
        LOG.info("上传图片入参:{}", JsonUtil.toString(reqDTO));
        Response response = imageBusiness.uploadForPost(reqDTO, request);
        LOG.info("上传图片出参:{}", JsonUtil.toString(response));
        return response;
    }

    @RequestMapping(value = "/uploadForCover", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.ImageController.uploadForCover", description = "封面图片上传")
    public Response uploadImageForCover(@Json ImageReqDTO reqDTO, HttpServletRequest request) throws Exception {
        LOG.info("上传图片入参:{}", JsonUtil.toString(reqDTO));
        Response response = imageBusiness.uploadForCover(reqDTO, request);
        LOG.info("上传图片出参:{}", JsonUtil.toString(response));
        return response;
    }

    @RequestMapping(value = "/obtain", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.ImageController.obtain")
    public Response obtainImage(@Json ImageLoadReqDTO reqDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = "WEB-INF/image/" + reqDTO.getImageId();
        File file = new File(request.getServletContext().getRealPath(path));
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {

            byte[] buffer = new byte[1024];
            int ch = 0;
            while ((ch = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, ch);
            }
            BASE64Encoder encoder = new BASE64Encoder();
            String imString = encoder.encode(outputStream.toByteArray());
            return ResponseBuilder.build(true, imString);

        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            outputStream.close();
            fileInputStream.close();
        }
        return null;
    }
}
