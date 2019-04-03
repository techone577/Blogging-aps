package com.blogging.aps.persistence;


import com.blogging.aps.model.entity.post.TagRelationEntity;

public interface TagRelationEntityMapper {
    int insert(TagRelationEntity record);

    int insertSelective(TagRelationEntity record);
}