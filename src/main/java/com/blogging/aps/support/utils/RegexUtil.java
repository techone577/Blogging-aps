package com.blogging.aps.support.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author techoneduan
 * @date 2019/4/24
 */
public class RegexUtil {

    public static String matchFirstImgUrlInMD(String content) {

        if (StringUtils.isBlank(content))
            return "";
        String pattern = "!\\[(.*?)\\]\\((.*?)\\)";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        while (m.find()) {
            String fisrtUrl = m.group(2);
            if (fisrtUrl.startsWith("http:"))
                return fisrtUrl;
            else
                continue;
        }
        return "";
    }
}

