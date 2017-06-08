package com.capinfo.omp.ws.server.impl;

import java.util.List;
import javax.jws.WebService;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.capinfo.omp.ws.model.CountMessge;
import com.capinfo.omp.ws.model.KeyCount;
import com.capinfo.omp.ws.server.service.KeyCountService;
import com.google.gson.Gson;

@WebService(endpointInterface = "com.capinfo.omp.ws.server.service.KeyCountService", serviceName = "KeyCountService")
public class KeyCountServiceImpl implements KeyCountService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String KeyCout(String KeyCount) {
		int result = 0;
//		int number = 0;
		String msg = "";
		String newJson = KeyCount.replaceAll("'", "\"");
		Gson gson = new Gson();
		KeyCount kCount = gson.fromJson(newJson, KeyCount.class);
		List<CountMessge> list = kCount.getCountMessge();
		try {
			String sql = "INSERT into OMP_KEYMAPPING_STATISTICAL (landLineNumber,keyPointMessage,keyPointCount,daysCount,commitTime,phoneDisplay) VALUES (?,?,?,?,?,?)";
//			number = Integer.valueOf(kCount.getLandLineNumber());
			for (int i = 0; i < list.size(); i++) {
				CountMessge countMessge = list.get(i);
//				System.out.println("INSERT into OMP_KEYMAPPING_STATISTICAL (landLineNumber,keyPointMessage,keyPointCount,daysCount,commitTime,phoneDisplay) VALUES ("+kCount.getLandLineNumber()+","+countMessge.getKeyPointMessage()+","+countMessge.getKeyPointCount()+","+kCount.getDaysCount()+","+kCount.getCommitTime()+","+kCount.getPhoneDisplay()+")");
				jdbcTemplate
						.update(sql,
								new Object[] { kCount.getLandLineNumber(),
										countMessge.getKeyPointMessage(),
										countMessge.getKeyPointCount(),
										kCount.getDaysCount(),
										kCount.getCommitTime(),kCount.getPhoneDisplay() });
			}
			String zjNumber = kCount.getLandLineNumber();
			String out = "大座机号码为"+zjNumber;
			System.out.println(out);
			if("01".equals(kCount.getPhoneDisplay())){		//同步来电显示到老人表
				String updateSql = "update omp_old_info set call_id = 1 where ZJNUMBER ='"+zjNumber+"'";
				int i = jdbcTemplate.update(updateSql);
				if(i>0){
					out+="有来电显示";
				}
			}else if("00".equals(kCount.getPhoneDisplay())){
				String updateSql = "update omp_old_info set call_id = 0 where ZJNUMBER ='"+zjNumber+"'";
				int i = jdbcTemplate.update(updateSql);
				if(i>0){
					out+="无来电显示";
				}
				
			}
			System.out.println(out);
			result = 1;
		} catch (Exception e) {
			result = 0;
		}
		if (result == 1) {
			msg = "";
		} else {
			msg = "信息填写有误";
		}
		JSONObject jsonArr = new JSONObject();
		jsonArr.put("landLineNumber", kCount.getLandLineNumber());
		jsonArr.put("statusCode", result);
		jsonArr.put("errorMessage", msg);
		
		return jsonArr.toString();

	}
}
