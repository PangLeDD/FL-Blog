package com.lei.domain.vo;

import com.lei.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/31 21:24
 * @Version : 1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuTreeVo {

    private List<MenuTreeVo> children;
    private Long id;
    private Long parentId;
    private String label;
}
