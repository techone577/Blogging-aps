/*
 * Copyright (C) 2006-2012 Tuniu.com. All rights reserved
 * Author:jiyuliang
 * Date:2013-10-15
 */
package com.blogging.aps.config.surpport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * @author techoneduan
 * @date 2018/12/13
 */
public class FrameWorkFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(FrameWorkFilter.class);

    //保存本线程sequenceId，标识调用
    private static ThreadLocal<String> sequenceId = new ThreadLocal<String>() {
        public String initialValue () {
            return null;
        }
    };

    //保存
    private static ThreadLocal<String> srcServiceName = new ThreadLocal<String>() {
        public String initialValue () {
            return null;
        }
    };

    private PathMatcher matcher = new AntPathMatcher();

    public static String getSrcServiceName () {
        return srcServiceName.get();
    }


    public static void setSrcServiceName (String srcSName) {
        srcServiceName.set(srcSName);
    }

    private static String applicationName = null;

    //filter忽略列表
    private List<String> noDecodeList = new ArrayList<String>();

    /**
     * Default constructor.
     */
    public FrameWorkFilter () {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy () {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @SuppressWarnings("unchecked")
    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        // place your code here
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (ifContainsInNoDecodeList(req.getServletPath())) {
            chain.doFilter(req, resp);
            return;
        }

        try {
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Content-Type", "application/json;charset=UTF-8");

            MapIHttpServletRequestWrapper mapIHttpServletRequestWrapper = new MapIHttpServletRequestWrapper(req);

            // pass the request along the filter chain
            chain.doFilter(mapIHttpServletRequestWrapper, resp);
        } catch (SocketException ex) {
            log.warn("[FrameWorkFilter] {}", ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {

        }
    }


    /**
     * @see Filter#init(FilterConfig)
     */
    public void init (FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
        applicationName = fConfig.getInitParameter("applicationName");

        //获取filter忽略列表
        String noDecodeStr = fConfig.getInitParameter("exclude");
        if (null != noDecodeStr) {
            StringTokenizer st = new StringTokenizer(noDecodeStr, ",");
            noDecodeList.clear();
            while (st.hasMoreTokens()) {
                noDecodeList.add(st.nextToken());
            }
        }
    }

    /**
     * 判断一个url是否在排除列表内
     *
     * @param servletPath
     * @return true 在排除列表中； false 不在排除列表中
     */
    private boolean ifContainsInNoDecodeList (String servletPath) {
        boolean ret = false;
        if (noDecodeList.contains(servletPath)) {
            ret = true;
        } else if (!noDecodeList.isEmpty()) {
            for (String filtermapping : noDecodeList) {
                if (matcher.match(filtermapping, servletPath)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }
}
