package com.capinfo.omp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.omp.model.Omp_phone_type;
import com.capinfo.omp.model.ServiceType;
import com.capinfo.omp.model.Sys_key;
import com.capinfo.omp.parameter.ServiceProviderParameter;
import com.capinfo.omp.parameter.ServiceSystemParameter;
import com.capinfo.omp.service.EnterpriseService;
import com.capinfo.omp.service.ServiceSystemService;
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
	 *
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/initialize.shtml")
	public ModelAndView initialize(
			@ModelAttribute("eccomm_admin") SystemUser user,
			ServiceSystemParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/serviceSystem/initialize");
		// 当前页
		// int currentPieceNum = parameter.getCurrentPieceNum();
		// //每页数量
		// int perPieceSize = parameter.getPerPieceSize();
		//
		HashMap<String, Object> ssList = serviceSystem.getSSList(user,
				parameter);
		mv.addObject("count", ssList.get("count"));
		mv.addObject("list", ssList.get("list"));
		mv.addObject("command", parameter);
		// serviceSystem.getSSPList();
		return mv;
	}
	/**
	 * 查询
	 *
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/list.shtml")
	public ModelAndView getList(
			@ModelAttribute("eccomm_admin") SystemUser user,
			ServiceSystemParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/serviceSystem/list");
		// 当前页
		// int currentPieceNum = parameter.getCurrentPieceNum();
		// //每页数量
		// int perPieceSize = parameter.getPerPieceSize();
		//
		HashMap<String, Object> ssList = serviceSystem.getSSList(user,
				parameter);
		mv.addObject("count", ssList.get("count"));
		mv.addObject("list", ssList.get("list"));
		mv.addObject("command", parameter);
		// serviceSystem.getSSPList();
		return mv;
	}

	/**
	 * 跳转到创建服务体系
	 *
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/addArchitecture.shtml")
	public ModelAndView addArchitecture(
			@ModelAttribute("eccomm_admin") SystemUser user,
			ServiceSystemParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/serviceSystem/addArchitecture");
		mv.addObject("command", parameter);
		List<OmpRegion> cityS = enterpriseService.queryCounty(0L);
		mv.addObject("cityS", cityS);
		List<Omp_phone_type> tellList = generalService
				.getAllObjects(Omp_phone_type.class);
		mv.addObject("tellList", tellList);
		List<ServiceType> serviceTypes = generalService
				.getAllObjects(ServiceType.class);
		mv.addObject("serviceTypes", serviceTypes);
		return mv;
	}

	/**
	 * 跳转到私有创建服务体系
	 *
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/addPrivateArchitecture.shtml")
	public ModelAndView addPrivateArchitecture(
			@ModelAttribute("eccomm_admin") SystemUser user,
			ServiceSystemParameter parameter) {
		ModelAndView mv = new ModelAndView(
				"/omp/serviceSystem/addPrivateArchitecture");
		mv.addObject("command", parameter);
		List<OmpRegion> cityS = enterpriseService.queryCounty(0L);
		mv.addObject("cityS", cityS);
		List<Omp_phone_type> tellList = generalService
				.getAllObjects(Omp_phone_type.class);
		mv.addObject("tellList", tellList);
		List<ServiceType> serviceTypes = generalService
				.getAllObjects(ServiceType.class);
		mv.addObject("serviceTypes", serviceTypes);
		return mv;
	}

	// 查询体系结构
	@RequestMapping("/queryarchitecture.shtml")
	@ResponseBody
	public List<Map<String, Object>> architecture(
			@ModelAttribute("eccomm_admin") SystemUser user, Long stId) {
		List<Map<String, Object>> list = serviceSystem.getQueryarchitecture(
				user, stId);
		return list;
	}

	// 查询服务商
	@RequestMapping("/serchService.shtml")
	@ResponseBody
	public List<Map<String, Object>> serchService(
			@ModelAttribute("eccomm_admin") SystemUser user,
			ServiceProviderParameter parameter) {
		List<Map<String, Object>> list = serviceSystem.serchService(parameter);
		return list;
	}

	// 添加服务体系
	@RequestMapping("/addServiceSystem.shtml")
	@ResponseBody
	public String addServiceSystem(
			@ModelAttribute("eccomm_admin") SystemUser user,
			ServiceSystemParameter parameter) {
		serviceSystem.addServiceSystem(parameter, user);
		return "添加成功";

	}

	/**
	 * 跳转到修改服务体系
	 *
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/udpArchitecture.shtml")
	public ModelAndView udpArchitecture(
			@ModelAttribute("eccomm_admin") SystemUser user,
			ServiceSystemParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/serviceSystem/updArchitecture");
		Sys_key ss = generalService.getObjectById(Sys_key.class,
				parameter.getSid());
		mv.addObject("ss", ss);
		mv.addObject("command", parameter);
		List<OmpRegion> cityS = enterpriseService.queryCounty(0L);
		mv.addObject("cityS", cityS);
		List<Omp_phone_type> tellList = generalService
				.getAllObjects(Omp_phone_type.class);
		mv.addObject("tellList", tellList);
		List<ServiceType> serviceTypes = generalService
				.getAllObjects(ServiceType.class);
		mv.addObject("serviceTypes", serviceTypes);
		return mv;
	}
	/**
	 * 查看服务体系
	 *
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/seeArchitecture.shtml")
	public ModelAndView seeArchitecture(
			@ModelAttribute("eccomm_admin") SystemUser user,
			ServiceSystemParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/serviceSystem/seeArchitecture");
		Sys_key ss = generalService.getObjectById(Sys_key.class,
				parameter.getSid());
		mv.addObject("ss", ss);
		mv.addObject("command", parameter);
		List<OmpRegion> cityS = enterpriseService.queryCounty(0L);
		mv.addObject("cityS", cityS);
		List<Omp_phone_type> tellList = generalService
				.getAllObjects(Omp_phone_type.class);
		mv.addObject("tellList", tellList);
		List<ServiceType> serviceTypes = generalService
				.getAllObjects(ServiceType.class);
		mv.addObject("serviceTypes", serviceTypes);
		return mv;
	}

	/**
	 * 获取服务体系
	 *
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/serchArchitecture.shtml")
	@ResponseBody
	public List<Map<String, Object>> serchArchitecture(
			@ModelAttribute("eccomm_admin") SystemUser user,
			ServiceSystemParameter parameter) {
		Sys_key ss = generalService.getObjectById(Sys_key.class,
				parameter.getSid());
		List<Map<String, Object>> list = serviceSystem.getupdateSys(ss);
		return list;
	}

	// 修改服务体系
	@RequestMapping("/updateServiceSystem.shtml")
	@ResponseBody
	public String updateServiceSystem(
			@ModelAttribute("eccomm_admin") SystemUser user,
			ServiceSystemParameter parameter) {
		Sys_key entity = parameter.getEntity();
		parameter.setTelltype(entity.getTelltype_id());
		serviceSystem.updateServiceSystem(parameter, user);
		return "修改成功";
	}
	// 删除服务体系
	@RequestMapping("/delService.shtml")
	@ResponseBody
	public String delServiceSystem(
			@ModelAttribute("eccomm_admin") SystemUser user,
			ServiceSystemParameter parameter) {
		Sys_key ss = generalService.getObjectById(Sys_key.class,parameter.getSid());
		ss.setUser_falg(0L);
		generalService.saveOrUpdate(ss);
		return "删除成功";
	}

	// 判断用户类型
	public int userType(SystemUser user) {
		if ("g".equals(user.getAccount_type())) {
			return 1;
		} else if ("m".equals(user.getAccount_type())
				|| "b".equals(user.getAccount_type())) {
			return 2;
		}
		return 0;

	}
}
