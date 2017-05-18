package com.capinfo.omp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;
import com.capinfo.framework.web.service.impl.CommonsDataOperationServiceImpl;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.model.New_Service_System;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.Sys_key;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.parameter.Ksp_id;
import com.capinfo.omp.parameter.ServiceProviderParameter;
import com.capinfo.omp.parameter.ServiceSystemParameter;
import com.capinfo.omp.service.ServiceSystemService;
import com.capinfo.omp.util.FixedDialling;
import com.capinfo.omp.util.Permissions;

@Service
public class ServiceSystemlmpl extends
		CommonsDataOperationServiceImpl<Enterprise, EnterpriseParameter>
		implements ServiceSystemService {
	

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> serchService(
			ServiceProviderParameter parameter,SystemUser user) {
		Long serviceId = parameter.getServiceId();
		if(serviceId == 23L || serviceId==24L){
			serviceId =11L;
		}
		String serchserviceId = "";
		String serchserviceName = "";
		String serchstreeId = "";
		String sqlService = "";

		if ((!"".equals(parameter.getId())) && parameter.getId() != null
				&& parameter.getId() != 0) {
			sqlService = " and t.id =" + parameter.getId();
		}
		if (serviceId != 0) {
			serchserviceId = " and t.serviceTypeId ="
					+ serviceId;
		}
		if ((!"".equals(parameter.getServiceName()))
				&& parameter.getId() != null
				&& parameter.getServiceName() != null) {
			serchserviceName = " and t.serviceName like '%"
					+ parameter.getServiceName() + "%'";
		}
		if (parameter.getStreetId() != null && parameter.getStreetId() != 0) {
			serchstreeId = " and t.serviceStreet_id like '%"
					+ parameter.getStreetId() + "%'";
		}else{
			if("g".equals(user.getAccount_type()) && user.getLeave()==5){
				serchstreeId = " and t.serviceStreet_id like '%"
						+ user.getParentid()+ "%'";
			}
		}
		String sql = "select t.id,t.serviceName,t.serviceTell from service_provider t where t.user_falg=1 and t.verify=3"
				+ serchserviceId + serchserviceName + serchstreeId + sqlService;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;

	}

	@Override
	public void addServiceSystem(ServiceSystemParameter parameter,
			SystemUser user) {
		Sys_key entity = parameter.getEntity();
		Long telltype = parameter.getTelltype();
		entity.setCreateTime(new Date());
		entity.setUser_falg(1L);
		entity.setTelltype_id(telltype);
		entity.setUid(user.getId());
		if (parameter.getEntity().getCommunity_id() != null
				&& parameter.getEntity().getCommunity_id() == 0
				&& parameter.getEntity().getStreet_id() != 0) {
			String sql = "select r.id from omp_region r where r.USE_FLAG = 1 and r.PARENTID="
					+ parameter.getEntity().getStreet_id();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			// 批量
			for (Map<String, Object> map : list) {
				String rid = map.get("id") + ""; // 社区id
				pubSavrSys(parameter, user, Long.parseLong(rid));
			}
		} else {
			pubSavrSys(parameter, user, 0L);
		}

	}

	@Override
	public HashMap<String, Object> getSSList(SystemUser user,
			ServiceSystemParameter parameter) {
		SearchCriteriaBuilder<Sys_key> searchCriteriaBuilder = new SearchCriteriaBuilder<Sys_key>(
				Sys_key.class);
		SearchCriteriaBuilder<SystemUser> old = new SearchCriteriaBuilder<SystemUser>(
				SystemUser.class);
		String sql = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (!("ADMIN".equals(user.getAccount_type())|| user.getLeave() == 2)) {

			if (user.getLeave() > 1 ) {
				if ("g".equals(user.getAccount_type())) {
					Long rid = user.getRid();
					Long id = user.getId();
					searchCriteriaBuilder.addQueryCondition("uid",
							RestrictionExpression.NOT_EQUALS_OP, 1);
					String regionId = "";
					if (user.getLeave() == 3) {
						regionId = "county_id";
					} else if (user.getLeave() == 4) {
						regionId = "street_id";
					} else if (user.getLeave() == 5) {
						regionId = "community_id";
					}
					searchCriteriaBuilder.addQueryCondition(regionId,
							RestrictionExpression.EQUALS_OP, rid);
				} else if ("m".equals(user.getAccount_type())
						|| "b".equals(user.getAccount_type())) {
					String ji = "";
					Integer uJi = 0;
					switch (user.getLeave()) {
					case 2:
						ji = "yiji";
						break;
					case 3:
						ji = "erji";
						break;
					case 4:
						ji = "sjji";
						break;
					case 5:
						ji = "siji";
						break;
					}
					old.addQueryCondition(ji, RestrictionExpression.EQUALS_OP,
							user.getJi());
					List<SystemUser> userList = getGeneralService().getObjects(
							old.build());
					String oids = "";
					for (SystemUser systemUser : userList) {
						oids += systemUser.getId() + ",";
						System.out.println(oids);
					}
					sql = " this_.uid in ("
							+ oids.substring(0, oids.length() - 1) + ")";
					if (!"".equals(sql)) {
						searchCriteriaBuilder.addAdditionalRestrictionSql(sql);
					}
				}

			}
		}
		searchCriteriaBuilder.addQueryCondition("user_falg",
				RestrictionExpression.EQUALS_OP, 1L);
		searchCriteriaBuilder.addLimitCondition(
				(parameter.getCurrentPieceNum() - 1)
						* parameter.getPerPieceSize(),
				parameter.getPerPieceSize());

		int count = getGeneralService().getCount(searchCriteriaBuilder.build());
		List<Sys_key> list = getGeneralService().getObjects(
				searchCriteriaBuilder.build());
		map.put("count", count);
		map.put("list", list);
		return map;
	}

	/**
	 * 保存服务体系
	 * 政府:判断 ==>> 社区 用户 user_falg 话机类型(telltype_id)
	 * @param parameter
	 * @param user
	 * @param rid
	 */
	@SuppressWarnings("deprecation")
	public void pubSavrSys(ServiceSystemParameter parameter, SystemUser user,
			Long rid) {
		
		System.out.println("初始化服务体系");
		Sys_key entity = parameter.getEntity();		//获取体系
		entity.setCreateTime(new Date());	//设置时间
		if (rid != null && rid != 0) {
			entity.setCommunity_id(rid);	//判断是否批量创建
		}
		if("g".equals(user.getAccount_type())){		//政府
			Long community_id = user.getRid();
			Long stree_id = user.getParentid();
			String regionSql = "select r.PARENTID from omp_region r where r.USE_FLAG=1 and r.id="+stree_id;
			Long county_id = jdbcTemplate.queryForLong(regionSql);
			entity.setCounty_id(county_id);
			entity.setStreet_id(stree_id);
			entity.setCommunity_id(community_id);
		}
		String communitysql = "";
		if(entity.getCommunity_id()!=null && !"g".equals(user.getAccount_type()) && !"ADMIN".equals(user.getAccount_type()) ){
			communitysql = " and s.community_id =" + entity.getCommunity_id();
		}
		String sqlCheck = "select count(*) from sys_key s where s.user_falg=1 and s.uid="
				+ entity.getUid()
				+ "  and s.telltype_id= "
				+ parameter.getTelltype() + communitysql;	//查重
		int forInt = jdbcTemplate.queryForInt(sqlCheck);
		if (forInt > 0 && !"ADMIN".equals(user.getAccount_type()) ) {	//覆盖
			String getIdSql = "select s.id from sys_key s where s.user_falg=1 and s.uid="
					+ entity.getUid()
					+ "  and s.telltype_id= "
					+ parameter.getTelltype() +communitysql;
			long id = jdbcTemplate.queryForLong(getIdSql);
			entity.setId(id);
			entity.setUpdateTime(new Date());
		}
		
		getGeneralService().saveOrUpdate(entity);		//保存或修改
		Long sk_id = entity.getId();

		Map<String, Long> mapKeys = Permissions.mapKeys(parameter);
		List<Map<String, Object>> mList = Permissions.getAllChitecture(user, parameter.getTelltype(), jdbcTemplate);
		
		
		for (Map<String, Object> map : mList) {
			New_Service_System ss = new New_Service_System();
			String key = map.get("key")+""; 	//获取键位
			Long sid = mapKeys.get(key);		//前台获取的服务商id
			if(!"g".equals(user.getAccount_type()) && user.getLeave()>1){	//农商行或其它商户 不匹配居委会(M9) 卫生站(M10)
				if("M9".equals(key) || "M10".equals(key)){
					continue ;
				}else if("M11".equals(key)){
					sid = FixedDialling.M11SP;
				}if("M13".equals(key)){
					sid = FixedDialling.M13SP;
				}if("M14".equals(key)){
					sid = FixedDialling.M14SP;
				}if("M15".equals(key)){
					sid = FixedDialling.M15SP;
				}if("M16".equals(key)){
					sid = FixedDialling.M16SP;
				}
			}else if("g".equals(user.getAccount_type()) && (sid==null || sid ==0)){		//从公共服务体系里面获取数据(政府)
				String gSql = "SELECT ss.sp_id FROM service_system ss LEFT JOIN sys_key sk ON ss.skid = sk.id LEFT JOIN omp_key k ON ss.key_state = k.id WHERE 1 = 1 AND sk.community_id = "+entity.getCommunity_id()+" AND k.`key` = '"
					+ map.get("key") + "' AND sk.telltype_id = "+entity.getTelltype_id()+" AND sk.user_falg = 1 and sk.uid = 1 ORDER BY sk.createTime DESC";
				List<Map<String,Object>> list = jdbcTemplate.queryForList(gSql);
				if(list.size()>0){
					Map<String, Object> sk_id_Map = list.get(0);
					sid = Long.parseLong(sk_id_Map.get("sp_id")+"");
				}else{
					System.out.println("公共服务体系不存在");
					return ;
				}
			}
			ss.setSkid(sk_id);	//设置skId
			ss.setSp_id(sid);	//设置服务商id
			String key_sql = " select k.id from omp_key k where k.pyId ="
					+ parameter.getTelltype() + " and k.`key` = '"
					+ map.get("key") + "'";
			
			Long kid = jdbcTemplate.queryForLong(key_sql);
			ss.setKey_state(kid);
			
			String	ssidcheckSql = "select count(*) from service_system t where t.key_state="+kid+" and t.skid="+sk_id;	//查重
			Long ssCount = jdbcTemplate.queryForLong(ssidcheckSql);
			if(ssCount>0){
				String ssidSql = "select t.id from service_system t where t.key_state="+kid+" and t.skid="+sk_id;	//修改
				Long ssid = jdbcTemplate.queryForLong(ssidSql);
				ss.setId(ssid);
			}
			getGeneralService().saveOrUpdate(ss);
			getGeneralService().clear();
			ss.setId(null);

		}
		getGeneralService().clear();
		entity.setId(null);
		if(user.getLeave()==1){
			return ;
		}
		
		//同步
		// 1. 获取用户的老人
		SearchCriteriaBuilder<Omp_Old_Info> searchCriteriaBuilder = new SearchCriteriaBuilder<Omp_Old_Info>(
				Omp_Old_Info.class);
		String gSql = "";
		if (user.getLeave() > 1) {
			if ("g".equals(user.getAccount_type())) {
				String rname = "";
				switch (user.getLeave()) {
				case 3:
					rname = "household_county_id";
					break;
				case 4:
					rname = "household_street_id";
					break;
				case 5:
					rname = "household_community_id";
					break;
				}
				if (!rname.isEmpty()) {
					searchCriteriaBuilder.addQueryCondition(rname,
							RestrictionExpression.EQUALS_OP, user.getRid());
				}
			} else if ("m".equals(user.getAccount_type())
					|| "b".equals(user.getAccount_type())) {
				String ji = "";
				Integer uJi = 0;
				switch (user.getLeave()) {
				case 1:
					String idSsql = "select t.id from composition t where t.prient_id is null and t.cid="
							+ user.getId();
					int id = jdbcTemplate.queryForInt(idSsql);
					ji = "yiji";
					uJi = id;
					break;
				case 2:
					ji = "yiji";
					uJi = user.getYiji();
					break;
				case 3:
					ji = "erji";
					uJi = user.getErji();
					break;
				case 4:
					ji = "sjji";
					uJi = user.getSjji();
					break;
				case 5:
					ji = "siji";
					uJi = user.getSiji();
					break;
				}
				searchCriteriaBuilder.addQueryCondition(ji,
						RestrictionExpression.EQUALS_OP, uJi);
			}
		}
		String sql = "";
		// sql +=
		// "and this_.CREATE_CARD_SUCCESS = 1 and this_.has_pushed = 1 and this_.residence_county_id !=31381";

		if (!"".equals(gSql)) {
			searchCriteriaBuilder.addAdditionalRestrictionSql(gSql);
		}

		List<Omp_Old_Info> oldList = getGeneralService().getObjects(
				searchCriteriaBuilder.build());
		for (Omp_Old_Info omp_Old_Info : oldList) {
			Permissions.addOmpOldOrderInfo(omp_Old_Info, jdbcTemplate);
		}
		
	}

	/***
	 * 修改服务体系
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void updateServiceSystem(ServiceSystemParameter parameter,
			SystemUser user) {
		System.out.println("服务体系修改");
		Sys_key entity = parameter.getEntity();
		entity.setUpdateTime(new Date());	
		getGeneralService().saveOrUpdate(entity);		//修改ss
		Long sk_id = entity.getId();

		Map<String, Long> mapKeys = Permissions.mapKeys(parameter);
		List<Map<String, Object>> mList = Permissions.getAllChitecture(user, parameter.getTelltype(), jdbcTemplate);
		
		
		for (Map<String, Object> map : mList) {
			New_Service_System ss = new New_Service_System();
			String key = map.get("key")+"";
			Long sid = mapKeys.get(key);		//新服务商id
			if(sid !=null && sid !=0){
				String sssql = "select ss.id  from service_system ss INNER JOIN  omp_key ok on ss.key_state = ok.id where 1=1 and ok.key='"+key+"' and ss.skid="+sk_id;
				int ssid = jdbcTemplate.queryForInt(sssql);
				String updatess = "update service_system set sp_id="+sid+" where id="+ssid;
				int i = jdbcTemplate.update(updatess);
				if(i>0)
					System.out.println("服务体系"+ssid+"修改成功为新服务商"+sid);
			}
				
				
				
//			if(!"g".equals(user.getAccount_type()) && user.getLeave()>1){	//农商行或其它商户 不匹配居委会(M9) 卫生站(M10)
//				if("M9".equals(key) || "M10".equals(key)){
//					continue ;
//				}else if("M11".equals(key)){
//					sid = FixedDialling.M11SP;
//				}if("M13".equals(key)){
//					sid = FixedDialling.M13SP;
//				}if("M14".equals(key)){
//					sid = FixedDialling.M14SP;
//				}if("M15".equals(key)){
//					sid = FixedDialling.M15SP;
//				}if("M16".equals(key)){
//					sid = FixedDialling.M16SP;
//				}
//			}if("g".equals(user.getAccount_type()) && (sid==null || sid ==0)){		//政府配置公共服务体系
//				String gSql = "SELECT ss.sp_id FROM service_system ss LEFT JOIN sys_key sk ON ss.skid = sk.id LEFT JOIN omp_key k ON ss.key_state = k.id WHERE 1 = 1 AND sk.community_id = "+entity.getCommunity_id()+" AND k.`key` = '"
//					+ map.get("key") + "' AND sk.telltype_id = "+entity.getTelltype_id()+" AND sk.user_falg = 1 and sk.uid = 1 ORDER BY sk.createTime DESC";
//				List<Map<String,Object>> list = jdbcTemplate.queryForList(gSql);
//				if(list.size()>0){
//					Map<String, Object> sk_id_Map = list.get(0);
//					sid = Long.parseLong(sk_id_Map.get("sp_id")+"");
//				}else{
//					System.out.println("服务体系不存在");
//				}
//			}
//			ss.setSkid(sk_id);	//设置skId
//			ss.setSp_id(sid);	//设置服务商id
//			String key_sql = " select k.id from omp_key k where k.pyId ="
//					+ parameter.getTelltype() + " and k.`key` = '"
//					+ map.get("key") + "'";
//			Long kid = jdbcTemplate.queryForLong(key_sql);
//			String	ssidcheckSql = "select count(*) from service_system t where t.key_state="+kid+" and t.skid="+sk_id;
//			Long ssCount = jdbcTemplate.queryForLong(ssidcheckSql);
//			if(ssCount>0){
//				String ssidSql = "select t.id from service_system t where t.key_state="+kid+" and t.skid="+sk_id;	//修改
//				Long ssid = jdbcTemplate.queryForLong(ssidSql);
//				ss.setId(ssid);
//			}
//			ss.setKey_state(kid);
//			getGeneralService().saveOrUpdate(ss);
//			getGeneralService().clear();
//			ss.setId(null);

		}
//		getGeneralService().clear();
//		entity.setId(null);
		
		
		//同步
		// 1. 获取用户的老人
		SearchCriteriaBuilder<Omp_Old_Info> searchCriteriaBuilder = new SearchCriteriaBuilder<Omp_Old_Info>(
				Omp_Old_Info.class);
		String gSql = "";
		if (user.getLeave() > 1) {
			if ("g".equals(user.getAccount_type())) {
				String rname = "";
				switch (user.getLeave()) {
				case 3:
					rname = "household_county_id";
					break;
				case 4:
					rname = "household_street_id";
					break;
				case 5:
					rname = "household_community_id";
					break;
				}
				if (!rname.isEmpty()) {
					searchCriteriaBuilder.addQueryCondition(rname,
							RestrictionExpression.EQUALS_OP, user.getRid());
				}
			} else if ("m".equals(user.getAccount_type())
					|| "b".equals(user.getAccount_type())) {
				String ji = "";
				Integer uJi = 0;
				switch (user.getLeave()) {
				case 1:
					String idSsql = "select t.id from composition t where t.prient_id is null and t.cid="
							+ user.getId();
					int id = jdbcTemplate.queryForInt(idSsql);
					ji = "yiji";
					uJi = id;
					break;
				case 2:
					ji = "yiji";
					uJi = user.getYiji();
					break;
				case 3:
					ji = "erji";
					uJi = user.getErji();
					break;
				case 4:
					ji = "sjji";
					uJi = user.getSjji();
					break;
				case 5:
					ji = "siji";
					uJi = user.getSiji();
					break;
				}
				searchCriteriaBuilder.addQueryCondition(ji,
						RestrictionExpression.EQUALS_OP, uJi);
			}
		}
		String sql = "";
		// sql +=
		// "and this_.CREATE_CARD_SUCCESS = 1 and this_.has_pushed = 1 and this_.residence_county_id !=31381";

		if (!"".equals(gSql)) {
			searchCriteriaBuilder.addAdditionalRestrictionSql(gSql);
		}

		List<Omp_Old_Info> oldList = getGeneralService().getObjects(
				searchCriteriaBuilder.build());
		for (Omp_Old_Info omp_Old_Info : oldList) {
			Permissions.addOmpOldOrderInfo(omp_Old_Info, jdbcTemplate);
		}
		
	}

	@Override
	public List<Map<String, Object>> getupdateSys(Sys_key ss) {
		String sql = "select st.id ,k.`key` ,p.serviceName ,p.serviceTell,t.sp_id ,st.serviceName tname from service_system t LEFT JOIN omp_key k on t.key_state=k.id LEFT JOIN omp_service_type st on k.stId =st.id LEFT JOIN service_provider p on t.sp_id=p.id   where t.skid ="+ss.getId();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

}
