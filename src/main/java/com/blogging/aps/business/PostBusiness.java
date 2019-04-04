package com.blogging.aps.business;

import com.blogging.aps.model.dto.HomePagePostListRespDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.entity.post.PassageEntity;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import com.blogging.aps.model.entity.post.TagEntity;
import com.blogging.aps.model.entity.post.TagRelationEntity;
import com.blogging.aps.model.entity.post.PostAddReqEntity;
import com.blogging.aps.service.TagService;
import com.blogging.aps.service.post.PostService;
import com.blogging.aps.support.utils.DateUtils;
import com.blogging.aps.support.utils.IdGenerator;
import com.blogging.aps.support.utils.ResponseBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author techoneduan
 * @date 2019/1/18
 */

@Component
public class PostBusiness {

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;


    /**
     * 添加新博客
     *
     * @param entity
     * @return
     */
    public Response addPost(PostAddReqEntity entity) {
        PostInfoEntity postInfoEntity = new PostInfoEntity();
        PassageEntity passageEntity = new PassageEntity();
        if (StringUtils.isBlank(entity.getTitle()))
            return ResponseBuilder.build(false, "文章标题为空");
        buildArticle(postInfoEntity, passageEntity, entity);
        //插入文章
        postService.insertPassgae(passageEntity);
        postService.insertPost(postInfoEntity);
        //储存对应tag
        buildTags(entity.getTags(), postInfoEntity.getPostId());
        return ResponseBuilder.build(true, null);
    }

    public Response homepagePostQuery() {
        List<PostInfoEntity> postInfoEntities = postService.queryLatestFivePosts();
        if (postInfoEntities.size() == 0)
            return ResponseBuilder.build(true, null);
        List<HomePagePostListRespDTO> respDTOS = buildHomePagePostRespDTO(postInfoEntities);
        return ResponseBuilder.build(true, respDTOS);
    }


    //TODO tag?

    private void buildArticle(PostInfoEntity postInfoEntity, PassageEntity passageEntity, PostAddReqEntity reqEntity) {
        postInfoEntity.setTitle(reqEntity.getTitle());
        postInfoEntity.setCategory(reqEntity.getCategory());
        postInfoEntity.setPostId(IdGenerator.generatePostId());
        postInfoEntity.setDelFlag(0);
        postInfoEntity.setMemberId(reqEntity.getMemberId());
        postInfoEntity.setSummary(reqEntity.getSummary());

        passageEntity.setPassageId(IdGenerator.generatePassageId());
        passageEntity.setDelFlag(0);
        passageEntity.setContent(reqEntity.getContent());

        Date now = new Date();
        postInfoEntity.setAddTime(now);
        postInfoEntity.setUpdateTime(now);
        passageEntity.setAddTime(now);
        passageEntity.setUpdateTime(now);

        postInfoEntity.setPassageId(passageEntity.getPassageId());

    }

    private void buildTags(List<String> tagList, String postId) {
        if (null == tagList || tagList.size() == 0)
            return;
        //去重
        tagList = tagList.stream().distinct().collect(Collectors.toList());
        tagList.forEach(item -> checkAndAddTag(item, postId));

    }


    //检查tag并添加对应关系
    private void checkAndAddTag(String tag, String postId) {
        List<TagEntity> tagEntityList = tagService.queryTagByName(tag);
        Integer tagId = null;
        if (null == tagEntityList || tagEntityList.size() == 0) {
            TagEntity tagEntity = new TagEntity() {
                {
                    setTagName(tag);
                    setDelFlag(0);
                    setAddTime(new Date());
                    setUpdateTime(new Date());

                }
            };
            tagService.insertTag(tagEntity);
            tagId = tagEntity.getId();
        }
        if (null == tagId) {
            tagId = tagEntityList.get(0).getId();
        }
        TagRelationEntity relationEntity = new TagRelationEntity() {
            {
                setPostId(postId);
                setDelFlag(0);
                setAddTime(new Date());
                setUpdateTime(new Date());
            }
        };
        relationEntity.setTagId(tagId);
        tagService.insertTagRelation(relationEntity);
    }

    private List<HomePagePostListRespDTO> buildHomePagePostRespDTO(List<PostInfoEntity> postInfoEntities) {
        List<HomePagePostListRespDTO> respDTOS = new ArrayList<>();
        for (PostInfoEntity postInfoEntity : postInfoEntities) {
            HomePagePostListRespDTO respDTO = new HomePagePostListRespDTO() {
                {
                    setTitle(postInfoEntity.getTitle());
                    setSummary(postInfoEntity.getSummary());
                    setUpdateTime(DateUtils.formatDate(postInfoEntity.getUpdateTime()));
                }
            };
            List<Integer> tagIdList = tagService.queryByPostId(postInfoEntity.getPostId())
                    .stream().map(item -> item.getTagId()).collect(Collectors.toList());
            //TODO 可以缓存tag列表
            if (null == tagIdList || tagIdList.size() == 0) {
                respDTO.setTagList(new ArrayList<>());
                respDTOS.add(respDTO);
                continue;
            }
            List<String> tagList = tagService.queryByTagIdList(tagIdList)
                    .stream().map(item -> item.getTagName()).collect(Collectors.toList());
            if (null == tagList || tagList.size() == 0) {
                respDTO.setTagList(new ArrayList<>());
                respDTOS.add(respDTO);
            }
            respDTO.setTagList(tagList);
            respDTOS.add(respDTO);
        }
        return respDTOS;
    }
}
