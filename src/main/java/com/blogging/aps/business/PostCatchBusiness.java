package com.blogging.aps.business;

import com.blogging.aps.model.dto.PostCatchReqDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.entity.post.PassageEntity;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import com.blogging.aps.model.entity.post.TagEntity;
import com.blogging.aps.model.entity.post.TagRelationEntity;
import com.blogging.aps.service.post.TagService;
import com.blogging.aps.service.post.PostService;
import com.blogging.aps.support.utils.IdGenerator;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author techoneduan
 * @date 2019/1/18
 */

@Component
public class PostCatchBusiness {

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    public Response postCatch(PostCatchReqDTO reqDTO) {
        String url = reqDTO.getUrl() + "/archives";
        String html = htmlCatch(url);
        if (null == html) {
            return ResponseBuilder.build(false, "github？？？");
        }
        decodeHtml(reqDTO.getUrl(), html);

        return ResponseBuilder.build(true, "so sad");
    }

    public void decodeHtml(String domain, String html) {
        Document doc = Jsoup.parse(html);
        Element element = doc.getElementById("posts");
        Elements postList = element.getElementsByClass("post");
        Iterator<Element> pi = postList.iterator();
        while (pi.hasNext()) {
            Elements el = pi.next().getElementsByClass("post-title-link");
            String href = el.last().attr("href");
            String url = (domain + href).replaceAll(" ", "%20").replaceAll("\\[", "%5B").replaceAll("\\]", "%5D");
            String info[] = href.split("\\/");
            String title = info[info.length - 1];
            String date = "";
            for (int i = 0; i < info.length - 2; ++i) {
                date += ("-" + info[i]);
            }
            decodePost(htmlCatch(url), title);
        }

    }

    private String decodePost(String html, String title) {
        Document doc = Jsoup.parse(html);
        Element element = doc.getElementsByClass("post-body").last();
        String post = HTML2Md.convert(element.toString());
        PostInfoEntity postInfoEntity = new PostInfoEntity() {
            {
                setPassageId(IdGenerator.generatePassageId());
                setPostId(IdGenerator.generatePostId());
                setTitle(title);
                setUpdateTime(new Date());
                setAddTime(new Date());
                setDelFlag(0);
                setMemberId("root");
                setCategory("分类");
            }
        };
        PassageEntity passageEntity = new PassageEntity() {
            {
                setContent(post);
                setPassageId(postInfoEntity.getPassageId());
                setUpdateTime(new Date());
                setAddTime(new Date());
                setDelFlag(0);
            }
        };
        postService.insertPost(postInfoEntity);
        postService.insertPassgae(passageEntity);
        List<TagEntity> tagList = tagService.queryTagByName("leetcode");
        TagRelationEntity tagRelationEntity= new TagRelationEntity() {
            {
                setPostId(postInfoEntity.getPostId());
                setTagId(tagList.get(0).getId());
                setUpdateTime(new Date());
                setAddTime(new Date());
                setDelFlag(0);
            }
        };
        tagService.insertTagRelation(tagRelationEntity);
        return post;
    }

    private String htmlCatch(String url) {
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
