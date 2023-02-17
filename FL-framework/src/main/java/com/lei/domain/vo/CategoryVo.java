package com.lei.domain.vo;

import lombok.Data;

@Data
public class CategoryVo {

    private Long id;
    //分类名
    private String name;

    private String description;

    private Long status;
}
