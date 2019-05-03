package com.blogging.aps.service.post;

import com.blogging.aps.model.entity.post.CategoryEntity;
import com.blogging.aps.persistence.CategoryEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author techoneduan
 * @date 2019/5/2
 */

@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryEntityMapper categoryEntityMapper;

    public void insertSelective(CategoryEntity entity) {
        entity.setUpdateTime(new Date());
        categoryEntityMapper.insertSelective(entity);
    }

    public CategoryEntity queryByName(String name) {
        return categoryEntityMapper.selectByName(name);
    }

    public void updateNumByName(String name, Integer num) {
        categoryEntityMapper.updateNumByName(name, num);
    }

    public void updateByIdSelecttive(CategoryEntity entity) {
        categoryEntityMapper.updateByIdSelective(entity);
    }

    public List<CategoryEntity> queryCategories() {
        return categoryEntityMapper.selectAllCategories();
    }

    public CategoryEntity queryById(Integer id) {
        return categoryEntityMapper.selectById(id);
    }
}
