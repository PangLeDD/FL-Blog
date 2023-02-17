package com.lei;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import com.lei.domain.entity.Article;
import com.lei.mapper.ArticleMapper;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/7 18:05
 * @Version : 1.0.0
 */

@SpringBootTest
public class Test01 {


    @Autowired
    ArticleMapper articleMapper;
    @Test
    public void test(){
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> collect = articles.stream()
                .collect(Collectors.toMap(article1 -> article1.getId().toString(), article -> article.getViewCount().intValue()));
        System.out.println(collect);
    }
}
