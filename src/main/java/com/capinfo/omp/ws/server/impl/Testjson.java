package com.capinfo.omp.ws.server.impl;

import java.util.List;

import net.sf.json.JSONObject;

import com.capinfo.omp.ws.model.CountMessge;
import com.capinfo.omp.ws.model.KeyCount;
import com.google.gson.Gson;

public class Testjson {
		public static void main(String[] args) {
			int number = 0;
			int result = 0;
			String msg = "";
			String json = "{'landLineNumber':'051087430716','CountMessge':[{'keyPointMessage':'M1 ','keyPointCount':'0'},{'keyPointMessage':'M2','keyPointCount':'0'},{'keyPointMe ssage':'M3','keyPointCount':'0'},{'keyPointMessage':'M4','keyPointCount':'0'},{'keyPointMessage':'M5','keyPointCount':'0'},{'keyPointMessage':'M6','keyPointCoun t':'0'},{'keyPointMessage':'M7','keyPointCount':'0'},{'keyPointMessage':'M8','ke yPointCount':'1'},{'keyPointMessage':'M9','keyPointCount':'0'},{'keyPointMessage ':'M10','keyPointCount':'0'},{'keyPointMessage':'M11','keyPointCount':'0'},{'key PointMessage':'M12','keyPointCount':'0'}],'daysCount':'0','commitTime':'2017-06- 01 14:28:59','phoneDisplay':'01'}";
			String newJson = json.replaceAll("'", "\"");
			Gson gson = new Gson();
			KeyCount kCount = gson.fromJson(newJson, KeyCount.class);
			System.out.println(kCount);
			List<CountMessge> list = kCount.getCountMessge();
			System.out.println(list);
			try {
				String sql = "INSERT into OMP_KEYMAPPING_STATISTICAL (landLineNumber,keyPointMessage,keyPointCount,daysCount,commitTime,phoneDisplay) VALUES (?,?,?,?,?,?)";
				System.out.println(sql);
				System.out.println("number");
				for (int i = 0; i < list.size(); i++) {
					CountMessge countMessge = list.get(i);
					System.out.println(countMessge.getKeyPointMessage());
					System.out.println("INSERT into OMP_KEYMAPPING_STATISTICAL (landLineNumber,keyPointMessage,keyPointCount,daysCount,commitTime,phoneDisplay) VALUES ("+kCount.getLandLineNumber()+","+countMessge.getKeyPointMessage()+","+countMessge.getKeyPointCount()+","+kCount.getDaysCount()+","+kCount.getCommitTime()+","+kCount.getPhoneDisplay()+")");
//					jdbcTemplate
//							.update(sql,
//									new Object[] { kCount.getLandLineNumber(),
//											countMessge.getKeyPointMessage(),
//											countMessge.getKeyPointCount(),
//											kCount.getDaysCount(),
//											kCount.getCommitTime(),kCount.getPhoneDisplay() });
				}
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
		
		}
}