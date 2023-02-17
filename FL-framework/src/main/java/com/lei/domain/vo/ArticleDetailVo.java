package com.lei.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;


@Data
public class ArticleDetailVo {

    private Integer id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分类
    private Long categoryId;
    //所属分类的名称 (数据库中没有此字段) 后面自己set进去使用，返回该字段

    private String categoryName;
    //缩略图
    private String thumbnail;

    //访问量
    private Long viewCount;

    private Date createTime;


}
