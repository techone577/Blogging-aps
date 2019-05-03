package com.blogging.aps.business.impl;

import com.blogging.aps.business.PostBusiness;
import com.blogging.aps.business.manage.AbstractPostListQueryBusiness;
import com.blogging.aps.model.dto.HomePagePostListDTO;
import com.blogging.aps.model.dto.PostListQueryRespDTO;
import com.blogging.aps.model.dto.PostPagingQueryDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.entity.post.CategoryEntity;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import com.blogging.aps.model.enums.ErrorCodeEnum;
import com.blogging.aps.service.post.CategoryService;
import com.blogging.aps.service.post.PostService;
import com.blogging.aps.service.post.TagService;
import com.blogging.aps.support.exception.UnifiedException;
import com.blogging.aps.support.utils.ResponseBuilder;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author techoneduan
 * @date 2019/5/3
 */

@Component
public class CategoryPostListQueryBusiness extends AbstractPostListQueryBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryPostListQueryBusiness.class);

    @Autowired
    private PostService postService;

    @Autowired
    private PostBusiness postBusiness;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Response queryPostList(PostPagingQueryDTO queryDTO) {
        String category = queryDTO.getTypeValue();
        CategoryEntity categoryEntity = categoryService.queryByName(category);
        if (null == categoryEntity)
            throw new UnifiedException(ErrorCodeEnum.FOUR_ZERO_FOUR_ERROR);
        List<PostInfoEntity> postInfoEntityList = postService.queryPostByCategory(queryDTO);
        PageInfo pageInfo = new PageInfo(postInfoEntityList);
        List<HomePagePostListDTO> homePagePostListDTOS = postBusiness.buildHomePagePostRespDTO(postInfoEntityList);
        PostListQueryRespDTO respDTO = new PostListQueryRespDTO() {
            {
                setTagInfoList(postBusiness.queryTagInfoList());
                setCategoryInfoList(postBusiness.queryCategoryList(true));
                setPostList(homePagePostListDTOS);
                setTotalNum(pageInfo.getTotal());
            }
        };
        Response resp = ResponseBuilder.build(true, respDTO);
        return resp;
    }

    @Override
    public boolean matching(String factor) {
        return "category".equals(factor);
    }
}
