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
import com.capinfo.omp.model.ServiceProvider;
import com.capinfo.omp.model.ServiceType;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.parameter.ServiceProviderParameter;
import com.capinfo.omp.service.EnterpriseService;
import com.capinfo.region.model.OmpRegion;


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
	//下拉追加
	@RequestMapping("/ajaxEnterprise.shtml")
	@ResponseBody
	public List<Composition> ajaxEnterprise(int uid ,Integer lv,Integer upId) {
		List<Composition> list = enterpriseService.getListByid(uid,lv,upId);
		return list;

	}


	/**
	 * 服务商导入
	 *
	 * @param request
	 * @param excelFile
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("/ServiceProvider.shtml")
	@ResponseBody
	public String importServiceProvider(HttpServletRequest request, @RequestParam("excelFile") MultipartFile excelFile, @ModelAttribute("eccomm_admin") SystemUser user) throws Exception {
		String errorstr = "";
		Map<String, Object> map = null;
		int error = 0;
		int errorCount = 0;
		if (excelFile != null && !"".equals(excelFile)) {
			InputStream fis = excelFile.getInputStream();
			map = importEmployeeByPoi(fis, user.getAccount_type(), errorstr);
			int c = (int) map.get("count");
			if(c<1){
				List<ServiceProvider> list = (List<ServiceProvider>) map.get("infos");
				for (ServiceProvider serviceProvider : list) {
					error++;
					// 保存服务商
					serviceProvider.setChannels(user.getId());
					serviceProvider.setCreateTime(new Date());
					serviceProvider.setUser_falg(1);
					boolean b = enterpriseService.queryForTell(serviceProvider.getServiceTell(),0L);
					if(!b){
						errorCount++;
						errorstr+="第"+error+"行,服务电话已存在; \n";
					}else{
						generalService.saveOrUpdate(serviceProvider);
					}
				}
				if(errorCount>0){
					return errorstr;
				}
				return "添加成功";
			}
		}
		return (String) map.get("errorstr");
	}

	/**
	 * POI:解析Excel文件中的数据并把每行数据封装成一个实体
	 *
	 * @param fis
	 *            文件输入流
	 * @param string
	 * @param flunk
	 * @param success
	 * @param map
	 * @return List<ServiceProvider> Excel中数据封装实体的集合
	 * @throws Exception
	 */
	public Map<String, Object> importEmployeeByPoi(InputStream fis, String acc, String errorstr) throws Exception {

		List<ServiceProvider> infos = new ArrayList<ServiceProvider>();
		List<ServiceProvider> failure = new ArrayList<ServiceProvider>();
		Workbook workbook = WorkbookFactory.create(fis);
		// sheet
		Sheet sheetAt0 = workbook.getSheetAt(0);
		int srrorLine = 0;
		int count = 0;
		errorstr +="错误信息 \n";
		String num = "";
		int rowNum = sheetAt0.getLastRowNum();
		String pattern  ="\\s+";
		for (int i = 1; i < rowNum +1 ; i++) {
			srrorLine++;
			Row row1 = sheetAt0.getRow(i);
			// 所属市
			String city = getCellValue(row1.getCell(0));
			boolean isMatch = Pattern.matches(pattern, city);
			if(isMatch){
				break;
			}

			// 所属区县
			String county = getCellValue(row1.getCell(1));
			// 所属街道
			String street = getCellValue(row1.getCell(2));

			// 服务单位名称
			String serviceName = getCellValue(row1.getCell(3));
			// 营业执照名称
			String charterName = getCellValue(row1.getCell(4));
			// 营业执照编码
			String charterNumber = getCellValue(row1.getCell(5));
			// 服务地址
			String serviceAddress = getCellValue(row1.getCell(6));
			// 服务类型
			String serviceType = getCellValue(row1.getCell(7));
			// 服务区域描述
			String addressDescribe = getCellValue(row1.getCell(8));

			String serviceCounty = getCellValue(row1.getCell(9));
			String serviceStreet = getCellValue(row1.getCell(10));
			String serviceCommunity = getCellValue(row1.getCell(11));
			// 渠道发展来源
			// String channels = getCellValue(row1.getCell(12));
			// 服务电话
			String serviceTell = getCellValue(row1.getCell(12));
			// 联系人
			String contact = getCellValue(row1.getCell(13));
			// 联系人手机号
			String contactPhone = getCellValue(row1.getCell(14));

			// 邮箱
			String email = getCellValue(row1.getCell(15));

			/**
			 * 是否能刷养老卡 0:否 1:是
			 */
			String is_pensionCard = getCellValue(row1.getCell(16));

			/**
			 * 是否能刷跨年 0:否 1:是
			 */
			String is_AcrossYears = getCellValue(row1.getCell(17));
			/**
			 * 是否能刷失能 0:否 1:是
			 */

			String is_anergy = getCellValue(row1.getCell(18));

			// 上级服务单位名称

			String superiorServiceName = getCellValue(row1.getCell(19));

			// 总负责人
			String principal = getCellValue(row1.getCell(20));

			// 总负责人联系电话
			String principalPhone = getCellValue(row1.getCell(21));

			// 售后对接人
			String aftermarketPerson = getCellValue(row1.getCell(22));

			// 售后电话
			String aftermarketPhone = getCellValue(row1.getCell(23));

			// 服务内容
			String serviceContent = getCellValue(row1.getCell(24));
			/**
			 * 折扣信息 0:否 1:是
			 */

			String discountInfo = getCellValue(row1.getCell(25));

			/**
			 * 核实状态 1:未审核2:无效 3:有效
			 */
			// String verify = getCellValue(row1.getCell(26));
			/**
			 * 是否签约 0:否 1:是
			 */
			// String is_signing = getCellValue(row1.getCell(27));
			// 签约时间
			// String signingDate = getCellValue(row1.getCell(28));
			// serviceState
			// String serviceState = getCellValue(row1.getCell(29));

			// 查询区域
			String cityId = enterpriseService.getRegionId(city, 2, "0");

			String countyId = enterpriseService.getRegionId(county, 3, cityId);
			String streetId = enterpriseService.getRegionId(street, 4, countyId);
			Long serviceTypeId = enterpriseService.getServiceTypeId(serviceType);

			String serviceCountyId = "";
			String serviceStreetId = "";
			String serviceCommunityId = "";
			if(!"".equals(serviceCounty) && serviceCounty != null){
				serviceCountyId = enterpriseService.getRegionId(serviceCounty, 3, "0");
				if("0".equals(serviceCountyId)){
					count++;
					errorstr+="第"+srrorLine+"行,服务区县不存在; \n";
				}
				if(!"".equals(serviceStreet) && serviceStreet != null){
					serviceStreetId = enterpriseService.getRegionId(serviceStreet, 4, serviceCountyId);
					if("0".equals(serviceStreetId)){
						count++;
						errorstr+="第"+srrorLine+"行,服务街道不存在; \n";
					}
					if(!"".equals(serviceCommunity) && serviceCommunity != null){
						serviceCommunityId = enterpriseService.getRegionId(serviceCommunity, 5, serviceStreetId);
						if("0".equals(serviceCommunityId)){
							count++;
							errorstr+="第"+srrorLine+"行,服务社区不存在; \n";
						}
					}
				}
			}
			//验证

			if("".equals(city)){
				count++;
				errorstr+="第"+srrorLine+"行,所属市不能为空; \n";
			}
			if("0".equals(cityId)){
				count++;
				errorstr+="第"+srrorLine+"行,所属市不存在; \n";
			}
			if("".equals(county)){
				count++;
				errorstr+="第"+srrorLine+"行,所属区不能为空; \n";
			}
			if("0".equals(countyId)){
				count++;
				errorstr+="第"+srrorLine+"行,所属区不存在; \n";
			}
			if("".equals(street)){
				count++;
				errorstr+="第"+srrorLine+"行,所属街道不能为空; \n";
			}
			if("0".equals(streetId)){
				count++;
				errorstr+="第"+srrorLine+"行,所属街道不存在; \n";
			}
			if("".equals(serviceName)){
				count++;
				errorstr+="第"+srrorLine+"行,服务单位名称不能为空; \n";
			}
			if("".equals(charterName)){
				count++;
				errorstr+="第"+srrorLine+"行,营业执照名称不能为空; \n";
			}
			if("".equals(charterNumber)){
				count++;
				errorstr+="第"+srrorLine+"行,营业执照编码不能为空; \n";
			}
			if("".equals(serviceAddress)){
				count++;
				errorstr+="第"+srrorLine+"行,服务地址不能为空; \n";
			}
			if("".equals(serviceType)){
				count++;
				errorstr+="第"+srrorLine+"行,服务类型名称不能为空; \n";
			}
			//检测服务类型
			if(serviceTypeId == 0L){
				count++;
				errorstr+="第"+srrorLine+"行,服务类型不存在; \n";
			}
			if("".equals(addressDescribe)){
				count++;
				errorstr+="第"+srrorLine+"行,服务区域描述不能为空; \n";
			}
			if("".equals(serviceTell)){
				count++;
				errorstr+="第"+srrorLine+"行,服务电话不能为空; \n";
			}
			if("".equals(contact)){
				count++;
				errorstr+="第"+srrorLine+"行,联系人不能为空; \n";
			}
			if("".equals(contactPhone)){
				count++;
				errorstr+="第"+srrorLine+"行,联系人手机号不能为空; \n";
			}
			if("".equals(email)){
				count++;
				errorstr+="第"+srrorLine+"行,邮箱不能为空; \n";
			}
			if("".equals(aftermarketPerson)){
				count++;
				errorstr+="第"+srrorLine+"行,售后对接人不能为空; \n";
			}
			if("".equals(aftermarketPhone)){
				count++;
				errorstr+="第"+srrorLine+"行,售后电话不能为空; \n";
			}
			if("".equals(serviceContent)){
				count++;
				errorstr+="第"+srrorLine+"行,服务内容不能为空; \n";
			}
			// 创建服务商
			ServiceProvider serviceProvider = new ServiceProvider();
			serviceProvider.setCity_id(Long.parseLong(cityId));
			serviceProvider.setCounty_id(Long.parseLong(countyId));
			serviceProvider.setStreet_id(Long.parseLong(streetId));
			serviceProvider.setServiceCounty_id(String.valueOf(serviceCountyId));
			serviceProvider.setServiceStreet_id(String.valueOf(serviceStreetId));
			serviceProvider.setServiceCommunity_id(String.valueOf(serviceCommunityId));
			serviceProvider.setAddressDescribe(addressDescribe);
			serviceProvider.setAftermarketPerson(aftermarketPerson);
			serviceProvider.setAftermarketPhone(aftermarketPhone);
			// serviceProvider.setChannels(channels);
			serviceProvider.setCharterName(charterName);
			serviceProvider.setCharterNumber(charterNumber);
			serviceProvider.setContact(contact);
			serviceProvider.setContactPhone(contactPhone);
			serviceProvider.setEmail(email);
			serviceProvider.setIs_AcrossYears(ShiConvertZero(is_AcrossYears));
			serviceProvider.setIs_anergy(ShiConvertZero(is_anergy));
			serviceProvider.setIs_pensionCard(ShiConvertZero(is_pensionCard));
			// serviceProvider.setIs_signing(is_signing);
			serviceProvider.setPrincipal(principal);
			serviceProvider.setPrincipalPhone(principalPhone);
			serviceProvider.setServiceAddress(serviceAddress);
			serviceProvider.setServiceName(serviceName);
			serviceProvider.setVerify(1);
			// serviceProvider.setServiceState(serviceState);

			// 判断是否有区号
			num = serviceTell.substring(0, 1);
			if ("0".equals(num)) {
				serviceProvider.setServiceTell(serviceTell.substring(3));
			} else {
				serviceProvider.setServiceTell(serviceTell);
			}
			// 查询服务类型id

			serviceProvider.setServiceTypeId(serviceTypeId);
			serviceProvider.setSigningDate(new Date());
			serviceProvider.setSuperiorServiceName(superiorServiceName);
			serviceProvider.setDiscountInfo(ShiConvertZero(discountInfo));
			infos.add(serviceProvider);

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("infos", infos);
		map.put("count", count);
		map.put("failure", failure);
		map.put("errorstr", errorstr);
		return map;
	}

	// 判断从Excel文件中解析出来数据的格式
	public static String getCellValue(Cell cell) {
		String value = null;

		// 简单的查检列类型
		if(cell != null){
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:// 字符串
			value = cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:// 数字
			long dd = (long) cell.getNumericCellValue();
			value = dd + "";
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			value = String.valueOf(cell.getCellFormula());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:// boolean型值
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			value = String.valueOf(cell.getErrorCellValue());
			break;
		default:
			break;
		}
		}
		return value;
	}

	public int ShiConvertZero(String shi) {
		int yn = 0;
		if ("是".equals(shi)) {
			yn = 1;
		}
		return yn;

	}
	/**
	 * 服务商初始化
	 * @param request
	 * @param current
	 * @return
	 */
	@RequestMapping("/initial.shtml")
	public ModelAndView initial(HttpServletRequest request, ServiceProviderParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/serviceMerchants/initial");
		mv.addObject("command", parameter);
		List<ServiceType> typeList = generalService.getAllObjects(ServiceType.class);
		mv.addObject("typeList",typeList);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = enterpriseService.getMerchantsList(parameter);
		mv.addObject("mList",map.get("list"));
		mv.addObject("DataTotalCount",map.get("count"));
		mv.addObject("PerPieceSize",parameter.getPageSize());
		mv.addObject("CurrentPieceNum", parameter.getCurrentPieceNum());

		return mv;
	}
	/**
	 * 服务商管理列表
	 *
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/list.shtml")
	public ModelAndView dicSortList(HttpServletRequest request,ServiceProviderParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/serviceMerchants/list");
	mv.addObject("command", parameter);
	List<ServiceType> typeList = generalService.getAllObjects(ServiceType.class);
	mv.addObject("typeList",typeList);
	@SuppressWarnings("unchecked")
	Map<String, Object> map = enterpriseService.getMerchantsList(parameter);
	mv.addObject("mList",map.get("list"));
	mv.addObject("DataTotalCount",map.get("count"));
	mv.addObject("PerPieceSize",parameter.getPageSize());
	mv.addObject("CurrentPieceNum", parameter.getCurrent());
	return mv;
	}
    /**
     * 导入
     * @return
     */
	@RequestMapping("/serviceMerchants/toImport.shtml")
	public ModelAndView toImport() {
		ModelAndView mv = new ModelAndView("/omp/serviceMerchants/Import");
		return mv;
	}

	/**
	 * 服务商详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/serviceMerchants/ServiceInfo.shtml")
	@ResponseBody
	public ModelAndView serviceInfo(int id) {
		ModelAndView mv = new ModelAndView("/omp/serviceMerchants/serverInfo");
		ServiceProvider serviceProvider = generalService.getObjectById(ServiceProvider.class, (long) id);
		String serviceCommunity_id = serviceProvider.getServiceCommunity_id();
		String serviceCounty_id = serviceProvider.getServiceCounty_id();
		String serviceStreet_id = serviceProvider.getServiceStreet_id();
		if(!"".equals(serviceCounty_id)){
			List<OmpRegion> countyList = enterpriseService.queryRegions(serviceCounty_id);
			mv.addObject("countyList",countyList);
		}
		if(!"".equals(serviceStreet_id)){
			List<OmpRegion> streetList = enterpriseService.queryRegions(serviceStreet_id);
			mv.addObject("streetList",streetList);
		}
		if(!"".equals(serviceCommunity_id)){
			List<OmpRegion> communityList = enterpriseService.queryRegions(serviceCommunity_id);
			mv.addObject("communityList",communityList);
		}
		mv.addObject("serviceProvider",serviceProvider);
		return mv;
	}


	/**
	 * 服务商修改
	 * @param id
	 * @return
	 */
	@RequestMapping("/serviceMerchants/Serviceupdate.shtml")
	@ResponseBody
	public ModelAndView serviceupdate(Long sid,ServiceProviderParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/serviceMerchants/serverupdate");
		Long o_mark = parameter.getOpenRegions();
		ServiceProvider serviceProvider = generalService.getObjectById(ServiceProvider.class, sid);
		String serviceCommunity_id = serviceProvider.getServiceCommunity_id();
		String serviceCounty_id = serviceProvider.getServiceCounty_id();
		String serviceStreet_id = serviceProvider.getServiceStreet_id();
		List<ServiceType> typeList = generalService.getAllObjects(ServiceType.class);
		mv.addObject("typeList",typeList);

		List<Map<String,Object>> countyAllList = enterpriseService.queryAllRegions(3);
		List<Map<String,Object>> streetAllList = enterpriseService.queryAllRegions(4);
		List<Map<String,Object>> communityAllList = enterpriseService.queryAllRegions(5);
		String[] countys = serviceCounty_id.split(",");
		String[] streets = serviceStreet_id.split(",");
		String[] communitys = serviceCommunity_id.split(",");
		for (String string : countys) {
			if(!"".equals(string)){
				for (Map<String, Object> map : countyAllList) {
					if ((Integer) map.get("id") == Integer.parseInt(string)) {
						map.put("checked", 1);
					}

				}
			}
		}
		for (String string : streets) {
			if(!"".equals(string)){
				for (Map<String, Object> map : streetAllList) {
					if((Integer) map.get("id")==Integer.parseInt(string)){
						map.put("checked", 1);
					}
				}
			}
		}
		for (String string : communitys) {
			if(!"".equals(string)){
				for (Map<String, Object> map : communityAllList) {
					Set<Entry<String,Object>> set = map.entrySet( );
					if(Integer.parseInt(map.get("id").toString())==Integer.parseInt(string)){
						map.put("checked", 1);
					}
				}
			}
		}

		for (Map<String, Object> map3 : communityAllList) {
			if (map3.get("checked") != null && (Integer) map3.get("checked") == 1) {
				for (Map<String, Object> map2 : streetAllList) {
					if (map2.get("checked") != null && (Integer) map2.get("checked") == 1) {
						if (((String) map3.get("pid")).equals(String.valueOf(map2.get("id")))) {
							map2.remove("checked");
						}
						for (Map<String, Object> map1 : countyAllList) {
							if (((String) map2.get("pid")).equals(String.valueOf(map1.get("id")))) {
								map1.remove("checked");
							}
						}
					}

				}

			}
			for (Map<String, Object> map2 : streetAllList) {
				if (map2.get("checked") != null && (Integer) map2.get("checked") == 1) {
					for (Map<String, Object> map1 : countyAllList) {
						if (((String) map2.get("pid")).equals(String.valueOf(map1.get("id")))) {
							map1.remove("checked");
						}
					}
				}

			}
		}

			mv.addObject("countyList",countyAllList);
			mv.addObject("streetList",streetAllList);
			mv.addObject("communityList",communityAllList);
		List<OmpRegion> cityS = enterpriseService.queryCounty(0L);
		List<OmpRegion> countyS = enterpriseService.queryCounty(serviceProvider.getCity_id());
		List<OmpRegion> streeS = enterpriseService.queryCounty(serviceProvider.getCounty_id());
		parameter.setEntity(serviceProvider);
		mv.addObject("serviceProvider",serviceProvider);
		mv.addObject("command", parameter);
		mv.addObject("countyS", countyS);
		mv.addObject("streeS", streeS);
		mv.addObject("cityS", cityS);
		mv.addObject("openRegions", o_mark);
		return mv;
	}
	/**
	 * 服务商修改
	 * @param id
	 * @return
	 */
	@RequestMapping("/serviceMerchants/ServiceupdateDo.shtml")
	@ResponseBody
	public String serviceupdateDo(ServiceProviderParameter parameter) {
		//ModelAndView mv = new ModelAndView("/omp/serviceMerchants/serverupdate");
		String r1 = "";
		String r2 = "";
		String r3 = "";
		String[] regions = parameter.getRegionIds().split(",");
		if(regions.length>0){
			for (String id : regions) {
				OmpRegion entity = generalService.getObjectById(OmpRegion.class, Long.parseLong(id));
				if(entity.getLevelid()==3){
					r1+=entity.getId()+",";
					
				}else if (entity.getLevelid()==4) {
					r2+=entity.getId()+",";
					r1+=entity.getParentid()+",";
				}else if (entity.getLevelid()==5) {
					r3+=entity.getId()+",";
					r2+=entity.getParentid()+",";
					OmpRegion entityP = generalService.getObjectById(OmpRegion.class, entity.getParentid());
					r1+=entityP.getParentid()+",";
				}
			}
			r1 = uniq(r1);
			r2 = uniq(r2);
			r3 = uniq(r3);
		}
		boolean b = enterpriseService.queryForTell(parameter.getEntity().getServiceTell(),0L);
		if(!b){
			return "添加失败服务商电话已存在";
		}
		ServiceProvider serviceProvider = parameter.getEntity();
		serviceProvider.setUpdateTime(new Date());
		serviceProvider.setServiceCounty_id(r1);
		serviceProvider.setServiceStreet_id(r2);
		serviceProvider.setServiceCommunity_id(r3);
		generalService.saveOrUpdate(serviceProvider);
		return "成功";
	}

	public String uniq(String r){
	    String aaArray[] = r.split(",");
	    String rn="";
        HashSet<String> hs = new HashSet<String>();
        for(String s : aaArray){
            hs.add(s);
        }
        Iterator<String> it = hs.iterator();
        if(it.hasNext()){
            rn= hs.toString().replace("[", "").replace("]", "");
            System.out.println(rn);
        }
		return rn.replaceAll(" ", "");
	}


	/**
	 * 服务商添加
	 * @param id
	 * @return
	 */
	@RequestMapping("/serviceMerchants/ServiceAdd.shtml")
	@ResponseBody
	public ModelAndView serviceAdd(ServiceProviderParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/serviceMerchants/serverAdd");
		List<ServiceType> typeList = generalService.getAllObjects(ServiceType.class);
		mv.addObject("typeList",typeList);
		mv.addObject("command", parameter);
		List<OmpRegion> cityS = enterpriseService.queryCounty(0L);
		mv.addObject("cityS", cityS);
		return mv;
	}

	/**
	 * 服务商保存
	 * @param id
	 * @return
	 */
	@RequestMapping("/serviceMerchants/ServiceAddDo.shtml")
	@ResponseBody
	public String serviceAddDo(ServiceProviderParameter parameter,@ModelAttribute("eccomm_admin") SystemUser user) {
		ServiceProvider entity = parameter.getEntity();
		//设置发展渠道来源
		String serviceTell = entity.getServiceTell();
		Long id = entity.getId();
		boolean b = enterpriseService.queryForTell(serviceTell,id);
		if(!b){
			return "该服务电话已存在";
		}

		entity.setChannels(user.getId());
		entity.setCreateTime(new Date());
		generalService.saveOrUpdate(entity);
		return "添加成功";
	}




}
