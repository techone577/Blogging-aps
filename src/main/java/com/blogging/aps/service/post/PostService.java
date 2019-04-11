package com.blogging.aps.service.post;

import com.blogging.aps.model.dto.PostPagingQueryDTO;
import com.blogging.aps.model.entity.post.PassageEntity;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import com.blogging.aps.persistence.PassageEntityMapper;
import com.blogging.aps.persistence.PostInfoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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


    public void insertPost(PostInfoEntity postInfoEntity) {
        postInfoEntityMapper.insertSelective(postInfoEntity);
    }

    //passage id 根据时间自生成
    public void insertPassgae(PassageEntity passageEntity) {
        passageEntityMapper.insertSelective(passageEntity);
    }

    //查询最新的五篇文章信息
    public List<PostInfoEntity> queryLatestFivePosts() {
        return postInfoEntityMapper.selectLatestFivePosts();
    }

    public PostInfoEntity queryPostByPostId(String postId) {
        return postInfoEntityMapper.selectPostByPostId(postId);
    }


    public PassageEntity queryPassageByPassageId(String passageId) {
        return passageEntityMapper.selectPassageByPassageId(passageId);
    }

    /**
     * 查询上一篇文章
     */
    public PostInfoEntity queryPreviousPost(Date addtime,String postId){
        return postInfoEntityMapper.selectPreviousPost(addtime,postId);
    }

    /**
     * 查询下一篇文章
     */
    public PostInfoEntity queryNextPost(Date addtime,String postId){
        return postInfoEntityMapper.selectNextPost(addtime,postId);
    }

    /**
     * 分页查询博客列表
     */
    public List<PostInfoEntity> queryPostListByPaging(PostPagingQueryDTO queryDTO){
        return postInfoEntityMapper.selectPostListByPaging(queryDTO.getPageNum()*queryDTO.getPageSize(),queryDTO.getPageSize());
    }

    /**
     * 根据id列表查询
     */
    public List<PostInfoEntity> queryPostListByIdList(List<String> postIds){
        return postInfoEntityMapper.selectPostByIdList(postIds);
    }

    /**
     * 统计所有博客数量
     */
    public Integer queryPostCount(){
        return postInfoEntityMapper.selectPostCount();
    }
}
