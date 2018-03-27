package com.tamguo.service;


import com.github.pagehelper.Page;
import com.tamguo.model.MenuEntity;

import java.util.List;
import java.util.Map;


/**
 * 题库菜单
 */
public interface ITikuMenuService {
    /**
     * 分页
     *
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<MenuEntity> queryList(Map<String, Object> map, int pageNum, int pageSize);

    /**
     * 保存类型
     */
    void save(MenuEntity entity);

    MenuEntity queryObject(Long menuId);

    void update(MenuEntity menu);

    void deleteBatch(String[] menuIds);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<MenuEntity> queryNotButtonList();
}
