package com.blogging.aps.config.surpport;

import org.apache.tomcat.util.codec.binary.Base64;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Base64解码包装HttpServletRequest
 *
 * @author xiehui
 */
public class Base64HttpServletRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String> parameters = new HashMap<String, String>();
    private byte[] bytes;
    private ObjectMapper objectMapper = ObjectMapperFactoryBean.getObjectMapper();
    private String encoding = "UTF-8";

    public String getEncoding () {
        return encoding;
    }

    public void setEncoding (String encoding) {
        this.encoding = encoding;
    }

    public Base64HttpServletRequestWrapper (HttpServletRequest request) {
        super(request);
        try {
            parseParameters();
        } catch (IOException e) {
            throw new RuntimeException("IOException", e);
        }
    }

    protected void parseParameters () throws IOException {
        String method = this.getHttpServletRequest().getMethod();
        StringBuilder jsonstr = new StringBuilder();
        String str = null;
        if (method.equalsIgnoreCase("GET") || method.equalsIgnoreCase("DELETE")) {
            jsonstr.append(this.getQueryString());
            str = jsonstr.toString();
        } else {
            String line;
            BufferedReader reader = getReader();
            while ((line = reader.readLine()) != null) {
                jsonstr.append(line);
            }
            str = jsonstr.toString();
        }
        if (jsonstr.length() > 0) {
            JsonNode node = objectMapper.readTree(str);
            Iterator<String> fieldNames = node.getFieldNames();
            for (; fieldNames.hasNext(); ) {
                String key = fieldNames.next();
                String value = node.get(key).toString();
                if (value.length() > 2 && value.startsWith("\"")) {
                    parameters.put(key, value.substring(1, value.length() - 1));
                } else {
                    parameters.put(key, value);
                }
            }
        }
    }

    private HttpServletRequest getHttpServletRequest () {
        return (HttpServletRequest) super.getRequest();
    }

    public String getParameter (String name) {
        return (String) parameters.get(name);
    }

    public String[] getParameterValues (String name) {
        String value = getParameter(name);
        if (value != null) {
            return new String[]{value};
        }
        return null;
    }

    public String getQueryString () {

        try {
            String queryString = super.getQueryString();
            if (queryString != null) {
                if (Base64.isBase64(queryString)) {
                    return new String(Base64.decodeBase64(queryString.getBytes(encoding)), encoding);
                } else {
                    return queryString;
                }
            }
            return null;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
    }

    public BufferedReader getReader () throws IOException {
        if (null == bytes)
            cacheBytes();
        InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(bytes), encoding);
        return new BufferedReader(isr);
    }

    public ServletInputStream getInputStream() throws IOException{
        if (null == bytes)
            return getHttpServletRequest().getInputStream();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Base64FilterServletInputStream bfis = new Base64FilterServletInputStream(bis);
        return bfis;
    }

    private void cacheBytes () throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = this.getHttpServletRequest().getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        byte[] originBytes = buffer.toString().getBytes(encoding);
        if (Base64.isBase64(originBytes)) {
            bytes = Base64.decodeBase64(originBytes);
        } else {
            bytes = originBytes;
        }
    }
    class Base64FilterServletInputStream extends ServletInputStream {
        private InputStream inputStream;

        public Base64FilterServletInputStream(InputStream inputStream){
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
