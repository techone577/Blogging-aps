package com.blogging.aps.web.api;

import com.blogging.aps.business.PostBusiness;
import com.blogging.aps.business.PostCatchBusiness;
import com.blogging.aps.business.manage.AbstractPostListQueryBusiness;
import com.blogging.aps.model.dto.PostCatchReqDTO;
import com.blogging.aps.model.dto.PostPagingQueryDTO;
import com.blogging.aps.model.dto.PostQueryReqDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.support.annotation.Json;
import com.blogging.aps.support.annotation.PostStatistic;
import com.blogging.aps.support.annotation.ServiceInfo;
import com.blogging.aps.support.strategy.FactoryList;
import com.blogging.aps.support.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author techoneduan
 * @date 2019/1/18
 */
@RequestMapping("/post")
@RestController
public class PostController {

    private static final Logger LOG = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostBusiness postBusiness;

    @Autowired
    private PostCatchBusiness postCatchBusiness;

    @Autowired
    private FactoryList<AbstractPostListQueryBusiness, String> postListQueryBusiness;

    /**
     * 文章查询
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.query", description = "文章信息查询")
    @PostStatistic
    public Response postQuery (@Json PostQueryReqDTO reqDTO) {
        LOG.info("文章查询入参:{}",JsonUtil.toString(reqDTO));
        Response resp = postBusiness.queryBlog(reqDTO);
        LOG.info("文章查询出参:{}",JsonUtil.toString(resp));
        return resp;
    }


    /**
     * 文章列表查询(分页)
     */
    @RequestMapping(value = "/postListPagingQuery", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.postListPagingQuery", description = "文章列表查询")
    public Response postListPaging (@Json PostPagingQueryDTO queryDTO) {
        LOG.info("文章列表分页查询入参:{}",JsonUtil.toString(queryDTO));
        Response resp = postListQueryBusiness.getBean(queryDTO.getType()).queryPostList(queryDTO);
        LOG.info("文章列表分页查询出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * html模板查询
     */
    @RequestMapping(value = "/queryHtml", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.queryHtml", description = "文章html查询")
    public Response postHtmlQuery (@Json PostQueryReqDTO htmlQueryReqDTO) {
        return null;
    }

    /**
     * md模板查询
     */
    @RequestMapping(value = "/queryMD", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.queryMD", description = "文章md查询查询")
    public Response postMDQuery(@Json PostQueryReqDTO mdQueryReqDTO) {
        LOG.info("文章md查询入参:{}", JsonUtil.toString(mdQueryReqDTO));
        Response resp = postBusiness.queryMDPost(mdQueryReqDTO);
        LOG.info("文章md查询出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 文章更新
     */
    @RequestMapping(value = "/postUpdate", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.postUpdate", description = "文章更新")
    public Response postUpdate () {
        return null;
    }

    /**
     * 文章拉取
     */
    @RequestMapping(value = "/postCatch", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.postCatch", description = "catch test")
    public Response postCatch (@Json PostCatchReqDTO reqDTO) {
        LOG.info("文章拉取入参:{}", JsonUtil.toString(reqDTO));
        Response response = postCatchBusiness.postCatch(reqDTO);
        LOG.info("文章拉取出参:{}", JsonUtil.toString(response));
        return response;
    }

    /**
     * 所有tag展示
     */
    @RequestMapping(value = "/tagshow", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.tagShow", description = "展示所有tag")
    public Response tagShow() {
        LOG.info("查询所有tag!");
        Response resp = postBusiness.queryAllTags();
        LOG.info("查询所有tag出参:{}", JsonUtil.toString(resp));
        return resp;
    }
}
