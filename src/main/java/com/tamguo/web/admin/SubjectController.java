package com.tamguo.web.admin;

import com.github.pagehelper.Page;
import com.tamguo.model.SubjectEntity;
import com.tamguo.model.SysRoleEntity;
import com.tamguo.service.ISubjectService;
import com.tamguo.service.ISysRoleMenuService;
import com.tamguo.service.ISysRoleService;
import com.tamguo.service.impl.SubjectService;
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
 * 题库类型管理
 */
@Controller(value="adminSubjectController")
@RequestMapping("admin/subject")
public class SubjectController {
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysRoleMenuService sysRoleMenuService;
	@Autowired
	private ISubjectService iSubjectService;


	/**
	 * 类型列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("tiku:subject:list")
	public @ResponseBody Map<String, Object> list(String name, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("name",name);
		page = page == null ? 0 : page;
		limit = limit == null ? 10 : limit;
		// 查询列表数据
		Page<SubjectEntity> list = iSubjectService.queryList(map, page, limit);
		List<SubjectEntity> result = list.getResult();
		return Result.jqGridResult(result, list.getTotal(), limit, page, list.getPages());

	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("tiku:subject:select")
	public @ResponseBody Result select() {
		// 查询列表数据
		List<SysRoleEntity> list = sysRoleService.queryList(new SysRoleEntity(), 1, 10000);
		return Result.successResult(list);
	}

	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{subjectId}")
	@RequiresPermissions("tiku:subject:info")
	public @ResponseBody Result info(@PathVariable("subjectId") Long subjectId) {
		SubjectEntity subject = iSubjectService.queryObject(subjectId);
		return Result.successResult(subject);
	}

	/**
	 * 保存角色
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@RequiresPermissions("tiku:subject:save")
	public @ResponseBody Result save(@RequestBody SubjectEntity subjectEntity) {
		if (StringUtils.isEmpty(subjectEntity.getName())) {
			return Result.failResult("名称不能为空");
		}
		try {
			iSubjectService.save(subjectEntity);
			return Result.successResult(null);
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("保存类型", this.getClass(), e);
		}
	}

	/**
	 * 修改角色
	 */
	@RequestMapping("/update")
	@RequiresPermissions("tiku:subject:update")
	public @ResponseBody Result update(@RequestBody SubjectEntity subject) {
		if (StringUtils.isEmpty(subject.getName())) {
			return Result.failResult("名称不能为空");
		}
		try {
			iSubjectService.update(subject);
			return Result.successResult(null);
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("修改类型", this.getClass(), e);
		}
	}

	/**
	 * 删除角色
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("tiku:subject:delete")
	public @ResponseBody Result delete(@RequestBody String[] roleIds) {
		try {
			sysRoleService.deleteBatch(roleIds);
			return Result.successResult(null);
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("删除角色", this.getClass(), e);
		}
	}
}
