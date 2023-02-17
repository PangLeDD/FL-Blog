package com.lei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.domain.entity.Category;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.CategoryVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 分类表(Category)表服务接口
 *
 * @author 是大大大橙子噢
 * @since 2022-05-29 22:07:47
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult getAllList(Integer pageNum, Integer pageSize, CategoryVo categoryVo);

    ResponseResult addCategory(CategoryVo categoryVo);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(CategoryVo categoryVo);

    ResponseResult deleteCategory(Long id);

    void export(HttpServletResponse httpServletResponse) throws IOException;
}

