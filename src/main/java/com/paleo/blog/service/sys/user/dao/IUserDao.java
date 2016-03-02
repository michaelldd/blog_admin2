package com.paleo.blog.service.sys.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface IUserDao {

	public List<Map> showUserPage(@Param("bo")Map deptUsers, RowBounds bounds);

	public void addUser(@Param("bo")Map user);

	public Map getUserById(@Param("userId")Long userId);

	public void updateUser(@Param("bo")Map user);

	public List<Map> isUnique(@Param("bo")Map user);

	public void authorizeUser(@Param("bo")Map userRole);

	public void deleteUserRoles(@Param("bo")Map userRole);

	public void addUserRoles(@Param("bo")Map userRoles);

	public void deleteUserById(@Param("userId")Long userId);

	public List<Map> getUserListInDept(@Param("bo")Map dept);

	public Map getUserByName(@Param("userName")String userName);

}
