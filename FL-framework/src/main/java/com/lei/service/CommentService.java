package com.lei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.domain.entity.Comment;
import com.lei.domain.entity.ResponseResult;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-06-06 21:04:27
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

}

