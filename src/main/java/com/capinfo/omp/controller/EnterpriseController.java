package com.capinfo.omp.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.service.EnterpriseService;


/**
 *
 * 展示主页面
 *
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/enterprise")
@SessionAttributes("eccomm_admin")
public class EnterpriseController {
	
	@Autowired
	private GeneralService generalService;
	@Autowired
	private EnterpriseService enterpriseService;
	
	//资源页面
	@RequestMapping("/initialize.shtml")
	public ModelAndView initialize(
			@ModelAttribute("eccomm_admin") SystemUser user,EnterpriseParameter parameter ) {
		ModelAndView mv = new ModelAndView("/omp/enterprise/initialize");
		mv.addObject("command", parameter);
		return mv;
	}
	//添加企业
	@RequestMapping("/addEnterprise.shtml")
	public ModelAndView addEnterprise(
			@ModelAttribute("eccomm_admin") SystemUser user,EnterpriseParameter parameter ) {
		ModelAndView mv = new ModelAndView("/omp/enterprise/initialize");
		Enterprise entity = parameter.getEntity();
		entity.setUse_flag(1l);
		entity.setCreatedate(new Date()); 
		generalService.saveOrUpdate(parameter.getEntity());
		mv.addObject("command", parameter);
		mv.addObject("msg", 1);
		return mv;
	}
	
	//查询企业名称
	@RequestMapping("/queryEnterprise.shtml")
	@ResponseBody
	public List<Enterprise> queryEnterprise(
			@ModelAttribute("eccomm_admin") SystemUser user,EnterpriseParameter parameter ) {
		List<Enterprise> list = enterpriseService.getListByName(parameter);
		return list;
		
	}
	
	



}
