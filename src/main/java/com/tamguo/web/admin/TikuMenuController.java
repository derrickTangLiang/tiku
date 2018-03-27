package com.tamguo.web.admin;

import com.github.pagehelper.Page;
import com.tamguo.model.MenuEntity;
import com.tamguo.model.SysMenuEntity;
import com.tamguo.service.ITikuMenuService;
import com.tamguo.util.ExceptionSupport;
import com.tamguo.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题库菜单管理
 */
@Controller(value = "adminMenuController")
@RequestMapping("admin/menu")
public class TikuMenuController {
    @Autowired
    private ITikuMenuService iTikuMenuService;


    /**
     * 菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("tiku:menu:list")
    public @ResponseBody
    Map<String, Object> list(String name, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        page = page == null ? 0 : page;
        limit = limit == null ? 10 : limit;
        // 查询列表数据
        Page<MenuEntity> list = iTikuMenuService.queryList(map, page, limit);
        List<MenuEntity> result = list.getResult();
        return Result.jqGridResult(result, list.getTotal(), limit, page, list.getPages());

    }

    /**
     * 题库菜单信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("tiku:menu:info")
    public @ResponseBody
    Result info(@PathVariable("menuId") Long menuId) {
        MenuEntity subject = iTikuMenuService.queryObject(menuId);
        return Result.successResult(subject);
    }

    /**
     * 保存题库菜单
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("tiku:menu:save")
    public @ResponseBody
    Result save(@RequestBody MenuEntity menuEntity) {
        if (StringUtils.isEmpty(menuEntity.getName())) {
            return Result.failResult("名称不能为空");
        }
        try {
            iTikuMenuService.save(menuEntity);
            return Result.successResult(null);
        } catch (Exception e) {
            return ExceptionSupport.resolverResult("保存类型", this.getClass(), e);
        }
    }

    /**
     * 修改题库菜单
     */
    @RequestMapping("/update")
    @RequiresPermissions("tiku:menu:update")
    public @ResponseBody
    Result update(@RequestBody MenuEntity menuEntity) {
        if (StringUtils.isEmpty(menuEntity.getName())) {
            return Result.failResult("名称不能为空");
        }
        try {
            iTikuMenuService.update(menuEntity);
            return Result.successResult(null);
        } catch (Exception e) {
            return ExceptionSupport.resolverResult("修改类型", this.getClass(), e);
        }
    }

    /**
     * 删除题库菜单
     */
    @RequestMapping("/delete")
    @RequiresPermissions("tiku:menu:delete")
    public @ResponseBody
    Result delete(@RequestBody String[] menuIds) {
        try {
            iTikuMenuService.deleteBatch(menuIds);
            return Result.successResult(null);
        } catch (Exception e) {
            return ExceptionSupport.resolverResult("删除类型", this.getClass(), e);
        }
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public @ResponseBody Result select() {
        // 查询列表数据
        List<MenuEntity> menuList = iTikuMenuService.queryNotButtonList();

        // 添加顶级菜单
        MenuEntity root = new MenuEntity();
        root.setUid(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
//        root.setOpen(true);
        menuList.add(root);
        return Result.successResult(menuList);
    }
}
