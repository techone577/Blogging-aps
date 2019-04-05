package com.blogging.aps.support.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * @author techoneduan
 * @date 2018/12/25
 */
public class Base64Util {

    private static final Logger LOG = LoggerFactory.getLogger(Base64Util.class);

    private static final String charset = "utf-8";

    public static String encode (String str) {
        String encodeStr = null;
        try {
            encodeStr = Base64Utils.encodeToString(str.getBytes(charset));
        } catch (Exception e) {
            LOG.info("Base64编码异常:{}", e);
        }
        return encodeStr;
    }

    public static String decode (String str) {
        String decodeStr = null;
        try {
            decodeStr = new String(Base64Utils.decodeFromString(str), charset);
        } catch (Exception e) {
            LOG.info("Base64解码异常:{}", e);
        }
        return decodeStr;
    }

    public static String encodeToUrlSafeString (String str) {
        String encodeStr = null;
        try {
            encodeStr = Base64Utils.encodeToUrlSafeString(str.getBytes(charset));
        } catch (Exception e) {
            LOG.info("Base64 url-safe编码异常:{}", e);
        }
        return encodeStr;
    }

    public static String decodeFromUrlSafeString (String str) {
        String decodeStr = null;
        try {
            decodeStr = new String(Base64Utils.decodeFromUrlSafeString(str), charset);
        } catch (Exception e) {
            LOG.info("Base64 url-safe解码异常:{}", e);
        }
        return decodeStr;
    }


    public static MultipartFile base64ToMultipart(String base64, String originalName) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, originalName);
        } catch (IOException e) {
            LOG.info("base64转 multipartfile 失败:{}", e);
        }
        return null;
    }
}
