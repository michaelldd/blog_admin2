package com.paleo.blog.service.sys.dept.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.Page;

public interface IDeptDao {
	
	public List<Map> getDeptList(@Param("bo") Map dept);
	
	public void addDept(@Param("bo") Map dept);

	public Map getDeptById(@Param("deptId")Long deptId);

	public void updateDept(@Param("bo")Map dept);
	
	public void updateTrees(@Param("oldTree")String oldTree, @Param("newTree")String newTree,@Param("levRes")int levRes);

	public Page<Map> showDeptPage(@Param("bo")Map dept, RowBounds bounds);

	public List<Map> getChildDeptList(@Param("bo")Map dept);

	public void deleteDeptById(@Param("deptId")Long deptId);

}
