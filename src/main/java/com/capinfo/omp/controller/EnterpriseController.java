package com.capinfo.omp.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.ServiceProvider;
import com.capinfo.omp.model.ServiceType;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.parameter.ServiceProviderParameter;
import com.capinfo.omp.service.EnterpriseService;
import com.capinfo.omp.utils.Page;
import com.capinfo.omp.ws.client.ClientGetLandNumberService;


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
	
	@RequestMapping("/ServiceProvider.shtml")
	@ResponseBody
	public String importServiceProvider(HttpServletRequest request, @RequestParam("excelFile") MultipartFile excelFile, @ModelAttribute("eccomm_admin") SystemUser user) throws Exception {
		String errorstr = "";
		if (excelFile != null && !"".equals(excelFile)) {
			InputStream fis = excelFile.getInputStream();
			Map<String, Object> map = importEmployeeByPoi(fis, user.getAccount_type(), errorstr);
			List<ServiceProvider> list = (List<ServiceProvider>) map.get("infos");
			for (ServiceProvider serviceProvider : list) {
				// 保存服务商
				generalService.saveOrUpdate(serviceProvider);
			}
		}
		return "添加成功";
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

		String num = "";
		int rowNum = sheetAt0.getLastRowNum();
		for (int i = 1; i < rowNum + 1; i++) {
			Row row1 = sheetAt0.getRow(i);
			// 所属市
			String city = getCellValue(row1.getCell(0));
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
			 * 核实状态 2:未审核 0:无效 1:有效
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

			
			String serviceCountyId = "";
			String serviceStreetId = "";
			String serviceCommunityId = "";
			if(!"".equals(serviceCounty) && serviceCounty != null){
				serviceCountyId = enterpriseService.getRegionId(serviceCounty, 3, "0");
				if(!"".equals(serviceStreet) && serviceStreet != null){
					serviceStreetId = enterpriseService.getRegionId(serviceStreet, 4, serviceCountyId);
					if(!"".equals(serviceCommunity) && serviceCommunity != null){
						serviceCommunityId = enterpriseService.getRegionId(serviceCommunity, 5, serviceStreetId);
					}
				}
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
			serviceProvider.setContact(contactPhone);
			serviceProvider.setContactPhone(contactPhone);
			serviceProvider.setEmail(email);
			serviceProvider.setIs_AcrossYears(ShiConvertZero(is_AcrossYears));
			serviceProvider.setIs_anergy(ShiConvertZero(is_anergy));
			serviceProvider.setIs_pensionCard(ShiConvertZero(is_pensionCard));
			// serviceProvider.setIs_signing(is_signing);
			serviceProvider.setPrincipal(principal);
			serviceProvider.setPrincipalPhone(principalPhone);
			serviceProvider.setServiceAddress(serviceAddress);
			serviceProvider.setServiceName(superiorServiceName);
			// serviceProvider.setServiceState(serviceState);

			// 判断是否有区号
			num = serviceTell.substring(0, 1);
			if ("0".equals(num)) {
				serviceProvider.setServiceTell(serviceTell.substring(3));
			} else {
				serviceProvider.setServiceTell(serviceTell);
			}
			// 查询服务类型id
			Long serviceTypeId = enterpriseService.getServiceTypeId(serviceType);
			serviceProvider.setServiceTypeId(serviceTypeId);
			serviceProvider.setSigningDate(new Date());
			serviceProvider.setSuperiorServiceName(superiorServiceName);
			serviceProvider.setDiscountInfo(ShiConvertZero(discountInfo));
			infos.add(serviceProvider);

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("infos", infos);
		map.put("failure", failure);
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
		ModelAndView mv = new ModelAndView("/omp/serviceMerchants/initial");
		//getSortList(parameter.getCurrent(), mv, "");
		return mv;
	}
	/**
	 * 服务商管理列表
	 *
	 * @param mv
	 *
	 * @param parameter
	 * @param mv
	 * @return
	 */
//	private void getSortList(String current, ModelAndView mv, String QSql) {
//		if (StringUtils.isEmpty(current)) {
//			current = "1";
//		}
//		int count = ompOldMatchService.getCountWithSql(QSql);
//		
//		
//		Page page = new Page(current, count, "10");
//		List<Map<String, Object>> list = ompOldMatchService.getListWithSql(page, QSql);
//		for (Map<String, Object> map : list) {
//			String id = map.get("SCOPE_DELIVERY").toString();
//			if (!StringUtils.isEmpty(id)) {
//				// String name = ompOldMatchService.getregion(id);
//				map.put("SCOPE_DELIVERY", id);
//			}
//		}
//		mv.addObject("DataTotalCount", count);
//		mv.addObject("PerPieceSize", 10);
//		mv.addObject("command", list);
//		mv.addObject("CurrentPieceNum", page.getCurrentPage());
//		mv.addObject("QSql", QSql);
//	}
    /**
     * 导入
     * @return
     */
	@RequestMapping("/serviceMerchants/toImport.shtml")
	public ModelAndView toImport() {
		ModelAndView mv = new ModelAndView("/omp/serviceMerchants/Import");
		return mv;
	}

}
