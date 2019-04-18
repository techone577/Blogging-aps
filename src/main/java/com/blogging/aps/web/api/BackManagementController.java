package com.blogging.aps.web.api;

import com.blogging.aps.business.BMBusiness;
import com.blogging.aps.business.PostBusiness;
import com.blogging.aps.business.manage.AbstractPostListQueryBusiness;
import com.blogging.aps.model.dto.*;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.support.annotation.Json;
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
 * @date 2019/4/13
 */
@RequestMapping("/BM")
@RestController
public class BackManagementController {

    private static final Logger LOG = LoggerFactory.getLogger(BackManagementController.class);

    @Autowired
    private PostBusiness postBusiness;

    @Autowired
    private BMBusiness bmBusiness;

    @Autowired
    private FactoryList<AbstractPostListQueryBusiness, String> postListQueryBusiness;

    /**
     *
     *通过查询参数查询tag
     */
    @RequestMapping(value = "/queryTags", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryTags",description = "查询tag")
    public Response queryTagByParam(@Json TagQueryDTO dto) {
        LOG.info("(BM)tag查询入参:{}", JsonUtil.toString(dto));
        Response resp = bmBusiness.queryTagByParam(dto);
        LOG.info("(BM)查询tag出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 查询tagList 下拉框使用
     */
    @RequestMapping(value = "/queryTagList", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryTagList",description = "查询tag下拉框")
    public Response queryTagList() {
        LOG.info("(BM)tagList查询入参:{}", JsonUtil.toString(null));
        Response resp = bmBusiness.queryTagList();
        LOG.info("(BM)查询tag出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 查询文章列表
     *
     */
    @RequestMapping(value = "/queryPosts", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryPosts", description = "文章列表查询")
    public Response queryPosts (@Json BMPostListQueryDTO queryDTO) {
        LOG.info("（BM）文章列表分页查询入参:{}",JsonUtil.toString(queryDTO));
        Response resp = bmBusiness.queryPostList(queryDTO);
        LOG.info("（BM）文章列表分页查询出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     *
     * 查询草稿箱
     *
     */
    @RequestMapping(value = "/queryDrafts", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryDrafts", description = "草稿列表查询")
    public Response queryDrafts (@Json BMDraftsQueryDTO queryDTO) {
        LOG.info("（BM）草稿列表分页查询入参:{}",JsonUtil.toString(queryDTO));
        Response resp = bmBusiness.queryDrafts(queryDTO);
        LOG.info("（BM）草稿列表分页查询出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     *
     * 查询回收站
     */
    @RequestMapping(value = "/queryRubbishes", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryRubbishes", description = "回收站文章列表查询")
    public Response queryRubbishes (@Json BMRubbishQueryDTO reqDTO) {
        LOG.info("（BM）回收站列表分页查询入参:{}",JsonUtil.toString(reqDTO));
        Response resp = bmBusiness.queryRubbish(reqDTO);
        LOG.info("（BM）回收站列表分页查询出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 文章查询
     */
    @RequestMapping(value = "/queryBlog", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryBlog", description = "查询博客")
    public Response queryBlog(@Json PostQueryReqDTO reqDTO) {
        LOG.info("（BM）博客查询入参:{}", JsonUtil.toString(reqDTO));
        Response resp = bmBusiness.queryBlog(reqDTO);
        LOG.info("（BM）博客查询出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 文章发布
     */
    @RequestMapping(value = "/postRelease", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.postRelease", description = "文章发布")
    public Response releasePost(@Json BMPostModifyReqDTO reqDTO) {
        LOG.info("（BM）发布文章入参:{}", JsonUtil.toString(reqDTO));
        Response resp = bmBusiness.releasePost(reqDTO);
        LOG.info("（BM）发布文章出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 文章下线
     */
    @RequestMapping(value = "/postOffline", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.postOffline", description = "文章下线")
    public Response offlinePost(@Json BMPostModifyReqDTO reqDTO) {
        LOG.info("（BM）下线文章入参:{}", JsonUtil.toString(reqDTO));
        Response resp = bmBusiness.offlinePost(reqDTO);
        LOG.info("（BM）下线文章出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 文章移至回收站
     */
    @RequestMapping(value = "/postRemove", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.postRemove", description = "文章移动至回收站")
    public Response removePost(@Json BMPostModifyReqDTO reqDTO) {
        LOG.info("（BM）移动文章入参:{}", JsonUtil.toString(reqDTO));
        Response resp = bmBusiness.releasePost(reqDTO);
        LOG.info("（BM）移动文章出参:{}", JsonUtil.toString(resp));
        return resp;
    }
}
