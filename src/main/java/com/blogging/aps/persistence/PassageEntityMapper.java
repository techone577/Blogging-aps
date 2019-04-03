package com.blogging.aps.persistence;


import com.blogging.aps.model.entity.post.PassageEntity;

public interface PassageEntityMapper {
    int insert(PassageEntity record);

    int insertSelective(PassageEntity record);
}