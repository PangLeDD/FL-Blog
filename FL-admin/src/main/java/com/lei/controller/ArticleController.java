package com.lei.controller;


import com.lei.domain.dto.AddArticleDto;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.ArticleListVo;
import com.lei.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto article){
        return articleService.addArticle(article);
    }

    @GetMapping("/list")
    public ResponseResult getArticle(Integer pageNum, Integer pageSize, ArticleListVo article){
        return articleService.getArticle(pageNum,pageSize,article);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleById(@PathVariable("id") Long id){
        return articleService.getArticleById(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody AddArticleDto articleDto){
        return articleService.updateArticle(articleDto);
    }


    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Integer id){
        return articleService.deleteArticle(id);
    }


}

