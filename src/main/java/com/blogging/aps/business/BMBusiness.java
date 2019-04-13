package com.blogging.aps.business;


import com.blogging.aps.model.dto.BMBlogQueryRespDTO;
import com.blogging.aps.model.dto.BMTagQueryRespDTO;
import com.blogging.aps.model.dto.PostQueryReqDTO;
import com.blogging.aps.model.dto.TagAmountDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.entity.post.PassageEntity;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import com.blogging.aps.model.entity.post.TagEntity;
import com.blogging.aps.service.TagService;
import com.blogging.aps.service.post.PostService;
import com.blogging.aps.support.utils.DateUtils;
import com.blogging.aps.support.utils.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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


    /**
     * 查询所有tag
     */
    public Response queryTags() {
        List<TagAmountDTO> tagAmountDTOS = tagService.queryTagAmount();
        if (null == tagAmountDTOS || tagAmountDTOS.size() == 0) {
            return ResponseBuilder.build(true, "标签列表为空");
        }
        tagAmountDTOS = tagAmountDTOS.stream().sorted(Comparator.comparing(TagAmountDTO::getTagId)).collect(Collectors.toList());
        List<TagEntity> tagEntities = tagService.queryByTagIdList(tagAmountDTOS.stream().map(item -> item.getTagId()).collect(Collectors.toList()));
        List<BMTagQueryRespDTO> tagQueryRespDTOS = buildTagRespDTO(tagEntities, tagAmountDTOS);
        return ResponseBuilder.build(true, tagQueryRespDTOS);
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
}
