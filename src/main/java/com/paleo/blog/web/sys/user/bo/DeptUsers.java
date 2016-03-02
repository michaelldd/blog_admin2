package com.paleo.blog.web.sys.user.bo;

import com.paleo.blog.web.sys.dept.bo.Dept;

public class DeptUsers {
	private Dept dept;
	private User user;
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
