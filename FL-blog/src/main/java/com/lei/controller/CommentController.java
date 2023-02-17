package com.lei.controller;

import com.lei.domain.dto.AddCommentDto;
import com.lei.domain.entity.Comment;
import com.lei.domain.entity.ResponseResult;
import com.lei.mapper.utils.BeanCopyUtils;
import com.lei.service.CommentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/6 21:07
 * @Version : 1.0.0
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult comment(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList("0",articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody AddCommentDto comment){
        Comment copyBeanComment = BeanCopyUtils.copyBean(comment, Comment.class);
        return commentService.addComment(copyBeanComment);
    }


    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友联评论列表",notes = "获取一页友联信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "commentType",value = "评论的类型(文章或是友链的)"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "页码大小"),
    })
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList("1", null,pageNum,pageSize);
    }
}
