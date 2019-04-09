package com.blogging.aps.business.impl;

import com.blogging.aps.business.PostBusiness;
import com.blogging.aps.business.manage.AbstractPostListQueryBusiness;
import com.blogging.aps.model.dto.*;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import com.blogging.aps.model.entity.post.TagEntity;
import com.blogging.aps.service.TagService;
import com.blogging.aps.service.post.PostService;
import com.blogging.aps.support.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HomePostListQueryBusiness extends AbstractPostListQueryBusiness {

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostBusiness postBusiness;

    @Override
    public Response queryPostList(PostPagingQueryDTO queryDTO) {
        return postBusiness.homepagePostQuery();
    }

    @Override
    public boolean matching(String factor) {
        return "home".equals(factor);
    }
}
