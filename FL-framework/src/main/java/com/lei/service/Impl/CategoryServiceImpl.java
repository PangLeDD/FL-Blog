package com.lei.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.constants.SystemConstants;
import com.lei.domain.entity.Article;
import com.lei.domain.entity.Category;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.CategoryVo;
import com.lei.domain.vo.ExcelCategoryVo;
import com.lei.domain.vo.PageVo;
import com.lei.mapper.CategoryMapper;
import com.lei.service.ArticleService;
import com.lei.service.CategoryService;
import com.lei.mapper.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author 是大大大橙子噢
 * @since 2022-05-29 22:07:47
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired
    ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询article表状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章分类id并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categoryList = listByIds(categoryIds);


        categoryList = categoryList.stream()
                .filter(category -> String.valueOf(SystemConstants.ARTICLE_STATUS_TURE).equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装VO
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus,0);
        List<Category> categoryList = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }


    @Override
    public ResponseResult getAllList(Integer pageNum, Integer pageSize, CategoryVo categoryVo) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(categoryVo.getName()),Category::getName,categoryVo.getName());
        wrapper.eq(categoryVo.getStatus()!=null,Category::getStatus,categoryVo.getStatus());

        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);

        return ResponseResult.okResult(new PageVo(page.getRecords(),page.getTotal()));
    }

    @Override
    public ResponseResult addCategory(CategoryVo categoryVo) {
        if (ObjectUtils.isEmpty(categoryVo)) {
            return ResponseResult.errorResult(250,"内容不能为空！");
        }
        Category category = BeanCopyUtils.copyBean(categoryVo, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getId,id);
        wrapper.select(Category::getName, Category::getId, Category::getStatus, Category::getDescription);
        Category category = getOne(wrapper);
        return ResponseResult.okResult(category);
    }

    @Override
    public ResponseResult updateCategory(CategoryVo categoryVo) {
        if (ObjectUtils.isEmpty(categoryVo)) {
            return ResponseResult.errorResult(250,"内容不能为空！");
        }
        updateById(BeanCopyUtils.copyBean(categoryVo,Category.class));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        try {
            //设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("文章分类", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=" + fileName + ".xlsx");
            //查询集合
            List<ExcelCategoryVo> list = BeanCopyUtils.copyBeanList(list(), ExcelCategoryVo.class);

            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE)
                    .sheet("分类导出")
                    .doWrite(list);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

}

