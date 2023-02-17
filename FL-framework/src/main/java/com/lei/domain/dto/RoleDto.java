package com.lei.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/2/1 11:02
 * @Version : 1.0.0
 */
@Data
public class RoleDto {
    private Long id;
    private String roleKey;
    private Integer roleSort;
    private String roleName;
    private String status;
    private List<Long> menuIds;
    private String remark;
}
