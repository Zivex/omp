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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.common.model.SystemUser;
import com.capinfo.common.security.Constants;
import com.capinfo.common.security.authentication.AuthenticationSuccessHandlerImpl;
import com.capinfo.common.web.service.SystemUserService;
import com.capinfo.omp.parameter.StaParameter;
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
	@ResponseBody
	public ModelAndView list(StaParameter p,@ModelAttribute(Constants.ADMIN_ATTRIBUTE_KEY) SystemUser user) {
		ModelAndView mv = new ModelAndView("/admin/list");
		listget(mv, p,user);
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
	public ModelAndView initial(StaParameter p,@ModelAttribute(Constants.ADMIN_ATTRIBUTE_KEY) SystemUser user) {
		ModelAndView mv = new ModelAndView("/admin/initial");
		listget(mv, p,user);
		return mv;
	}
	/**
	 * 快捷键修改次数统计(初始化)
	 * @param county
	 * @param street
	 * @param community
	 * @param otype
	 * @param stime
	 * @param etime
	 * @return
	 */
	@RequestMapping("/ReportFrom/keyboardUpdateCount.shtml")
	public ModelAndView initialKey(String county, String street, String community,String otype,Date stime,Date etime) {
		ModelAndView mv = new ModelAndView("/admin/initialKey");
		int sata = 1;
		listKeyGet(mv, county, street, community, otype, stime, etime,sata);
		return mv;
	}
	
	/**
	 * 快捷键修改次数统计 (查询)
	 * @param county
	 * @param street
	 * @param community
	 * @param otype
	 * @param stime
	 * @param etime
	 * @return
	 */
	@RequestMapping("/ReportFrom/queryKeyboard.shtml")
	public ModelAndView keyboardUpdateCount(String county, String street, String community,String otype,Date stime,Date etime) {
		ModelAndView mv = new ModelAndView("/admin/keyboardUpdateCount");
		int sata = 1;
		listKeyGet(mv, county, street, community, otype, stime, etime,sata);
		return mv;
	}
	
	
	
	/**
	 * 语音发送次数(初始化)
	 * @param county
	 * @param street
	 * @param community
	 * @param otype
	 * @param stime
	 * @param etime
	 * @return
	 */
	@RequestMapping("/ReportFrom/initialVoice.shtml")
	public ModelAndView initialVoice(String county, String street, String community,String otype,Date stime,Date etime) {
		ModelAndView mv = new ModelAndView("/admin/initialVoice");
		int sata = 2;
		listKeyGet(mv, county, street, community, otype, stime, etime,sata);
		return mv;
	}
	
	/**
	 * 语音发送次数 (查询)
	 * @param county
	 * @param street
	 * @param community
	 * @param otype
	 * @param stime
	 * @param etime
	 * @return
	 */
	@RequestMapping("/ReportFrom/VoiceCount.shtml")
	public ModelAndView VoiceCount(String county, String street, String community,String otype,Date stime,Date etime) {
		ModelAndView mv = new ModelAndView("/admin/sendVoce");
		int sata = 2;
		listKeyGet(mv, county, street, community, otype, stime, etime,sata);
		return mv;
	}
	
	
	
	
	@RequestMapping("/Report/sendVoice.shtml")
	public ModelAndView  sendVoice(String county, String street, String community,String otype,Date stime,Date etime){
		ModelAndView mv = new ModelAndView("/admin/initial");
		return mv;
	}
	
	public void listget(ModelAndView mv,StaParameter p, SystemUser user) {
//		SimpleDateFormat fromg = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
//		String stimes = "";
//		String etimes = "";
//		if (stime==null) {
//			stimes = "";
//		}else {
//			stimes = fromg.format(stime.getTime());
//		}
//		if (etime==null) {
//			etimes = "";
//		}else {
//			etimes = fromg.format(etime.getTime());
//		}
//		
//		
		
		List<Map<String, Object>> showcout = systemLogs.getlistCount(p,user);
		Double sum = 0.0;
		for (Map<String, Object> map : showcout) {
			Double c = Double.parseDouble(map.get("count")+"");
			sum +=c;
		}
		long round = Math.round(sum);
		mv.addObject("showcout",showcout);
		
		mv.addObject("sum",round);
//		mv.addObject("county", county);
//		mv.addObject("street", street);
//		mv.addObject("community", community);
//		mv.addObject("otype", otype);
//		mv.addObject("stimes", stimes);
//		mv.addObject("etimes", etimes);
	}
	
	
	
	public void listKeyGet(ModelAndView mv,String county, String street, String community,String otype,Date stime,Date etime,int sata) {
		SimpleDateFormat fromg = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
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
		
		
		List<Map<String, Object>> list = systemLogs.getKeyboardUpdateCount(county,street,community,otype,stimes,etimes,sata);
		Long sumCount = 0L;
		Long sumSuc = 0L;
		Long sumFai = 0L;
		Long sumListen= 0L;
		Long noListen= 0L;
		
		for (Map<String, Object> map : list) {
			sumCount +=(Long)map.get("count");
			sumSuc +=(Long)map.get("suc");
			sumFai +=(Long)map.get("fai");
			sumListen +=(Long)map.get("listen");
			noListen +=(Long)map.get("noListen");
		}
		
		mv.addObject("sumSuc",sumSuc);
		mv.addObject("sumFai",sumFai);
		mv.addObject("sumListen",sumListen);
		mv.addObject("noListen",noListen);
		mv.addObject("sumCount",sumCount);
		mv.addObject("keyCount",list);
		mv.addObject("county", county);
		mv.addObject("street", street);
		mv.addObject("community", community);
		mv.addObject("otype", otype);
		mv.addObject("stimes", stimes);
		mv.addObject("etimes", etimes);
	}

}
