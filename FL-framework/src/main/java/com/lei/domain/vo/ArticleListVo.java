package com.lei.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleListVo {

    private Integer id;

    private String title;

    private String summary;

    private String categoryName;

    private String thumbnail;

    private Long viewCount;

    private Date createTime;

}
