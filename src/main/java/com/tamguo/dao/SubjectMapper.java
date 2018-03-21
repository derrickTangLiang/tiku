package com.tamguo.dao;

import com.tamguo.model.SchoolEntity;
import com.tamguo.model.SubjectEntity;
import com.tamguo.model.SysMenuEntity;
import com.tamguo.mybatis.dao.BaseDao;

import java.util.List;
import java.util.Map;

public interface SubjectMapper extends BaseDao<SubjectEntity> {
    List<SubjectEntity> queryList(Map<String, Object> map);
}
