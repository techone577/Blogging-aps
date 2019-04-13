package com.blogging.aps.persistence;


import com.blogging.aps.model.entity.post.PostInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PostInfoEntityMapper {
    int insert(PostInfoEntity record);

    int insertSelective(PostInfoEntity record);

    List<PostInfoEntity> selectLatestFivePosts();

    PostInfoEntity selectPostByPostId(@Param("postId") String postId);

    PostInfoEntity selectPreviousPost(@Param("id")Integer id,@Param("postId")String postId);

    PostInfoEntity selectNextPost(@Param("id")Integer id,@Param("postId")String postId);

    List<PostInfoEntity> selectPostListByPaging(@Param("offSet")Integer offSet,@Param("pageSize") Integer pageSize,@Param("releaseFlag") Integer releaseFlag);

    List<PostInfoEntity> selectPostByIdList(@Param("postIdList") List<String> postIdList, @Param("releaseFlag") Integer releaseFlag);

    Integer selectPostCount(@Param("releaseFlag") Integer releaseFlag);
}