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


	/**
	 * 服务体系管理首页
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/initialize.shtml")
	public ModelAndView initialize(
			@ModelAttribute("eccomm_admin") SystemUser user,ServiceSystemParameter parameter ) {
		ModelAndView mv = new ModelAndView("/omp/serviceSystem/initialize");
		//当前页
		int currentPieceNum = parameter.getCurrentPieceNum();
		//每页数量
		int perPieceSize = parameter.getPerPieceSize();

		HashMap<String, Object> ssList = serviceSystem.getSSList(user,parameter);
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
	/**
	 * 跳转到私有创建服务体系
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/addPrivateArchitecture.shtml")
	public ModelAndView addPrivateArchitecture(
			@ModelAttribute("eccomm_admin") SystemUser user,ServiceSystemParameter parameter ) {
		ModelAndView mv = new ModelAndView("/omp/serviceSystem/addPrivateArchitecture");
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
			@ModelAttribute("eccomm_admin") SystemUser user,Long stId ) {
		List<Map<String,Object>> list = serviceSystem.getQueryarchitecture(user,stId);
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

	/**
	 * 跳转到修改服务体系
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/udpArchitecture.shtml")
	public ModelAndView udpArchitecture(
			@ModelAttribute("eccomm_admin") SystemUser user,ServiceSystemParameter parameter ) {
		ModelAndView mv = new ModelAndView("/omp/serviceSystem/updArchitecture");
		Service_System ss = generalService.getObjectById(Service_System.class, parameter.getId());
		mv.addObject("ss", ss);
		mv.addObject("command", parameter);
		List<OmpRegion> cityS = enterpriseService.queryCounty(0L);
		mv.addObject("cityS", cityS);
		List<Omp_phone_type> tellList = generalService.getAllObjects(Omp_phone_type.class);
		mv.addObject("tellList", tellList);
		List<ServiceType> serviceTypes = generalService.getAllObjects(ServiceType.class);
		mv.addObject("serviceTypes", serviceTypes);
		return mv;
	}

	/**
	 * 获取服务体系
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/serchArchitecture.shtml")
	@ResponseBody
	public List<Map<String, Object>> serchArchitecture(
			@ModelAttribute("eccomm_admin") SystemUser user,ServiceSystemParameter parameter ) {
		Service_System ss = generalService.getObjectById(Service_System.class, parameter.getId());
		List<Map<String,Object>> list = serviceSystem.getQueryarchitecture(user,ss.getTelltype());
		for (Map<String, Object> map : list) {
			map.put("sid", ss.getM1());
			map.put("sname", ss.getS1Name().getServiceName());
			map.put("sid", ss.getM2());
			map.put("sname", ss.getS2Name().getServiceName());
			map.put("sid", ss.getM3());
			map.put("sname", ss.getS3Name().getServiceName());
			map.put("sid", ss.getM4());
			map.put("sname", ss.getS5Name().getServiceName());
			map.put("sid", ss.getM5());
			map.put("sname", ss.getS5Name().getServiceName());
			map.put("sid", ss.getM6());
			map.put("sname", ss.getS6Name().getServiceName());
			map.put("sid", ss.getM7());
			map.put("sname", ss.getS7Name().getServiceName());
			map.put("sid", ss.getM8());
			map.put("sname", ss.getS8Name().getServiceName());
			if(userType(user)!=2){
				map.put("sid", ss.getM9());
				map.put("sname", ss.getS9Name().getServiceName());
			}
			map.put("sid", ss.getM10());
			map.put("sname", ss.getS10Name().getServiceName());
			if(userType(user)==0){
				map.put("sid", ss.getM11());
				map.put("sname", ss.getS11Name().getServiceName());
			}
			if(userType(user)==1 || userType(user)==0){
				map.put("sid", ss.getM12());
				map.put("sname", ss.getS12Name().getServiceName());
			}
			if(userType(user)==0){
				map.put("sid", ss.getM13());
				map.put("sname", ss.getS13Name().getServiceName());
				map.put("sid", ss.getM14());
				map.put("sname", ss.getS14Name().getServiceName());
				map.put("sid", ss.getM15());
				map.put("sname", ss.getS15Name().getServiceName());
				map.put("sid", ss.getM16());
				map.put("sname", ss.getS16Name().getServiceName());
			}
		}
		return list;
	}

	//修改服务体系
		@RequestMapping("/updateServiceSystem.shtml")
		@ResponseBody
		public String updateServiceSystem(
				@ModelAttribute("eccomm_admin") SystemUser user,ServiceSystemParameter parameter) {
			serviceSystem.updateServiceSystem(parameter,user);
			return "修改成功";
		}

	//判断用户类型
		public int userType(SystemUser user){
			if("g".equals(user.getAccount_type())){
				return 1;
			}else if("m".equals(user.getAccount_type()) || "b".equals(user.getAccount_type())){
				return 2;
			}
			return 0;
			
		}
		//键位为空判断
//		public Stirng keyEmpty(Service_System ss){
//			
//			
//		}
}
