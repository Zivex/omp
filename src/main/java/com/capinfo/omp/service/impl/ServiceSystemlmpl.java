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
import com.capinfo.omp.model.Sys_key;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.parameter.ServiceProviderParameter;
import com.capinfo.omp.parameter.ServiceSystemParameter;
import com.capinfo.omp.service.ServiceSystemService;
import com.capinfo.omp.util.Permissions;

@Service
public class ServiceSystemlmpl extends
		CommonsDataOperationServiceImpl<Enterprise, EnterpriseParameter>
		implements ServiceSystemService {

	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Override
	public List<Map<String, Object>> getQueryarchitecture(SystemUser user,
			Long stId) {
		String conditions = "";
		if ("m".equals(user.getAccount_type())
				|| "b".equals(user.getAccount_type())) {
			conditions += " and k.other=1";
		} else if ("g".equals(user.getAccount_type())) {
			conditions += " and k.government=1";
		}

		String sql = "select st.id, k.`key`,st.serviceName from omp_key k INNER JOIN omp_phone_type t on k.pyId = t.id INNER JOIN omp_service_type st on st.id = k.stId where k.pyId="
				+ stId + conditions;
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;

	}

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
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
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
			List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
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
		Sys_key entity = parameter.getEntity();		//获取体系
		entity.setCreateTime(new Date());	//设置时间
		if (rid != null && rid != 0) {
			entity.setCommunity_id(rid);	//判断是否微社区体系
		}
		if("g".equals(user.getAccount_type())){		//政府
			Long community_id = user.getRid();
			Long stree_id = user.getParentid();
			String regionSql = "select r.PARENTID from omp_region r where r.USE_FLAG=1 and r.id="+stree_id;
			Long county_id = JdbcTemplate.queryForLong(regionSql);
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
				+ parameter.getTelltype() + communitysql;
		int forInt = JdbcTemplate.queryForInt(sqlCheck);
		//if (entity.getId() != null) {	
		if (forInt > 0 && !"ADMIN".equals(user.getAccount_type()) ) {	//覆盖
			String getIdSql = "select s.id from sys_key s where s.user_falg=1 and s.uid="
					+ entity.getUid()
					+ "  and s.telltype_id= "
					+ parameter.getTelltype() +communitysql;
			long id = JdbcTemplate.queryForLong(getIdSql);
			entity.setId(id);
			entity.setUpdateTime(new Date());
		}
		getGeneralService().saveOrUpdate(entity);		//保存或修改
		Long sk_id = entity.getId();

		Map<String, Long> mapKeys = Permissions.mapKeys(parameter);
		List<Map<String, Object>> mList = getQueryarchitecture(user,
				parameter.getTelltype());
		
		for (Map<String, Object> map : mList) {
			New_Service_System ss = new New_Service_System();
			Long sid = mapKeys.get(map.get("key"));		//服务商id
			ss.setSkid(sk_id);	//设置skId
			ss.setSp_id(sid);	//设置服务商id
			String key_sql = " select k.id from omp_key k where k.pyId ="
					+ parameter.getTelltype() + " and k.`key` = '"
					+ map.get("key") + "'";
			Long kid = JdbcTemplate.queryForLong(key_sql);
			String	ssidcheckSql = "select count(*) from service_system t where t.key_state="+kid+" and t.skid="+sk_id;
			Long ssCount = JdbcTemplate.queryForLong(ssidcheckSql);
			if(ssCount>0){
				String ssidSql = "select t.id from service_system t where t.key_state="+kid+" and t.skid="+sk_id;	//修改
				Long ssid = JdbcTemplate.queryForLong(ssidSql);
				ss.setId(ssid);
			}
			ss.setKey_state(kid);
			getGeneralService().saveOrUpdate(ss);
			getGeneralService().clear();
			ss.setId(null);

		}
		getGeneralService().clear();
		entity.setId(null);
	}

	@Override
	public void updateServiceSystem(ServiceSystemParameter parameter,
			SystemUser user) {
		pubSavrSys(parameter, user, parameter.getEntity().getCommunity_id());
	}

	@Override
	public List<Map<String, Object>> getupdateSys(Sys_key ss) {
		SearchCriteriaBuilder<New_Service_System> searchCriteriaBuilder = new SearchCriteriaBuilder<New_Service_System>(
				New_Service_System.class);
		String sql = "select st.id ,k.`key` ,p.serviceName ,p.serviceTell,t.sp_id ,st.serviceName tname from service_system t LEFT JOIN omp_key k on t.key_state=k.id LEFT JOIN omp_service_type st on k.stId =st.id LEFT JOIN service_provider p on t.sp_id=p.id   where t.skid ="+ss.getId();
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;
	}

}
