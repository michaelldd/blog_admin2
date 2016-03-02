package com.paleo.blog.service.sys.menu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.paleo.blog.model.sys.menu.tree.ztree.Menu;


public interface IMenuDao {

	public List<Map> getMenuList();

	public Map getMenuById(@Param("menuId")Long menuId);

	public void addMenu(@Param("bo")Map menu);

	public void updateMenu(@Param("bo")Map menu);

	public void deleteMenuById(@Param("menuId")Long menuId);

	public void deleteMenuIncludeChildrenById(@Param("menuId")Long menuId);

	public void sortMenu(@Param("menuList")List<Menu> menuList);

	public List showMenuChildrenPage(@Param("bo")Map menu, RowBounds bounds);
	
	List<Map> getRoleMenuList(@Param("roleId")Long roleId);

	public List<Map> getAllMenuList();

	public List<Map> getUserMenuList(@Param("userId")Long userId);

	public List<Map> isUnique(@Param("bo")Map menu);

	public List<Map> getMenuByMenuCodes(@Param("menuList")List<Map> hasMenuCode);
}
