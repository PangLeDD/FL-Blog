package com.lei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.domain.entity.Menu;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.MenuVo;
import com.lei.domain.vo.RoutersVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-01-26 17:32:15
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsById(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getMenuList(Long status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(MenuVo menuVo);

    ResponseResult deleteById(Long id);

    ResponseResult getMenuTree();

    ResponseResult roleMenuTree(Long id);
}

