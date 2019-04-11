package com.blogging.aps.persistence;


import com.blogging.aps.model.dto.TagAmountDTO;
import com.blogging.aps.model.entity.post.TagRelationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagRelationEntityMapper {
    int insert(TagRelationEntity record);

    int insertSelective(TagRelationEntity record);

    List<TagRelationEntity> selectByPostId(@Param("postId") String postId);

    List<TagAmountDTO> selectTagAmount();

    List<TagRelationEntity> selectByTagIdPaging(@Param("tagId") Integer tagId,@Param("offSet") Integer offSet, @Param("pageSize") Integer pageSize);

    List<TagRelationEntity> selectByTagId(@Param("tagId") Integer tagId);
}