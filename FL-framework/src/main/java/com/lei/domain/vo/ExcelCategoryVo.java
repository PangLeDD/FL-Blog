package com.lei.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/2/2 18:08
 * @Version : 1.0.0
 */

@Data
public class ExcelCategoryVo {

    //分类名
    @ExcelProperty("分类名")
    private String name;

    @ExcelProperty("描述信息")
    private String description;

    @ExcelProperty("状态:正常为0，禁用为1")
    private Long status;
}
