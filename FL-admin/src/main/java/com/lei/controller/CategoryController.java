package com.lei.controller;

import com.lei.domain.entity.Category;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.CategoryVo;
import com.lei.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/30 15:29
 * @Version : 1.0.0
 */

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse  httpServletResponse) throws IOException {
        categoryService.export(httpServletResponse);
    }


    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }


    @GetMapping("/list")
    public ResponseResult getAllList(Integer pageNum, Integer pageSize, CategoryVo categoryVo){
        return categoryService.getAllList(pageNum,pageSize,categoryVo);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryVo categoryVo){
        return categoryService.addCategory(categoryVo);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryVo categoryVo){
        return categoryService.updateCategory(categoryVo);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}
