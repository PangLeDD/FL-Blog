package com.lei.handler.security;

import com.alibaba.fastjson.JSON;
import com.lei.Enum.AppHttpCodeEnum;
import com.lei.domain.entity.ResponseResult;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/6 14:47
 * @Version : 1.0.0
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();

        ResponseResult errorResult = null;
        if(e instanceof InsufficientAuthenticationException){
            errorResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else if(e instanceof InternalAuthenticationServiceException){
            errorResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }else{
            errorResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),"认证或授权失败！");
        }

        //InternalAuthenticationServiceException  用户名密码错误
        //InsufficientAuthenticationException   没带token的认证失败

        //响应回前端
        httpServletResponse.setStatus(200);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("utf8");
        httpServletResponse.getWriter().print(JSON.toJSONString(errorResult));


    }
}
