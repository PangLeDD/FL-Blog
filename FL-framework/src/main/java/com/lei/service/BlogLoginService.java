package com.lei.service;

import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
