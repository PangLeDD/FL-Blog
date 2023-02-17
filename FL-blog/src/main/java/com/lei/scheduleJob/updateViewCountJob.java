package com.lei.scheduleJob;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.domain.entity.Article;
import com.lei.mapper.ArticleMapper;
import com.lei.mapper.utils.RedisCache;
import com.lei.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/9/19 18:52
 * @Version : 1.0.0
 */

@Component
public class updateViewCountJob{

    @Autowired
    RedisCache redisCache;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleService articleService;
    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount(){
        Map<String, Integer> map = redisCache.getCacheMap("article:viewCount");
        map.forEach((K,V)->{
            LambdaUpdateWrapper<Article> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(Article::getId,Long.valueOf(K));
            wrapper.set(Article::getViewCount,V.longValue());
            articleMapper.update(null,wrapper);
            System.out.println("定时任务执行了,更新了数据库中的浏览记录!");
        });

    }
}
