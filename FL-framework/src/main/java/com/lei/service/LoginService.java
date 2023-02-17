package com.lei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.User;
import com.lei.domain.vo.RoutersVo;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult getInfo();

    ResponseResult getRouters();

    ResponseResult logout();
}
