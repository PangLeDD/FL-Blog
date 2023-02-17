package com.lei.domain.vo;

import com.lei.domain.dto.UserDto;
import com.lei.domain.entity.Role;
import com.lei.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/2/1 20:48
 * @Version : 1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserMsgVo {
   private List<Long> roleIds;
   private List<Role> roles;
   private UserDto user;
}
