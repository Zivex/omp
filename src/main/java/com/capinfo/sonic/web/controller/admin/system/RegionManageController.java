package com.capinfo.sonic.web.controller.admin.system;


import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.common.web.service.RegionManageService;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.omp.parameter.RegionManageParameter;

@Controller
@RequestMapping("/admin/sys/region")
public class RegionManageController {
	@Autowired
	private GeneralService generalService;
	
	@Autowired
	private RegionManageService service;

	/**
	 *区域管理首页
	 * 
	 * @param request
	 * @param response
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/initial.shtml")
	public ModelAndView index(NativeWebRequest request, HttpServletResponse response, RegionManageParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/regionManage/initialize");
		getregionList(parameter,mv);
		
		return mv;
	}
	/**
	 *区域管理查询
	 * 
	 * @param request
	 * @param response
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/query.shtml")
	public ModelAndView query(NativeWebRequest request, HttpServletResponse response, RegionManageParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/regionManage/list");
		getregionList(parameter,mv);
		return mv;
	}
	
	public void getregionList(RegionManageParameter parameter, ModelAndView mv){
		Map<String, Object> map = service.getregionList(parameter);
		mv.addObject("list",map.get("list"));
		mv.addObject("count",map.get("count"));
		mv.addObject("command", parameter);
	}
/**
 * 添加省/直辖市
 * @param request
 * @param response
 * @param parameter
 * @return
 */
	@RequestMapping("/addRegion.shtml")
	@ResponseBody
	public String addRegion(NativeWebRequest request, HttpServletResponse response, RegionManageParameter parameter) {
		service.addRegion(parameter);
		return "添加成功";
	}
	/**
	 * 添加省/直辖市
	 * @param request
	 * @param response
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/addChildRegion.shtml")
	@ResponseBody
	public String addChildRegion(NativeWebRequest request, HttpServletResponse response, RegionManageParameter parameter) {
		service.addChildRegion(parameter);
		return "添加成功";
	}
	/**
	 * 修改区域名称
	 * @param request
	 * @param response
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/updateRegion.shtml")
	@ResponseBody
	public String updateRegion(NativeWebRequest request, HttpServletResponse response, RegionManageParameter parameter) {
		service.updateRegion(parameter);
		return "修改成功";
	}
	/**
	 * 删除区域名称
	 * @param request
	 * @param response
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/deleteRegion.shtml")
	@ResponseBody
	public String deleteRegion(NativeWebRequest request, HttpServletResponse response, RegionManageParameter parameter) {
		service.deleteRegion(parameter);
		return "删除成功";
	}
}
