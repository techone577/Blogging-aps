package com.blogging.aps.business.impl;

import com.blogging.aps.business.PostBusiness;
import com.blogging.aps.business.manage.AbstractPostListQueryBusiness;
import com.blogging.aps.model.dto.HomePagePostListDTO;
import com.blogging.aps.model.dto.PostListQueryRespDTO;
import com.blogging.aps.model.dto.PostPagingQueryDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import com.blogging.aps.model.entity.post.TagEntity;
import com.blogging.aps.model.entity.post.TagRelationEntity;
import com.blogging.aps.service.post.TagService;
import com.blogging.aps.service.post.PostService;
import com.blogging.aps.support.utils.ResponseBuilder;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagPostListQueryBusiness extends AbstractPostListQueryBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(TagPostListQueryBusiness.class);

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostBusiness postBusiness;

    @Override
    public Response queryPostList(PostPagingQueryDTO queryDTO) {
        String tagName = queryDTO.getTypeValue();
        List<TagEntity> tagEntities = tagService.queryTagByName(tagName);
        if(null == tagEntities || tagEntities.size() == 0 ) {
            LOG.info("查询tag失败，不存在此tag:{}", queryDTO.getTypeValue());
            return ResponseBuilder.build(true,"tag不存在！");
        }
        List<TagRelationEntity> tagRelationEntities = tagService.queryTagReLationByTagIdPaging(tagEntities.get(0).getId(),
                queryDTO.getPageNum(), queryDTO.getPageSize());
        PageInfo pageInfo = new PageInfo(tagRelationEntities);
        List<String> postIds = tagRelationEntities
                .stream().map(i->i.getPostId()).collect(Collectors.toList());
        List<PostInfoEntity> postInfoEntities = postService.queryPostListByIdList(postIds,queryDTO.getReleaseFlag());
        List<HomePagePostListDTO> homePagePostListDTOS = postBusiness.buildHomePagePostRespDTO(postInfoEntities);
        PostListQueryRespDTO respDTO = new PostListQueryRespDTO(){
            {
                setPostList(homePagePostListDTOS);
                setTotalNum(pageInfo.getTotal());
                setTagInfoList(postBusiness.queryTagInfoList());
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
