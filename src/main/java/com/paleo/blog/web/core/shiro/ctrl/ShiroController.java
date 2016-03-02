package com.paleo.blog.web.core.shiro.ctrl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paleo.blog.model.core.Shiro;
import com.paleo.blog.remote.sys.menu.IMenuService;
import com.paleo.blog.shiro.chain.imp.SimpleFilterChainDefinitionsService;
import com.paleo.blog.tools.spring.SpringContextHolder;
import com.paleo.blog.web.core.BaseController;

@RequestMapping("/core/shiro/")
@Controller("blog.web.core.login.ShiroController")
public class ShiroController extends BaseController{

	@Resource(name="blog.service.sys.menu.imp.MenuService")
	private IMenuService menuService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("permission")
	public String permission(ModelMap rps) throws Exception{
		SimpleFilterChainDefinitionsService chainService = (SimpleFilterChainDefinitionsService) SpringContextHolder.getBean("filterChainDefinitionsService");
		Map<String, String> chains = chainService.getFilterChains();
		//从chains里面提取menuCode，将有menuCode和没有menuCode的封装为2个List<Map> hasMenuCode和noMenuCode
		Map res = Shiro.getMenuCodesFromChains(chains);
		List<Map> hasMenuCode = (List<Map>) res.get("hasMenuCode");
		//根据hasMenuCode从数据库查询对应menu的信息
		List<Map> menuList = menuService.getMenuByMenuCodes(hasMenuCode);
		//将menuList和hasMenuCode合并
		List<Map> permsList = Shiro.addPermsToMenus(menuList,hasMenuCode);
		//将noMenuCode和permsList合并
		permsList.addAll((List<Map>) res.get("noMenuCode"));
		rps.put("permsList", permsList);
		return "_common/core/shiro/permission";
	}
	
}
