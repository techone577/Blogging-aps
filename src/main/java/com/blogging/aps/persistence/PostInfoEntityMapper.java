package com.blogging.aps.persistence;


import com.blogging.aps.model.dto.PostPagingQueryDTO;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PostInfoEntityMapper {
    int insert(PostInfoEntity record);

    int insertSelective(PostInfoEntity record);

    List<PostInfoEntity> selectLatestFivePosts();

    PostInfoEntity selectPostByPostId(@Param("postId") String postId);

    PostInfoEntity selectPreviousPost(@Param("addTime")Date addTime,@Param("postId")String postId);

    PostInfoEntity selectNextPost(@Param("addTime")Date addTime,@Param("postId")String postId);

    List<PostInfoEntity> selectPostListByPaging(PostPagingQueryDTO queryDTO);
}