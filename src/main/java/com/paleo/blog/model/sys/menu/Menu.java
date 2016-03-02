package com.paleo.blog.model.sys.menu;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Menu {
	
	public final static int MENU_TYPE_FUNC = 1;//1为功能菜单
	/**
	 * 增加checked属性
	 * @param menuList 可授权菜单列表
	 * @param roleMenuList 被授权角色已被授权菜单列表
	 * @return 
	 */
	public static Object buildCheckedMenus(List<Map> menuList,List<Map> roleMenuList) {
		try{
			for(int i=0;i<menuList.size();i++){
				Map menu = menuList.get(i);
				for(Map rmenu:roleMenuList){
					/**
					 * 强转Long，Redis缓存取出来的Json反序列化小数字会识别为Integer，这也是Map
					 * 对比Bean的一大缺点，强转隐藏的运行时错误！
					 */
//					if(((Long)menu.get("menuId")).compareTo((Long)rmenu.get("menuId"))==0){
					if((new Long(menu.get("menuId")+"")).compareTo(new Long(rmenu.get("menuId")+""))==0){
						menu.put("checked", true);
						menuList.set(i, menu);
					}
				}
			}
		}catch(Exception e){
			//异常返回自身
			e.printStackTrace();
		}
		return menuList;
	}

	/**
	 * 去除掉功能菜单，构建主菜单
	 * @param menuList
	 * @return
	 */
	public static List<com.paleo.blog.model.sys.account.bo.Menu> buildMainMenus(List< com.paleo.blog.model.sys.account.bo.Menu> menuList) {
		for (Iterator<com.paleo.blog.model.sys.account.bo.Menu> it = menuList.iterator(); it.hasNext();) {
			if(it.next().getType()==MENU_TYPE_FUNC){
				it.remove();
			}
		}
		return menuList;
	}
}
