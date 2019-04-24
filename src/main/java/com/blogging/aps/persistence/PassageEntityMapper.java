package com.blogging.aps.persistence;


import com.blogging.aps.model.entity.post.PassageEntity;
import org.apache.ibatis.annotations.Param;

public interface PassageEntityMapper {
    int insert(PassageEntity record);

    int insertSelective(PassageEntity record);

    PassageEntity selectPassageByPassageId(@Param("passageId") String passageId);

    void updatePassageByPassageId(PassageEntity entity);

    void deletePassageByPassageId(@Param("passageId") String passageId);
}