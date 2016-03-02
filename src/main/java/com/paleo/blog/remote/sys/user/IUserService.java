package com.paleo.blog.remote.sys.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageInfo;

public interface IUserService {

	/**
	 * 用户分页
	 * @param deptUser
	 * @param bounds
	 * @return
	 */
	PageInfo<Map> showUserPage(Map deptUsers, RowBounds bounds);
	/**
	 * 新增用户
	 * @param user
	 */
	void addUser(Map user);
	/**
	 * 根据ID获取用户
	 * @param userId
	 * @return
	 */
	Map getUserById(Long userId);
	/**
	 * 更新用户
	 * @param user
	 */
	void updateUser(Map user);
	/**
	 * 判断用户是否是唯一存在
	 * @param user
	 * @return
	 */
	List<Map> isUnique(Map user);
	/**
	 * 授权用户
	 * @param userRoles 一对多的用户角色
	 */
	void authorizeUser(Map userRoles);
	/**
	 * 删除用户（逻辑删除）
	 * @param userId
	 */
	void deleteUserById(Long userId);
	/**
	 * 获取dept下的用户（不包含dept的子部门用户）
	 * @param dept
	 * @return
	 */
	List<Map> getUserListInDept(Map dept);
	/**
	 * 获取用户 by Name
	 * @param userName
	 * @return
	 */
	Map getUserByName(String userName);

}
