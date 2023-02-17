package com.lei.domain.vo;

import lombok.Data;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/1 13:39
 * @Version : 1.0.0
 */
@Data
public class LinkVo {

    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;

    private String status;

}
