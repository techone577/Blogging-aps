package com.blogging.aps.persistence;


import com.blogging.aps.model.entity.post.TagRelationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagRelationEntityMapper {
    int insert(TagRelationEntity record);

    int insertSelective(TagRelationEntity record);

    List<TagRelationEntity> selectByPostId(@Param("postId") String postId);
}