package com.capinfo.omp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.capinfo.omp.service.SystemLogs;

@Service
public class SystemLogsImpl implements SystemLogs {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> list() {
		String sql = "select * from  omp_log_record";
		List<Map<String, Object>> showList = jdbcTemplate.queryForList(sql);
		return showList;
	}

	@Override
	public List<Map<String, Object>> getlistCount(String county, String street, String community, String otype, String stimes, String etimes) {
		String querySql = "";

		String resutSql = getSql(county, street, community, otype, stimes, etimes, querySql);
		
//		String sql = "SELECT k.keyPointMessage 'key',SUM(k.keyPointCount) AS nbr,r1.`NAME` community,r2.`NAME` county,r3.`NAME` street FROM omp_old_info i,omp_keymapping_statistical k,omp_region r1,omp_region r2,omp_region r3 WHERE k.landLineNumber = i.ZJNUMBER AND i.HOUSEHOLD_COMMUNITY_ID = r1.ID AND i.HOUSEHOLD_COUNTY_ID = r2.ID AND i.HOUSEHOLD_STREET_ID = r3.ID "
//				+ county
//				+ street
//				+ community
//				+ otype
//				+ stimes
//				+ etimes
//				+ " GROUP BY k.keyPointMessage ORDER BY k.id";
		String sql = "SELECT t.serviceName sname, SUM(s.keyPointCount) count FROM omp_keymapping_statistical s INNER JOIN omp_old_info i ON s.landLineNumber = i.ZJNUMBER INNER JOIN omp_key k ON k.`key` = s.keyPointMessage INNER JOIN omp_service_type t on t.id = k.stId WHERE  1=1" + resutSql + " and k.pyId=i.TELTYPE GROUP BY s.keyPointMessage";
		List<Map<String, Object>> showList = jdbcTemplate.queryForList(sql);
		return showList;
	}



	@Override
	public void getsendService(String street, String community, String otype,
			Date stimes, Date etime) {
		String region_sql = "LEFT JOIN omp_region r ON r.ID = o.HOUSEHOLD_COUNTY_ID";
		if (street != null && !StringUtils.isEmpty(street)) {
			street = " AND I.HOUSEHOLD_STREET_ID = '" + street + "'";
		}
		if (community != null && !StringUtils.isEmpty(community)) {
			community = "  AND I.HOUSEHOLD_COMMUNITY_ID = '" + community + "'";

		}
		if (otype != null && otype != null && !StringUtils.isEmpty(otype)) {
			otype = " AND I.TELTYPE = '" + otype + "'";
		}
		String sql = "SELECT r. NAME, r.id, COUNT(*) zl, SUM(CASE when v.execute_flag=1 then 1 else 0 END) execute_suc, SUM(CASE when v.execute_flag=0 then 1 else 0 END)	execute_fail FROM omp_voice_order v LEFT JOIN omp_old_info o ON v.oldId = o.ID LEFT JOIN omp_region r ON r.ID = o.HOUSEHOLD_COUNTY_ID where 1=1"
				+ ""
				+ ""
				+ ""
				+ "GROUP BY r.name ,r.id ORDER BY r.id";

	}

	@Override
	public List<Map<String, Object>> getKeyboardUpdateCount(String county, String street, String community, String otype, String stime, String etime) {
		String querySql = "";
		String resutSql = getSql(county, street, community, otype, stime, etime, querySql);
		
		
		
		return null;
	}
	
	
	
	private String getSql(String county, String street, String community, String otype, String stimes, String etimes, String querySql) {
		if (county != null && !StringUtils.isEmpty(county)) {
			querySql+= " AND I.HOUSEHOLD_COUNTY_ID = '" + county + "'";
		}
		if (street != null && !StringUtils.isEmpty(street)) {
			querySql+= " AND I.HOUSEHOLD_STREET_ID = '" + street + "'";
		}
		if (community != null && !StringUtils.isEmpty(community)) {
			querySql+= "  AND I.HOUSEHOLD_COMMUNITY_ID = '" + community + "'";

		}
		if (otype != null && !StringUtils.isEmpty(otype)) {
			querySql+= " AND I.TELTYPE = '" + otype + "'";
		}
		if (stimes != null && !StringUtils.isEmpty(stimes)) {
			querySql+= " AND s.commitTime > '" + stimes + "'";
		}
		if (etimes != null && !StringUtils.isEmpty(etimes)) {
			querySql+= " AND s.commitTime <= '" + etimes + "'";
		}
		return querySql;
	}
	
	
}
