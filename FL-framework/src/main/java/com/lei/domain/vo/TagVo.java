package com.lei.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/29 18:23
 * @Version : 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagVo {

    private Long id;

    //标签名
    private String name;

    //备注
    private String remark;

}
