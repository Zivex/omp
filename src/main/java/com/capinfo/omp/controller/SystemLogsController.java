package com.capinfo.omp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.common.model.SystemUser;
import com.capinfo.common.security.Constants;
import com.capinfo.common.security.authentication.AuthenticationSuccessHandlerImpl;
import com.capinfo.common.web.service.SystemUserService;
import com.capinfo.omp.service.SystemLogs;
import com.capinfo.omp.utils.Page;

@Controller
@RequestMapping("/syslog")
@SessionAttributes("eccomm_admin")
public class SystemLogsController extends AuthenticationSuccessHandlerImpl {
	
	@Autowired
	private SystemLogs systemLogs;
	
	@Autowired
	private SystemUserService systemUserService;
	
	@RequestMapping("/Systemlogs/list.shtml")
	public ModelAndView showlist(@ModelAttribute(Constants.ADMIN_ATTRIBUTE_KEY) SystemUser user) {
		ModelAndView mv = new ModelAndView("/admin/syslog");
		List<Map<String, Object>> showlog = systemLogs.list();
		System.err.println(user.getLogonName());
		return mv.addObject("showlog",showlog);
	}
	
	
	/**
	 * 呼叫服务统计查询
	 * @param county
	 * @param street
	 * @param community
	 * @param otype
	 * @param stime
	 * @param etime
	 * @return
	 */
	
	@RequestMapping("/ReportFrom/list.shtml")
	public ModelAndView list(String county, String street, String community,String otype,Date stime,Date etime) {
		ModelAndView mv = new ModelAndView("/admin/list");
		listget(mv, county, street, community, otype, stime, etime);
		return mv;
	}
	/**
	 * 呼叫服务统计初始化
	 * @param county
	 * @param street
	 * @param community
	 * @param otype
	 * @param stime
	 * @param etime
	 * @return
	 */
	@RequestMapping("/ReportFrom/initial.shtml")
	public ModelAndView initial(String county, String street, String community,String otype,Date stime,Date etime) {
		ModelAndView mv = new ModelAndView("/admin/initial");
		listget(mv, county, street, community, otype, stime, etime);;
		return mv;
	}
	/**
	 * 快捷键修改次数统计
	 * @param county
	 * @param street
	 * @param community
	 * @param otype
	 * @param stime
	 * @param etime
	 * @return
	 */
	@RequestMapping("/ReportFrom/keyboardUpdateCount.shtml")
	public ModelAndView keyboardUpdateCount(String county, String street, String community,String otype,Date stime,Date etime) {
		ModelAndView mv = new ModelAndView("/admin/initial");
		SimpleDateFormat fromg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String stimes = "";
		String etimes = "";
		if (stime==null) {
			stimes = "";
		}else {
			stimes = fromg.format(stime.getTime());
		}
		if (etime==null) {
			etimes = "";
		}else {
			etimes = fromg.format(etime.getTime());
		}
		List<Map<String, Object>> list = systemLogs.getKeyboardUpdateCount(county,street,community,otype,stimes,etimes);
		return mv;
	}
	
	
	
	
	@RequestMapping("/Report/sendVoice.shtml")
	public ModelAndView  sendVoice(String county, String street, String community,String otype,Date stime,Date etime){
		ModelAndView mv = new ModelAndView("/admin/initial");
		systemLogs.getsendService(street,community,otype,stime,etime);
		return mv;
	}
	
	public void listget(ModelAndView mv,String county, String street, String community,String otype,Date stime,Date etime) {
		SimpleDateFormat fromg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String stimes = "";
		String etimes = "";
		if (stime==null) {
			stimes = "";
		}else {
			stimes = fromg.format(stime.getTime());
		}
		if (etime==null) {
			etimes = "";
		}else {
			etimes = fromg.format(etime.getTime());
		}
		List<Map<String, Object>> showcout = systemLogs.getlistCount(county, street, community, otype, stimes, etimes);
		mv.addObject("showcout",showcout);
		mv.addObject("county", county);
		mv.addObject("street", street);
		mv.addObject("community", community);
		mv.addObject("otype", otype);
		mv.addObject("stimes", stimes);
		mv.addObject("etimes", etimes);
	}

}
