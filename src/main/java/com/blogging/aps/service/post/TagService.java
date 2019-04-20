package com.blogging.aps.service.post;


import com.blogging.aps.model.dto.TagAmountDTO;
import com.blogging.aps.model.entity.post.TagEntity;
import com.blogging.aps.model.entity.post.TagRelationEntity;
import com.blogging.aps.persistence.TagEntityMapper;
import com.blogging.aps.persistence.TagRelationEntityMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {


    @Autowired
    private TagEntityMapper tagEntityMapper;

    @Autowired
    private TagRelationEntityMapper tagRelationEntityMapper;

    public List<TagEntity> queryTagByName(String name) {
        return tagEntityMapper.selectTagByName(name);
    }

    public void insertTag(TagEntity tagEntity) {
        tagEntityMapper.insertSelective(tagEntity);
    }

    public void insertTagRelation(TagRelationEntity tagRelationEntity) {
        tagRelationEntityMapper.insertSelective(tagRelationEntity);
    }

    public List<TagRelationEntity> queryByPostId(String postId) {
        List<TagRelationEntity> relationEntities = tagRelationEntityMapper.selectByPostId(postId);
        return relationEntities;

    }

    public List<TagRelationEntity> queryByPostIdWithoutDel(String postId) {
        List<TagRelationEntity> relationEntities = tagRelationEntityMapper.selectByPostIdWithoutDel(postId);
        return relationEntities;

    }

    public List<TagEntity> queryByTagIdList(List<Integer> tagIdList) {
        return tagEntityMapper.selectTagByIdList(tagIdList);
    }

    public List<TagAmountDTO> queryTagAmount(){
        return tagRelationEntityMapper.selectTagAmount();
    }

    public List<TagAmountDTO> queryAllTagAmount(){
        return tagRelationEntityMapper.selectAllTagAmount();
    }

    public List<TagRelationEntity> queryTagReLationByTagIdPaging(Integer tagId,Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return tagRelationEntityMapper.selectByTagIdPaging(tagId);
    }

    public List<TagRelationEntity> queryTagRelationByTagId(Integer tagId){
        return tagRelationEntityMapper.selectByTagId(tagId);
    }

    public List<TagEntity> queryTagList(){
        return tagEntityMapper.selectTagList();
    }

    public void updateTagRelation(TagRelationEntity tagRelationEntity){
        tagRelationEntityMapper.updateByPostIdSelective(tagRelationEntity);
    }
}
