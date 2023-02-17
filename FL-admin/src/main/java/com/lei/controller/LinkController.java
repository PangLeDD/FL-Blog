package com.lei.controller;

import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.CategoryVo;
import com.lei.domain.vo.LinkVo;
import com.lei.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/2/2 16:29
 * @Version : 1.0.0
 */

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @GetMapping("/list")
    public ResponseResult getLinkList(Integer pageNum, Integer pageSize, LinkVo linkVo){
        return linkService.getLinkList(pageNum,pageSize,linkVo);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody LinkVo linkVo){
        return linkService.addLink(linkVo);
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable Long id){
        return linkService.getLinkById(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkVo linkVo){
        return linkService.updateLink(linkVo);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id){
        return linkService.deleteLink(id);
    }

}
