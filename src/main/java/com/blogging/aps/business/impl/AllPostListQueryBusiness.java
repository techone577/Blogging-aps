package com.blogging.aps.business.impl;

import com.blogging.aps.business.PostBusiness;
import com.blogging.aps.business.manage.AbstractPostListQueryBusiness;
import com.blogging.aps.model.dto.HomePagePostListDTO;
import com.blogging.aps.model.dto.PostPagingQueryDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import com.blogging.aps.service.TagService;
import com.blogging.aps.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AllPostListQueryBusiness extends AbstractPostListQueryBusiness {

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostBusiness postBusiness;

    @Override
    public Response queryPostList(PostPagingQueryDTO queryDTO) {
        return postBusiness.postPagingQuery(queryDTO);
    }

    @Override
    public boolean matching(String factor) {
        return "all".equals(factor);
    }
}
