package com.blogging.aps.persistence;


import com.blogging.aps.model.entity.post.CategoryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryEntityMapper {

    int insert(CategoryEntity record);

    int insertSelective(CategoryEntity record);

    CategoryEntity selectByName(@Param("name") String name);

    CategoryEntity selectById(@Param("id") Integer id);

    void updateNumByName(@Param("name") String name, @Param("num") Integer num);

    void updateByIdSelective(CategoryEntity entity);

    List<CategoryEntity> selectAllCategories();
}