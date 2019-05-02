package com.blogging.aps.persistence;


import com.blogging.aps.model.entity.post.CategoryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryEntityMapper {

    int insert(CategoryEntity record);

    int insertSelective(CategoryEntity record);

    CategoryEntity selectByName(@Param("name") String name);

    void updateNumByName(@Param("name") String name, @Param("num") Integer num);

    List<CategoryEntity> selectAllCategories();
}