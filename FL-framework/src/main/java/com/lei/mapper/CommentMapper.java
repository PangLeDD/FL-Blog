package com.lei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-06 21:04:26
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}

