package com.lei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.User;
import com.lei.domain.vo.UserVo;
import org.springframework.stereotype.Service;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/9/9 19:32
 * @Version : 1.0
 */

@Service
public interface UserService extends IService<User> {
    ResponseResult getUserInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    //--------------------------------------------------------------------------以下是后台上面是前台

    ResponseResult getUserList(Long pageNum, Long pageSize, UserVo userVo);

    ResponseResult addUser(UserVo userVo);

    ResponseResult deleteUser(Long userId);

    ResponseResult getUserMsg(Long userId);

    ResponseResult updateUser(UserVo userVo);
}
