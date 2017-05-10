package com.capinfo.omp.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.framework.util.DateUtils;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.ServiceProvider;
import com.capinfo.omp.parameter.CompositionParameter;
import com.capinfo.omp.parameter.Ksp_id;
import com.capinfo.omp.parameter.OldParameter;
import com.capinfo.omp.service.OldService;
import com.capinfo.omp.util.Permissions;
import com.capinfo.omp.utils.JsonUtil;
import com.capinfo.omp.utils.Page;
import com.capinfo.omp.utils.excel.ExcelBuilder;
import com.capinfo.omp.utils.excel.ExportUtils;
import com.capinfo.omp.ws.client.ClientGetLandNumberService;
import com.capinfo.region.model.OmpRegion;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 展示主页面
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/old")
@SessionAttributes("eccomm_admin")
public class OldForController {

	@Autowired
	private OldService oldService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private GeneralService generalService;

	@RequestMapping("/oldMatch/list.shtml")
	public ModelAndView list(@ModelAttribute("eccomm_admin") SystemUser user,
			OldParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/old/initial");
		getList(mv, parameter, user);

		// oldService.saveLogger("2", "老人信息表", "lixing", "1");
		return mv;
	}

	/**
	 * 老人列表查询
	 * 
	 * @param current
	 * @param name
	 * @param idCard
	 * @param zjNumber
	 * @param county
	 * @param street
	 * @param community
	 * @param isGenerationOrder
	 * @param isindividuation
	 * @param creationTime
	 * @return
	 */
	@RequestMapping("/oldMatch/listtoo.shtml")
	@ResponseBody
	public ModelAndView listtoo(OldParameter parameter,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		ModelAndView mv = new ModelAndView("/omp/old/list");
		getList(mv, parameter, user);
		// LogRecord.logger("2", "", "", "", "2");
		return mv;
	}

	public void getList(ModelAndView mv, OldParameter parameter, SystemUser user) {

		int count = oldService.getCount(parameter, user);

		// count = count == 0 ? 1 : count;
		List<Omp_Old_Info> entities = oldService.getOldContextList(parameter,
				user);

		mv.addObject("dataList", entities);
		mv.addObject("count", count);
		mv.addObject("command", parameter);
	}

	/**
	 * 导入老人Excel
	 * 
	 * @param request
	 * @param excelFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/oldMatch/importInformation.shtml")
	@ResponseBody
	public String importInformation(HttpServletRequest request,
			@RequestParam("excelFile") MultipartFile excelFile,
			@ModelAttribute("eccomm_admin") SystemUser user) throws Exception {
		System.out.println(excelFile);
		String errorstr = "错误行数为: \n";
		String linkNbr = "";
		String num = "";
		if (excelFile != null && !"".equals(excelFile)) {
			InputStream fis = excelFile.getInputStream();
			Map<String, Object> map = importEmployeeByPoi(fis,
					user.getAccount_type());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String format = df.format(new Date());// new Date()为获取当前系统时间
			String zjNumber = "{'landLineNumber':'";// 导入成功后将座机号码同步到中航
			int nb = 10;
			int enb = 0;
			List<Omp_Old_Info> list = (List<Omp_Old_Info>) map.get("infos");
			for (Omp_Old_Info ompOldInfo : list) {
				// 判断是否以010开头
				CharSequence subSequence = ompOldInfo.getZjnumber()
						.subSequence(0, 1);
				if (!subSequence.equals("0")) {
					nb++;
					errorstr = errorstr + "第" + nb + "行:大座机缺少区号 \n";
					enb++;
				} else {
					nb++;
					int t = oldService.checkOldIsHave(ompOldInfo.getZjnumber(),
							ompOldInfo.getCertificates_number());
					if (t > 0) {
						errorstr = errorstr + "第" + nb + "行:大座机号重复 \n ";
						enb++;
					}
					if (enb == 0) {
						linkNbr = ompOldInfo.getZjnumber();
						// 判断
						num = linkNbr.substring(0, 3);
						if ("010".equals(num)) {
							ompOldInfo.setZjnumber(linkNbr.substring(3));
						}
						zjNumber = zjNumber + linkNbr.substring(3) + ",";
						oldService.addOld(ompOldInfo, user);
					}

				}
			}
			zjNumber = zjNumber.substring(0, zjNumber.length() - 1) + "'}";
			System.out.println("获得的座机号码：" + zjNumber);
			// 调用webService接口发送信息
			ClientGetLandNumberService.getZjnumber(zjNumber);
			fis.close();
			if (enb > 0) {
				errorstr = errorstr + "  总共导入失败：" + enb + "个";
			} else {
				return "1";
			}

			// mv.addObject("failure", map.get("failure"));
			System.out.println(errorstr);
			// mv.addObject("errorstr", errorstr);
		}

		return errorstr;
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
	 * @return List<OmpOldInfo> Excel中数据封装实体的集合
	 * @throws Exception
	 */
	public Map<String, Object> importEmployeeByPoi(InputStream fis, String acc)
			throws Exception {

		List<Omp_Old_Info> infos = new ArrayList<Omp_Old_Info>();
		List<Omp_Old_Info> failure = new ArrayList<Omp_Old_Info>();
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sheetAt0 = workbook.getSheetAt(0);
		Row row3 = sheetAt0.getRow(3);
		// 工作人员姓名
		String workername = getCellValue(row3.getCell(1));
		String workertel = row3.getCell(7).getStringCellValue();
		int rowNum = sheetAt0.getLastRowNum();
		for (int i = 7; i < rowNum + 1; i++) {
			Row row = sheetAt0.getRow(i);
			String call_id = getCellValue(row.getCell(12));
			String area = getCellValue(row.getCell(1));
			String street = getCellValue(row.getCell(2));
			String community = getCellValue(row.getCell(3));
			String tel_type = getCellValue(row.getCell(10));

			// 根据市区名称查询市区ID
			String countyId = oldService.getIdByName(area, 3);
			// 根据街道名称查询街道ID
			String streetId = oldService.getIdByName(street, 4);
			// 根据社区名称查询社区ID
			String communityId = oldService.getIdByName(community, 5);
			// 查询社区编码
			String comNUm = oldService.getIdByComCod(community, 5);

			int tel_num = oldService.getTel_type(tel_type);
			Long callId = 0l;
			if ("是".equals(call_id)) {
				callId = 1l;
			}
			Omp_Old_Info old_info = new Omp_Old_Info();
			old_info.setHousehold_county_id(countyId);
			old_info.setHousehold_street_id(streetId);
			old_info.setHousehold_community_id(communityId);
			old_info.setWorkername(workername);
			old_info.setWorkertel(workertel);
			old_info.setName(getCellValue(row.getCell(4)));
			old_info.setCertificates_number(getCellValue(row.getCell(5)));
			old_info.setZjnumber(getCellValue(row.getCell(6)));
			old_info.setPhone(getCellValue(row.getCell(7)));
			old_info.setEmergencycontact(getCellValue(row.getCell(8)));
			old_info.setEmergencycontacttle(getCellValue(row.getCell(9)));
			// 话机类型
			old_info.setTeltype(String.valueOf(tel_num));
			old_info.setAddress(getCellValue(row.getCell(11)));
			old_info.setCall_id(callId);
			old_info.setAccount_type(acc + comNUm);
			infos.add(old_info);

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
		return value;
	}

	/**
	 * 去修改老人数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/oldMatch/upd.shtml")
	@ResponseBody
	public ModelAndView upd(String id) {
		ModelAndView mv = new ModelAndView("/omp/old/upd");
		Omp_Old_Info old = oldService.getOldById(id);
		// Map<String, Object> map = list.get(0);
		Map Region = oldService.getRegionList(old);
		mv.addObject("detaMap", old);
		mv.addObject("Region", Region);
		return mv;
	}

	/**
	 * 查看老人数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/oldMatch/see.shtml")
	@ResponseBody
	public ModelAndView seePerson(String id, String cardId) {
		ModelAndView mv = new ModelAndView("/omp/old/see");
		Omp_Old_Info old = generalService.getObjectById(Omp_Old_Info.class,
				Long.parseLong(id));
		List<Map<String, Object>> person = oldService.getPerson(cardId);
		if (person.size() != 0) {
			Map<String, Object> mapPreson = person.get(0);
			mv.addObject("mapPreson", mapPreson);
		}
		mv.addObject("detaMap", old);
		return mv;
	}

	/**
	 * 去查看老人数据
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/oldMatch/oldinfo.shtml")
	@ResponseBody
	public ModelAndView oldinfo(String oid,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		ModelAndView mv = new ModelAndView("/omp/old/oldInfo");
		ArrayList arrayList = new ArrayList<>();
		List<Map<String, Object>> list = oldService.getOldById1(oid);
		// 判断是否老人已经设置指令了
		List<Map<String, Object>> sp = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = list.get(0);
		if (list.size() > 0 && list.get(0).get("ksp_id") != null) {
			String ksp_idJsom = map.get("ksp_id") + "";
			JSONArray json = JSONArray.fromObject(ksp_idJsom);
			System.out.println(json.get(0));
			JSONObject o = JSONObject.fromObject(json.get(0));
			Ksp_id kd = (Ksp_id) JSONObject.toBean(o, Ksp_id.class);
			System.out.println(kd);
			sp = kd.getSp(generalService);
			if (user.getId() > 1) {
				sp.remove(15);
				sp.remove(14);
				sp.remove(13);
				sp.remove(12);
				sp.remove(10);
			}
			mv.addObject("sp", sp);

		}

		mv.addObject("arrayList", list);
		mv.addObject("detaMap", map);
		Map Region = oldService.getRegionList1(map);
		mv.addObject("Region", Region);
		return mv;
	}

	/**
	 * 去修改老人话机数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/oldMatch/ompKeyModify.shtml")
	@ResponseBody
	public ModelAndView ompKeyModify(String oid, String typeid,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		ModelAndView mv = new ModelAndView("/omp/old/ompKeyModify");
		ArrayList arrayList = new ArrayList<>();
		// List<Map<String,Object>> Region = oldService.getOldById(id);

		List<Map<String, Object>> Infolist = oldService
				.getOldKeyPointMessage(oid);
		List<Map<String, Object>> list = oldService.getOldById1(oid);

		// 判断是否老人已经设置指令了
		List<Map<String, Object>> sp = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = list.get(0);
		if (list.size() > 0 && list.get(0).get("ksp_id") != null) {
			String ksp_idJsom = map.get("ksp_id") + "";
			JSONArray json = JSONArray.fromObject(ksp_idJsom);
			System.out.println(json.get(0));
			JSONObject o = JSONObject.fromObject(json.get(0));
			Ksp_id kd = (Ksp_id) JSONObject.toBean(o, Ksp_id.class);
			System.out.println(kd);
			sp = kd.getSp(generalService);
			List<Map<String, Object>> ssNull = Permissions.getQueryarchitecture(
					user, Long.parseLong(typeid), jdbcTemplate);
			for (Map<String, Object> m1 : ssNull) {
				for (Map<String, Object> m2 : sp) {
					if(m1.get("key").equals(m2.get("key"))){
						m1.put("service", m2.get("sp"));
					}
				}
			}
			mv.addObject("sp", ssNull);

		}

		mv.addObject("detaMap", arrayList);
		mv.addObject("hxUserID", oid);
		return mv;
	}

	/**
	 * 老人个性化数据修改
	 * 
	 * @param id
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@RequestMapping("/oldMatch/uploadOldIndividuation.shtml")
	@ResponseBody
	public String uploadOldIndividuation(String oid,Ksp_id ksp) throws IllegalArgumentException, IllegalAccessException {
		System.out.println(ksp.toString());
		List<Map<String, Object>> list = oldService.getOldById1(oid);
		Map<String, Object> map = list.get(0);
		String ksp_idJsom = map.get("ksp_id") + "";
		JSONArray jsonOld = JSONArray.fromObject(ksp_idJsom);
		JSONObject o = JSONObject.fromObject(jsonOld.get(0));
		Ksp_id kd = (Ksp_id) JSONObject.toBean(o, Ksp_id.class);
		  Class cls1 = ksp.getClass();  
		  Class cls2 = kd.getClass();  
	        Field[] fields1 = cls1.getDeclaredFields();  
	        Field[] fields2 = cls2.getDeclaredFields();  
	        for(int i=0; i<fields1.length; i++){  
	            Field f1 = fields1[i];  
	            Field f2 = fields2[i];  
	            f1.setAccessible(true);  
	            f2.setAccessible(true);  
	            if(f1.get(ksp)==null){
	            	f1.set(ksp, f2.get(kd));
	            }
	        }  
	        System.out.println(ksp.toString());
	        Boolean updateSjson = oldService.uploadOldIndividuation(oid, ksp.toString(),1);
	        List<Map<String, Object>> jsonMap = kd.getSp(generalService);
	        String json = "";
	        json += "[{";
	        for (Map<String, Object> m: jsonMap) {
				String key = m.get("key")+"";
				ServiceProvider sp = (ServiceProvider) m.get("sp");
				String serviceTell = "";
				if(sp != null){
					serviceTell = sp.getServiceTell();
				}else{
					//固定号码
					if("M11".equals(key)){
						serviceTell = "8008100032";
					}else if("M13".equals(key)){
						serviceTell = "84925513";
					}else if("M14".equals(key)){
						serviceTell = "84931297";
					}else if("M15".equals(key)){
						serviceTell = "8008100032";
					}else if("M16".equals(key)){
						serviceTell = "8008100032";
					}else{
						serviceTell = "96003";
					}
				}
				json += "\"" + key + "\":";
				json += "\"" + serviceTell + "\",";
			}
			json = json.substring(0, json.length() - 1);
			json += "}]";
			 Boolean updateJson = oldService.uploadOldIndividuation(oid, json,2);
		if (updateSjson && updateJson) {
			return "修改成功！";
		}
		return "修改失败，请稍后尝试！";
	}

	/**
	 * 修改老人数据
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/oldMatch/updete.shtml")
	public String updete(HttpServletRequest request, OldParameter parameter,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		Omp_Old_Info old = parameter.getEntity();

		String county = parameter.getCounty();
		String street = parameter.getStreet();
		String community = parameter.getCommunity();
		Omp_Old_Info newOld = oldService
				.getOldById(String.valueOf(old.getId()));
		newOld.setName(old.getName());
		newOld.setHousehold_county_id(county);
		newOld.setHousehold_street_id(street);
		newOld.setHousehold_community_id(community);
		if (!community.equals(old.getHousehold_community())
				&& "g".equals(user.getAccount_type())) {
			String sql = "select t.id from users t where t.ENABLED = 1 and  t.rid = "
					+ community;
			long id = jdbcTemplate.queryForLong(sql);
			newOld.setAgent_id(id);
		}

		newOld.setZjnumber(old.getZjnumber());
		newOld.setPhone(old.getPhone());
		newOld.setAddress(old.getAddress());
		newOld.setEmergencycontact(old.getEmergencycontact());
		newOld.setEmergencycontacttle(old.getEmergencycontacttle());
		newOld.setTel(old.getTel());
		if (user.getId() == 1) {
			newOld.setTeltype(old.getTeltype());
		}

		newOld.setCall_id(old.getCall_id());
		oldService.updOldById(newOld);
		return "redirect:/old/oldMatch/list.shtml?name=&idCard=&zjNumber=&county=&street=&community=&isGenerationOrder=&creationTime=";
	}

	/**
	 * 省市街区三级联动
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/oldMatch/getRegionById.shtml")
	@ResponseBody
	public List<Map<String, Object>> getRegionById(String id) {
		if (StringUtils.isEmpty(id)) {
			id = "2";
		}
		List<Map<String, Object>> list = oldService.getRegionById(id);
		return list;
	}

	/**
	 * 查询政府单位所在区域
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/oldMatch/getRegionByPid.shtml")
	@ResponseBody
	public List<Map<String, Object>> getRegionByPid(String id) {
		if (StringUtils.isEmpty(id)) {
			id = "2";
		}
		List<Map<String, Object>> list = oldService.getRegionByPid(id);
		return list;
	}

	/**
	 * 停用老人信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/oldMatch/delete.shtml")
	public String oldMatchDelete(String id) {
		int flg = oldService.delOldById(id);
		return "redirect:/old/oldMatch/list.shtml?name=&idCard=&zjNumber=&county=&street=&community=&isGenerationOrder=&creationTime=";
	}

	/**
	 * 老人导入
	 * 
	 * @return
	 */
	@RequestMapping("/Import/toImport.shtml")
	public ModelAndView toImport() {
		ModelAndView mv = new ModelAndView("/omp/old/Import");
		return mv;
	}

	/**
	 * 生成指令
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/oldMatch/createOrder.shtml")
	@ResponseBody
	public String createOrder(String ids) {
		if (checkId(ids)) {
			return "您当前所选的老人中已经有部分生成了指令，请仔细核对！！！";
		} else {

			return toCreate(ids);
		}
	}

	public boolean checkId(String ids) {
		String[] id = ids.split(",");
		for (String string : id) {
			if (!oldService.checkId(string)) {
				return false;
			}
		}
		return true;
	}

	public String toCreate(String ids) {
		int i = 0;
		String[] id = ids.split(",");
		for (String string : id) {
			if (toCreateOrder(string)) {
				i++;
			}
		}
		if (i == id.length) {
			return "指令生成成功";
		}
		return "指令生成失败，请检查该社区该类型是否以添加";
	}

	// 生成指令
	private boolean toCreateOrder(String id) {
		List<Map<String, Object>> list = oldService.queryCommunityOrder(id);
		String keyPointMessage = "[{";
		for (Map<String, Object> map : list) {
			String key = map.get("key").toString();
			String number = map.get("number").toString();
			keyPointMessage += "\"" + key + "\":\"" + number + "\",";
		}
		keyPointMessage = keyPointMessage.substring(0,
				keyPointMessage.length() - 1);
		keyPointMessage += "}]";
		String pname = list.get(0).get("pname").toString();
		String comId = list.get(0).get("comId").toString();
		boolean saveOrder = oldService.saveOrder(id, pname, comId,
				keyPointMessage);
		if (saveOrder) {
			oldService.updOldState(id);
		}
		return saveOrder;
	}

	@RequestMapping("/oldMatch/getOldKeyPointMessage.shtml")
	@ResponseBody
	public List<Map<String, Object>> getOldKeyPointMessage(String oid) {
		List<Map<String, Object>> list = oldService.getOldKeyPointMessage(oid);
		return list;
	}

	@RequestMapping("/oldMatch/batchSendInstructions.shtml")
	public void batchSendInstructions() throws Exception {
		if (oldService.getcountid()) {
			System.out.println("batchSendInstructions:定时器自动执行发送指令程序，间隔时间1分钟");
			String id = oldService.checkDeBatchSendInstructions();
			oldService.saveolInfo(id);
		}
	}

	/*
	 * 老人信息导出
	 */
	@RequestMapping("/oldMatch/exportExcel.shtml")
	public void exportExcel(NativeWebRequest request,
			HttpServletResponse response, OldParameter parameter,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		try {
			OutputStream stream = response.getOutputStream();
			response.setContentType("application/msexcel;charset=UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment;filename=\""
							+ ExportUtils.getExportFileName(request, "老人信息"
									+ DateUtils.currentDateTime()) + ".xlsx");
			ExcelBuilder exportExcel = oldService.exportExcel(parameter, user);
			exportExcel.writeToStream(stream);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		;

	}

	/**
	 * 跳转添加老人页面
	 * 
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/oldMatch/oldToAdd.shtml")
	@ResponseBody
	public ModelAndView oldToAdd(
			@ModelAttribute("eccomm_admin") SystemUser user,
			OldParameter parameter) {
		ModelAndView mv = new ModelAndView("/omp/old/oldAdd");
		// 获取市
		List<OmpRegion> citys = Permissions.queryCounty(0L, generalService);
		// oldService.saveLogger("2", "老人信息表", "lixing", "1");
		mv.addObject("command", parameter);
		mv.addObject("citys", citys);
		return mv;
	}

	/**
	 * 录入老人
	 * 
	 * @param user
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/oldMatch/oldAdd.shtml")
	@ResponseBody
	public String oldAdd(@ModelAttribute("eccomm_admin") SystemUser user,
			OldParameter parameter) {
		String errorstr = "";
		Omp_Old_Info ompOldInfo = parameter.getEntity();
		int t = oldService.checkOldIsHave(ompOldInfo.getZjnumber(),
				ompOldInfo.getCertificates_number());
		if (t > 0) {
			errorstr = "大座机号重复 \n 添加失败";
		} else {
			errorstr = "添加成功";
			String linkNbr = ompOldInfo.getZjnumber();
			// 判断
			String num = linkNbr.substring(0, 3);
			if ("010".equals(num)) {
				ompOldInfo.setZjnumber(linkNbr.substring(3));
			}
			oldService.addOld(ompOldInfo, user);
		}
		return errorstr;
	}

}
