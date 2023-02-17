package com.lei.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lei.domain.entity.Menu;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/31 15:48
 * @Version : 1.0.0
 */
@Data
public class MenuVo {

    //菜单ID@TableId
    private Long id;
    //菜单名称
    private String menuName;
    //父菜单ID
    private Long parentId;
    //显示顺序
    private Integer orderNum;
    //路由地址
    private String path;
    //组件路径
    private String component;
    //是否为外链（0是 1否）
    private Integer isFrame;
    //菜单类型（M目录 C菜单 F按钮）
    private String menuType;
    //菜单状态（0显示 1隐藏）
    private String visible;
    //菜单状态（0正常 1停用）
    private String status;
    //权限标识
    private String perms;
    //菜单图标
    private String icon;
    //备注
    private String remark;


}
