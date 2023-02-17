package com.lei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-02 17:45:43
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    void saveUserToRoleId(@Param("userId") Long userId,@Param("roleId") Long roleId);

    List<Long> getRolesByUserId(@Param("userId") Long userId);

    void deleteRoleIdsByUserId(@Param("userId") Long userId);
}

