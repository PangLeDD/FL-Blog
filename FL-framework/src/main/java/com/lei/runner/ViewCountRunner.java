package com.lei.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lei.domain.entity.Article;
import com.lei.mapper.ArticleMapper;
import com.lei.mapper.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/9/15 16:38
 * @Version : 1.0.0
 */

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    RedisCache redisCache;
    @Autowired
    ArticleMapper articleMapper;
    @Override
    public void run(String... args) throws Exception {

        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> collect = articles.stream()
                .collect(Collectors.toMap(article1 -> article1.getId().toString(), article -> article.getViewCount().intValue()));
        redisCache.setCacheMap("article:viewCount",collect);
    }
}
