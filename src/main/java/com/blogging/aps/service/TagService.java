package com.blogging.aps.service;


import com.blogging.aps.model.dto.TagAmountDTO;
import com.blogging.aps.model.entity.post.TagEntity;
import com.blogging.aps.model.entity.post.TagRelationEntity;
import com.blogging.aps.persistence.TagEntityMapper;
import com.blogging.aps.persistence.TagRelationEntityMapper;
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
        return tagRelationEntityMapper.selectByTagIdPaging(tagId,pageNum,pageSize);
    }

    public List<TagRelationEntity> queryTagRelationByTagId(Integer tagId){
        return tagRelationEntityMapper.selectByTagId(tagId);
    }
}
