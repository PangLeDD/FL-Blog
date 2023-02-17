package com.lei.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lei.domain.entity.Tag;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/30 16:32
 * @Version : 1.0.0
 */

@Data
public class AddArticleDto {
    private Integer id;

    private String title;

    private String thumbnail;

    private String isTop;

    private String isComment;

    private String content;

    private List<Long> tags;

    private Long categoryId;

    private String summary;

    private String status;

    private Date updateTime;

    private Long updateBy;

    private Long viewCount;

    private Integer delFlag;

    private Long createBy;

    private Date createTime;


}
