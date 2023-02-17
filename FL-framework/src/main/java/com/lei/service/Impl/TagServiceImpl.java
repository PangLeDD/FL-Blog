package com.lei.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.Enum.AppHttpCodeEnum;
import com.lei.domain.dto.TagListDto;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.entity.Tag;
import com.lei.domain.vo.PageVo;
import com.lei.domain.vo.TagVo;
import com.lei.mapper.TagMapper;
import com.lei.mapper.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import com.lei.service.TagService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-01-25 17:27:04
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public ResponseResult<PageVo> getList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {

        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        wrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(),page.getTotal()));
    }

    @Override
    public ResponseResult addTag(TagListDto tag) {
        Tag addTag = BeanCopyUtils.copyBean(tag, Tag.class);
        save(addTag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        Tag tag = getById(id);

        if (ObjectUtils.isEmpty(tag)){
            return ResponseResult.errorResult(AppHttpCodeEnum.ID_ERROR);
        }
        removeById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTag(Long id) {
        Tag tag = getById(id);
        if (ObjectUtils.isEmpty(tag)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ID_ERROR);
        }
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(TagVo tagVo) {
        Tag tag = BeanCopyUtils.copyBean(tagVo, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAllList() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        return ResponseResult.okResult(list);
    }

}

