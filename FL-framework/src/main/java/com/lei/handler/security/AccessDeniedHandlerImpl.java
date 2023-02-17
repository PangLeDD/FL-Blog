package com.lei.handler.security;

import com.alibaba.fastjson.JSON;
import com.lei.Enum.AppHttpCodeEnum;
import com.lei.domain.entity.ResponseResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/6 14:52
 * @Version : 1.0.0
 */

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        //响应回前端
        ResponseResult errorResult = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        httpServletResponse.setStatus(200);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("utf8");
        httpServletResponse.getWriter().print(JSON.toJSONString(errorResult));
    }
}
