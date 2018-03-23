package com.tamguo.service;


import com.github.pagehelper.Page;
import com.tamguo.model.SubjectEntity;
import com.tamguo.model.SysMenuEntity;
import com.tamguo.model.SysRoleEntity;

import javax.security.auth.Subject;
import java.util.Map;


/**
 * 题库类型
 *
 */
public interface ISubjectService {
	/**
	 * 分页
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Page<SubjectEntity> queryList(Map<String, Object> map, int pageNum, int pageSize);
	/**
	 * 保存类型
	 */
	void save(SubjectEntity entity);

	SubjectEntity queryObject(Long subjectId);

	void update(SubjectEntity subject);

	void deleteBatch(String[] subjectIds);
}
