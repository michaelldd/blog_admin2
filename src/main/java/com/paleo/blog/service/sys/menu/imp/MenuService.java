package com.paleo.blog.service.sys.menu.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.cache.AfterCache;
import com.paleo.blog.cache.CacheNamespace;
import com.paleo.blog.cache.annotation.CacheEvict;
import com.paleo.blog.cache.annotation.Cacheable;
import com.paleo.blog.cache.annotation.Caching;
import com.paleo.blog.model.sys.menu.tree.ztree.MenuTree;
import com.paleo.blog.model.sys.menu.tree.ztree.TreeUtils;
import com.paleo.blog.remote.sys.menu.IMenuService;
import com.paleo.blog.service.sys.menu.dao.IMenuDao;
import com.paleo.blog.tools.SPT;
import com.paleo.blog.tools.json.jackson.JacksonUtils;

@Service("blog.service.sys.menu.imp.MenuService")
public class MenuService implements IMenuService{

	@Resource
	private IMenuDao menuDao;
	
	public List<Map> getMenuList() {
		return menuDao.getMenuList();
	}

	public Map getMenuById(Long menuId) {
		return menuDao.getMenuById(menuId);
	}
	/**
	 * Name为空暂时删除不了缓存，所以不影响缓存
	 */
	@CacheEvict(cacheName = CacheNamespace.NOT_EXIST,afterCache=AfterCache.SHIRO_PERMISSION_UPDATE)
	@Transactional
	public Map addMenu(Map menu) {
		menuDao.addMenu(menu);
		//获取upper menu后再获取tree
		String tree = (String) this.getMenuById((Long)menu.get("upperMenuId")).get("tree");
		//拼接
		tree  = tree+menu.get("menuId")+SPT.DOT.getSpt();
		menu.put("tree", tree);
		menu.put("level", tree.split("\\"+SPT.DOT.getSpt()).length-1);
		updateMenu(menu);
		return menu;
	}

	@Caching(
			evict={@CacheEvict(cacheName = CacheNamespace.ROLE_MENU,allEntries=true),
				  @CacheEvict(cacheName = CacheNamespace.USER_MENU,allEntries=true),
				  @CacheEvict(cacheName = CacheNamespace.ACCOUNT,allEntries=true),
				  @CacheEvict(cacheName = CacheNamespace.NOT_EXIST,afterCache=AfterCache.SHIRO_PERMISSION_UPDATE)}
			)
	@Transactional
	public void updateMenu(Map menu) {
		menuDao.updateMenu(menu);
	}

	@Caching(
			evict={@CacheEvict(cacheName = CacheNamespace.ROLE_MENU,allEntries=true),
				  @CacheEvict(cacheName = CacheNamespace.USER_MENU,allEntries=true),
				  @CacheEvict(cacheName = CacheNamespace.ACCOUNT,allEntries=true),
				  @CacheEvict(cacheName = CacheNamespace.NOT_EXIST,afterCache=AfterCache.SHIRO_PERMISSION_UPDATE)}
			)
	@Transactional
	public void deleteMenuById(Long menuId) {
		menuDao.deleteMenuById(menuId);
	}

	@Caching(
			evict={@CacheEvict(cacheName = CacheNamespace.ROLE_MENU,allEntries=true),
				  @CacheEvict(cacheName = CacheNamespace.USER_MENU,allEntries=true),
				  @CacheEvict(cacheName = CacheNamespace.ACCOUNT,allEntries=true),
				  @CacheEvict(cacheName = CacheNamespace.NOT_EXIST,afterCache=AfterCache.SHIRO_PERMISSION_UPDATE)}
			)
	@Transactional
	public void deleteMenuIncludeChildrenById(Long menuId) {
		menuDao.deleteMenuIncludeChildrenById(menuId);
	}

	@CacheEvict(cacheName = CacheNamespace.ACCOUNT,allEntries=true)
	@Transactional
	public void sortMenu(String menuTreeJson) {
		MenuTree menuTree = JacksonUtils.json2Obj(menuTreeJson, MenuTree[].class)[0];
		//遍历多叉树，转为List<Menu>用于更新
		List<com.paleo.blog.model.sys.menu.tree.ztree.Menu> menuList = TreeUtils.iteratorTree(menuTree,SPT.DOT.getSpt());
		//批量更新
		menuDao.sortMenu(menuList);
	}

	public PageInfo<Map> showMenuChildrenPage(Map menu, RowBounds bounds) {
		return new PageInfo(menuDao.showMenuChildrenPage(menu,bounds));
	}
	
	@Cacheable(cacheName=CacheNamespace.ROLE_MENU)
	public List<Map> getRoleMenuList(Long roleId) {
		return menuDao.getRoleMenuList(roleId);
	}

	@Cacheable(cacheName=CacheNamespace.USER_MENU)
	public List<Map> getUserMenuList(Long userId) {
		return menuDao.getUserMenuList(userId);
	}

	public List<Map> isUnique(Map menu) {
		return menuDao.isUnique(menu);
	}

	public List<Map> getMenuByMenuCodes(List<Map> hasMenuCode) {
		return menuDao.getMenuByMenuCodes(hasMenuCode);
	}
}
