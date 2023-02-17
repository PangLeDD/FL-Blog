package com.lei.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.Enum.AppHttpCodeEnum;
import com.lei.domain.dto.UserDto;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.Role;
import com.lei.domain.entity.Tag;
import com.lei.domain.entity.User;
import com.lei.domain.vo.PageVo;
import com.lei.domain.vo.UserInfoVo;
import com.lei.domain.vo.UserMsgVo;
import com.lei.domain.vo.UserVo;
import com.lei.exception.SystemException;
import com.lei.mapper.UserMapper;
import com.lei.mapper.utils.BeanCopyUtils;
import com.lei.mapper.utils.SecurityUtils;
import com.lei.service.RoleService;
import com.lei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/9/9 19:34
 * @Version : 1.0.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;
    @Override
    public ResponseResult getUserInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId,user.getId());
        updateWrapper.set(User::getNickname,user.getNickname())
                .set(User::getAvatar,user.getAvatar())
                .set(User::getSex,user.getSex())
                .set(User::getEmail,user.getEmail());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对传入进来的用户名进行判断
        if(userNameExist(user.getUsername())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickname())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(emailNameExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //对密码进行加密
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserList(Long pageNum, Long pageSize, UserVo userVo) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(userVo.getUserName()),User::getUsername,userVo.getUserName());
        wrapper.like(StringUtils.hasText(userVo.getPhonenumber()),User::getPhonenumber,userVo.getPhonenumber());
        wrapper.eq(StringUtils.hasText(userVo.getStatus()),User::getStatus,userVo.getStatus());
        Page<User> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);
        List<UserDto> userDtos = page.getRecords().stream().map(user -> {
            UserDto userDto = BeanCopyUtils.copyBean(user, UserDto.class);
            userDto.setNickName(user.getNickname());
            userDto.setUserName(user.getUsername());
            return userDto;
        }).collect(Collectors.toList());

        return ResponseResult.okResult(new PageVo(userDtos,page.getTotal()));
    }

    @Override
    @Transactional
    public ResponseResult addUser(UserVo userVo) {
        User user = BeanCopyUtils.copyBean(userVo, User.class);
        user.setUsername(userVo.getUserName());
        user.setNickname(userVo.getNickName());
        String encodePassWord = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassWord);
        save(user);
        userVo.getRoleIds()
                .forEach(roleId->getBaseMapper().saveUserToRoleId(user.getId(),roleId));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(Long userId) {
        removeById(userId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserMsg(Long userId) {
        List<Long> rolesByUserId = getBaseMapper().getRolesByUserId(userId);
        List<Role> roles = roleService.listAllRole().getData();
        User user = getById(userId);
        UserDto userDto = BeanCopyUtils.copyBean(user, UserDto.class);
        userDto.setUserName(user.getUsername());
        userDto.setNickName(user.getNickname());
        return ResponseResult.okResult(new UserMsgVo(rolesByUserId,roles,userDto));
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UserVo userVo) {
        //更新用户
        User user = BeanCopyUtils.copyBean(userVo, User.class);
        user.setUsername(userVo.getUserName());
        user.setNickname(userVo.getNickName());
        updateById(user);
        //删除原来的角色id
        getBaseMapper().deleteRoleIdsByUserId(user.getId());
        //插入新的角色id
        userVo.getRoleIds().forEach(roleId->{
            getBaseMapper().saveUserToRoleId(user.getId(),roleId);
        });
        return ResponseResult.okResult();
    }

    private boolean emailNameExist(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail,email);
        return count(wrapper) > 0;
    }

    private boolean nickNameExist(String nickname) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNickname,nickname);
        return count(wrapper) > 0;
    }

    private boolean userNameExist(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,username);
        return count(wrapper) > 0;
    }
}
