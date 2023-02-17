package com.lei.service;

import com.lei.domain.entity.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/9/11 18:02
 * @Version : 1.0
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
