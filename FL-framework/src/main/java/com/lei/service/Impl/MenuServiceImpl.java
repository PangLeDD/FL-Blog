package com.lei.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.domain.entity.Menu;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.MenuTreeVo;
import com.lei.domain.vo.MenuVo;
import com.lei.domain.vo.RoleMenuTreeVo;
import com.lei.mapper.MenuMapper;
import com.lei.mapper.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lei.service.MenuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-01-26 17:32:16
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuService menuService;

    @Override
    public List<String> selectPermsById(Long id) {
        if (id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType,"C","F");
            wrapper.eq(Menu::getStatus,"0");
            List<Menu> list = menuService.list(wrapper);
            List<String> perms = list.stream().map(Menu::getPerms).collect(Collectors.toList());
            return perms;
        }
        return getBaseMapper().getPermsById(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long id) {

        List<Menu> menus;

        //如果是管理员 id = 1
        if (id.equals(1L)){
            menus = getBaseMapper().selectAllRouterMenu();
        }else {
            //如果不是管理员就根据id查找相应的角色对应的权限
            menus = getBaseMapper().selectRouterMenuTreeByUserId(id);
        }
        //构建Tree并且返回
        return buildMenuTree(menus);
    }

    @Override
    public ResponseResult getMenuList(Long status, String menuName) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(menuName),Menu::getMenuName,menuName);
        wrapper.eq(status!=null,Menu::getStatus,status);
        wrapper.orderByAsc(Arrays.asList(Menu::getOrderNum,Menu::getParentId));
        List<Menu> menuList = list(wrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);

        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long id) {

        Menu menu = getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(menu,MenuVo.class));
    }

    @Override
    public ResponseResult updateMenu(MenuVo menuVo) {
        Menu menu = BeanCopyUtils.copyBean(menuVo, Menu.class);
        if (menu.getId().equals(menu.getParentId())){
            return ResponseResult.okResult(500, "修改菜单'" +menu.getMenuName()+"'失败，上级菜单不能选择自己！");
        }
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getId,menu.getId());
        if (!update(menu,wrapper)) {
            return ResponseResult.errorResult(500,"更新失败！");
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteById(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    //获取菜单树
    @Override
    public ResponseResult<List<MenuTreeVo>> getMenuTree() {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Menu::getId, Menu::getParentId,Menu::getMenuName);
        wrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menus = list(wrapper);
        List<MenuTreeVo> menuTreeVos = menus.stream().map(
                menu -> new MenuTreeVo(null,menu.getId(),menu.getParentId(),menu.getMenuName())
        ).collect(Collectors.toList());
        // 得到了TreeVO对象，children装填进去，复用之前的代码就行了
        List<MenuTreeVo> treeVos = menuTreeVos.stream()
                .filter(menuTreeVo -> menuTreeVo.getParentId().equals(0L))
                .map(menuTreeVo -> menuTreeVo.setChildren(getMenuTreeChildren(menuTreeVo.getId(), menuTreeVos)))
                .collect(Collectors.toList());
        return ResponseResult.okResult(treeVos);
    }

    @Override
    @Transactional
    public ResponseResult roleMenuTree(Long id) {
        //如果是管理员则拥有全部权限，回显全部权限
        if (id.equals(1L)){
          ResponseResult<List<MenuTreeVo>> menuTreeResult = getMenuTree();
          List<MenuTreeVo> menuTree = menuTreeResult.getData();
          LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
          wrapper.select(Menu::getId);
          List<Long> checkedKeys = list(wrapper).stream().map(Menu::getId).collect(Collectors.toList());
          return ResponseResult.okResult(new RoleMenuTreeVo(menuTree,checkedKeys));
      }
        //不是管理员的话  看错需求写的烂复杂代码 舍不得删除
//        List<MenuTreeVo> menuTreeVos;
//        List<Menu> menus = getBaseMapper().getMenuByRoleId(id);
//        menuTreeVos = menus.stream()
//                .map(m -> new MenuTreeVo(null, m.getId(), m.getParentId(), m.getMenuName()))
//                .collect(Collectors.toList());
//
//        List<MenuTreeVo> result = menuTreeVos.stream()
//                .filter(menuTreeVo -> menuTreeVo.getParentId().equals(0L))
//                .map(menuTreeVo -> menuTreeVo.setChildren(getMenuTreeChildren(menuTreeVo.getId(), menuTreeVos)))
//                .collect(Collectors.toList());

        //不是管理员的话
        ResponseResult<List<MenuTreeVo>> menuTreeResult = getMenuTree();
        List<MenuTreeVo> menuTree = menuTreeResult.getData();
        List<Long> roleId = getBaseMapper().getMenuIdByRoleId(id);
        return ResponseResult.okResult(new RoleMenuTreeVo(menuTree,roleId));
    }

    private List<MenuTreeVo> getMenuTreeChildren(Long pid, List<MenuTreeVo> menuTreeVos) {
        return  menuTreeVos.stream()
                .filter(menu -> menu.getParentId().equals(pid))
                .map(menu-> menu.setChildren(getMenuTreeChildren(menu.getId(),menuTreeVos)))
                .collect(Collectors.toList());
    }

    private List<Menu> buildMenuTree(List<Menu> menus) {

        return menus.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> menu.setChildren(getChildren(menus, menu.getId())))
                .collect(Collectors.toList());
    }

    private List<Menu> getChildren(List<Menu> menus, Long pid){
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(pid))
                .collect(Collectors.toList());
    }

}

