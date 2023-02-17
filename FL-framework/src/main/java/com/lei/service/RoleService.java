package com.lei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.domain.dto.ChangeStatusDto;
import com.lei.domain.dto.RoleDto;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.Role;
import com.lei.domain.vo.RoleVo;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-01-26 17:45:03
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeysByUserId(Long id);

    ResponseResult getRoleList(Long pageNum, Long pageSize, RoleVo roleVo);

    ResponseResult changeStatus(ChangeStatusDto changeStatusDto);

    ResponseResult addRole(RoleDto roleDto);

    ResponseResult getRoleById(Long id);

    ResponseResult updateRole(RoleDto roleDto);

    ResponseResult deleteRole(Long id);

    ResponseResult<List<Role>> listAllRole();

}

