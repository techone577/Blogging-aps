package com.blogging.aps.persistence;

import com.blogging.aps.model.dto.BMPostListQueryDTO;
import com.blogging.aps.model.entity.post.PostInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author techoneduan
 * @date 2019/4/18
 */
public interface PostTagWrapperMapper {

    List<PostInfoEntity> selectPostByParams(@Param("title") String title, @Param("tags") List<String> tags);
}
