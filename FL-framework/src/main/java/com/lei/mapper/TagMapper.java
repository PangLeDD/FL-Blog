package com.lei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-25 17:27:03
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    Long saveArticleTags(@Param("articleId") Integer articleId,@Param("tagId")Long tagId);

    void updateArticleTags(@Param("articleId") Integer articleId,@Param("tagId")Long tagId);

    List<Long> getTagsById(@Param("articleId") Integer articleId);
}

