package com.lei.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/2/1 18:10
 * @Version : 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserVo {
    private Long id;
    private String userName;
    private String phonenumber;
    private String status;
    private String sex;
    private String email;
    private List<Long> roleIds;
    private String password;
    private String nickName;
}
