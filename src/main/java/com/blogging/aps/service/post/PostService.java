package com.blogging.aps.service.post;

import com.blogging.aps.model.entity.post.PassageEntity;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import com.blogging.aps.persistence.PassageEntityMapper;
import com.blogging.aps.persistence.PostInfoEntityMapper;
import com.blogging.aps.persistence.TagEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author techoneduan
 * @date 2019/1/18
 */

@Service
public class PostService {

    @Autowired
    private PostInfoEntityMapper postInfoEntityMapper;

    @Autowired
    private PassageEntityMapper passageEntityMapper;

    @Autowired
    private TagEntityMapper tagEntityMapper;

    public void insertPost(PostInfoEntity postInfoEntity) {
        postInfoEntityMapper.insertSelective(postInfoEntity);
    }

    //passage id 根据时间自生成
    public void insertPassgae(PassageEntity passageEntity) {
        passageEntityMapper.insertSelective(passageEntity);
    }
}
