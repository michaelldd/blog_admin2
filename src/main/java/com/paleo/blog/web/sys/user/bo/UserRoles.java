package com.paleo.blog.web.sys.user.bo;

import java.util.List;

public class UserRoles {
	private User user;
	private List<Role> roleList;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
}
