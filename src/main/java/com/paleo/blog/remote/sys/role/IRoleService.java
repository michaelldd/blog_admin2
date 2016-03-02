package com.paleo.blog.remote.sys.role;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageInfo;

public interface IRoleService {

	/**
	 * 根据用户，获取其下角色列表
	 * @param user
	 * @return
	 */
	List<Map> getRoleList(Long userId);

	/**
	 * 根据用户，获取其下角色列表（分页）
	 * @param user
	 * @param bounds
	 * @return
	 */
	PageInfo<Map> showRolePage(Map user, RowBounds bounds);

	/**
	 * 新增角色
	 * @param role
	 */
	void addRole(Map role);
	/**
	 * 用户已授权的角色
	 * @param userId
	 * @return
	 */
	List<Map> getUserRoleList(Long userId);
	/**
	 * 获取角色
	 * @param roleId
	 * @return
	 */
	Map getRoleById(Long roleId);
	/**
	 * 更新角色
	 * @param wrapBean
	 */
	void updateRole(Map role);
	/**
	 * 删除角色（逻辑删除）
	 * @param roleId
	 */
	void deleteRoleById(Long roleId);
	/**
	 * 角色授权菜单
	 * @param role 
	 * @param menuList
	 */
	void authorizeRole(Map role, List<Map> menuList);
	/**
	 * 获取角色分组下的角色
	 * @param roleGroup
	 * @return
	 */
	List<Map> getRoleListInGroup(Map roleGroup);

}
