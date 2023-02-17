package com.lei.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/2/1 20:01
 * @Version : 1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDto {
    private Long id;
    private String avatar;
    private Date createTime;
    private String userName;
    private String phonenumber;
    private String status;
    private String sex;
    private String email;
    private Date updateTime;
    private Long updateBy;
    private String nickName;
}
