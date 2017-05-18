package com.capinfo.omp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.chainsaw.Main;
import org.springframework.jdbc.core.JdbcTemplate;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.Omp_old_order;
import com.capinfo.omp.model.ServiceProvider;
import com.capinfo.omp.parameter.Ksp_id;
import com.capinfo.omp.parameter.ServiceSystemParameter;
import com.capinfo.region.model.OmpRegion;

/**
 * 用户查询权限
 * @author Rivex
 *
 */
public class Permissions {
	//封装键位
	public static Map<String, Long> mapKeys(ServiceSystemParameter p){
		 Map<String, Long> mapKey = new HashMap<String, Long>();
			mapKey.put("M1", p.getM1());
			mapKey.put("M2", p.getM2());
			mapKey.put("M3", p.getM3());
			mapKey.put("M4", p.getM4());
			mapKey.put("M5", p.getM5());
			mapKey.put("M6", p.getM6());
			mapKey.put("M7", p.getM7());
			mapKey.put("M8", p.getM8());
			mapKey.put("M9", p.getM9());
			mapKey.put("M10", p.getM10());
			mapKey.put("M11", p.getM11());
			mapKey.put("M12", p.getM12());
			mapKey.put("M13", p.getM13());
			mapKey.put("M14", p.getM14());
			mapKey.put("M15", p.getM15());
			mapKey.put("M16", p.getM16());
			return mapKey;
	}
	
	//查询市
	public static List<OmpRegion> queryCounty(long id ,GeneralService g) {
		SearchCriteriaBuilder<OmpRegion> searchCriteriaBuilder = new SearchCriteriaBuilder<OmpRegion>(OmpRegion.class);
		String sql ="";
		if(id==0L){
			sql= " USE_FLAG=1 and LEVELID=2  ";
		}else {
			sql=" USE_FLAG=1 and PARENTID="+id;
		}
		if (!"".equals(sql)) {
			searchCriteriaBuilder.addAdditionalRestrictionSql(sql);
		}
		searchCriteriaBuilder.addOrderCondition("id", "ASC");
		List<OmpRegion> list = g.getObjects(searchCriteriaBuilder.build());
		return list;
	}
	
	
	/**
	 * 老人匹配指令
	 * @param old
	 * @param jdbcTemplate
	 */
	@SuppressWarnings("deprecation")
	public static void addOmpOldOrderInfo(Omp_Old_Info old ,JdbcTemplate jdbcTemplate) {
		List<Map<String, Object>> alllist = null;
		//查询用户设置的指令是否存在
		String sssql = "select count(*) from sys_key ss where ss.tellType_id="
				+ old.getTeltype() + " and ss.uid=" + old.getAgent_id()
				+ " and  ss.user_falg=1";
		Long id = jdbcTemplate.queryForLong(sssql);
		
		if (id == 0) {	//不存在 查询老人所在公共服务体系
			String sqlsp = "select ok.`key`, ss.key_state,sp.serviceName,sp.serviceTell from sys_key sk left JOIN service_system ss on ss.skid = sk.id LEFT JOIN service_provider sp on sp.id = ss.sp_id left join omp_key ok on ok.id=ss.key_state where sk.community_id="
					+ old.getHousehold_community_id()
					+ " and sk.telltype_id="
					+ old.getTeltype();
			alllist = jdbcTemplate.queryForList(sqlsp);
			if (alllist.size() == 0) {
				System.out.println("没有公共服务体系");
				return ;
			}
		} else {	//存在
			String sidsql = "select ss.id from sys_key ss where ss.tellType_id="
					+ old.getTeltype()
					+ " and ss.uid="
					+ old.getAgent_id()
					+ " and  ss.user_falg=1;";
			Long ssid = jdbcTemplate.queryForLong(sidsql);
			String sql = "SELECT t.key_state,st.id stid, k.`key`, p.serviceName, p.serviceTell, t.sp_id, st.serviceName tname FROM service_system t LEFT JOIN omp_key k ON t.key_state = k.id LEFT JOIN omp_service_type st ON k.stId = st.id LEFT JOIN service_provider p ON t.sp_id = p.id where t.skid="
					+ ssid;

			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

			String sqlallss = "select k.id, k.`key`,st.serviceName from omp_key k INNER JOIN omp_phone_type t on k.pyId = t.id INNER JOIN omp_service_type st on st.id = k.stId where k.pyId="
					+ old.getTeltype();

			alllist = jdbcTemplate.queryForList(sqlallss);

			for (Map<String, Object> mapAll : alllist) {
				mapAll.put("sp_id", 0);
				for (Map<String, Object> map : list) {
					if (mapAll.get("key").equals(map.get("key"))) {
						mapAll.put("serviceTell", map.get("serviceTell"));
						mapAll.put("sp_id", map.get("sp_id"));
					}
				}
			}
			for (Map<String, Object> map : alllist) {
				Integer spid = (Integer) map.get("sp_id");
				if (spid == null || spid == 0) {
					String sqlsp = "select ss.key_state,sp.serviceName,sp.serviceTell from sys_key sk left JOIN service_system ss on ss.skid = sk.id LEFT JOIN service_provider sp on sp.id = ss.sp_id where sk.community_id="
							+ old.getHousehold_community_id()
							+ " and sk.telltype_id="
							+ old.getTeltype()
							+ " and sk.uid=1 and ss.key_state=" + map.get("id");
					List<Map<String, Object>> list2 = jdbcTemplate
							.queryForList(sqlsp);
					if (list2.size() == 0) {
						map.put("serviceTell", "96003");
					} else {
						map.put("serviceTell", list2.get(0).get("serviceTell")); // json数据
					}
				}

			}
		}
		String json = "";
		String sjson = "";
		json += "[{";
		sjson += "[{";
		for (int i = 1; i < 17; i++) {
			String m = "M" + i;
			String mm = "m" + i;
			json += "\"" + m + "\":";
			sjson += "\"" + mm + "\":";
			for (Map<String, Object> map : alllist) {
				if (map.get("key").equals(m)) {
					String serviceTell = map.get("serviceTell") + "";
					String sp_id = map.get("sp_id") + "";
					json += "\"" + serviceTell + "\",";
					sjson += "\"" + sp_id + "\",";
				}
			}
		}
		json = json.substring(0, json.length() - 1);
		sjson = sjson.substring(0, sjson.length() - 1);
		json += "}]";
		sjson += "}]";

		System.out.println(json);
		
		String queryOrder="select t.id from omp_old_order t where t.oldId="+old.getId();
		List<Map<String,Object>> list = jdbcTemplate.queryForList(queryOrder);
		String sql = "";
		if(list.size()>0){
			String  oid = list.get(0).get("id")+"";
			 sql = "update omp_old_order set keyPointMessage = '"+json+"' ,k_and_sp_id = '"+sjson+"' where id="+oid ;
		}else{
			 sql = "INSERT INTO omp_old_order ("
					+ "oldId, phoneName, communityOrderId, keyPointMessage,k_and_sp_id)"
					+ "  select t.ID,t.TELTYPE,t.HOUSEHOLD_COMMUNITY_ID,'" + json
					+ "','"+sjson+"' from omp_old_info t WHERE t.ID = " + old.getId();
		}

		jdbcTemplate.update(sql);
	}


/**
 * 查询所拥有的服务体系
 * @param user
 * @param stId
 * @return
 */
	public static List<Map<String, Object>> getQueryarchitecture(SystemUser user,
			Long stId,JdbcTemplate jdbcTemplate) {
		String conditions = "";
		if ("m".equals(user.getAccount_type())
				|| "b".equals(user.getAccount_type())) {
			conditions += " and k.other=1";
		} else if ("g".equals(user.getAccount_type())) {
			conditions += " and k.government=1";
		}

		String sql = "select st.id, k.`key`,st.serviceName from omp_key k INNER JOIN omp_phone_type t on k.pyId = t.id INNER JOIN omp_service_type st on st.id = k.stId where k.pyId="
				+ stId + conditions;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;

	}
	/**
	 * 查询所有的服务体系
	 * @param user
	 * @param pyid	话机类型
	 * @return
	 */
	public static List<Map<String, Object>> getAllChitecture(SystemUser user,
			Long pyid,JdbcTemplate jdbcTemplate) {
		
		String sql = "select st.id, k.`key`,st.serviceName from omp_key k INNER JOIN omp_phone_type t on k.pyId = t.id INNER JOIN omp_service_type st on st.id = k.stId where k.pyId="
				+ pyid ;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
		
	}

	/**
	 * 生成json
	 * @param orderid
	 * @param g
	 */
	public static void mobsync (Long orderid,GeneralService g) {
		Omp_old_order order = g.getObjectById(Omp_old_order.class,orderid);
		String kpjson = order.getK_and_sp_id();
		JSONArray jsonOld = JSONArray.fromObject(kpjson);
		JSONObject o = JSONObject.fromObject(jsonOld.get(0));
		Ksp_id kd = (Ksp_id) JSONObject.toBean(o, Ksp_id.class);
		List<Map<String, Object>> jsonMap = kd.getSp(g);
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
    	System.out.println("服务商审核后同步动指令:"+json);
    	order.setKeyPointMessage(json);
    	g.saveOrUpdate(order);

	}

	/**
	 * 生成sjson
	 * @param old
	 * @param jdbcTemplate
	 */
//	@SuppressWarnings("deprecation")
//	public static sjson(Omp_Old_Info old ,JdbcTemplate jdbcTemplate) {
//		List<Map<String, Object>> alllist = null;
//		//查询用户设置的指令是否存在
//		String sssql = "select count(*) from sys_key ss where ss.tellType_id="
//				+ old.getTeltype() + " and ss.uid=" + old.getAgent_id()
//				+ " and  ss.user_falg=1";
//		Long id = jdbcTemplate.queryForLong(sssql);
//		
//		if (id == 0) {	//不存在 查询老人所在公共服务体系
//			String sqlsp = "select ok.`key`, ss.key_state,sp.serviceName,sp.serviceTell from sys_key sk left JOIN service_system ss on ss.skid = sk.id LEFT JOIN service_provider sp on sp.id = ss.sp_id left join omp_key ok on ok.id=ss.key_state where sk.community_id="
//					+ old.getHousehold_community_id()
//					+ " and sk.telltype_id="
//					+ old.getTeltype();
//			alllist = jdbcTemplate.queryForList(sqlsp);
//			if (alllist.size() == 0) {
//				System.out.println("没有公共服务体系");
//				return ;
//			}
//		} else {
//			String sidsql = "select ss.id from sys_key ss where ss.tellType_id="
//					+ old.getTeltype()
//					+ " and ss.uid="
//					+ old.getAgent_id()
//					+ " and  ss.user_falg=1;";
//			Long ssid = jdbcTemplate.queryForLong(sidsql);
//			String sql = "SELECT t.key_state,st.id stid, k.`key`, p.serviceName, p.serviceTell, t.sp_id, st.serviceName tname FROM service_system t LEFT JOIN omp_key k ON t.key_state = k.id LEFT JOIN omp_service_type st ON k.stId = st.id LEFT JOIN service_provider p ON t.sp_id = p.id where t.skid="
//					+ ssid;
//
//			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//
//			String sqlallss = "select k.id, k.`key`,st.serviceName from omp_key k INNER JOIN omp_phone_type t on k.pyId = t.id INNER JOIN omp_service_type st on st.id = k.stId where k.pyId="
//					+ old.getTeltype();
//
//			alllist = jdbcTemplate.queryForList(sqlallss);
//
//			for (Map<String, Object> mapAll : alllist) {
//				mapAll.put("sp_id", 0);
//				for (Map<String, Object> map : list) {
//					if (mapAll.get("key").equals(map.get("key"))) {
//						mapAll.put("serviceTell", map.get("serviceTell"));
//						mapAll.put("sp_id", map.get("sp_id"));
//					}
//				}
//			}
//			for (Map<String, Object> map : alllist) {
//				Integer spid = (Integer) map.get("sp_id");
//				if (spid == null || spid == 0) {
//					String sqlsp = "select ss.key_state,sp.serviceName,sp.serviceTell from sys_key sk left JOIN service_system ss on ss.skid = sk.id LEFT JOIN service_provider sp on sp.id = ss.sp_id where sk.community_id="
//							+ old.getHousehold_community_id()
//							+ " and sk.telltype_id="
//							+ old.getTeltype()
//							+ " and sk.uid=1 and ss.key_state=" + map.get("id");
//					List<Map<String, Object>> list2 = jdbcTemplate
//							.queryForList(sqlsp);
//					if (list2.size() == 0) {
//						map.put("serviceTell", "96003");
//					} else {
//						map.put("serviceTell", list2.get(0).get("serviceTell")); // json数据
//					}
//				}
//
//			}
//		}
//		String json = "";
//		String sjson = "";
//		json += "[{";
//		sjson += "[{";
//		for (int i = 1; i < 17; i++) {
//			String m = "M" + i;
//			String mm = "m" + i;
//			json += "\"" + m + "\":";
//			sjson += "\"" + mm + "\":";
//			for (Map<String, Object> map : alllist) {
//				if (map.get("key").equals(m)) {
//					String serviceTell = map.get("serviceTell") + "";
//					String sp_id = map.get("sp_id") + "";
//					json += "\"" + serviceTell + "\",";
//					sjson += "\"" + sp_id + "\",";
//				}
//			}
//		}
//		json = json.substring(0, json.length() - 1);
//		sjson = sjson.substring(0, sjson.length() - 1);
//		json += "}]";
//		sjson += "}]";
//
//		System.out.println(json);
//		
//		String queryOrder="select t.id from omp_old_order t where t.oldId="+old.getId();
//		List<Map<String,Object>> list = jdbcTemplate.queryForList(queryOrder);
//		String sql = "";
//		if(list.size()>0){
//			String  oid = list.get(0).get("id")+"";
//			 sql = "update omp_old_order set keyPointMessage = '"+json+"' ,k_and_sp_id = '"+sjson+"' where id="+oid ;
//		}else{
//			 sql = "INSERT INTO omp_old_order ("
//					+ "oldId, phoneName, communityOrderId, keyPointMessage,k_and_sp_id)"
//					+ "  select t.ID,t.TELTYPE,t.HOUSEHOLD_COMMUNITY_ID,'" + json
//					+ "','"+sjson+"' from omp_old_info t WHERE t.ID = " + old.getId();
//		}
//
//		jdbcTemplate.update(sql);
//	}










}
