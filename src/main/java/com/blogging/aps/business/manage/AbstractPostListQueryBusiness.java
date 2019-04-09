package com.blogging.aps.business.manage;

import com.blogging.aps.model.dto.PostPagingQueryDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.support.strategy.MatchingBean;

public abstract class AbstractPostListQueryBusiness implements MatchingBean<String> {

    public abstract Response queryPostList(PostPagingQueryDTO queryDTO);

}
