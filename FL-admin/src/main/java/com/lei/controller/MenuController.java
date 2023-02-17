package com.lei.controller;

import com.lei.domain.entity.Menu;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.MenuVo;
import com.lei.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2023/1/31 15:12
 * @Version : 1.0.0
 */


@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    MenuService menuService;


    @GetMapping("/list")
    public ResponseResult getMenuList(Long status, String menuName){
        return  menuService.getMenuList(status,menuName);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        return  menuService.addMenu(menu);
    }


    @GetMapping("{id}")
    public ResponseResult getMenuById(@PathVariable Long id){
        return  menuService.getMenuById(id);
    }

    @PutMapping
    public ResponseResult getMenuById(@RequestBody MenuVo menuVo){
        return  menuService.updateMenu(menuVo);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable("id") Long id){
        return  menuService.deleteById(id);
    }

    @GetMapping("/treeselect")
    public ResponseResult getMenuTree(){
        return  menuService.getMenuTree();
    }
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselect(@PathVariable("id") Long id){
        return menuService.roleMenuTree(id);
    }
}
