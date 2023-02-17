package com.lei.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/31 18:26
 * @Version : 1.0.0
 */
@Data
public class RoleVo {
    private Long id;
    private String roleKey;
    private String roleSort;
    private String roleName;
    private String status;
}
