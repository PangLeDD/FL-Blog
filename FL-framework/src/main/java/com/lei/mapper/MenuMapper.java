package com.lei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-26 17:32:14
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> getPermsById(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long id);

    List<Menu> selectAllRouterMenu();

    void saveRoleMenuId(@Param("roleId") Long roleId, @Param("menuId")Long menuId);

    List<Menu> getMenuByRoleId(@Param("roleId") Long id);

    List<Long> getMenuIdByRoleId(@Param("menuId") Long id);
}

