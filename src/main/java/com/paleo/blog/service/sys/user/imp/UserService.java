package com.paleo.blog.service.sys.user.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.cache.CacheNamespace;
import com.paleo.blog.cache.annotation.CacheEvict;
import com.paleo.blog.cache.annotation.Caching;
import com.paleo.blog.remote.sys.user.IUserService;
import com.paleo.blog.service.sys.user.dao.IUserDao;
import com.paleo.blog.tools.reflect.WrapUtils;

@Service("blog.service.sys.user.imp.UserService")
public class UserService implements IUserService{

	@Resource
	private IUserDao userDao;
	
	@Override
	public PageInfo<Map> showUserPage(Map deptUsers, RowBounds bounds) {
		return new PageInfo<Map>(userDao.showUserPage(deptUsers,bounds));
	}

	@Transactional
	public void addUser(Map user) {
		userDao.addUser(user);
	}

	@Override
	public Map getUserById(Long userId) {
		return userDao.getUserById(userId);
	}

	@Caching(evict=
		 {@CacheEvict(cacheName=CacheNamespace.USER_ROLE,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.USER_MENU,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
		)
	@Transactional
	public void updateUser(Map user) {
		userDao.updateUser(user);
	}

	@Override
	public List<Map> isUnique(Map user) {
		return userDao.isUnique(user);
	}

	@Caching(evict=
		 {@CacheEvict(cacheName=CacheNamespace.USER_ROLE,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
		)
	@Transactional
	public void authorizeUser(Map userRoles) {
		//1. 删除原用户角色
		userDao.deleteUserRoles((Map)userRoles.get("user"));
		//2. 增加现用户角色
		if(userRoles.get("roleList")!=null&&!((List<Map>)userRoles.get("roleList")).isEmpty()){
			userDao.addUserRoles(userRoles);
		}
	}

	@Caching(evict=
		 {@CacheEvict(cacheName=CacheNamespace.USER_ROLE,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.USER_MENU,allEntries=true),
		  @CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)}
		)
	@Transactional
	public void deleteUserById(Long userId) {
		userDao.deleteUserById(userId);
		Map user = WrapUtils.model();
		user.put("userId", userId);
		userDao.deleteUserRoles(user);
	}

	@Override
	public List<Map> getUserListInDept(Map dept) {
		return userDao.getUserListInDept(dept);
	}

	@Override
	public Map getUserByName(String userName) {
		return userDao.getUserByName(userName);
	}

}
