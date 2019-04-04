package com.blogging.aps.persistence;


import com.blogging.aps.model.entity.post.PostInfoEntity;

import java.util.List;

public interface PostInfoEntityMapper {
    int insert(PostInfoEntity record);

    int insertSelective(PostInfoEntity record);

    List<PostInfoEntity> selectLatestFivePosts();
}