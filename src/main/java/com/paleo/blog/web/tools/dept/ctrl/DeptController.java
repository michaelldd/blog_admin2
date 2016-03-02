package com.paleo.blog.web.tools.dept.ctrl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paleo.blog.remote.sys.dept.IDeptService;
import com.paleo.blog.tools.reflect.WrapUtils;
import com.paleo.blog.web.core.BaseController;
import com.paleo.blog.web.tools.dept.bo.Dept;

@RequestMapping("/tools/dept/")
@Controller("blog.web.tools.dept.ctrl.DeptController")
public class DeptController extends BaseController{

	@Resource(name="blog.service.sys.dept.imp.DeptService")
	private IDeptService deptService;
	
	@RequestMapping(value = "single_select", method = { RequestMethod.POST, RequestMethod.GET })
	public String single_select(Dept bo,ModelMap rps){
		List<Map> deptList = deptService.getDeptList(WrapUtils.wrapBean(bo));
		rps.addAttribute("deptList", deptList);
		return "_common/tools/dept/single_select";
	}
}
