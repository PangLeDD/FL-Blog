package com.lei.controller;

import com.lei.domain.dto.UserDto;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.UserVo;
import com.lei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/2/1 18:08
 * @Version : 1.0.0
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResponseResult getUserList(Long pageNum, Long pageSize, UserVo userVo){
        return userService.getUserList(pageNum,pageSize,userVo);
    }
    @PostMapping
    public ResponseResult addUser(@RequestBody UserVo userVo){
        return userService.addUser(userVo);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long userId){
        return userService.deleteUser(userId);
    }

    @GetMapping("/{id}")
    public ResponseResult getUserMsg(@PathVariable("id") Long userId){
        return userService.getUserMsg(userId);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UserVo userVo){
        return userService.updateUser(userVo);
    }

}
