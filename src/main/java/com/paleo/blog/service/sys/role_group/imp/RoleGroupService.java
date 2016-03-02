package com.paleo.blog.service.sys.role_group.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.cache.CacheNamespace;
import com.paleo.blog.cache.annotation.CacheEvict;
import com.paleo.blog.remote.sys.role_group.IRoleGroupService;
import com.paleo.blog.service.sys.role_group.dao.IRoleGroupDao;
import com.paleo.blog.tools.SPT;

@Service("blog.service.sys.role_group.imp.RoleGroupService")
public class RoleGroupService implements IRoleGroupService{

	@Resource
	private IRoleGroupDao roleGroupDao;
	
	@Override
	public List<Map> getRoleGroupList(Map roleGroup) {
		return roleGroupDao.getRoleGroupList(roleGroup);
	}

	@Override
	public PageInfo<Map> showRoleGroupPage(Map roleGroup, RowBounds bounds) {
		return new PageInfo<Map>(roleGroupDao.showRoleGroupPage(roleGroup,bounds));
	}

	@Transactional
	public Map addRoleGroup(Map roleGroup) {
		roleGroupDao.addRoleGroup(roleGroup);
		//获取upper roleGroup后再获取tree
		String tree = (String) this.getRoleGroupById((Long)roleGroup.get("upperRoleGroupId")).get("tree");
		//拼接
		tree  = tree+roleGroup.get("roleGroupId")+SPT.DOT.getSpt();
		roleGroup.put("tree", tree);
		roleGroup.put("level", tree.split("\\"+SPT.DOT.getSpt()).length-1);
		roleGroupDao.updateRoleGroup(roleGroup);
		return roleGroup;
	}

	@CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)
	@Transactional
	public void updateRoleGroup(Map roleGroup) {
		//获取roleGroup后再获取tree
		Map father = this.getRoleGroupById((Long)roleGroup.get("upperRoleGroupId"));
		String newTree = father.get("tree") +""+ roleGroup.get("deptId")+SPT.DOT.getSpt();
		roleGroup.put("tree", newTree);
		
		String oldTree = (String) roleGroup.get("tree");
		//level的改变
		int levRes = newTree.split("\\"+SPT.DOT.getSpt()).length - oldTree.split("\\"+SPT.DOT.getSpt()).length;
		roleGroup.put("level", newTree.split("\\"+SPT.DOT.getSpt()).length);
		
		
		roleGroupDao.updateRoleGroup(roleGroup);
		//将关联的子角色组的tree修改为newTree,前缀替换
		roleGroupDao.updateTrees(oldTree,newTree,levRes);
	}

	@Override
	public Map getRoleGroupById(Long roleGroupId) {
		return roleGroupDao.getRoleGroupById(roleGroupId);
	}

	@Override
	public List<Map> getChildRoleGroupList(Map roleGroup) {
		return roleGroupDao.getChildRoleGroupList(roleGroup);
	}

	@CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)
	@Transactional
	public void deleteRoleGroupById(Long roleGroupId) {
		 roleGroupDao.deleteRoleGroupById(roleGroupId);
	}

}
