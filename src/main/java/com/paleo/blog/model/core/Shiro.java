package com.paleo.blog.model.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.paleo.blog.tools.reflect.WrapUtils;

public class Shiro {
	//"perms[{0}]"; // 资源结构格式
	//正则 http://www.cnblogs.com/yirlin/archive/2006/04/12/373222.html
	private static final String PATTERN_PERMS = "^perms\\[(.*)\\]$";
	
	public static Map getMenuCodesFromChains(Map<String, String> chains) {
		Map res = WrapUtils.model();
		List<Map> hasMenuCode = WrapUtils.list();
		List<Map> noMenuCode = WrapUtils.list();
		Pattern p=Pattern.compile(PATTERN_PERMS);  
		Set<Entry<String, String>> c =  chains.entrySet();
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			Map e = WrapUtils.model();
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			e.put("url", entry.getKey());
			e.put("perms", entry.getValue());
			Matcher m=p.matcher(entry.getValue());  
			if(m.find()){  
				e.put("menuCode", m.group(1));
			}else{//没有匹配
				e.put("menuCode", null);
			}
			if(StringUtils.isEmpty((String) e.get("menuCode"))){
				noMenuCode.add(e);
			}else{
				hasMenuCode.add(e);
			}
		}
		res.put("hasMenuCode",hasMenuCode);
		res.put("noMenuCode",noMenuCode);
		return res;
	}

	/**
	 * 合并数据，将perms，url，menuCode，menu信息合并在一起,menuList与hasMenuCode的对应顺序是一样的
	 * @param menuList 根据hasMenuCode的menuCode查出来的menuList
	 * @param hasMenuCode  含有menuCode，url，perms的List<Map>
	 * @return
	 * @throws Exception 
	 */
	public static List<Map> addPermsToMenus(List<Map> menuList, List<Map> hasMenuCode) throws Exception {
		if(menuList.size()!=hasMenuCode.size()){
			throw new Exception("数据异常！");
		}else{
			List<Map> res = WrapUtils.list();
			for(int i=0;i<hasMenuCode.size();i++){
				Map e = hasMenuCode.get(i);
				e.put("menuName", menuList.get(i).get("menuName"));
				e.put("upperMenuName", menuList.get(i).get("upperMenuName"));
				res.add(e);
			}
			return res;
		}
	}

}
