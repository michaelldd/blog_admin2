package com.paleo.blog.web.sys.role.bo;

public class Role {
	private Long roleId;
	private String roleName;
	private Long roleGroupId;
	private String roleGroupName;
	private Integer status;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getRoleGroupId() {
		return roleGroupId;
	}
	public void setRoleGroupId(Long roleGroupId) {
		this.roleGroupId = roleGroupId;
	}
	public String getRoleGroupName() {
		return roleGroupName;
	}
	public void setRoleGroupName(String roleGroupName) {
		this.roleGroupName = roleGroupName;
	}
}
