package com.paleo.blog.service.sys.role_group.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface IRoleGroupDao {

	public List<Map> getRoleGroupList(@Param("bo")Map roleGroup);

	public List<Map> showRoleGroupPage(@Param("bo")Map roleGroup, RowBounds bounds);

	public void addRoleGroup(@Param("bo")Map roleGroup);

	public void updateRoleGroup(@Param("bo")Map roleGroup);

	public Map getRoleGroupById(@Param("roleGroupId")Long roleGroupId);

	public List<Map> getChildRoleGroupList(@Param("bo")Map roleGroup);

	public void deleteRoleGroupById(@Param("roleGroupId")Long roleGroupId);

	public void updateTrees(@Param("oldTree")String oldTree, @Param("newTree")String newTree, @Param("levRes")int levRes);

}
