package com.lei.controller;

import com.lei.domain.dto.TagListDto;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.TagVo;
import com.lei.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/25 17:33
 * @Version : 1.0.0
 */

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.getList(pageNum,pageSize,tagListDto);
    }
    @PostMapping
    public ResponseResult addTag(TagListDto tag){
        return tagService.addTag(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable("id") Long id){
        return tagService.getTag(id);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody TagVo tagVo){
        return tagService.updateTag(tagVo);
    }

    @GetMapping("/listAllTag")
    public ResponseResult AllList(){
        return tagService.getAllList();
    }

}
