package com.lei.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.Enum.AppHttpCodeEnum;
import com.lei.constants.SystemConstants;
import com.lei.domain.dto.AddArticleDto;
import com.lei.domain.entity.Article;
import com.lei.domain.entity.Category;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.ArticleDetailVo;
import com.lei.domain.vo.ArticleListVo;
import com.lei.domain.vo.HotArticleVo;
import com.lei.domain.vo.PageVo;
import com.lei.exception.SystemException;
import com.lei.mapper.ArticleMapper;
import com.lei.mapper.TagMapper;
import com.lei.mapper.utils.RedisCache;
import com.lei.service.ArticleService;
import com.lei.service.CategoryService;
import com.lei.mapper.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    CategoryService categoryService;

    @Autowired
    RedisCache redisCache;

    @Autowired
    TagMapper tagMapper;
    /**
     * 访问最热即浏览量最高的文章前十名返回。
     * @return
     */
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //status为0的是发布的  1是草稿就不查询
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量来降序排序  ASC为升序
        wrapper.orderByDesc(Article::getViewCount);
        //        current,  size,       total,  searchCount
        //分页查询 查询第一页  一页10条数据  总共n条   查询总共的条数目
        Page<Article> page = new Page<>(1,10);
        page(page, wrapper);
        //获取page中的记录
        List<Article> articles = page.getRecords();

        //VO优化
//        原始写法：List<HotArticleVo> collect = articles.stream()
//                .map(article -> {
//                    HotArticleVo vo = new HotArticleVo();
//                    BeanUtils.copyProperties(article, vo);
//                    return vo;
//                }).collect(Collectors.toList());

        //VO优化：使用自己的BeanCopyUtils工具类

        List<HotArticleVo> list = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);


        //封装到ResponseResult对象中返回
        //VO优化
        return ResponseResult.okResult(list);
    }

    /**
     * 实现要求：前端访问此接口，展示所有的文章(Status为已发布)信息，根据top的值降序，如果通过分类标签查询就值展示相应标签的文章信息。
     */
    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize,String categoryId) {
        //查询文章信息
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //有且不为空就执行此操作。
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && Long.parseLong(categoryId) > 0,Article::getCategoryId,Long.parseLong(categoryId));
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询（不能一下子全部把数据给前端，会造成资源浪费和服务器压力过大）
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);
        List<Article> records = page.getRecords();

        //
        records.stream()
                .forEach(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()));

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);


        //根据前端接口二次优化。
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }


    /**
     * 根据ID查询文章,并且查出文章对应的分类目录
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据ID查询文章
        Article article = getById(id);
        //将数据库中的浏览记录还没有更新到数据库中的数据拿出来返回给前台
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        if (Objects.isNull(article)) {
            return ResponseResult.okResult("查询不到此文章！请输入正确的ID");
        }
        //查询文章对应的分类
        Long categoryId = article.getCategoryId();

        //VO优化
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据有id查询分类名称
        Category category = categoryService.getById(categoryId);

        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }

        //一下语句虽然简单，但是缺乏条件判断，漏洞百出！
        //articleDetailVo.setCategoryName(categoryService.getById(article.getCategoryId()).getName());

        return ResponseResult.okResult(articleDetailVo);
    }

    /**
     * 更新Redis中的浏览数量 ViewCount
    */
    @Override
    public ResponseResult updateViewCount(Long id) {

        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);

        return ResponseResult.okResult();
    }

    /**
     * @description 增添博文
     * @author XiaoFan DaWang
     * @time 2023/1/30 16:16
     * @return ResponseResult
    */
    @Override
    @Transactional
    public ResponseResult addArticle(AddArticleDto article) {

        Article addArticle = BeanCopyUtils.copyBean(article, Article.class);

        List<Long> tags = article.getTags();

        save(addArticle);
        tags.forEach(tag->{
           tagMapper.saveArticleTags(addArticle.getId(),tag);
       });
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticle(Integer pageNum, Integer pageSize, ArticleListVo article) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //模糊匹配查询
        wrapper.like(StringUtils.hasText(article.getTitle()),Article::getTitle,article.getTitle());
        wrapper.like(StringUtils.hasText(article.getSummary()),Article::getSummary,article.getSummary());
        //这里偷懒了，懒得在创建一个心的实体类来封装相应的数据，直接从Article原始文章里面查找就行了。
        wrapper.select(Article::getCategoryId,
                        Article::getContent,
                        Article::getCreateTime,
                        Article::getId,
                        Article::getIsComment,
                        Article::getIsTop,
                        Article::getStatus,
                        Article::getSummary,
                        Article::getThumbnail,
                        Article::getTitle,
                        Article::getViewCount
                );
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(),page.getTotal()));
    }

    @Override
    public ResponseResult getArticleById(Long id) {
        Article article = getById(id);
        if (ObjectUtils.isEmpty(article)){
            return ResponseResult.errorResult(AppHttpCodeEnum.ID_ERROR);
        }
        List<Long> tags = tagMapper.getTagsById(article.getId());
        AddArticleDto addArticleDto = BeanCopyUtils.copyBean(article, AddArticleDto.class);
        addArticleDto.setTags(tags);
        return ResponseResult.okResult(addArticleDto);
    }

    @Override
    @Transactional
    public ResponseResult updateArticle(AddArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        if (ObjectUtils.isEmpty(article)){
            return ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }

        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getId,article.getId());
        update(article,wrapper);
        articleDto.getTags().forEach(tag->{
            tagMapper.updateArticleTags(article.getId(),tag);
        });
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Integer id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}
