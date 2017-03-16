package com.capinfo.omp.controller;

import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.beans.factory.annotation.Autowired;
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
import com.capinfo.framework.util.DateUtils;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.parameter.CompositionParameter;
import com.capinfo.omp.parameter.OrderParameter;
import com.capinfo.omp.service.OldService;
import com.capinfo.omp.utils.JsonUtil;
import com.capinfo.omp.utils.Page;
import com.capinfo.omp.utils.excel.ExcelBuilder;
import com.capinfo.omp.utils.excel.ExportUtils;
import com.capinfo.omp.ws.client.ClientGetLandNumberService;

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

	// @Autowired
	// private CardPersonMessageBack CardPersonMessageBack;

	@RequestMapping("/oldMatch/list.shtml")
	public ModelAndView list(@ModelAttribute("eccomm_admin") SystemUser user,
			String current, String name, String idCard, String zjNumber,
			String county, String street, String community,
			String isGenerationOrder, String isindividuation,
			String creationTime, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/omp/old/initial");
		getList(mv, current, name, idCard, zjNumber, county, street, community,
				isGenerationOrder, isindividuation, creationTime, user);

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
	public ModelAndView listtoo(String current, String name, String idCard,
			String zjNumber, String county, String street, String community,
			String isGenerationOrder, String isindividuation,
			String creationTime, Integer call_id,
			@ModelAttribute("eccomm_admin") SystemUser user) {
		ModelAndView mv = new ModelAndView("/omp/old/list");
		// oldService.getOldContextList(page, name, idCard, zjNumber, county,
		// street, community, isGenerationOrder, isindividuation, user);

		getList(mv, current, name, idCard, zjNumber, county, street, community,
				isGenerationOrder, isindividuation, creationTime, user);
		// LogRecord.logger("2", "", "", "", "2");
		return mv;
	}

	public void getList(ModelAndView mv, String current, String name,
			String idCard, String zjNumber, String county, String street,
			String community, String isGenerationOrder, String isindividuation,
			String creationTime, SystemUser user) {

		if (StringUtils.isEmpty(current)) {
			current = "1";
		}
		if (StringUtils.isEmpty(isindividuation)) {
			isindividuation = "";
		}

		int count = oldService.getCount(name, idCard, zjNumber, county, street,
				community, isGenerationOrder, isindividuation, user);
		// count = count == 0 ? 1 : count;
		Page page = new Page<>(current, count, "10");
		List<Omp_Old_Info> entities = oldService.getOldContextList(page, name,
				idCard, zjNumber, county, street, community, isGenerationOrder,
				isindividuation, user);
		mv.addObject("dataList", entities);
		mv.addObject("DataTotalCount", count);
		mv.addObject("CurrentPieceNum", page.getCurrentPage());
		mv.addObject("PerPieceSize", "10");
		mv.addObject("name", name);
		mv.addObject("idCard", idCard);
		mv.addObject("zjNumber", zjNumber);
		mv.addObject("county", county);
		mv.addObject("street", street);
		mv.addObject("community", community);
		mv.addObject("isGenerationOrder", isGenerationOrder);
		mv.addObject("isindividuation", isindividuation);
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
			Map<String, Object> map = importEmployeeByPoi(fis,user.getAccount_type());
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
					// 通过座机号，身份证号判断是否存在老人 1大座机号已存在 2身份证号已存在 0不存在
					int t = oldService.checkOldIsHave(ompOldInfo.getZjnumber(),
							ompOldInfo.getCertificates_number());
					if (t == 3) {
						errorstr = errorstr + "第" + nb + "行:身份证号和大座机号重复 \n";
						enb++;

					} else if (t == 1) {
						errorstr = errorstr + "第" + nb + "行:大座机号重复 \n ";
						enb++;
					} else if (t == 2) {
						errorstr = errorstr + "第" + nb + "行:身份证号重复 \n";
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
		// 市区
		String area = getCellValue(row3.getCell(0));
		// 街道
		String street = getCellValue(row3.getCell(3));
		// 社区
		String community = getCellValue(row3.getCell(6));
		// 根据市区名称查询市区ID
		String countyId = oldService.getIdByName(area, 3);
		// 根据街道名称查询街道ID
		String streetId = oldService.getIdByName(street, 4);
		// 根据社区名称查询社区ID
		String communityId = oldService.getIdByName(community, 5);
		//查询社区编码
		String comNUm = oldService.getIdByComCod(community, 5);
		Row row6 = sheetAt0.getRow(6);
		// 姓名
		String workername = getCellValue(row6.getCell(1));
		String workertel = row6.getCell(4).getStringCellValue();
		int rowNum = sheetAt0.getLastRowNum();

		for (int i = 10; i < rowNum + 1; i++) {
			Row row = sheetAt0.getRow(i);

			String call_id = getCellValue(row.getCell(9));
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
			old_info.setName(getCellValue(row.getCell(1)));
			old_info.setCertificates_number(getCellValue(row.getCell(2)));
			old_info.setZjnumber(getCellValue(row.getCell(3)));
			old_info.setPhone(getCellValue(row.getCell(4)));
			old_info.setEmergencycontact(getCellValue(row.getCell(5)));
			old_info.setEmergencycontacttle(getCellValue(row.getCell(6)));
			old_info.setTeltype(getCellValue(row.getCell(7)));
			old_info.setAddress(getCellValue(row.getCell(8)));
			old_info.setCall_id(callId);
			old_info.setAccount_type(acc+comNUm);
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
		List<Map<String, Object>> list = oldService.getOldById(id);
		Map<String, Object> map = list.get(0);
		Map Region = oldService.getRegionList(map);
		mv.addObject("detaMap", map);
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
		List<Map<String, Object>> list = oldService.getOldById(id);
		Map<String, Object> map = list.get(0);
		List<Map<String, Object>> person = oldService.getPerson(cardId);
		if (person.size() != 0) {
			Map<String, Object> mapPreson = person.get(0);
			mv.addObject("mapPreson", mapPreson);
		}
		Map Region = oldService.getRegionList(map);
		mv.addObject("Region", Region);
		mv.addObject("detaMap", map);
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
	public ModelAndView oldinfo(String id) {
		ModelAndView mv = new ModelAndView("/omp/old/oldInfo");
		ArrayList arrayList = new ArrayList<>();
		List<Map<String, Object>> list = oldService.getOldById(id);
		List<Map<String, Object>> list1 = oldService.getOldById1(id);
		// 判断是否老人已经设置指令了
		if (list.size() > 0 && list.get(0).get("Kp") != null) {
			Map<String, Object> map = list.get(0);
			Map Region = oldService.getRegionList(map);
			String kpLiString = (String) map.get("Kp");
			if (kpLiString != null && !"".equals(kpLiString)) {
				JSONObject jsonObject = JsonUtil.getJson(kpLiString);
				JSONArray json1 = JsonUtil.getJson1(jsonObject);
				if (json1.size() > 0) {
					for (int i = 0; i < json1.size(); i++) {
						JSONObject job = json1.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成json 对象
						arrayList.add(job);
					}
				}
			}

			mv.addObject("arrayList", arrayList);
			mv.addObject("detaMap", map);
			mv.addObject("Region", Region);
		} else {
			Map<String, Object> map = list1.get(0);
			Map Region = oldService.getRegionList(map);
			mv.addObject("arrayList", arrayList);
			mv.addObject("detaMap", map);
			mv.addObject("Region", Region);
		}

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
	public ModelAndView ompKeyModify(String id, String typeid) {
		ModelAndView mv = new ModelAndView("/omp/old/ompKeyModify");
		ArrayList arrayList = new ArrayList<>();
		// List<Map<String,Object>> Region = oldService.getOldById(id);

		List<Map<String, Object>> Infolist = oldService
				.getOldKeyPointMessage(id);
		List<Map<String, Object>> list = oldService.getOldById(id);

		// 判断是否老人已经设置指令了
		if (list.size() > 0 && list.get(0).get("Kp") != null) {
			Map<String, Object> map = list.get(0);
			Map Region = oldService.getRegionList(map);
			String kpLiString = (String) map.get("Kp");
			if (kpLiString != null && !"".equals(kpLiString)) {
				JSONObject jsonObject = JsonUtil.getJson(kpLiString);
				JSONArray json1 = JsonUtil.getJson1(jsonObject);
				if (json1.size() > 0) {
					for (int i = 0; i < json1.size(); i++) {
						JSONObject job = json1.getJSONObject(i); // 遍历 jsonarray// 数组，把每一个对象转成// json 对象
						arrayList.add(job);
					}
				}
			}

			mv.addObject("detaMap", arrayList);
			mv.addObject("hxUserID", id);
		}

		mv.addObject("detaMap", arrayList);
		mv.addObject("hxUserID", id);
		return mv;
	}

	/**
	 * 老人个性化数据修改
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/oldMatch/uploadOldIndividuation.shtml")
	@ResponseBody
	public String uploadOldIndividuation(String id, String json) {
		Boolean isUpdateBoolean = oldService.uploadOldIndividuation(id, json);
		if (isUpdateBoolean) {

			return "修改成功！";
		}
		boolean newBl = oldService.addOmpOldOrderInfo(id, json);
		if (newBl) {
			return "添加成功！";
		}
		return "修改失败，请稍后尝试！";
	}

	/**
	 * 修改老人数据
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/oldMatch/updete.shtml")
	public String updete(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		map.put("id", id);
		String name = request.getParameter("name");
		map.put("name", name);
		String county = request.getParameter("county");
		map.put("county", county);
		String street = request.getParameter("street");
		map.put("street", street);
		String community = request.getParameter("community");
		map.put("community", community);
		String zjnumber = request.getParameter("zjnumber");
		map.put("zjnumber", zjnumber);
		String phone = request.getParameter("phone");
		map.put("phone", phone);
		String address = request.getParameter("address");
		map.put("address", address);
		String emergencycontact = request.getParameter("emergencycontact");
		map.put("emergencycontact", emergencycontact);
		String emergencycontacttle = request
				.getParameter("emergencycontacttle");
		map.put("emergencycontacttle", emergencycontacttle);
		String teltype = request.getParameter("teltype");
		map.put("teltype", teltype);
		oldService.updOldById(map);
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
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = oldService.getRegionById(id);
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
			HttpServletResponse response, OrderParameter parameter,@ModelAttribute("eccomm_admin") SystemUser user) {
		try {
			OutputStream stream = response.getOutputStream();
			response.setContentType("application/msexcel;charset=UTF-8");
			response.setHeader(
					"Content-Disposition",
					"attachment;filename=\""
							+ ExportUtils.getExportFileName(request, "老人信息"
									+ DateUtils.currentDateTime()) + ".xlsx");
			ExcelBuilder exportExcel = oldService.exportExcel(parameter,user);
			exportExcel.writeToStream(stream);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		;

	}


}
