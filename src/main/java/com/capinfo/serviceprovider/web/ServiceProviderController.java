package com.capinfo.serviceprovider.web;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.omp.utils.Page;
import com.capinfo.serviceprovider.model.ServiceProviders;
import com.capinfo.serviceprovider.service.ServiceProviderService;
import com.capinfo.serviceprovider.utils.ExportExcel;
import com.capinfo.serviceprovider.utils.ImportExcelUtil;

@Controller
@RequestMapping("/admin/omp/ServiceProvider")
public class ServiceProviderController {

	@Autowired
	private ServiceProviderService ompOldMatchService;

	@RequestMapping("/initial.shtml")
	public ModelAndView initial(HttpServletRequest request, String current) {
		ModelAndView mv = new ModelAndView("/omp/serviceProvider/initial");
		return mv;
	}

	/**
	 * 服务商管理列表
	 *
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/list.shtml")
	public ModelAndView dicSortList(HttpServletRequest request, String current) {
		ModelAndView mv = new ModelAndView("/omp/serviceProvider/initial");
		// String current = request.getParameter("current");
		getSortList(current, mv, "");
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
	private void getSortList(String current, ModelAndView mv, String QSql) {
		if (StringUtils.isEmpty(current)) {
			current = "1";
		}
		if (StringUtils.isEmpty(QSql)) {
			QSql = "";
		}
		int count = ompOldMatchService.getCountWithSql(QSql);
		Page page = new Page(current, count, "10");
		List<Map<String, Object>> list = ompOldMatchService.getListWithSql(page, QSql);
		for (Map<String, Object> map : list) {
			String id = map.get("SCOPE_DELIVERY").toString();
			if (!StringUtils.isEmpty(id)) {
				// String name = ompOldMatchService.getregion(id);
				map.put("SCOPE_DELIVERY", id);
			}
		}
		mv.addObject("DataTotalCount", count);
		mv.addObject("PerPieceSize", 10);
		mv.addObject("command", list);
		mv.addObject("CurrentPieceNum", page.getCurrentPage());
		mv.addObject("QSql", QSql);
	}

	/**
	 * 服务商列表
	 *
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/lists.shtml")
	public ModelAndView userList(HttpServletRequest request, String current, String QSql) {
		ModelAndView mv = new ModelAndView("/omp/serviceProvider/list");
		getSortList(current, mv, QSql);
		return mv;
	}

	@RequestMapping("/serverPro.shtml")
	public ModelAndView serverPro(String id) {
		ModelAndView mv = null;
		Map<String, Object> map = ompOldMatchService.getMapById(id);
		switch (map.get("tify").toString()) {
		case "YL":
			mv = new ModelAndView("/omp/serviceProvider/serverPro_P");
			break;
		case "SN":
			mv = new ModelAndView("/omp/serviceProvider/serverPro_D");
			break;
		}
		mv.addObject("map", map);
		return mv;
	}

	// 查询服务商详情
	@RequestMapping("/serverInfo.shtml")
	public ModelAndView serverInfo(String id) {
		ModelAndView mv = new ModelAndView("/omp/serviceProvider/serverInfo");
		// ompOldMatchService.getMapById(id);
		mv.addObject("item", ompOldMatchService.getServerInfoWithID(id));
		return mv;
	}

	/**
	 * 导出服务商信息
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/export.shtml")
	@ResponseBody
	public ModelAndView exportExcel() throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/admin/omp/ServiceProvider/list.shtml");
		getSortList(null, mv, "");
		// setWorkbookStyle(mv);// 获取列头样式对象

		String title = "服务商信息表";
		String[] rowName = { "序号", "名称", "配送范围", "服务类型", "服务商电话", "是否大座机服务商" };
		List<List> dataList = new ArrayList<List>();
		// List<Map<String, Object>> list = (List<Map<String, Object>>)
		// mv.getModel().get("command");
		// 取得所有的服务商信息
		List<Map<String, Object>> list = ompOldMatchService.getAllList();
		for (Map<String, Object> map : list) {
			List list2 = mapTransitionList(map);
			dataList.add(list2);

		}
		ExportExcel excel = new ExportExcel(title, rowName, dataList);
		OutputStream out = new FileOutputStream("D:\\服务商信息.xls");
		excel.export(out);
		return mv;
	}

	// map转换成lisy
	public static List mapTransitionList(Map map) {
		List list = new ArrayList();
		Iterator iter = map.entrySet().iterator(); // 获得map的Iterator
		Set keySet = map.keySet();
		for (Object object : keySet) {
			if (!(object.equals("SERVER_ID") || object.equals("SERVICE_PROVIDERS_IDENTIFY"))) {
				if (object.equals("IS_VALID")) {
					list.add("是");
				} else {
					list.add(map.get(object));
				}
			}
		}
		return list;
	}
    /**
     * 导入
     * @return
     */
	@RequestMapping("/serviceProvider/toImport.shtml")
	public ModelAndView toImport() {
		ModelAndView mv = new ModelAndView("/omp/serviceProvider/Import");
		return mv;
	}


    /**
     * 删除
     * @return
     */
	@RequestMapping("deleteService.shtml")
	public void deleteService(String id) {
		//ModelAndView mv = new ModelAndView("redirect:/admin/omp/ServiceProvider/list.shtml");
		ompOldMatchService.deleteService(id);
	//	ModelAndView mv = new ModelAndView("/omp/serviceProvider/Import");
	///	return mv;
	}


	/**
	 * 服务商导入
	 *
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/serviceProvider/importService.shtml")
	public ModelAndView importService(HttpServletRequest request, HttpServletResponse response,@RequestParam("excelFile") MultipartFile file) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/admin/omp/ServiceProvider/list.shtml");
		InputStream in = null;
		List<List<Object>> listob = null;
		in = file.getInputStream();
		listob = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename(),0);
		int i = ompOldMatchService.importService(listob);
		mv.addObject("messageCount", i);
		return mv;
	}


}
