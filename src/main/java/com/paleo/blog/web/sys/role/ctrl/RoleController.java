package com.paleo.blog.web.sys.role.ctrl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.model.sys.menu.Menu;
import com.paleo.blog.remote.sys.menu.IMenuService;
import com.paleo.blog.remote.sys.role.IRoleService;
import com.paleo.blog.remote.sys.role_group.IRoleGroupService;
import com.paleo.blog.tools.json.jackson.JacksonUtils;
import com.paleo.blog.tools.mvc.ctrl.MSG;
import com.paleo.blog.tools.mvc.ctrl.OPT;
import com.paleo.blog.tools.mvc.page.PageUtils;
import com.paleo.blog.tools.mvc.view.EmptyViewResolver;
import com.paleo.blog.tools.mvc.view.JsonView;
import com.paleo.blog.tools.reflect.WrapUtils;
import com.paleo.blog.web.core.BaseController;
import com.paleo.blog.web.core.mvc.bjui.bo.BjuiView;
import com.paleo.blog.web.sys.role.bo.Role;
import com.paleo.blog.web.sys.role.bo.RoleGroup;
import com.paleo.blog.web.sys.role.bo.RoleGroupRoles;

@RequestMapping("/sys/role/")
@Controller("blog.web.sys.role.ctrl.RoleController")
public class RoleController extends BaseController{

	@Resource(name="blog.service.sys.role.imp.RoleService")
	private IRoleService roleService;
	@Resource(name="blog.service.sys.menu.imp.MenuService")
	private IMenuService menuService;
	@Resource(name="blog.service.sys.role_group.imp.RoleGroupService")
	private IRoleGroupService roleGroupService;
	
	@RequestMapping(value = "list_role_group_tree", method = { RequestMethod.POST, RequestMethod.GET })
	public String list_dept_tree(RoleGroup bo,ModelMap rps){
		Map roleGroup = WrapUtils.model();
		roleGroup.put("roleGroupId", 1);
		List<Map> roleGroupList = roleGroupService.getRoleGroupList(roleGroup);
		rps.put("roleGroupList", roleGroupList);
		return "sys/role/list_role_group_tree";
	}
	
	@RequestMapping(value = "list_role", method = { RequestMethod.POST, RequestMethod.GET })
	public String list(RoleGroupRoles bo,ModelMap rps){
		PageInfo<Map> rolePage = roleService.showRolePage(WrapUtils.wrapBean(bo),PageUtils.buildRowBounds(req()));
		rps.put("rolePage", rolePage);
		return "sys/role/list_role";
	}

	@RequestMapping(value = "add_view", method = { RequestMethod.POST, RequestMethod.GET })
	public String add_view(Role bo,ModelMap rps){
		rps.addAttribute("bo",bo);
		return "sys/role/add";
	}
	
	@RequestMapping(value = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public String add(Role bo){
		try{
			roleService.addRole(WrapUtils.wrapBean(bo));
			BjuiView.success(MSG.ADD_SUCCESS.getMsg(),true);
		}catch(Exception e){
			e.printStackTrace();
			BjuiView.fail(MSG.ADD_FAIL.getMsg(),false);
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
	@RequestMapping(value = "edit_view", method = { RequestMethod.POST, RequestMethod.GET })
	public String edit_view(Role bo,ModelMap rps){
		rps.addAttribute("bo",roleService.getRoleById(bo.getRoleId()));
		return "sys/role/edit";
	}
	
	@RequestMapping(value = "edit", method = { RequestMethod.POST, RequestMethod.GET })
	public String edit(Role bo){
		try{
			roleService.updateRole(WrapUtils.wrapBean(bo));
			BjuiView.success(MSG.UPDATE_SUCCESS.getMsg(),true);
		}catch(Exception e){
			e.printStackTrace();
			BjuiView.fail(MSG.UPDATE_FAIL.getMsg(),false);
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
	@RequestMapping(value = "delete",  method = { RequestMethod.POST, RequestMethod.GET })
	public String delete(Role bo,ModelMap rps){
		try {
			roleService.deleteRoleById(bo.getRoleId());
			BjuiView.success(MSG.DELETE_SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			BjuiView.fail(MSG.DELETE_FAIL.getMsg());
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
	@RequestMapping(value = "authorize_view", method = { RequestMethod.POST, RequestMethod.GET })
	public String authorize_view(Role bo,ModelMap rps){
		List<Map> menuList =  menuService.getMenuList();//登陆用户可分配的菜单
		List<Map> roleMenuList =  menuService.getRoleMenuList(bo.getRoleId());//角色已授权的菜单
		rps.put("checkedMenuList",Menu.buildCheckedMenus(menuList,roleMenuList));
		rps.put("role", roleService.getRoleById(bo.getRoleId()));
		return "sys/role/authorize";
	}
	@RequestMapping(value = "authorize", method = { RequestMethod.POST, RequestMethod.GET })
	public String authorize(@RequestParam(value="menuTreeArray",required=false) String menuTreeArray,Role bo,ModelMap rps){
		try{
			roleService.authorizeRole(WrapUtils.wrapBean(bo),JacksonUtils.json2List(menuTreeArray, Map.class));
			JsonView.success(res(), MSG.AUTHRIZE_SUCCESS.getMsg());
		}catch(Exception e){
			e.printStackTrace();
			JsonView.error(res(),MSG.AUTHRIZE_FAIL.getMsg());
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
}
