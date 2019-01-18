package com.blogging.aps.persistence;

import com.blogging.aps.model.entity.post.PostInfoEntity;

public interface PostInfoEntityMapper {
    int deleteByPrimaryKey (Long id);

    int insert (PostInfoEntity record);

    int insertSelective (PostInfoEntity record);

    PostInfoEntity selectByPrimaryKey (Long id);

    int updateByPrimaryKeySelective (PostInfoEntity record);

    int updateByPrimaryKey (PostInfoEntity record);
}