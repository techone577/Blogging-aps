package com.blogging.aps.config.surpport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

public class MapIHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger logger = LoggerFactory.getLogger(MapIHttpServletRequestWrapper.class);
 
    private Map<String, String[]> parameterMap; // get方法
    private byte[] requestBody = null;          // post 方法body数据
 
    public MapIHttpServletRequestWrapper (HttpServletRequest request) {
        super(request);
        //缓存请求body
        parameterMap = request.getParameterMap();
        requestBody = getRequestBody(request).getBytes(Charset.forName("UTF-8"));
        System.out.println(Arrays.toString(requestBody));
    }


    public String getRequestBody(HttpServletRequest request){
        InputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    /**
     * 获取所有参数名, get相关方法重写
     *
     * @return 返回所有参数名
     */
    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<>(parameterMap.keySet());
        return vector.elements();
    }
 
    /**
     * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
     *
     * @param name
     *            指定参数名
     * @return 指定参数名的值
     */
    @Override
    public String getParameter(String name) {
        String[] results = parameterMap.get(name);
        if (results == null || results.length <= 0)
            return null;
        else {
            System.out.println("modify before：" + results[0]);
            return modify(results[0]);
        }
    }
 
    /**
     * 获取指定参数名的所有值的数组，如：checkbox的所有数据
     * 接收数组变量 ，如checkobx类型
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] results = parameterMap.get(name);
        if (results == null || results.length <= 0)
            return null;
        else {
            int length = results.length;
            for (int i = 0; i < length; i++) {
                results[i] = modify(results[i]);
                logger.info("modify before,{}:{}",name, results[i]);
            }
            return results;
        }
    }
 
    /**
     * 自定义的一个简单修改原参数的方法，即：给原来的参数值前面添加了一个修改标志的字符串
     *
     * @param string
     *            原参数值
     * @return 修改之后的值 ,这里并不进行改变
     */
    private String modify(String string) {
        return string;
    }
 
 
    /**
     * 重写 getReader()
     */
    @Override
    public BufferedReader getReader() throws IOException {
        InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(requestBody), "UTF-8");
        return new BufferedReader(isr);
    }
 
    /**
     * 重写 getInputStream()
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        logger.info("modify before post");
        if(requestBody == null){
            requestBody= new byte[0];
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        MyServletInputStream myServletInputStream = new MyServletInputStream(bais);
        return myServletInputStream;
    }
 
    // 定义自己的ServletInputStream
    class MyServletInputStream extends ServletInputStream {
        private InputStream inputStream;
 
        public MyServletInputStream(InputStream inputStream){
            super();
            this.inputStream = inputStream;
        }
 
        @Override
        public boolean isFinished() {
            return false;
        }
 
        @Override
        public boolean isReady() {
            return false;
        }
 
        @Override
        public void setReadListener(ReadListener readListener) {
            return;
        }
 
        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }
}
