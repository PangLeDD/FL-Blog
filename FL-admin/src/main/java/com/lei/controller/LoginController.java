package com.lei.controller;

import com.lei.Enum.AppHttpCodeEnum;
import com.lei.domain.entity.LoginUser;
import com.lei.domain.entity.Menu;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.User;
import com.lei.domain.vo.AdminUserInfoVo;
import com.lei.domain.vo.RoutersVo;
import com.lei.exception.SystemException;
import com.lei.mapper.utils.SecurityUtils;
import com.lei.service.LoginService;
import com.lei.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/26 16:15
 * @Version : 1.0.0
 */


@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    MenuService menuService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){

        if (!StringUtils.hasText(user.getUsername())){
            throw new SystemException(AppHttpCodeEnum.REQUEST_USERNAME);
        }
        return loginService.login(user);
    }


    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        return loginService.getInfo();
    }


    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        return loginService.getRouters();
    }


    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

}
