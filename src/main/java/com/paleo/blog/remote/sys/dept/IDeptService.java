package com.paleo.blog.remote.sys.dept;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageInfo;

public interface IDeptService {

	/**
	 * 更新部门
	 * @param dept
	 */
	void updateDept(Map dept);
	/**
	 * 新增部门
	 * @param dept
	 * @return
	 */
	Map addDept(Map dept);
	/**
	 * 根据ID获取部门
	 * @param deptId
	 * @return
	 */
	Map getDeptById(Long deptId);
	/**
	 * 获取部门列表
	 * @param dept
	 * @return
	 */
	List<Map> getDeptList(Map dept);
	/**
	 * 获取部门分页
	 * @param dept
	 * @param bounds
	 * @return
	 */
	PageInfo<Map> showDeptPage(Map dept,RowBounds bounds);
	/**
	 * dept的子部门列表（不包含子部门的子部门，只包含下一级部门）
	 * @param dept
	 * @return
	 */
	List<Map> getChildDeptList(Map dept);
	/**
	 * 删除机构（逻辑删除）
	 * @param deptId
	 */
	void deleteDeptById(Long deptId);

}
