package com.lei.domain.vo;

import com.lei.domain.entity.Menu;
import com.lei.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/26 17:58
 * @Version : 1.0.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class AdminUserInfoVo {
    private List<String> permissions;
    private List<String> roles;
    private UserInfoVo user;

}
