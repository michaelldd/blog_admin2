package com.paleo.blog.model.sys.role;

import java.util.List;
import java.util.Map;

public class Role {
	/**
	 * 增加selected属性和isManaged
	 * @param roleList 授权用户可授权角色列表
	 * @param userRoleList 被授权用户已被授权角色列表
	 * @return 
	 */
	public static List<Map> buildSelectedRoles(List<Map> roleList,List<Map>userRoleList){
		try{
			for(int i=0;i<roleList.size();i++){
				Map role = roleList.get(i);
				for(Map urole:userRoleList){
					if(((Long)role.get("roleId")).compareTo((Long)urole.get("roleId"))==0){
						role.put("selected", true);
						role.put("isManaged", userRoleList.get(i).get("isManaged"));
						roleList.set(i, role);
					}
				}
			}
		}catch(Exception e){
			//异常返回自身
		}
		return roleList;
	}
}
