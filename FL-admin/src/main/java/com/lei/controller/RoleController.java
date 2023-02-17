package com.lei.controller;

import com.lei.domain.dto.ChangeStatusDto;
import com.lei.domain.dto.RoleDto;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.Role;
import com.lei.domain.vo.RoleVo;
import com.lei.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/31 18:19
 * @Version : 1.0.0
 */

@RestController
@RequestMapping("/system/role")
public class RoleController {


    @Autowired
    RoleService roleService;


    @GetMapping("/list")
    public ResponseResult getRoleList(Long pageNum, Long pageSize, RoleVo roleVo){
        return roleService.getRoleList(pageNum,pageSize,roleVo);
    }


    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeStatusDto changeStatusDto){
        return roleService.changeStatus(changeStatusDto);
    }
    @PostMapping
    public ResponseResult addRole(@RequestBody RoleDto roleDto){
        return roleService.addRole(roleDto);
    }
    @GetMapping("/{id}")
    public ResponseResult getRoleById(@PathVariable("id") Long id){
        return roleService.getRoleById(id);
    }
    @PutMapping
    public ResponseResult updateRole(@RequestBody RoleDto roleDto){
        return roleService.updateRole(roleDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long id){
        return roleService.deleteRole(id);
    }


    @GetMapping("/listAllRole")
    public ResponseResult<List<Role>> listAllRole(){
        return roleService.listAllRole();
    }

}
