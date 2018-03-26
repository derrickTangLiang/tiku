package com.tamguo.dao;

import com.tamguo.model.MenuEntity;
import com.tamguo.mybatis.dao.BaseDao;

import java.util.List;
import java.util.Map;

public interface MenuMapper extends BaseDao<MenuEntity>{

	public List<MenuEntity> findFatherMenus();
	
	public List<MenuEntity> findMenuByParentId(String parentId);

	public List<MenuEntity> findAllFatherMenus();

	public List<MenuEntity> findLeftFatherMenus();

	public List<MenuEntity> findFooterFatherMenus();

	public List<MenuEntity> queryList(Map<String, Object> map);
	
}
