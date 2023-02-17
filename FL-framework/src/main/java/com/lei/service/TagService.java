package com.lei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.domain.dto.TagListDto;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.Tag;
import com.lei.domain.vo.TagVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-01-25 17:27:04
 */

public interface TagService extends IService<Tag> {

    ResponseResult getList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tag);

    ResponseResult deleteTag(Long id);

    ResponseResult getTag(Long id);

    ResponseResult updateTag(TagVo tagVo);

    ResponseResult getAllList();

}

