package com.blogging.aps.web.api;

import com.blogging.aps.business.BMBusiness;
import com.blogging.aps.business.PostBusiness;
import com.blogging.aps.business.manage.AbstractPostListQueryBusiness;
import com.blogging.aps.model.dto.*;
import com.blogging.aps.model.dto.BM.BMCategoryModifyDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.dto.BMPostAddDTO;
import com.blogging.aps.support.annotation.Json;
import com.blogging.aps.support.annotation.ServiceInfo;
import com.blogging.aps.support.strategy.FactoryList;
import com.blogging.aps.support.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
     * 通过查询参数查询tag
     */
    @RequestMapping(value = "/queryTags", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryTags", description = "查询tag")
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
    @ServiceInfo(name = "Blogging.APS.BMController.queryTagList", description = "查询tag下拉框")
    public Response queryTagList() {
        LOG.info("(BM)tagList查询入参:{}", JsonUtil.toString(null));
        Response resp = bmBusiness.queryTagList();
        LOG.info("(BM)查询tag出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 查询文章列表
     */
    @RequestMapping(value = "/queryPosts", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryPosts", description = "文章列表查询")
    public Response queryPosts(@Json BMPostListQueryDTO queryDTO) {
        LOG.info("（BM）文章列表分页查询入参:{}", JsonUtil.toString(queryDTO));
        Response resp = bmBusiness.queryPostList(queryDTO);
        LOG.info("（BM）文章列表分页查询出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 查询草稿箱
     */
    @RequestMapping(value = "/queryDrafts", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryDrafts", description = "草稿列表查询")
    public Response queryDrafts(@Json BMDraftsQueryDTO queryDTO) {
        LOG.info("（BM）草稿列表分页查询入参:{}", JsonUtil.toString(queryDTO));
        Response resp = bmBusiness.queryDrafts(queryDTO);
        LOG.info("（BM）草稿列表分页查询出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 查询回收站
     */
    @RequestMapping(value = "/queryRubbishes", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryRubbishes", description = "回收站文章列表查询")
    public Response queryRubbishes(@Json BMRubbishQueryDTO reqDTO) {
        LOG.info("（BM）回收站列表分页查询入参:{}", JsonUtil.toString(reqDTO));
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
        Response resp = bmBusiness.removePost(reqDTO);
        LOG.info("（BM）移动文章出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 文章从回收站恢复
     */
    @RequestMapping(value = "/postRecover", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.postRecover", description = "文章从回收站恢复")
    public Response recoverPost(@Json BMPostModifyReqDTO reqDTO) {
        LOG.info("（BM）恢复文章入参:{}", JsonUtil.toString(reqDTO));
        Response resp = bmBusiness.recoverPost(reqDTO);
        LOG.info("（BM）恢复文章出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 编辑tag名称
     */
    @RequestMapping(value = "/editTag", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.editTag", description = "编辑标签名称")
    public Response editTag(@Json BMTagEditReqDTO reqDTO) {
        LOG.info("（BM）修改标签名称入参:{}", JsonUtil.toString(reqDTO));
        Response resp = bmBusiness.editTagName(reqDTO);
        LOG.info("（BM）修改标签名称出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 文章添加tag
     */
    @RequestMapping(value = "/addTag", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.addTag", description = "添加新标签")
    public Response addTag(@Json BMTagModifyReqDTO reqDTO) {
        LOG.info("（BM）添加标签入参:{}", JsonUtil.toString(reqDTO));
        Response resp = bmBusiness.addTag(reqDTO);
        LOG.info("（BM）添加标签出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 文章删除tag
     */
    @RequestMapping(value = "/delTagForPost", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.delTagForPost", description = "删除文章标签")
    public Response delTagForPost(@Json BMTagModifyReqDTO reqDTO) {
        LOG.info("（BM）删除文章标签入参:{}", JsonUtil.toString(reqDTO));
        Response resp = bmBusiness.delTagForPost(reqDTO);
        LOG.info("（BM）删除文章标签出参:{}", JsonUtil.toString(resp));
        return resp;
    }

    /**
     * 文章新增
     */
    @RequestMapping(value = "/postAdd", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.postAdd", description = "文章新增")
    public Response postAdd(@Json BMPostAddDTO entity) {
        LOG.info("（BM）新增文章入参：{}", JsonUtil.toString(entity));
        Response response = postBusiness.addPost(entity);
        LOG.info("（BM）新增文章出参：{}", JsonUtil.toString(response));
        return response;
    }

    /**
     * 文章更新
     */
    @RequestMapping(value = "/postUpdate", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.postUpdate", description = "文章更新")
    public Response postUpdate(@Json BMPostUpdateDTO updateDTO) {
        LOG.info("（BM）更新文章入参：{}", JsonUtil.toString(updateDTO));
        Response response = bmBusiness.postUpdate(updateDTO);
        LOG.info("（BM）更新文章出参：{}", JsonUtil.toString(response));
        return response;
    }

    /**
     * 文章删除
     */
    @RequestMapping(value = "/postDelete", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.postDelete", description = "文章删除")
    public Response postDelete(@Json BMPostModifyReqDTO reqDTO) {
        LOG.info("（BM）删除文章入参：{}", JsonUtil.toString(reqDTO));
        Response response = bmBusiness.postDelete(reqDTO);
        LOG.info("（BM）删除文章出参：{}", JsonUtil.toString(response));
        return response;
    }

    /**
     * 删除标签
     */
    @RequestMapping(value = "/tagDelete", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.tagDelete", description = "标签删除")
    public Response tagDelete(@Json BMTagDelReqDTO reqDTO) {
        LOG.info("（BM）删除标签入参：{}", JsonUtil.toString(reqDTO));
        Response response = bmBusiness.deleteTag(reqDTO);
        LOG.info("（BM）删除标签出参：{}", JsonUtil.toString(response));
        return response;
    }

    /**
     * 添加分类
     */
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.addCategory", description = "添加分类")
    public Response addCategory(@Json BMCategoryAddDTO addDTO) {
        LOG.info("（BM）添加分类入参：{}", JsonUtil.toString(addDTO));
        Response response = bmBusiness.addCategory(addDTO);
        LOG.info("（BM）添加分类出参：{}", JsonUtil.toString(response));
        return response;
    }

    /**
     * 查询所有分类
     */
    @RequestMapping(value = "/queryCategory", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryCategory", description = "查询分类")
    public Response queryCategory() {
        LOG.info("（BM）查询分类");
        Response response = bmBusiness.queryCategory();
        LOG.info("（BM）查询分类出参：{}", JsonUtil.toString(response));
        return response;
    }

    /**
     * 修改分类信息
     */
    @RequestMapping(value = "/modifyCategory", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.modifyCategory", description = "修改分类信息")
    public Response modifyCategory(@Json BMCategoryModifyDTO dto, HttpServletRequest request) {
        LOG.info("（BM）修改分类信息入参：{}", JsonUtil.toString(dto));
        Response response = bmBusiness.modifyCategory(dto, request);
        LOG.info("（BM）修改分类信息出参：{}", JsonUtil.toString(response));
        return response;
    }

    /**
     * 查询分类下拉信息
     */
    @RequestMapping(value = "/queryCategoriesSelect", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.queryCategoryNames", description = "查询分类下拉信息")
    public Response queryCategoriesSelect() {
        LOG.info("（BM）查询分类下拉信息!");
        Response response = bmBusiness.queryCategoriesSelect();
        LOG.info("（BM）查询分类下拉信息出参：{}", JsonUtil.toString(response));
        return response;
    }

    /**
     * 删除分类信息
     */
    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
    @ServiceInfo(name = "Blogging.APS.BMController.deleteCategory", description = "删除分类")
    public Response deleteCategory(@Json BMCategoryModifyDTO modifyDTO) {
        LOG.info("（BM）删除分类入参：{}", JsonUtil.toString(modifyDTO));
        Response response = bmBusiness.deleteCategory(modifyDTO);
        LOG.info("（BM）删除分类出参：{}", JsonUtil.toString(response));
        return response;
    }

}
