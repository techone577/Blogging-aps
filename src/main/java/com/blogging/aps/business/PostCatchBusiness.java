package com.blogging.aps.business;

import com.blogging.aps.model.dto.PostCatchReqDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.support.utils.ResponseBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Iterator;

/**
 * @author techoneduan
 * @date 2019/1/18
 */

@Component
public class PostCatchBusiness {
    public Response postCatch (PostCatchReqDTO reqDTO) {
        String url = reqDTO.getUrl() + "/archives";
        String html = htmlCatch(url);
        decodeHtml(reqDTO.getUrl(), html);

        return ResponseBuilder.build(true, "so sad");
    }

    public void decodeHtml (String domain, String html) {
        Document doc = Jsoup.parse(html);
        Element element = doc.getElementById("posts");
        Elements postList = element.getElementsByClass("post");
        Iterator<Element> pi = postList.iterator();
        while (pi.hasNext()) {
            Elements el = pi.next().getElementsByClass("post-title-link");
            String href = el.last().attr("href");
            String url = (domain + href).replaceAll(" ","%20").replaceAll("\\[","%5B").replaceAll("\\]","%5D");
            decodePost(htmlCatch(url));
            System.out.println(href);
        }

    }

    private void decodePost (String html) {
        Document doc = Jsoup.parse(html);
        Element element = doc.getElementsByClass("post-body").last();
        String post = HTML2Md.convert(element.toString());
        //i am done with this shit
        System.out.println(post);
    }

    private String htmlCatch (String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String html = null;
        try {
            httpClient = HttpClients.createDefault();
            // 创建httpget.
            HttpGet httpGet = new HttpGet(url);
            // 执行get请求.
            response = httpClient.execute(httpGet);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                httpClient.close();
                response.close();
            } catch (Exception e) {

            }
        }
        return html;
    }
}
