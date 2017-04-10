package com.capinfo.omp.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.omp.model.Composition;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.model.Omp_phone_type;
import com.capinfo.omp.model.ServiceProvider;
import com.capinfo.omp.model.ServiceType;
import com.capinfo.omp.model.Service_System;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.parameter.ServiceProviderParameter;
import com.capinfo.omp.parameter.ServiceSystemParameter;
import com.capinfo.omp.service.EnterpriseService;
import com.capinfo.omp.service.ServiceSystemService;
import com.capinfo.omp.service.impl.EnterpriseServiceImpl;
import com.capinfo.region.model.OmpRegion;


/**
 *
 * 服务体系
 *
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/serviceSystem")
@SessionAttributes("eccomm_admin")
public class ServiceSystemController {

	@Autowired
	private GeneralService generalService;
	@Resource(name = "serviceSystemService")
	private ServiceSystemService serviceSystem;
	@Autowired
	private EnterpriseService enterpriseService;


	//资源页面
	@RequestMapping("/initialize.shtml")
	public ModelAndView initialize(
			@ModelAttribute("eccomm_admin") SystemUser user,ServiceSystemParameter parameter ) {
		ModelAndView mv = new ModelAndView("/omp/serviceSystem/initialize");
		//当前页
		int currentPieceNum = parameter.getCurrentPieceNum();
		//每页数量
		int perPieceSize = parameter.getPerPieceSize();
		
		HashMap<String, Object> ssList = serviceSystem.getSSList(parameter);
		mv.addObject("count",ssList.get("count"));
		mv.addObject("list",ssList.get("list"));
		mv.addObject("command", parameter);
		//serviceSystem.getSSPList();
		return mv;
	}

	/**
	 * 跳转到创建服务体系
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/addArchitecture.shtml")
	public ModelAndView addArchitecture(
			@ModelAttribute("eccomm_admin") SystemUser user,ServiceSystemParameter parameter ) {
		ModelAndView mv = new ModelAndView("/omp/serviceSystem/addArchitecture");
		mv.addObject("command", parameter);
		List<OmpRegion> cityS = enterpriseService.queryCounty(0L);
		mv.addObject("cityS", cityS);
		List<Omp_phone_type> tellList = generalService.getAllObjects(Omp_phone_type.class);
		mv.addObject("tellList", tellList);
		List<ServiceType> serviceTypes = generalService.getAllObjects(ServiceType.class);
		mv.addObject("serviceTypes", serviceTypes);
		return mv;
	}

	//查询体系结构
	@RequestMapping("/queryarchitecture.shtml")
	@ResponseBody
	public List<Map<String, Object>> architecture(
			@ModelAttribute("eccomm_admin") SystemUser user,int stId ) {
		List<Map<String,Object>> list = serviceSystem.getQueryarchitecture(stId);
		return list;
	}

	//查询服务商
	@RequestMapping("/serchService.shtml")
	@ResponseBody
	public List<Map<String, Object>> serchService(
			@ModelAttribute("eccomm_admin") SystemUser user,ServiceProviderParameter parameter) {
		List<Map<String,Object>> list = serviceSystem.serchService(parameter);
		return list;
	}
	//添加服务体系
	@RequestMapping("/addServiceSystem.shtml")
	@ResponseBody
	public String addServiceSystem(
			@ModelAttribute("eccomm_admin") SystemUser user,ServiceSystemParameter parameter) {
		serviceSystem.addServiceSystem(parameter,user);
		return "添加成功";
	}

}
