package com.blogging.aps.persistence;

import com.blogging.aps.model.entity.post.TagEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagEntityMapper {
    int insert(TagEntity record);

    int insertSelective(TagEntity record);

    List<TagEntity> selectTagByName(@Param("tagName") String name);

    TagEntity selectById(@Param("tagId") Integer tagId);

    void deleteById(@Param("tagId") Integer tagId);

    List<TagEntity> selectTagByIdList(@Param("idList") List<Integer> idList);

    List<TagEntity> selectTagList();

    void updateByIdSelective(TagEntity tagEntity);
}