package com.blogging.aps.web.api;

import com.blogging.aps.business.BMBusiness;
import com.blogging.aps.business.PostBusiness;
import com.blogging.aps.business.manage.AbstractPostListQueryBusiness;
import com.blogging.aps.model.dto.PostPagingQueryDTO;
import com.blogging.aps.model.dto.PostQueryReqDTO;
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
     * 查询所有tag（供后管使用）
     */
    @RequestMapping(value = "/queryTags", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryTags", description = "查询所有tag")
    public Response queryTags() {
        LOG.info("查询所有tag!");
        Response resp = bmBusiness.queryTags();
        LOG.info("查询所有tag出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 查询文章列表
     *
     */
    @RequestMapping(value = "/queryPosts", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryPosts", description = "文章列表查询")
    public Response queryPosts (@Json PostPagingQueryDTO queryDTO) {
        LOG.info("（后管）文章列表分页查询入参:{}",JsonUtil.toString(queryDTO));
        Response resp = postListQueryBusiness.getBean("all").queryPostList(queryDTO);
        LOG.info("（后管）文章列表分页查询出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 文章模板查询
     */
    @RequestMapping(value = "/queryBlog", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryBlog", description = "查询博客")
    public Response queryBlog(@Json PostQueryReqDTO reqDTO) {
        LOG.info("（后管）博客查询入参:{}", JsonUtil.toString(reqDTO));
        Response resp = bmBusiness.queryBlog(reqDTO);
        LOG.info("（后管）博客查询出参:{}", JsonUtil.toString(resp));
        return resp;
    }

}
