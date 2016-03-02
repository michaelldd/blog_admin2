package com.paleo.blog.service.sys.dept.imp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paleo.blog.cache.CacheNamespace;
import com.paleo.blog.cache.annotation.CacheEvict;
import com.paleo.blog.cache.annotation.Cacheable;
import com.paleo.blog.remote.sys.dept.IDeptService;
import com.paleo.blog.service.sys.dept.dao.IDeptDao;
import com.paleo.blog.tools.SPT;

@Service("blog.service.sys.dept.imp.DeptService")
public class DeptService implements IDeptService{
	@Resource
	private IDeptDao deptDao;

	public PageInfo<Map> showDeptPage(Map dept,RowBounds bounds) {
		return new PageInfo<Map>(deptDao.showDeptPage(dept,bounds));
	}
	
	
	public List<Map> getDeptList(Map dept) {
		return deptDao.getDeptList(dept);
	}
	
	@Transactional
	public Map addDept(Map dept) {
		deptDao.addDept(dept);
		//获取upper menu后再获取tree
		String tree = (String) this.getDeptById((Long)dept.get("upperDeptId")).get("tree");
		//拼接
		tree  = tree+dept.get("deptId")+SPT.DOT.getSpt();
		dept.put("tree", tree);
		dept.put("level", tree.split("\\"+SPT.DOT.getSpt()).length-1);
		updateDept(dept);
		return dept;
	}
	
	public Map getDeptById(Long deptId) {
		return deptDao.getDeptById(deptId);
	}
	
	@CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)
	@Transactional
	public void updateDept(Map dept) {
		//获取dept后再获取tree
		Map father = this.getDeptById((Long)dept.get("upperDeptId"));
		String newTree = father.get("tree") +""+ dept.get("deptId")+SPT.DOT.getSpt();
		dept.put("tree", newTree);
		
		String oldTree = (String) dept.get("tree");
		//level的改变
		int levRes = newTree.split("\\"+SPT.DOT.getSpt()).length - oldTree.split("\\"+SPT.DOT.getSpt()).length;
		dept.put("level", newTree.split("\\"+SPT.DOT.getSpt()).length);
		
		
		deptDao.updateDept(dept);
		//将关联的子机构的tree修改为newTree,前缀替换
		deptDao.updateTrees(oldTree,newTree,levRes);
	}

	public List<Map> getChildDeptList(Map dept) {
		return deptDao.getChildDeptList(dept);
	}

	@CacheEvict(cacheName=CacheNamespace.ACCOUNT,allEntries=true)
	@Transactional
	public void deleteDeptById(Long deptId) {
		deptDao.deleteDeptById(deptId);
	}

}
