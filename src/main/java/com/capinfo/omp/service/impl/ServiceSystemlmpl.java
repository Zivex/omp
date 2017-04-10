package com.capinfo.omp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.web.service.impl.CommonsDataOperationServiceImpl;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.model.Service_System;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.parameter.ServiceProviderParameter;
import com.capinfo.omp.parameter.ServiceSystemParameter;
import com.capinfo.omp.service.ServiceSystemService;

@Service
public class ServiceSystemlmpl extends
		CommonsDataOperationServiceImpl<Enterprise, EnterpriseParameter>
		implements ServiceSystemService {

	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Override
	public List<Map<String, Object>> getQueryarchitecture(int stId) {
		String sql = "select k.id, k.`key`,st.serviceName from omp_key k INNER JOIN omp_phone_type t on k.pyId = t.id INNER JOIN omp_service_type st on st.id = k.stId where k.pyId="+stId;
		List<Map<String,Object>> list = JdbcTemplate.queryForList(sql);
		return list;


	}


	@Override
	public List<Map<String, Object>> serchService(ServiceProviderParameter parameter) {
		String serchserviceId = "";
		String serchserviceName = "";
		String serchstreeId = "";

		if(parameter.getServiceId()!=0){
			serchserviceId=" and t.serviceTypeId ="+parameter.getServiceId();
		}
		if(!"".equals(parameter.getServiceName())){
			serchserviceName=" and t.serviceName like '%"+parameter.getServiceName()+"%'";
		}
		if(parameter.getStreetId()!=0){
			serchstreeId=" and t.serviceStreet_id like '%"+parameter.getStreetId()+"%'";
		}
		String sql = "select t.id,t.serviceName,t.serviceTell from service_provider t where 1=1 "+serchserviceId+serchserviceName+serchstreeId;
		List<Map<String,Object>> list = JdbcTemplate.queryForList(sql);
		return list;

	}


	@SuppressWarnings("deprecation")
	@Override
	public void addServiceSystem(ServiceSystemParameter parameter, SystemUser user) {
		Service_System entity = parameter.getEntity();
		int city = parameter.getCity();
		int county = parameter.getCounty();
		int street = parameter.getStreet();
		int community = parameter.getCommunity();
		int telltype = parameter.getTelltype();
		entity.setCreateTime(new Date());
		entity.setUser_falg(1);
		entity.setTelltype(telltype);
		entity.setUid(user.getId());
		Service_System entityT = new Service_System(); 
		entityT = entity;
		if(community==0){
			String sql = "select r.id from omp_region r where r.PARENTID="+street;
			List<Map<String,Object>> list = JdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : list) {
				entity = entityT;
				String rid =  map.get("id")+"";
				entity.setRid(Long.parseLong(rid));
				String sqlCheck = "select count(*) from service_system s where s.user_falg=1 and  s.rid="+rid+" and s.tellType_id= "+telltype;
				int forInt = JdbcTemplate.queryForInt(sqlCheck);
				if(forInt>0){
					String getIdSql = "select s.id from service_system s where s.user_falg=1 and  s.rid="+rid+" and s.tellType_id= "+telltype;
					long id = JdbcTemplate.queryForLong(getIdSql);
					entity.setId(id);
				}
				getGeneralService().saveOrUpdate(entity);
				entity=null;
			}
		}else{
			entity.setRid((long) community);
			getGeneralService().saveOrUpdate(entity);
		}
		
		
		
	}
	
	public String sqlAndSql (){
		String sql="";
		
		return sql;
	}

	


}
