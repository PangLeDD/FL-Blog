package com.lei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.domain.dto.AddArticleDto;
import com.lei.domain.entity.Article;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.ArticleListVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult getArticleList(Integer pageNum, Integer pageSize, String categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto article);

    ResponseResult getArticle(Integer pageNum, Integer pageSize, ArticleListVo article);

    ResponseResult getArticleById(Long id);

    ResponseResult updateArticle(AddArticleDto articleDto);

    ResponseResult deleteArticle(Integer id);
}
