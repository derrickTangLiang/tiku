package com.tamguo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tamguo.dao.MenuMapper;
import com.tamguo.dao.SubjectMapper;
import com.tamguo.model.MenuEntity;
import com.tamguo.model.SubjectEntity;
import com.tamguo.service.ITikuMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

@Service
public class TikuMenuService implements ITikuMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Page<MenuEntity> queryList(Map<String, Object> map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<MenuEntity> pageList = (Page<MenuEntity>) menuMapper.queryList(map);
        return pageList;
    }

    @Override
    public void save(MenuEntity menuEntity) {
        menuMapper.insert(menuEntity);
    }

    @Override
    public MenuEntity queryObject(Long menuId) {
        return menuMapper.select(String.valueOf(menuId));
    }

    @Override
    @Transactional
    public void update(MenuEntity menu) {
        menuMapper.update(menu);
    }

    @Override
    @Transactional
    public void deleteBatch(String[] menuIds) {
        menuMapper.deleteByIds(Arrays.asList(menuIds));
    }
}
