package com.lei.domain.vo;

import com.lei.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/2/1 15:08
 * @Version : 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoleMenuTreeVo {
    private List<MenuTreeVo> menus;
    private List<Long> checkedKeys;
}
