package com.lei.filter;

import com.alibaba.fastjson.JSON;
import com.lei.Enum.AppHttpCodeEnum;
import com.lei.domain.entity.LoginUser;
import com.lei.domain.entity.ResponseResult;
import com.lei.mapper.utils.JwtUtil;
import com.lei.mapper.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/2 19:50
 * @Version : 1.0.0
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {

        //首先检查请求头是否携带token，如果没有直接放行
        String token = req.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(req,resp);
            return;
        }
        //有token先解析token就去redis中查找信息
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时，token非法都需要重新登录！
            ResponseResult errorResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf8");
            resp.getWriter().print(JSON.toJSONString(errorResult));
            return;
        }
        //查出redis中的信息
        String userId = claims.getSubject();
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);

        if(Objects.isNull(loginUser)){
            //说明登录过期  提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf8");
            resp.getWriter().print(JSON.toJSONString(result));
            return;
        }

        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        //放行
        filterChain.doFilter(req,resp);
    }
}
