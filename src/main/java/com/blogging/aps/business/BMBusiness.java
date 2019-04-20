package com.blogging.aps.business;


import com.blogging.aps.business.manage.AbstractPostListQueryBusiness;
import com.blogging.aps.model.dto.*;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.entity.post.PassageEntity;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import com.blogging.aps.model.entity.post.TagEntity;
import com.blogging.aps.model.entity.post.TagRelationEntity;
import com.blogging.aps.model.enums.ErrorCodeEnum;
import com.blogging.aps.service.post.TagService;
import com.blogging.aps.service.post.PostService;
import com.blogging.aps.support.exception.UnifiedException;
import com.blogging.aps.support.strategy.FactoryList;
import com.blogging.aps.support.utils.DateUtils;
import com.blogging.aps.support.utils.ResponseBuilder;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 后台管理服务
 *
 * @author techoneduan
 * @date 2019/4/13
 */

@Component
public class BMBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(BMBusiness.class);

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostBusiness postBusiness;

    @Autowired
    private FactoryList<AbstractPostListQueryBusiness, String> postListQueryBusiness;


    /**
     * 查询所有tag
     */
    private Response queryTags() {
        List<TagAmountDTO> tagAmountDTOS = tagService.queryTagAmount();
        if (null == tagAmountDTOS || tagAmountDTOS.size() == 0) {
            return ResponseBuilder.build(true, "标签列表为空");
        }
        tagAmountDTOS = tagAmountDTOS.stream().sorted(Comparator.comparing(TagAmountDTO::getTagId)).collect(Collectors.toList());
        List<TagEntity> tagEntities = tagService.queryByTagIdList(tagAmountDTOS.stream().map(item -> item.getTagId()).collect(Collectors.toList()));
        List<BMTagQueryRespDTO> tagQueryRespDTOS = buildTagRespDTO(tagEntities, tagAmountDTOS);
        return ResponseBuilder.build(true, tagQueryRespDTOS);
    }

    /**
     * 按照参数查询tag
     * @param dto
     * @return
     */
    public Response queryTagByParam(TagQueryDTO dto){
        if(null == dto || StringUtils.isBlank(dto.getName()))
            return queryTags();
        List<TagAmountDTO> tagAmountDTOS = tagService.queryTagAmount();
        List<TagEntity> tagList = tagService.queryTagByName(dto.getName());
        if(null == tagList || tagList.size() == 0)
            return ResponseBuilder.build(true,null);
        TagEntity tagEntity = tagList.get(0);
        Optional<TagAmountDTO> optional = tagAmountDTOS.stream().filter(i->i.getTagId() == tagList.get(0).getId()).findAny();
        BMTagQueryRespDTO respDTO = new BMTagQueryRespDTO();
        if(optional.isPresent())
            respDTO.setPostNum(optional.get().getAmount());
        respDTO.setTagId(tagEntity.getId());
        respDTO.setTagName(tagEntity.getTagName());
        respDTO.setUpdateTime(DateUtils.formatDateTime(tagEntity.getUpdateTime()));
        respDTO.setAddTime(DateUtils.formatDateTime(tagEntity.getAddTime()));
        List<BMTagQueryRespDTO> respDTOS = new ArrayList<BMTagQueryRespDTO>(){{
            add(respDTO);
        }};
        return ResponseBuilder.build(true,respDTOS);
    }

    public Response queryTagList(){
        List<TagEntity> list = tagService.queryTagList();
        if(null == list || list.size() == 0)
            return ResponseBuilder.build(true,null);
        List<String> namList = list.stream().map(item-> item.getTagName()).collect(Collectors.toList());
        Map<String,Object> map = new HashMap<>();
        map.put("tag",namList);
        return ResponseBuilder.build(true,map);
    }
    private List<BMTagQueryRespDTO> buildTagRespDTO(List<TagEntity> tagEntities, List<TagAmountDTO> tagAmountDTOS) {
        List<BMTagQueryRespDTO> respDTOS = new ArrayList<>();
        for (int i = 0; i < tagEntities.size(); ++i) {
            BMTagQueryRespDTO dto = new BMTagQueryRespDTO();
            TagEntity entity = tagEntities.get(i);
            dto.setTagId(entity.getId());
            dto.setTagName(entity.getTagName());
            dto.setAddTime(DateUtils.formatDateTime(entity.getAddTime()));
            dto.setUpdateTime(DateUtils.formatDateTime(entity.getUpdateTime()));
            dto.setPostNum(tagAmountDTOS.get(i).getAmount());
            respDTOS.add(dto);
        }
        return respDTOS;
    }


    /**
     * 查询blog 编辑器使用
     * @param reqDTO
     * @return
     */
    public Response queryBlog(PostQueryReqDTO reqDTO){
        String postId = reqDTO.getPostId();
        PostInfoEntity postInfoEntity = postService.queryPostByPostId(postId);
        if (null == postInfoEntity)
            return ResponseBuilder.build(true,null);

        PassageEntity passageEntity = postService.queryPassageByPassageId(postInfoEntity.getPassageId());
        List<String> tags = postBusiness.getPostTags(postId);
        BMBlogQueryRespDTO respDTO = new BMBlogQueryRespDTO(){
            {
                setTitle(postInfoEntity.getTitle());
                setContent(passageEntity.getContent());
                setSummary(postInfoEntity.getSummary());
                setTagList(tags);
            }
        };
        return ResponseBuilder.build(true,respDTO);
    }

    /**
     * 查询文章列表（包括未发布、已发布，不包含回收站）
     * @param queryDTO
     * @return
     */
    public Response queryPostList(BMPostListQueryDTO queryDTO) {
        List<PostInfoEntity> entities = postService.BMQueryPostList(queryDTO);
        PageInfo pageInfo = new PageInfo(entities);
        return buildPostTable(pageInfo);
    }


    /**
     * 查询草稿箱
     * @param queryDTO
     * @return
     */
    public Response queryDrafts(BMDraftsQueryDTO queryDTO) {
        List<PostInfoEntity> entities = postService.BMDraftsQuery(queryDTO);
        PageInfo pageInfo = new PageInfo(entities);
        return buildPostTable(pageInfo);
    }


    /**
     * 查询回收站
     * @param queryDTO
     * @return
     */
    public Response queryRubbish(BMRubbishQueryDTO queryDTO){
        List<PostInfoEntity> entities = postService.BMRubbishQuery(queryDTO);
        PageInfo pageInfo = new PageInfo(entities);
        return buildPostTable(pageInfo);

    }

    private Response buildPostTable(PageInfo pageInfo) {
        List<HomePagePostListDTO> homePagePostListDTOS = postBusiness.buildBMPostRespDTO(pageInfo.getList());
        PostListQueryRespDTO resp = new PostListQueryRespDTO() {
            {
                setPostList(homePagePostListDTOS);
                setTotalNum(pageInfo.getTotal());
            }
        };
        return ResponseBuilder.build(true, resp);
    }

    /**
     * 文章发布
     */
    public Response releasePost(BMPostModifyReqDTO reqDTO) {
        if (null == reqDTO || StringUtils.isBlank(reqDTO.getPostId()))
            return ResponseBuilder.build(false, "请求PostId为空");
        PostInfoEntity postInfoEntity = postService.queryPostByPostId(reqDTO.getPostId());
        if (null == postInfoEntity)
            return ResponseBuilder.build(false, "文章不存在");
        if (1 == postInfoEntity.getReleaseFlag() || 1 == postInfoEntity.getDelFlag())
            return ResponseBuilder.build(false, "文章状态异常");
        PostInfoEntity entity = new PostInfoEntity() {
            {
                setPostId(reqDTO.getPostId());
                setReleaseFlag(1);
            }
        };
        postService.updatePostByPostId(entity);
        modifyTagRelation(reqDTO.getPostId(),0);
        return ResponseBuilder.build(true, "发布成功");
    }

    /**
     * 文章下线
     */
    public Response offlinePost(BMPostModifyReqDTO reqDTO) {
        if (null == reqDTO || StringUtils.isBlank(reqDTO.getPostId()))
            return ResponseBuilder.build(false, "请求PostId为空");
        PostInfoEntity postInfoEntity = postService.queryPostByPostId(reqDTO.getPostId());
        if (null == postInfoEntity)
            return ResponseBuilder.build(false, "文章不存在");
        if (0 == postInfoEntity.getReleaseFlag() || 1 == postInfoEntity.getDelFlag())
            return ResponseBuilder.build(false, "文章状态异常");
        PostInfoEntity entity = new PostInfoEntity() {
            {
                setPostId(reqDTO.getPostId());
                setReleaseFlag(0);
            }
        };
        postService.updatePostByPostId(entity);
        modifyTagRelation(reqDTO.getPostId(),1);
        return ResponseBuilder.build(true, "下线成功");
    }
    /**
     * 文章逻辑删除
     */
    public Response removePost(BMPostModifyReqDTO reqDTO) {
        if (null == reqDTO || StringUtils.isBlank(reqDTO.getPostId()))
            return ResponseBuilder.build(false, "请求PostId为空");
        PostInfoEntity postInfoEntity = postService.queryPostByPostIdWithoutDel(reqDTO.getPostId());
        if (null == postInfoEntity)
            return ResponseBuilder.build(false, "文章不存在");
        if (1 == postInfoEntity.getDelFlag())
            return ResponseBuilder.build(false, "文章状态异常");
        PostInfoEntity entity = new PostInfoEntity() {
            {
                setPostId(reqDTO.getPostId());
                setReleaseFlag(0);
                setDelFlag(1);
            }
        };
        postService.updatePostByPostId(entity);
        //逻辑删除tag-post对应
        modifyTagRelation(reqDTO.getPostId(),1);
        return ResponseBuilder.build(true, "移动成功");
    }

    public Response recoverPost(BMPostModifyReqDTO reqDTO) {
        if (null == reqDTO || StringUtils.isBlank(reqDTO.getPostId()))
            return ResponseBuilder.build(false, "请求PostId为空");
        PostInfoEntity postInfoEntity = postService.queryPostByPostIdWithoutDel(reqDTO.getPostId());
        if (null == postInfoEntity)
            return ResponseBuilder.build(false, "文章不存在");
        if (0 == postInfoEntity.getDelFlag())
           throw new UnifiedException(ErrorCodeEnum.POST_STATE_ERROR);
        PostInfoEntity entity = new PostInfoEntity() {
            {
                setPostId(reqDTO.getPostId());
                setReleaseFlag(0);
                setDelFlag(0);
            }
        };
        postService.updatePostByPostId(entity);
        //恢复tag-post对应
        modifyTagRelation(reqDTO.getPostId(),0);
        return ResponseBuilder.build(true, "恢复成功");
    }

    //删除or重建tag-post关系
    private void modifyTagRelation(String postId,Integer delFlag){
        TagRelationEntity tagRelationEntity = new TagRelationEntity(){
            {
                setPostId(postId);
                setDelFlag(delFlag);
                setUpdateTime(new Date());
            }
        };
        tagService.updateTagRelation(tagRelationEntity);
    }
}
