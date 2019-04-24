package com.blogging.aps.persistence;


import com.blogging.aps.model.entity.post.PostInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostInfoEntityMapper {
    int insert(PostInfoEntity record);

    int insertSelective(PostInfoEntity record);

    List<PostInfoEntity> selectLatestFivePosts();

    PostInfoEntity selectPostByPostId(@Param("postId") String postId, @Param("releaseFlag") Integer releaseFlag);

    PostInfoEntity selectPostByPostIdWithoutDel(@Param("postId") String postId);

    PostInfoEntity selectPreviousPost(@Param("id") Integer id, @Param("postId") String postId);

    PostInfoEntity selectNextPost(@Param("id") Integer id, @Param("postId") String postId);

    List<PostInfoEntity> selectPostListByPaging(@Param("releaseFlag") Integer releaseFlag, @Param("delFlag") Integer delFlag);

    List<PostInfoEntity> selectPostByIdList(@Param("postIdList") List<String> postIdList, @Param("releaseFlag") Integer releaseFlag);

    Integer selectPostCount(@Param("releaseFlag") Integer releaseFlag, @Param("delFlag") Integer delFlag);

    List<PostInfoEntity> selectDrafts(@Param("releaseFlag") Integer releaseFlag);

    List<PostInfoEntity> selectRubbish(@Param("delFlag") Integer delFlag);

    void updateByPrimaryKeySelective(PostInfoEntity postInfoEntity);

    void deletePostByPostId(@Param("postId") String postId);
}