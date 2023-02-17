package com.lei.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.Enum.AppHttpCodeEnum;
import com.lei.constants.SystemConstants;
import com.lei.domain.entity.Comment;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.User;
import com.lei.domain.vo.CommentVo;
import com.lei.domain.vo.PageVo;
import com.lei.exception.SystemException;
import com.lei.mapper.CommentMapper;
import com.lei.mapper.utils.BeanCopyUtils;
import com.lei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lei.service.CommentService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-06-06 21:04:28
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    UserService userService;


    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        if (commentType.equals(SystemConstants.ARTICLE_COMMENT) && articleId == null){
            throw new SystemException(AppHttpCodeEnum.ARTICLE_ID_NOT_NULL);
        }
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRootId,SystemConstants.COMMENT_ROOT);
        wrapper.eq(Comment::getType,commentType);
        wrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        wrapper.orderByDesc(Comment::getCreateTime);
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);
        List<CommentVo> rootComments = toCommentVoList(page.getRecords());

        rootComments.forEach(rootComment->{
            rootComment.setChildren(getChildren(rootComment.getId()));
        });


        return ResponseResult.okResult(new PageVo(rootComments,page.getTotal()));
    }

    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRootId,id);
        List<Comment> list = list(wrapper);
        List<CommentVo> commentVos = toCommentVoList(list);
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        commentVos.forEach(
                commentVo -> {
                    User user = userService.getById(commentVo.getCreateBy());
                    if (ObjectUtils.isEmpty(user)){
                        commentVo.setUsername("不存在此用户！");
                    }else {
                        commentVo.setUsername(user.getNickname());
                        if (commentVo.getToCommentUserId()!=-1){
                            commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickname());

                        }
                    }

                }
        );
        return commentVos;
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if(!StringUtils.hasText(comment.getContent())) {
           throw  new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }
}
