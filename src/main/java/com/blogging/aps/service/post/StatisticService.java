package com.blogging.aps.service.post;

import com.blogging.aps.model.entity.post.StatisticEntity;
import com.blogging.aps.persistence.StatisticEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author techoneduan
 * @date 2019/4/20
 */

@Service
public class StatisticService {

    @Autowired
    private StatisticEntityMapper statisticEntityMapper;

    public void updatePageView(StatisticEntity entity){
        statisticEntityMapper.updateSelective(entity);
    }

    public List<StatisticEntity> queryByPostId(String postId){
        return statisticEntityMapper.selectByPostId(postId);
    }

    public void insertSelective(StatisticEntity entity){
        statisticEntityMapper.insertSelective(entity);
    }
}
