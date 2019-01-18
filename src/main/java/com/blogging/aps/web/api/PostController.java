package com.blogging.aps.web.api;

import com.blogging.aps.business.PostBusiness;
import com.blogging.aps.business.PostCatchBusiness;
import com.blogging.aps.model.dto.PostCatchReqDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.support.annotation.Json;
import com.blogging.aps.support.annotation.ServiceInfo;
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

    /**
     * 文章查询
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.query", description = "文章信息查询")
    public Response postQuery () {
        return null;
    }

    /**
     * 文章列表查询
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.queryList", description = "文章列表查询")
    public Response postListQuery () {
        return null;
    }

    /**
     * html模板查询
     */
    @RequestMapping(value = "/queryHtml", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.queryHtml", description = "文章html查询")
    public Response postHtmlQuery () {
        return null;
    }

    /**
     * md模板查询
     */
    @RequestMapping(value = "/queryMD", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.queryMD", description = "文章md查询查询")
    public Response postMDQuery () {
        return null;
    }

    /**
     * 文章新增
     */
    @RequestMapping(value = "/postAdd", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.postAdd", description = "文章新增")
    public Response postAdd () {
        return null;
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
    @RequestMapping(value = "/poatCatch", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.PostController.postUpdate", description = "github pages文章抓取")
    public Response postCatch (@Json PostCatchReqDTO reqDTO) {
        LOG.info("文章拉取入参:{}", JsonUtil.toString(reqDTO));
        Response response = postCatchBusiness.postCatch(reqDTO);
        LOG.info("文章拉取出参:{}", JsonUtil.toString(response));
        return response;
    }
}
