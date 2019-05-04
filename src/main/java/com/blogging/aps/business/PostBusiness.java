package com.blogging.aps.business;

import com.blogging.aps.model.dto.*;
import com.blogging.aps.model.dto.BM.BMCategoryQueryRespDTO;
import com.blogging.aps.model.entity.Response;
import com.blogging.aps.model.entity.post.*;
import com.blogging.aps.model.enums.ErrorCodeEnum;
import com.blogging.aps.service.post.CategoryService;
import com.blogging.aps.service.post.StatisticService;
import com.blogging.aps.service.post.TagService;
import com.blogging.aps.service.post.PostService;
import com.blogging.aps.support.exception.UnifiedException;
import com.blogging.aps.support.utils.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author techoneduan
 * @date 2019/1/18
 */

@Component
public class PostBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(PostBusiness.class);

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private CategoryService categoryService;


    /**
     * 添加新博客
     *
     * @param entity
     * @return
     */
    public Response addPost(BMPostAddDTO entity) {
        PostInfoEntity postInfoEntity = new PostInfoEntity();
        PassageEntity passageEntity = new PassageEntity();
        if (StringUtils.isBlank(entity.getTitle()))
            throw new UnifiedException(ErrorCodeEnum.TITLE_EMPTY_ERROR);
        buildArticle(postInfoEntity, passageEntity, entity);
        //插入文章
        postService.insertPassgae(passageEntity);
        postService.insertPost(postInfoEntity);
        //储存对应tag
        buildTags(entity.getTags(), postInfoEntity.getPostId());
        return ResponseBuilder.build(true, null);
    }

    /**
     * 首页文章查询
     *
     * @return
     */
    public Response homepagePostQuery() {
        List<PostInfoEntity> postInfoEntities = postService.queryLatestFivePosts();
        if (postInfoEntities.size() == 0)
            return ResponseBuilder.build(true, null);
        List<HomePagePostListDTO> homePagePostListDTOS = buildHomePagePostRespDTO(postInfoEntities);
        List<TagInfoDTO> tagInfoDTOS = queryTagInfoList();
        List<CategoryInfo> categoryInfos = queryCategoryList(true);
        HomePageRespDTO respDTO = new HomePageRespDTO() {
            {
                setHomePagePostList(homePagePostListDTOS);
                setTagInfoList(tagInfoDTOS);
                setCategoryInfoList(categoryInfos);
            }
        };
        return ResponseBuilder.build(true, respDTO);
    }

    public List<TagInfoDTO> queryTagInfoList() {
        List<TagAmountDTO> tagAmountDTOS = tagService.queryTagAmount();
        tagAmountDTOS = tagAmountDTOS.stream().sorted(Comparator.comparing(TagAmountDTO::getTagId)).collect(Collectors.toList());
        List<TagEntity> tagEntities = tagService.queryByTagIdList(tagAmountDTOS.stream().map(item -> item.getTagId()).collect(Collectors.toList()));
        return buildTagInfoList(tagAmountDTOS, tagEntities);
    }

    public List<CategoryInfo> queryCategoryList(boolean home) {
        List<CategoryEntity> entities = categoryService.queryCategories();
        if (null == entities || entities.size() == 0)
            return null;
        if (entities.size() < 5)
            return buildCategoryInfos(entities);
        if (home) {
            entities = entities.stream().sorted(Comparator.comparing(CategoryEntity::getPostNum)).collect(Collectors.toList());
            List<CategoryEntity> homes = new ArrayList<>();
            for (int i = entities.size() - 1; i >= entities.size() - 5; --i) {
                homes.add(entities.get(i));
            }
            return buildCategoryInfos(homes);
        }
        return buildCategoryInfos(entities);
    }

    private List<CategoryInfo> buildCategoryInfos(List<CategoryEntity> entities) {
        List<CategoryInfo> infos = new ArrayList<>();
        for (CategoryEntity entity : entities) {
            CategoryInfo info = new CategoryInfo();
            info.setName(entity.getName());
            info.setNum(checkPostNum(entity.getName(), false));
            info.setSummary(entity.getSummary());
            info.setUrl(entity.getCoverUrl());
            infos.add(info);
        }
        return infos;
    }

    public Integer checkPostNum(String name, boolean update) {
        CategoryEntity categoryEntity = categoryService.queryByName(name);
        if (null != categoryEntity) {
            PostPagingQueryDTO queryDTO = new PostPagingQueryDTO() {
                {
                    setTypeValue(name);
                    setPageNum(1);
                    setPageSize(5);
                }
            };
            PageInfo pageInfo = new PageInfo(postService.queryPostByCategory(queryDTO));
            if (update)
                categoryService.updateNumByName(name, Integer.valueOf(String.valueOf(pageInfo.getTotal())));
            return Integer.valueOf(String.valueOf(pageInfo.getTotal()));
        }
        return 0;
    }

    private List<TagInfoDTO> buildTagInfoList(List<TagAmountDTO> tagAmountDTOS, List<TagEntity> tagEntities) {
        List<TagInfoDTO> tagInfoDTOS = new ArrayList<>();
        for (int i = 0; i < tagAmountDTOS.size(); ++i) {
            TagInfoDTO tagInfoDTO = new TagInfoDTO();
            tagInfoDTO.setTagName(tagEntities.get(i).getTagName());
            tagInfoDTO.setTagNum(tagAmountDTOS.get(i).getAmount());
            tagInfoDTOS.add(tagInfoDTO);
        }
        return tagInfoDTOS;
    }

    /**
     * 查询博客展示信息
     *
     * @param reqDTO
     * @return
     */
    public Response queryBlog(PostQueryReqDTO reqDTO) {
        PostInfoEntity postInfoEntity = postService.queryPostByPostId(reqDTO.getPostId(), 1);
        if (null == postInfoEntity)
            throw new UnifiedException(ErrorCodeEnum.FOUR_ZERO_FOUR_ERROR,"文章不存在");
        PassageEntity passageEntity = postService.queryPassageByPassageId(postInfoEntity.getPassageId());
        String htmlContent = MarkDownUtils.markDownToHtml(passageEntity.getContent());

        PostInfoEntity previous = postService.queryPreviousPost(postInfoEntity.getId(), reqDTO.getPostId());
        PostInfoEntity next = postService.queryNextPost(postInfoEntity.getId(), reqDTO.getPostId());

        PostInfoDTO previousDTO = null, nextDTO = null;
        if (null != previous) {
            previousDTO = new PostInfoDTO() {
                {
                    setPostId(previous.getPostId());
                    setTitle(previous.getTitle());
                }
            };
        }
        if (null != next) {
            nextDTO = new PostInfoDTO() {
                {
                    setPostId(next.getPostId());
                    setTitle(next.getTitle());
                }
            };
        }
        List<String> tags = getPostTags(reqDTO.getPostId());
        String TOC = MarkDownUtils.generateTOCForMD(passageEntity.getContent());
        BlogQueryRespDTO respDTO = new BlogQueryRespDTO() {
            {
                setPostId(reqDTO.getPostId());
                setTitle(postInfoEntity.getTitle());
                setHtmlContent(htmlContent);
                setAddTime(DateUtils.formatDate(postInfoEntity.getAddTime()));
                setTagList(tags);
                setTOC(TOC);
                setCategory(postInfoEntity.getCategory());
            }
        };
        respDTO.setPreviousPost(previousDTO);
        respDTO.setNextPost(nextDTO);
        StatisticInfo statisticInfo = getStatisticInfo(reqDTO.getPostId());
        respDTO.setStatisticInfo(statisticInfo);
        return ResponseBuilder.build(true, respDTO);
    }


    /**
     * 统计信息
     */
    private StatisticInfo getStatisticInfo(String postId) {
        List<StatisticEntity> statisticEntities = statisticService.queryByPostId(postId);
        StatisticInfo statisticInfo = new StatisticInfo();
        if (null == statisticEntities || statisticEntities.size() == 0) {
            statisticInfo.setPageView(0l);
            statisticInfo.setReadTime("No One Read");
        } else {
            statisticInfo.setPageView(statisticEntities.get(0).getStatistic());
            statisticInfo.setReadTime(caculateReadTime(statisticEntities.get(0).getUpdateTime()));
        }
        return statisticInfo;
    }


    private String caculateReadTime(Date date) {
        long timeMillions = date.getTime();
        long now = System.currentTimeMillis();
        long minus = now - timeMillions;
        StringBuilder sb = new StringBuilder();
        long minute = minus / 60000;
        if (minute > 0 && minute < 60) {
            return sb.append(minute + " minutes read").toString();
        }
        long second = minus / 1000;
        if (second > 0 && second < 60) {
            return sb.append(second + " seconds read").toString();
        }
        long hour = minus / 3600000;
        if (hour > 0 && hour < 24) {
            return sb.append(hour + " hours read").toString();
        }
        long day = hour / 24;
        if (day > 0 && day < 30) {
            return sb.append(day + " days read").toString();
        }
        long month = day / 30;
        if (month > 0 && month < 12) {
            return sb.append(month + " months read").toString();
        }
        if (month > 12) {
            return sb.append(month / 12 + " years read").toString();
        }
        return sb.append(minus + " mills read").toString();
    }

    /**
     * 分页查询所有博客列表
     *
     * @param queryDTO
     * @return
     */
    public Response postPagingQuery(PostPagingQueryDTO queryDTO) {
        if (null == queryDTO)
            return ResponseBuilder.build(true, "分页查询条件为空");
        List<PostInfoEntity> entities = postService.queryPostListByPaging(queryDTO);
        PageInfo pageInfo = new PageInfo(entities);
        if (null == pageInfo || pageInfo.getSize() == 0)
            return ResponseBuilder.build(true, "文章列表为空");
        Integer minId = entities.stream().min(Comparator.comparing(PostInfoEntity::getId)).get().getId();
        List<HomePagePostListDTO> homePagePostListDTOS = buildHomePagePostRespDTO(pageInfo.getList());
//        Integer totalAmount = postService.queryPostCount(queryDTO.getReleaseFlag(), queryDTO.getDelFlag());
        PostListQueryRespDTO respDTO = new PostListQueryRespDTO() {
            {
                setPostList(homePagePostListDTOS);
                setTotalNum(pageInfo.getTotal());
                setTagInfoList(queryTagInfoList());
                setCategoryInfoList(queryCategoryList(true));
                setMinId(minId);
            }
        };
        return ResponseBuilder.build(true, respDTO);
    }

    //TODO tag?

    private void buildArticle(PostInfoEntity postInfoEntity, PassageEntity passageEntity, BMPostAddDTO reqEntity) {
        postInfoEntity.setTitle(reqEntity.getTitle());
        postInfoEntity.setCategory(reqEntity.getCategory());
        postInfoEntity.setPostId(IdGenerator.generatePostId());
        postInfoEntity.setDelFlag(0);
        postInfoEntity.setMemberId(reqEntity.getMemberId());
        postInfoEntity.setSummary(reqEntity.getSummary());

        passageEntity.setPassageId(IdGenerator.generatePassageId());
        passageEntity.setDelFlag(0);
        passageEntity.setContent(reqEntity.getContent());
        postInfoEntity.setReleaseFlag(reqEntity.getReleaseFlag());

        Date now = new Date();
        postInfoEntity.setAddTime(now);
        postInfoEntity.setUpdateTime(now);
        passageEntity.setAddTime(now);
        passageEntity.setUpdateTime(now);

        postInfoEntity.setPassageId(passageEntity.getPassageId());

    }

    private void buildTags(List<String> tagList, String postId) {
        if (null == tagList || tagList.size() == 0)
            return;
        //去重
        tagList = tagList.stream().distinct().collect(Collectors.toList());
        tagList.forEach(item -> checkAndAddTag(item, postId));

    }


    //检查tag并添加对应关系
    private void checkAndAddTag(String tag, String postId) {
        List<TagEntity> tagEntityList = tagService.queryTagByName(tag);
        Integer tagId = null;
        if (null == tagEntityList || tagEntityList.size() == 0) {
            TagEntity tagEntity = new TagEntity() {
                {
                    setTagName(tag);
                    setDelFlag(0);
                    setAddTime(new Date());
                    setUpdateTime(new Date());

                }
            };
            tagService.insertTag(tagEntity);
            tagId = tagEntity.getId();
        }
        if (null == tagId) {
            tagId = tagEntityList.get(0).getId();
        }
        TagRelationEntity relationEntity = new TagRelationEntity() {
            {
                setPostId(postId);
                setDelFlag(0);
                setAddTime(new Date());
                setUpdateTime(new Date());
            }
        };
        relationEntity.setTagId(tagId);
        tagService.insertTagRelation(relationEntity);
    }

    public List<HomePagePostListDTO> buildHomePagePostRespDTO(List<PostInfoEntity> postInfoEntities) {
        List<HomePagePostListDTO> respDTOS = buildPostListDTO(postInfoEntities);
        respDTOS.stream().forEach(item -> {
            item.setAddTime(DateUtils.DateTimeToDate(item.getAddTime()));
            item.setUpdateTime(DateUtils.DateTimeToDate(item.getUpdateTime()));
        });
        for (HomePagePostListDTO homePagePostListDTO : respDTOS) {
            List<String> tagList = getPostTags(homePagePostListDTO.getPostId());
            //TODO 可以缓存tag列表
            if (null == tagList || tagList.size() == 0) {
                homePagePostListDTO.setTagList(new ArrayList<>());
                continue;
            }
            homePagePostListDTO.setTagList(tagList);
        }
        return respDTOS;
    }


    public List<HomePagePostListDTO> buildBMPostRespDTO(List<PostInfoEntity> postInfoEntities) {
        List<HomePagePostListDTO> respDTOS = buildPostListDTO(postInfoEntities);
        respDTOS.stream().forEach(item -> {
            item.setTagList(getPostTagsWithoutDel(item.getPostId()));
        });
        return respDTOS;
    }

    private List<HomePagePostListDTO> buildPostListDTO(List<PostInfoEntity> postInfoEntities) {
        List<HomePagePostListDTO> respDTOS = new ArrayList<>();
        for (PostInfoEntity postInfoEntity : postInfoEntities) {
            HomePagePostListDTO respDTO = new HomePagePostListDTO() {
                {
                    setPostId(postInfoEntity.getPostId());
                    setTitle(postInfoEntity.getTitle());
                    setCategory(postInfoEntity.getCategory());
                    setSummary(postInfoEntity.getSummary());
                    setReleaseFlag(postInfoEntity.getReleaseFlag());
                    setAddTime(DateUtils.formatDateTime(postInfoEntity.getAddTime()));
                    setUpdateTime(DateUtils.formatDateTime(postInfoEntity.getUpdateTime()));
                    setMemberId(postInfoEntity.getMemberId());
                }
            };
            PassageEntity passageEntity = postService.queryPassageByPassageId(postInfoEntity.getPassageId());
            if (null != passageEntity) {
                respDTO.setFirstImgUrl(RegexUtil.matchFirstImgUrlInMD(passageEntity.getContent()));
            }
            StatisticInfo statisticInfo = getStatisticInfo(postInfoEntity.getPostId());
            respDTO.setStatisticInfo(statisticInfo);
            respDTOS.add(respDTO);
        }
        return respDTOS;
    }

    public List<String> getPostTags(String postId) {
        List<TagRelationEntity> entities = tagService.queryByPostId(postId);
        if (null == entities || entities.size() == 0)
            return null;
        List<Integer> tagIdList = entities
                .stream().map(item -> item.getTagId()).collect(Collectors.toList());
        List<String> tagList = tagService.queryByTagIdList(tagIdList)
                .stream().map(item -> item.getTagName()).collect(Collectors.toList());
        return tagList;
    }

    //BM查询所有tagrelation无论有没有删除标志
    public List<String> getPostTagsWithoutDel(String postId) {

        List<Integer> tagIdList = tagService.queryByPostIdWithoutDel(postId)
                .stream().map(item -> item.getTagId()).collect(Collectors.toList());
        if (null == tagIdList || tagIdList.size() == 0)
            return null;
        List<String> tagList = tagService.queryByTagIdList(tagIdList)
                .stream().map(item -> item.getTagName()).collect(Collectors.toList());
        return tagList;
    }

    /**
     * 查询文章md格式
     */

    public Response queryMDPost(PostQueryReqDTO reqDTO) {
        String postId = reqDTO.getPostId();
        PostInfoEntity postInfoEntity = postService.queryPostByPostId(postId, null);
        if (null == postInfoEntity)
            return ResponseBuilder.build(true, "文章为空");

        PassageEntity passageEntity = postService.queryPassageByPassageId(postInfoEntity.getPassageId());
        if (null == passageEntity)
            return ResponseBuilder.build(true, "文章为空");

        return ResponseBuilder.build(true, passageEntity.getContent());
    }

    /**
     * 查询所有tag
     */
    public Response queryAllTags() {
        List<TagAmountDTO> tagAmountDTOS = tagService.queryTagAmount();
        if (null == tagAmountDTOS || tagAmountDTOS.size() == 0) {
            return ResponseBuilder.build(true, "标签列表为空");
        }
        tagAmountDTOS = tagAmountDTOS.stream().sorted(Comparator.comparing(TagAmountDTO::getTagId)).collect(Collectors.toList());
        List<TagEntity> tagEntities = tagService.queryByTagIdList(tagAmountDTOS.stream().map(item -> item.getTagId()).collect(Collectors.toList()));
        List<TagInfoDTO> allTagInfos = buildTagInfoList(tagAmountDTOS, tagEntities);
        List<TagInfoDTO> hotTagInfos = queryTagInfoList();
        TagShowRespDTO respDTO = new TagShowRespDTO() {
            {
                setAllTags(allTagInfos);
                setHotTags(hotTagInfos);
            }
        };
        return ResponseBuilder.build(true, respDTO);

    }

    /**
     * 查询所有分类
     *
     * @return
     */
    public Response queryCategoryInfos() {
        List<CategoryEntity> entities = categoryService.queryCategories();
        List<CategoryInfo> infos = new ArrayList<>();
        for (CategoryEntity entity : entities) {
            CategoryInfo info = new CategoryInfo();
            info.setName(entity.getName());
            info.setUrl(entity.getCoverUrl());
            info.setSummary(entity.getSummary());
            info.setNum(checkPostNum(entity.getName(), false));
            infos.add(info);
        }
        List<TagInfoDTO> hotTags = queryTagInfoList();
        Map<String, Object> map = new HashMap<>();
        map.put("hotTags", hotTags);
        map.put("categories", infos);
        map.put("total", entities.size());
        return ResponseBuilder.build(true, map);
    }
}
