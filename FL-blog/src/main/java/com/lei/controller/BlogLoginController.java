package com.lei.controller;

import com.lei.Enum.AppHttpCodeEnum;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.User;
import com.lei.exception.SystemException;
import com.lei.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/2 17:47
 * @Version : 1.0.0
 */

@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.REQUEST_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
