package com.lei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-26 17:45:02
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeysByUserId(Long id);

    void deleteOldMenuIdByRoleId(@Param("roleId") Long roleId);

    void saveNewMenuIdByRoleId(@Param("roleId")Long roleId,@Param("newMenuId") Long newMenuId);
}

