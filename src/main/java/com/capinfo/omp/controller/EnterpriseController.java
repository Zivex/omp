package com.capinfo.omp.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.capinfo.framework.service.GeneralService;
import com.capinfo.omp.model.Composition;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.ServiceProvider;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.service.EnterpriseService;
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
//	@RequestMapping("/ServiceProvider.shtml")
//	@ResponseBody
//	public String importServiceProvider(HttpServletRequest request,
//			@RequestParam("excelFile") MultipartFile excelFile,
//			@ModelAttribute("eccomm_admin") SystemUser user) throws Exception {
//		System.out.println(excelFile);
//		String errorstr = "错误行数为: \n";
//		String linkNbr = "";
//		String num = "";
//		if (excelFile != null && !"".equals(excelFile)) {
//			InputStream fis = excelFile.getInputStream();
//			Map<String, Object> map = importEmployeeByPoi(fis,user.getAccount_type());
//			
//			
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
//			String format = df.format(new Date());// new Date()为获取当前系统时间
//			String zjNumber = "{'landLineNumber':'";// 导入成功后将座机号码同步到中航
//			int nb = 10;
//			int enb = 0;
//			List<Omp_Old_Info> list = (List<Omp_Old_Info>) map.get("infos");
//			for (Omp_Old_Info ompOldInfo : list) {
//				// 判断是否以010开头
//				CharSequence subSequence = ompOldInfo.getZjnumber()
//						.subSequence(0, 1);
//				if (!subSequence.equals("0")) {
//					nb++;
//					errorstr = errorstr + "第" + nb + "行:大座机缺少区号 \n";
//					enb++;
//				} else {
//					nb++;
//					// 通过座机号，身份证号判断是否存在老人 1大座机号已存在 2身份证号已存在 0不存在
//					int t = oldService.checkOldIsHave(ompOldInfo.getZjnumber(),
//							ompOldInfo.getCertificates_number());
//					if (t == 3) {
//						errorstr = errorstr + "第" + nb + "行:身份证号和大座机号重复 \n";
//						enb++;
//
//					} else if (t == 1) {
//						errorstr = errorstr + "第" + nb + "行:大座机号重复 \n ";
//						enb++;
//					} else if (t == 2) {
//						errorstr = errorstr + "第" + nb + "行:身份证号重复 \n";
//						enb++;
//					}
//					if (enb == 0) {
//						linkNbr = ompOldInfo.getZjnumber();
//						// 判断
//						num = linkNbr.substring(0, 3);
//						if ("010".equals(num)) {
//							ompOldInfo.setZjnumber(linkNbr.substring(3));
//						}
//						zjNumber = zjNumber + linkNbr.substring(3) + ",";
//						oldService.addOld(ompOldInfo, user);
//					}
//
//				}
//			}
//			zjNumber = zjNumber.substring(0, zjNumber.length() - 1) + "'}";
//			System.out.println("获得的座机号码：" + zjNumber);
//			// 调用webService接口发送信息
//			ClientGetLandNumberService.getZjnumber(zjNumber);
//			fis.close();
//			if (enb > 0) {
//				errorstr = errorstr + "  总共导入失败：" + enb + "个";
//			} else {
//				return "1";
//			}
//
//			// mv.addObject("failure", map.get("failure"));
//			System.out.println(errorstr);
//			// mv.addObject("errorstr", errorstr);
//		}
//
//		return errorstr;
//	}
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
//	public Map<String, Object> importEmployeeByPoi(InputStream fis, String acc)
//			throws Exception {
//
//		List<ServiceProvider> infos = new ArrayList<ServiceProvider>();
//		List<ServiceProvider> failure = new ArrayList<ServiceProvider>();
//		Workbook workbook = WorkbookFactory.create(fis);
//		//sheet
//		Sheet sheetAt0 = workbook.getSheetAt(0);
//		
//		Row row3 = sheetAt0.getRow(3);
//		// 工作人员姓名
//		String workername = getCellValue(row3.getCell(1));
//		String workertel = row3.getCell(7).getStringCellValue();
//		int rowNum = sheetAt0.getLastRowNum();
//		for (int i = 7; i < rowNum + 1; i++) {
//			Row row = sheetAt0.getRow(i);
//			String call_id = getCellValue(row.getCell(12));
//			String area = getCellValue(row.getCell(1));
//			String street = getCellValue(row.getCell(2));
//			String community = getCellValue(row.getCell(3));
//			String tel_type = getCellValue(row.getCell(10));
//			
//			 //根据市区名称查询市区ID
//			String countyId = oldService.getIdByName(area, 3);
//			// 根据街道名称查询街道ID
//			String streetId = oldService.getIdByName(street, 4);
//			// 根据社区名称查询社区ID
//			String communityId = oldService.getIdByName(community, 5);
//			//查询社区编码
//			String comNUm = oldService.getIdByComCod(community, 5);
//			
//			int tel_num = oldService.getTel_type(tel_type);
//			Long callId = 0l;
//			if ("是".equals(call_id)) {
//				callId = 1l;
//			}
//			Omp_Old_Info old_info = new Omp_Old_Info();
//			old_info.setHousehold_county_id(countyId);
//			old_info.setHousehold_street_id(streetId);
//			old_info.setHousehold_community_id(communityId);
//			old_info.setWorkername(workername);
//			old_info.setWorkertel(workertel);
//			old_info.setName(getCellValue(row.getCell(4)));
//			old_info.setCertificates_number(getCellValue(row.getCell(5)));
//			old_info.setZjnumber(getCellValue(row.getCell(6)));
//			old_info.setPhone(getCellValue(row.getCell(7)));
//			old_info.setEmergencycontact(getCellValue(row.getCell(8)));
//			old_info.setEmergencycontacttle(getCellValue(row.getCell(9)));
//			//话机类型
//			old_info.setTeltype(String.valueOf(tel_num));
//			old_info.setAddress(getCellValue(row.getCell(11)));
//			old_info.setCall_id(callId);
//			old_info.setAccount_type(acc+comNUm);
//			infos.add(old_info);
//
//		}
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("infos", infos);
//		map.put("failure", failure);
//		return map;
//	}
//
//	// 判断从Excel文件中解析出来数据的格式
//	public static String getCellValue(Cell cell) {
//		String value = null;
//		// 简单的查检列类型
//		switch (cell.getCellType()) {
//		case HSSFCell.CELL_TYPE_STRING:// 字符串
//			value = cell.getRichStringCellValue().getString();
//			break;
//		case HSSFCell.CELL_TYPE_NUMERIC:// 数字
//			long dd = (long) cell.getNumericCellValue();
//			value = dd + "";
//			break;
//		case HSSFCell.CELL_TYPE_BLANK:
//			value = "";
//			break;
//		case HSSFCell.CELL_TYPE_FORMULA:
//			value = String.valueOf(cell.getCellFormula());
//			break;
//		case HSSFCell.CELL_TYPE_BOOLEAN:// boolean型值
//			value = String.valueOf(cell.getBooleanCellValue());
//			break;
//		case HSSFCell.CELL_TYPE_ERROR:
//			value = String.valueOf(cell.getErrorCellValue());
//			break;
//		default:
//			break;
//		}
//		return value;
//	}


}
