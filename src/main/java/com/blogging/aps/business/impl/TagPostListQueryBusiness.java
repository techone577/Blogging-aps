package com.blogging.aps.business.impl;

import com.blogging.aps.business.PostBusiness;
import com.blogging.aps.business.manage.AbstractPostListQueryBusiness;
import com.blogging.aps.model.dto.HomePagePostListDTO;
import com.blogging.aps.model.dto.HomePageRespDTO;
import com.blogging.aps.model.dto.PostListQueryRespDTO;
import com.blogging.aps.model.dto.PostPagingQueryDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import com.blogging.aps.model.entity.post.TagEntity;
import com.blogging.aps.model.entity.post.TagRelationEntity;
import com.blogging.aps.service.TagService;
import com.blogging.aps.service.post.PostService;
import com.blogging.aps.support.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagPostListQueryBusiness extends AbstractPostListQueryBusiness {

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostBusiness postBusiness;

    @Override
    public Response queryPostList(PostPagingQueryDTO queryDTO) {
        String tagName = queryDTO.getTypeValue();
        //TODO 分页
        List<TagEntity> tagEntities = tagService.queryTagByName(tagName);
        List<TagRelationEntity> tagRelationEntities = tagService.queryTagReLationByTagId(tagEntities.get(0).getId());
        List<String> postIds = tagRelationEntities
                .stream().map(i->i.getPostId()).collect(Collectors.toList());
        List<PostInfoEntity> postInfoEntities = postService.queryPostListByIdList(postIds);
        List<HomePagePostListDTO> homePagePostListDTOS = postBusiness.buildHomePagePostRespDTO(postInfoEntities);
        PostListQueryRespDTO respDTO = new PostListQueryRespDTO(){
            {
                setPostList(homePagePostListDTOS);
                setTotalNum(postInfoEntities.size());
                setTagInfoList(postBusiness.buildTagInfoList());
            }
        };
        Response resp = ResponseBuilder.build(true,respDTO);
        return resp;
    }

    @Override
    public boolean matching(String factor) {
        return "tag".equals(factor);
    }
}
