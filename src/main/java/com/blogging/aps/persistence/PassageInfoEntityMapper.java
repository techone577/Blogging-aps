package com.blogging.aps.persistence;

import com.blogging.aps.model.entity.post.PassageInfoEntity;

public interface PassageInfoEntityMapper {
    int deleteByPrimaryKey (Long id);

    int insert (PassageInfoEntity record);

    int insertSelective (PassageInfoEntity record);

    PassageInfoEntity selectByPrimaryKey (Long id);

    int updateByPrimaryKeySelective (PassageInfoEntity record);

    int updateByPrimaryKeyWithBLOBs (PassageInfoEntity record);

    int updateByPrimaryKey (PassageInfoEntity record);
}