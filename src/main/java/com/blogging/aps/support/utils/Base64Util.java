package com.blogging.aps.support.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

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
}
