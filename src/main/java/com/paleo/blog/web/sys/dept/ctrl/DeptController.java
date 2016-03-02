package com.paleo.blog.web.sys.dept.ctrl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;
import com.paleo.blog.remote.sys.dept.IDeptService;
import com.paleo.blog.remote.sys.user.IUserService;
import com.paleo.blog.tools.mvc.ctrl.MSG;
import com.paleo.blog.tools.mvc.ctrl.OPT;
import com.paleo.blog.tools.mvc.page.PageUtils;
import com.paleo.blog.tools.mvc.view.EmptyViewResolver;
import com.paleo.blog.tools.reflect.WrapUtils;
import com.paleo.blog.web.core.BaseController;
import com.paleo.blog.web.core.mvc.bjui.bo.BjuiView;
import com.paleo.blog.web.sys.dept.bo.Dept;

@RequestMapping("/sys/dept/")
@Controller("blog.web.sys.dept.ctrl.DeptController")
public class DeptController extends BaseController{
	@Resource(name="blog.service.sys.dept.imp.DeptService")
	private IDeptService deptService;
	@Resource(name="blog.service.sys.user.imp.UserService")
	private IUserService userService;
	
	@RequestMapping(value = "list_dept_tree", method = { RequestMethod.POST, RequestMethod.GET })
	public String list_dept_tree(Dept bo,ModelMap rps){
		//从session获取dept
		bo.setDeptId(1l);
		List<Map> deptList = deptService.getDeptList(WrapUtils.wrapBean(bo));
		rps.put("deptList", deptList);
		return "sys/dept/list_dept_tree";
	}
	
	@RequestMapping(value = "list_dept", method = { RequestMethod.POST, RequestMethod.GET })
	public String list_dept(Dept bo,ModelMap rps){
		PageInfo<Map> deptPage = deptService.showDeptPage(WrapUtils.wrapBean(bo),PageUtils.buildRowBounds(req()));
		rps.put("deptPage", deptPage);
		rps.addAttribute("deptTreeId",req().getParameter("deptTreeId"));//前端ztree的div id
		return "sys/dept/list_dept";
	}
	
	@RequestMapping(value = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public String add(Dept bo,ModelMap rps){
		try{
			deptService.addDept(WrapUtils.wrapBean(bo));
			BjuiView.success(MSG.ADD_SUCCESS.getMsg(),true);
		}catch(Exception e){
			e.printStackTrace();
			BjuiView.fail(MSG.ADD_FAIL.getMsg(),false);
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
	@RequestMapping(value = "add_view", method = { RequestMethod.POST, RequestMethod.GET })
	public String add_view(Dept bo,ModelMap rps){
		rps.addAttribute("deptTreeId",req().getParameter("deptTreeId"));//前端ztree的div id
		rps.addAttribute("bo",bo);
		return "sys/dept/add";
	}
	
	@RequestMapping(value = "edit", method = { RequestMethod.POST, RequestMethod.GET })
	public String edit(Dept bo,ModelMap rps){
		//更新到数据库
		try{
			deptService.updateDept(WrapUtils.wrapBean(bo));
			BjuiView.success(MSG.UPDATE_SUCCESS.getMsg(),true);
		}catch(Exception e){
			e.printStackTrace();
			BjuiView.fail(MSG.UPDATE_FAIL.getMsg(),false);
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
	@RequestMapping(value = "edit_view", method = { RequestMethod.POST, RequestMethod.GET })
	public String edit_view(Dept bo,ModelMap rps){
		rps.addAttribute("bo",deptService.getDeptById(bo.getDeptId()));
		return "sys/dept/edit";
	}
	
	@RequestMapping(value = "delete",  method = { RequestMethod.POST, RequestMethod.GET })
	public String delete(Dept bo,ModelMap rps){
		try {
			int childDeptNum = deptService.getChildDeptList(WrapUtils.wrapBean(bo)).size();
			int userNumInDept = userService.getUserListInDept(WrapUtils.wrapBean(bo)).size();
			if(childDeptNum>0){//1. 判断是否存在子机构，存在子机构则不允许删除，返回结果
				BjuiView.fail("该机构下存在"+childDeptNum+"个子机构，不允许删除！");
			}else if(userNumInDept>0){//2. 如果不存在子机构，判断机构是否有用户，有用户则不允许删除，返回结果
				BjuiView.fail("该机构下存在"+userNumInDept+"个用户，不允许删除！");
			}else{//3. 否则便可删除机构
				deptService.deleteDeptById(bo.getDeptId());
				BjuiView.success(MSG.DELETE_SUCCESS.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			BjuiView.fail(MSG.DELETE_FAIL.getMsg());
		}
		return EmptyViewResolver.EMPTY_VIEW;
	}
	
}
