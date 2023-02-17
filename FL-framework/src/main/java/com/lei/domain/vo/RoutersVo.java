package com.lei.domain.vo;

import com.lei.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/27 18:22
 * @Version : 1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {
    List<Menu> menus;
}
