package com.blogging.aps.support.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author techoneduan
 * @date 2019/4/5
 */

public class BASE64DecodedMultipartFile implements MultipartFile {

    private final byte[] imgContent;

    private String originalName;

    public BASE64DecodedMultipartFile(byte[] imgContent ,String name) {
        this.imgContent = imgContent;
        this.originalName = name;
    }

    @Override
    public String getName() {
        return IdGenerator.generateImageId() + getContentType();
    }

    @Override
    public String getOriginalFilename() {
        return originalName;
    }

    @Override
    public String getContentType() {
        return originalName.substring(originalName.lastIndexOf("."));
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }
}