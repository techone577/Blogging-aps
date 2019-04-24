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
        PostInfoEntity postInfoEntity = postService.queryPostByPostId(postId,null);
        if (null == postInfoEntity)
            throw new UnifiedException(ErrorCodeEnum.POST_NOT_EXIST_ERROR);

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
        PostInfoEntity postInfoEntity = postService.queryPostByPostId(reqDTO.getPostId(),null);
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
        PostInfoEntity postInfoEntity = postService.queryPostByPostId(reqDTO.getPostId(),null);
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

    /**
     * 文章恢复
     * @param reqDTO
     * @return
     */
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

    /**
     * 删除or重建tag-post关系
     *
     * @param postId
     * @param delFlag
     */
    private void modifyTagRelation(String postId, Integer delFlag) {
        TagRelationEntity tagRelationEntity = new TagRelationEntity() {
            {
                setPostId(postId);
                setDelFlag(delFlag);
                setUpdateTime(new Date());
            }
        };
        tagService.updateTagRelation(tagRelationEntity);
    }

    /**
     * 编辑tag名称
     *
     * @param reqDTO
     * @return
     */
    public Response editTagName(BMTagEditReqDTO reqDTO) {
        if (null == reqDTO.getId() || StringUtils.isBlank(reqDTO.getTagName()))
            throw new UnifiedException(ErrorCodeEnum.PARAM_ILLEGAL_ERROR);
        List<TagEntity> entities = tagService.queryTagByName(reqDTO.getTagName());
        if (null != entities && entities.size() > 0)
            throw new UnifiedException(ErrorCodeEnum.TAG_NAME_ALREADY_EXIST_ERROR);
        TagEntity tagEntity = new TagEntity() {
            {
                setId(reqDTO.getId());
                setTagName(reqDTO.getTagName());
            }
        };
        tagService.updateTag(tagEntity);
        return ResponseBuilder.build(true, "修改成功");
    }

    /**
     * 添加tag
     *
     * @param reqDTO
     * @return
     */
    public Response addTag(BMTagModifyReqDTO reqDTO) {
        if (StringUtils.isBlank(reqDTO.getPostId()) || StringUtils.isBlank(reqDTO.getTagName()))
            throw new UnifiedException(ErrorCodeEnum.PARAM_ILLEGAL_ERROR);
        List<String> tags = postBusiness.getPostTags(reqDTO.getPostId());
        Optional optional = tags.stream().filter(i -> i.equals(reqDTO.getTagName())).findAny();
        if (optional.isPresent())
            throw new UnifiedException(ErrorCodeEnum.TAG_ALREADY_EXIST_ERROR);
        addTag(reqDTO.getTagName(), reqDTO.getPostId());
        return ResponseBuilder.build(true, "添加成功");
    }

    public Response delTagForPost(BMTagModifyReqDTO reqDTO) {
        if (StringUtils.isBlank(reqDTO.getPostId()) || StringUtils.isBlank(reqDTO.getTagName()))
            throw new UnifiedException(ErrorCodeEnum.PARAM_ILLEGAL_ERROR);
        deleteTagRelation(reqDTO.getPostId(), reqDTO.getTagName());
        return ResponseBuilder.build(true, "删除成功");
    }


    private void addTag(String tagName, String postId) {
        List<TagEntity> entities = tagService.queryTagByName(tagName);
        if (null == entities || entities.size() == 0)
            createNewTagForPost(tagName, postId);
        else
            addTagFroPost(tagName, postId);
    }

    /**
     * tag不存在的情况下添加
     *
     * @param name
     * @param postId
     */
    private void createNewTagForPost(String name, String postId) {
        TagEntity tagEntity = new TagEntity() {
            {
                setTagName(name);
                setDelFlag(0);
                setAddTime(new Date());
            }
        };
        tagService.insertTag(tagEntity);
        Integer tagId = tagEntity.getId();
        TagRelationEntity relationEntity = new TagRelationEntity() {
            {
                setTagId(tagId);
                setPostId(postId);
                setAddTime(new Date());
                setDelFlag(0);
            }
        };
        tagService.insertTagRelation(relationEntity);
    }

    /**
     * tag存在的情况下
     *
     * @param name
     * @param postId
     */
    private void addTagFroPost(String name, String postId) {
        List<TagEntity> entities = tagService.queryTagByName(name);
        TagRelationEntity relationEntity = new TagRelationEntity() {
            {
                setTagId(entities.get(0).getId());
                setPostId(postId);
                setAddTime(new Date());
                setDelFlag(0);
            }
        };
        tagService.insertTagRelation(relationEntity);
    }


    /**
     * 文章更新
     *
     * @param updateDTO
     * @return
     */
    public Response postUpdate(BMPostUpdateDTO updateDTO) {
        String postId = updateDTO.getPostId();
        if (StringUtils.isBlank(postId))
            throw new UnifiedException(ErrorCodeEnum.INTERFACE_ERROR, "postId为空");
        PostInfoEntity postInfoEntity = postService.queryPostByPostId(postId, null);
        if (null == postInfoEntity)
            throw new UnifiedException(ErrorCodeEnum.POST_NOT_EXIST_ERROR);
        //更新post
        BMPostAddDTO addDTO = updateDTO.getAddDTO();
        postInfoEntity.setReleaseFlag(addDTO.getReleaseFlag());
        postInfoEntity.setSummary(addDTO.getSummary());
        postInfoEntity.setTitle(addDTO.getTitle());
        postInfoEntity.setMemberId(addDTO.getMemberId());
        postService.updatePostByPostId(postInfoEntity);

        //更新passage
        String passageId = postInfoEntity.getPassageId();
        PassageEntity passageEntity = postService.queryPassageByPassageId(passageId);
        if (null == passageEntity) {
            passageEntity = new PassageEntity() {
                {
                    setAddTime(new Date());
                    setContent(addDTO.getContent());
                    setDelFlag(0);
                    setPassageId(passageId);
                    setUpdateTime(new Date());
                }
            };
            postService.insertPassgae(passageEntity);
        }
        passageEntity.setPassageId(passageId);
        passageEntity.setContent(addDTO.getContent());
        postService.updatePassageByPassageId(passageEntity);
        //更新tag
        //删除
        List<String> orgTags = postBusiness.getPostTags(postId);
        if(null != orgTags) {
            orgTags.stream().forEach(i -> {
                deleteTagRelation(postId, i);
            });
        }
        //添加
        List<String> tags = addDTO.getTags();
        if(null != tags) {
            tags.stream().forEach(i -> {
                addTag(i, postId);
            });
        }
        return ResponseBuilder.build(true, "更新成功");
    }

    /**
     * 文章删除
     *
     */
    public Response postDelete(BMPostModifyReqDTO reqDTO) {
        if (null == reqDTO || StringUtils.isBlank(reqDTO.getPostId()))
            throw new UnifiedException(ErrorCodeEnum.PARAM_ILLEGAL_ERROR, "postId为空");
        PostInfoEntity postInfoEntity = postService.queryPostByPostIdWithoutDel(reqDTO.getPostId());
        if (null == postInfoEntity)
            throw new UnifiedException(ErrorCodeEnum.POST_NOT_EXIST_ERROR);
        //删除passage
        String passageId = postInfoEntity.getPassageId();
        PassageEntity passageEntity = postService.queryPassageByPassageId(passageId);
        if (null != passageEntity)
            postService.deletePassageByPassageId(passageId);
        //删除tags
        List<String> tags = postBusiness.getPostTagsWithoutDel(postInfoEntity.getPostId());
        if(null != tags) {
            tags.stream().forEach(i -> {
                try {
                    deleteTagRelation(postInfoEntity.getPostId(), i);
                } catch (UnifiedException ue) {
                }
            });
        }
        //删除post
        postService.deletePostByPostId(reqDTO.getPostId());
        return ResponseBuilder.build(true, "删除成功");
    }


    /**
     * 删除tag-post关联
     *
     * @param postId
     * @param tagName
     */
    private void deleteTagRelation(String postId, String tagName) {
        List<TagEntity> entities = tagService.queryTagByName(tagName);
        if (null == entities || entities.size() == 0)
            throw new UnifiedException(ErrorCodeEnum.TAG_ALREADY_EXIST_ERROR);
        Integer tagId = entities.get(0).getId();
        tagService.delTagForPost(postId, tagId);
    }
}
