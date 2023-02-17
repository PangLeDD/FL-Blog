package com.lei.domain.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * (Article)表实体类
 *
 * @author makejava
 * @since 2022-05-29 12:31:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("fl_article")
public class Article implements Serializable{
    //主键
    @TableId
    private Integer id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章类型：1文章 2草稿
    private String type;
    //文章摘要
    private String summary;
    //所属分类
    private Long categoryId;
    //所属分类的名称 (数据库中没有此字段) 后面自己set进去使用，返回该字段
    @TableField(exist = false)
    private String categoryName;
    //缩略图
    private String thumbnail;
    //是否置顶 （0否，1是）
    private String isTop;
    //状态 0已发布 1草稿
    private String status;
    //评论数
    private Integer commentCount;
    //访问量
    private Long viewCount;
    //是否允许评论 1是 0否
    private String isComment;


    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    //删除标志位 (0代表未删除）
    private Integer delFlag;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;



}

