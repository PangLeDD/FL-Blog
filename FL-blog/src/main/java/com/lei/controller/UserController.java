package com.lei.controller;

import com.lei.annotation.SystemLog;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.User;
import com.lei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.Valid;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/9/9 19:28
 * @Version : 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.getUserInfo();
    }


    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }


    @PostMapping("/register")
    public ResponseResult register(@Valid @RequestBody User user){
        return userService.register(user);
    }


}
