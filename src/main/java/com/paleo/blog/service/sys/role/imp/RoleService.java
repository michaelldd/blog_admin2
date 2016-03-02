package com.paleo.blog.service.sys.role.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.cache.CacheNamespace;
import com.paleo.blog.cache.annotation.CacheEvict;
import com.paleo.blog.cache.annotation.Cacheable;
import com.paleo.blog.cache.annotation.Caching;
import com.paleo.blog.remote.sys.role.IRoleService;
import com.paleo.blog.service.sys.role.dao.IRoleDao;
import com.paleo.blog.tools.reflect.WrapUtils;

@Service("blog.service.sys.role.imp.RoleService")
public class RoleService implements IRoleService{

	@Resource
	private IRoleDao roleDao;
	
	public List<Map> getRoleList(Long userId) {
		return roleDao.getRoleList(userId);
	}

	public PageInfo<Map> showRolePage(Map role, RowBounds bounds) {
		return new PageInfo<Map>(roleDao.showRolePage(role,bounds));
	}

	@Transactional
	public void addRole(Map role) {
		roleDao.addRole(role);
	}

	@Cacheable(cacheName=CacheNamespace.USER_ROLE)
	public List<Map> getUserRoleList(Long userId) {
		return roleDao.getUserRoleList(userId);
	}

	public Map getRoleById(Long roleId) {
		return roleDao.getRoleById(roleId);
	}
	
	@Caching(evict=
			 {@CacheEvict(cacheName=CacheNamespace.USER_ROLE,allEntries=true),
			  @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
			)
	@Transactional
	public void updateRole(Map role) {
		roleDao.updateRole(role);
	}

	@Caching(evict=
		 {@CacheEvict(cacheName=CacheNamespace.USER_ROLE,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.ROLE_MENU,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
		)
	@Transactional
	public void deleteRoleById(Long roleId) {
		roleDao.deleteRoleById(roleId);
		Map role = WrapUtils.model();
		role.put("roleId", roleId);
		roleDao.deleteRoleMenus(role);
		roleDao.deleteUserRolesByRoleId(roleId);
	}
	
	@Caching(evict=
		  {@CacheEvict(cacheName=CacheNamespace.ROLE_MENU,allEntries=true),
		   @CacheEvict(cacheName=CacheNamespace.USER_MENU,allEntries=true),
		   @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
		)
	@Transactional
	public void authorizeRole(Map role,List<Map> menuList) {
		//1. 删除原角色菜单
		roleDao.deleteRoleMenus(role);
		//2. 增加现角色菜单
		if(menuList!=null&&!menuList.isEmpty()){
			roleDao.addRoleMenus(role,menuList);
		}
	}

	public List<Map> getRoleListInGroup(Map roleGroup) {
		return roleDao.getRoleListInGroup(roleGroup);
	}

}
