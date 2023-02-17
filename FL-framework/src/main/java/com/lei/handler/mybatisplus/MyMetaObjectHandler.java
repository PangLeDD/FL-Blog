package com.lei.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lei.mapper.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/7 21:28
 * @Version : 1.0.0
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"createTime", Date::new,Date.class); //这里的Date对应实体类的字段Date 如果用LocalDateTime则需要改变实体类的类的类型
        this.strictInsertFill(metaObject,"createBy", SecurityUtils::getUserId,Long.class);
        this.strictInsertFill(metaObject,"updateTime", Date::new,Date.class);
        this.strictInsertFill(metaObject,"updateBy", SecurityUtils::getUserId,Long.class);

//        this.setFieldValByName("createTime", new Date(), metaObject);
//        this.setFieldValByName("createBy",SecurityUtils.getUserId() , metaObject);
//        this.setFieldValByName("updateTime", new Date(), metaObject);
//        this.setFieldValByName("updateBy", SecurityUtils.getUserId(), metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.strictUpdateFill(metaObject,"updateTime", Date::new,Date.class);
        this.strictUpdateFill(metaObject,"updateBy",SecurityUtils::getUserId,Long.class);
    }

}
