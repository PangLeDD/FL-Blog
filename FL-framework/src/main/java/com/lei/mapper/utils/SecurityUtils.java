package com.lei.mapper.utils;

import com.lei.domain.entity.LoginUser;
import com.lei.domain.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/7 20:43
 * @Version : 1.0.0
 */

public class SecurityUtils {

    public static Authentication getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    public static LoginUser getLoginUser(){
        return (LoginUser) getAuthentication().getPrincipal();
    }
    public static Long getUserId(){
        return getLoginUser().getUser().getId();
    }



    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && id.equals(1L);
    }

}
