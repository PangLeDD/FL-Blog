package com.lei.handler.exception;


import com.lei.Enum.AppHttpCodeEnum;
import com.lei.domain.entity.ResponseResult;
import com.lei.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/6 18:39
 * @Version : 1.0.0
 */


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印信息
        log.error("出现了异常！",e);
        //从异常对象中获取提示信息封装
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //异常信息打印出来方便调试
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
    //JSR303统一异常处理！
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult validateHandler(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        HashMap<String , String> map = new HashMap<>();
        result.getFieldErrors().stream()
                .forEach(fieldError->{
                    //      错误字段名字              错误信息
                    map.put(fieldError.getField(),fieldError.getDefaultMessage());
                });
        return ResponseResult.errorResult(400,"数据校验出现了问题！"+map.toString());
    }

}
