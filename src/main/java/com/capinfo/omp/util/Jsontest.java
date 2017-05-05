package com.capinfo.omp.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.capinfo.omp.parameter.Ksp_id;

public class Jsontest {
	public static void main(String[] args) {
		String ksp_idJsom = "[{\"m1\":\"82\",\"M2\":\"89\",\"M3\":\"83\",\"M4\":\"84\",\"M5\":\"98\",\"M6\":\"85\",\"M7\":\"86\",\"M8\":\"0\",\"M9\":\"0\",\"M10\":\"0\",\"M11\":\"0\",\"M12\":\"0\",\"M13\":\"0\",\"M14\":\"0\",\"M15\":\"0\",\"M16\":\"0\"}]";
		JSONArray json = JSONArray.fromObject(ksp_idJsom ); 
		System.out.println(json.get(0));
		JSONObject o=JSONObject.fromObject(json.get(0));
		Ksp_id kd=(Ksp_id)JSONObject.toBean(o, Ksp_id.class);
		System.out.println(kd);
	}
}
