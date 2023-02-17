package com.lei.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/28 20:58
 * @Version : 1.0.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagListDto {
    private String name;
    private String remark;
}
