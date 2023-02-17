package com.lei.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.Enum.AppHttpCodeEnum;
import com.lei.domain.dto.ChangeStatusDto;
import com.lei.domain.dto.RoleDto;
import com.lei.domain.entity.Menu;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.Role;
import com.lei.domain.vo.MenuVo;
import com.lei.domain.vo.PageVo;
import com.lei.domain.vo.RoleVo;
import com.lei.mapper.MenuMapper;
import com.lei.mapper.RoleMapper;
import com.lei.mapper.utils.BeanCopyUtils;
import com.lei.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-01-26 17:45:03
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    MenuMapper menuMapper;


    @Override
    public List<String> selectRoleKeysByUserId(Long id) {
        if (id == 1L){
            ArrayList<String> list = new ArrayList<>();
            list.add("admin");
            return list;
        }

        return getBaseMapper().selectRoleKeysByUserId(id);
    }

    @Override
    public ResponseResult getRoleList(Long pageNum, Long pageSize, RoleVo roleVo) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(roleVo.getRoleName()),Role::getRoleName,roleVo.getRoleName());
        wrapper.eq(StringUtils.hasText(roleVo.getStatus()),Role::getStatus,roleVo.getStatus());
        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);
        List<RoleVo> menuVos = BeanCopyUtils.copyBeanList(page.getRecords(), RoleVo.class);
        return ResponseResult.okResult(new PageVo(menuVos,page.getTotal()));
    }

    @Override
    public ResponseResult changeStatus(ChangeStatusDto changeStatusDto) {
        Role role = getById(changeStatusDto.getRoleId());
        role.setStatus(changeStatusDto.getStatus());
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getId,changeStatusDto.getRoleId());
        update(role,wrapper);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        if (!save(role)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
        }
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getRoleName,role.getRoleName());
        Role one = getOne(wrapper);
        roleDto.getMenuIds().forEach(menuId->{menuMapper.saveRoleMenuId(one.getId(),menuId);});
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(role,RoleDto.class));
    }

    @Override
    @Transactional
    public ResponseResult updateRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        List<Long> newMenuIds = roleDto.getMenuIds();
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getId,role.getId());
        update(role,wrapper);
        //删除之前的MenuId
        getBaseMapper().deleteOldMenuIdByRoleId(role.getId());
        newMenuIds.forEach(newMenuId-> getBaseMapper().saveNewMenuIdByRoleId(role.getId(),newMenuId));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<List<Role>> listAllRole() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getStatus,"0");
        List<Role> list = list(wrapper);
        return ResponseResult.okResult(list);
    }

}

