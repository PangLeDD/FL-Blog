package com.lei.service.Impl;

import com.lei.domain.entity.*;
import com.lei.domain.vo.AdminUserInfoVo;
import com.lei.domain.vo.RoutersVo;
import com.lei.domain.vo.UserInfoVo;
import com.lei.mapper.utils.BeanCopyUtils;
import com.lei.mapper.utils.JwtUtil;
import com.lei.mapper.utils.RedisCache;
import com.lei.mapper.utils.SecurityUtils;
import com.lei.service.LoginService;
import com.lei.service.MenuService;
import com.lei.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class SystemLoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    MenuService menuService;

    @Autowired
    RoleService roleService;

    //登录业务逻辑
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误!");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        HashMap<String , String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }


    //获取用户信息业务逻辑
    @Override
    public ResponseResult getInfo() {
        //获取登录信息
        LoginUser user = SecurityUtils.getLoginUser();
        //根据ID查询权限信息
        List<String> perms =  menuService.selectPermsById(user.getUser().getId());
        //根据ID查询角色信息
        List<String > rolesKeyList  = roleService.selectRoleKeysByUserId(user.getUser().getId());
        //用户信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user.getUser(), UserInfoVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,rolesKeyList,userInfoVo);

        return ResponseResult.okResult(adminUserInfoVo);
    }

    //获取动态路由给前端
    @Override
    public ResponseResult getRouters() {
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(SecurityUtils.getUserId());
        RoutersVo routersVo = new RoutersVo(menus);
        return ResponseResult.okResult(routersVo);
    }

    //注销用户(登出)
    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        //从Redis中删除缓存数据
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}