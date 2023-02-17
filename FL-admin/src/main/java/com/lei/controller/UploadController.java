package com.lei.controller;

import com.lei.domain.entity.ResponseResult;
import com.lei.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/30 16:02
 * @Version : 1.0.0
 */


@RestController
public class UploadController {

    @Autowired
    UploadService uploadService;


    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("img") MultipartFile file){
        try {
            return uploadService.uploadImg(file);
        } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException("文件上传失败！");
        }
    }
}
