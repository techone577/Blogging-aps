package com.blogging.aps.web.view;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

@Controller
@RequestMapping("/imageView")
public class ImageViewController {

    private static final Logger LOG = LoggerFactory.getLogger(ImageViewController.class);

    @RequestMapping(value = "/showImg", method = RequestMethod.GET)
    public void postImageShow(@RequestParam String imgId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = "WEB-INF/image/post/" + imgId;
        imageShow(path, request, response);
    }

    @RequestMapping(value = "/showCoverImg", method = RequestMethod.GET)
    public void coverImageShow(@RequestParam String imgId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = "WEB-INF/image/cover/" + imgId;
        imageShow(path, request, response);
    }

    private void imageShow(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
        File file = new File(request.getServletContext().getRealPath(path));
        ServletOutputStream out = response.getOutputStream();
        FileInputStream fileInputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            outputStream = new BufferedOutputStream(out);
            response.setContentType("multipart/form-data");

            byte[] buffer = new byte[1024];
            int ch = 0;
            while ((ch = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, ch);
            }
            outputStream.flush();

        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            out.close();
            outputStream.close();
            fileInputStream.close();
        }
    }
}
