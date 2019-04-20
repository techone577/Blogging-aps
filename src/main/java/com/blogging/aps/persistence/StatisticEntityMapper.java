package com.blogging.aps.persistence;


import com.blogging.aps.model.entity.post.StatisticEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatisticEntityMapper {
    int insert(StatisticEntity entity);

    int insertSelective(StatisticEntity entity);

    void updateSelective(StatisticEntity entity);

    List<StatisticEntity> selectByPostId(@Param("postId") String postId);
}