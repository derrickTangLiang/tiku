package com.tamguo.service;


import com.github.pagehelper.Page;
import com.tamguo.model.MenuEntity;

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
}
