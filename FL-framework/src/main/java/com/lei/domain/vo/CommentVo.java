package com.lei.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/7 16:34
 * @Version : 1.0.0
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommentVo {

    private Long id;

    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;

    //表中无此字段，原实体类无此字段，自己加的，需要自己赋值
    private String toCommentUserName;
    //回复目标评论id
    private Long toCommentId;

    private Long createBy;

    private Date createTime;
    //原实体类无此字段，自己加的，需要自己赋值
    private List<CommentVo> children;
    //原实体类无此字段，自己加的，需要自己赋值
    //用户昵称
    private String username;



}
