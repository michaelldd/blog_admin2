package com.paleo.blog.remote.sys.role_group;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.PageInfo;

public interface IRoleGroupService {
	
	List<Map> getRoleGroupList(Map roleGroup);

	PageInfo<Map> showRoleGroupPage(Map roleGroup, RowBounds bounds);

	Map addRoleGroup(Map roleGroup);

	void updateRoleGroup(Map roleGroup);

	Map getRoleGroupById(Long roleGroupId);

	List<Map> getChildRoleGroupList(Map roleGroup);

	void deleteRoleGroupById(Long roleGroupId);
}
