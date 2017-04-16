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
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.framework.web.service.impl.CommonsDataOperationServiceImpl;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.model.New_Service_System;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.Service_System;
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

		String sql = "select k.id, k.`key`,st.serviceName from omp_key k INNER JOIN omp_phone_type t on k.pyId = t.id INNER JOIN omp_service_type st on st.id = k.stId where k.pyId="
				+ stId + conditions;
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;

	}

	@Override
	public List<Map<String, Object>> serchService(
			ServiceProviderParameter parameter) {
		String serchserviceId = "";
		String serchserviceName = "";
		String serchstreeId = "";
		String sqlService = "";

		if ((!"".equals(parameter.getId())) && parameter.getId() != null
				&& parameter.getId() != 0) {
			sqlService = " and t.id =" + parameter.getId();
		}
		if (parameter.getServiceId() != 0) {
			serchserviceId = " and t.serviceTypeId ="
					+ parameter.getServiceId();
		}
		if ((!"".equals(parameter.getServiceName()))
				&& parameter.getId() != null
				&& parameter.getServiceName() != null) {
			serchserviceName = " and t.serviceName like '%"
					+ parameter.getServiceName() + "%'";
		}
		if (parameter.getStreetId() != 0) {
			serchstreeId = " and t.serviceStreet_id like '%"
					+ parameter.getStreetId() + "%'";
		}
		String sql = "select t.id,t.serviceName,t.serviceTell from service_provider t where 1=1 "
				+ serchserviceId + serchserviceName + serchstreeId + sqlService;
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;

	}

	@SuppressWarnings("deprecation")
	@Override
	public void addServiceSystem(ServiceSystemParameter parameter,
			SystemUser user) {
		Sys_key entity = parameter.getEntity();

		// Long city = parameter.getCity();
		// Long county = parameter.getCounty();
		// Long street = parameter.getStreet();
		// Long community = parameter.getCommunity();
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
				pubSavrSys(parameter, user, rid);
				//
				// entity.setCommunity_id(Long.parseLong(rid));
				// @SuppressWarnings("unchecked")
				// Map<String, Long> mapKey = parameter.getMapKey();
				// for (Map.Entry<String, Long> entry : mapKey.entrySet()) {
				// List<Map<String,Object>> mList = getQueryarchitecture(user,
				// parameter.getTelltype());
				//
				// System.out.println("Key = " + entry.getKey() + ", Value = " +
				// entry.getValue());
				//
				// }
				//
				//
				// String sqlCheck =
				// "select count(*) from service_system s where s.user_falg=1 and  s.rid="+rid+" and s.tellType_id= "+telltype;
				// int forInt = JdbcTemplate.queryForInt(sqlCheck);
				// if(forInt>0){
				// String getIdSql =
				// "select s.id from service_system s where s.user_falg=1 and  s.rid="+rid+" and s.tellType_id= "+telltype;
				// long id = JdbcTemplate.queryForLong(getIdSql);
				// entity.setId(id);
				// }
				// GeneralService service = getGeneralService();
				// service.saveOrUpdate(entity);
				// service.clear();
				// entity.setId(null);
			}
		} else {

			pubSavrSys(parameter, user, "0");

			// entity.setRid((long) community); //社区id

			// String sqlCheck =
			// "select count(*) from service_system s where s.user_falg=1 and s.uid="+user.getId()+" and  s.rid="+community+" and s.tellType_id= "+telltype;
			// int forInt = JdbcTemplate.queryForInt(sqlCheck);
			// if(forInt>0){
			// String getIdSql =
			// "select s.id from service_system s where s.user_falg=1 and s.uid="+user.getId()+" and  s.rid="+community+" and s.tellType_id= "+telltype;
			// long id = JdbcTemplate.queryForLong(getIdSql);
			// entity.setId(id);
			// }
			// if("g".equals(user.getAccount_type())){
			// Long m11 = entity.getM11();
			// entity.setM11(null);
			// entity.setM12(m11);
			// }else if("m".equals(user.getAccount_type()) ||
			// "b".equals(user.getAccount_type())){
			// Long m10 = entity.getM9();
			// entity.setM9(null);
			// entity.setM10(m10);
			// }

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
		if (!"ADMIN".equals(user.getAccount_type())) {

			if (user.getLeave() > 1) {
				if ("g".equals(user.getAccount_type())) {
					Long rid = user.getRid();
					Long id = user.getId();
					searchCriteriaBuilder.addQueryCondition("uid",
							RestrictionExpression.EQUALS_OP, id);
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

	// @Override
	// public void updateServiceSystem(ServiceSystemParameter parameter,
	// SystemUser user) {
	// Service_System entity = parameter.getEntity();
	// entity.setUpdateTime(new Date());
	// getGeneralService().saveOrUpdate(entity);
	//
	// }

	// 保存服务体系
	@SuppressWarnings("deprecation")
	public void pubSavrSys(ServiceSystemParameter parameter, SystemUser user,
			String rid) {
		Sys_key entity = parameter.getEntity();
		entity.setCreateTime(new Date());
		if (!"0".equals(rid)) {
			entity.setCommunity_id(Long.parseLong(rid));
		}
		String sqlCheck = "select count(*) from sys_key s where s.user_falg=1 and s.uid="
				+ user.getId()
				+ "  and s.telltype_id= "
				+ parameter.getTelltype() + " and s.community_id =" + rid;
		int forInt = JdbcTemplate.queryForInt(sqlCheck);
		if (forInt > 0) {
			String getIdSql = "select s.id from sys_key s where s.user_falg=1 and s.uid="
					+ user.getId()
					+ "  and s.telltype_id= "
					+ parameter.getTelltype() + " and s.community_id =" + rid;
			long id = JdbcTemplate.queryForLong(getIdSql);
			entity.setId(id);
			entity.setUpdateTime(new Date());
		}
		getGeneralService().saveOrUpdate(entity);
		Long sk_id = entity.getId();

		Map<String, Long> mapKeys = Permissions.mapKeys(parameter);
		List<Map<String, Object>> mList = getQueryarchitecture(user,
				parameter.getTelltype());
		for (Map<String, Object> map : mList) {
			New_Service_System ss = new New_Service_System();
			Long sid = mapKeys.get(map.get("key"));
			if (sid == 0) {
				sid = null;
			}
			ss.setSkid(sk_id);
			ss.setSp_id(sid);
			String key_sql = " select k.id from omp_key k where k.pyId ="
					+ parameter.getTelltype() + " and k.`key` = '"
					+ map.get("key") + "'";
			Long kid = JdbcTemplate.queryForLong(key_sql);
			ss.setKey_state(kid);
			getGeneralService().saveOrUpdate(ss);
			getGeneralService().clear();
			ss.setId(null);

		}
		getGeneralService().saveOrUpdate(entity);
		getGeneralService().clear();
		entity.setId(null);
	}

	@Override
	public void updateServiceSystem(ServiceSystemParameter parameter,
			SystemUser user) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<New_Service_System> getupdateSys(Sys_key ss) {
		SearchCriteriaBuilder<New_Service_System> searchCriteriaBuilder = new SearchCriteriaBuilder<New_Service_System>(
				New_Service_System.class);
		searchCriteriaBuilder.addQueryCondition("skid", RestrictionExpression.EQUALS_OP,ss.getId());

		List<New_Service_System> list = getGeneralService().getObjects(
				searchCriteriaBuilder.build());

//		String sql = "select t.id,t.name from service_system t where t.skid ="+ss.getId();
//		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;
	}

}
