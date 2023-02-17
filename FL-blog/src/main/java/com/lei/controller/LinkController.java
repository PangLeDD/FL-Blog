package com.lei.controller;

import com.lei.domain.entity.ResponseResult;
import com.lei.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/1 12:41
 * @Version : 1.0
 */

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return  linkService.getAllLink();
    }
}
