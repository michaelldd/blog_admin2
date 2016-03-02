package com.paleo.blog.service.sys.role.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;


public interface IRoleDao {

	List<Map> getRoleList(@Param("userId")Long userId);

	List<Map> showRolePage(@Param("bo")Map user, RowBounds bounds);

	void addRole(@Param("bo")Map role);

	List<Map> getUserRoleList(@Param("userId")Long userId);

	Map getRoleById(@Param("roleId")Long roleId);

	void updateRole(@Param("bo")Map role);

	void deleteRoleById(@Param("roleId")Long roleId);

	void authorizeRole(@Param("menuList")List<Map> menuList);

	void deleteRoleMenus(@Param("bo")Map role);

	void addRoleMenus(@Param("role")Map role, @Param("menuList")List<Map> menuList);

	void deleteUserRolesByRoleId(@Param("roleId")Long roleId);

	List<Map> getRoleListInGroup(@Param("bo")Map roleGroup);

}
