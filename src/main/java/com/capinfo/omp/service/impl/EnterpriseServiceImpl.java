package com.capinfo.omp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;
import com.capinfo.framework.web.service.impl.CommonsDataOperationServiceImpl;
import com.capinfo.omp.model.Composition;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.service.EnterpriseService;


@Service
public class EnterpriseServiceImpl extends
		CommonsDataOperationServiceImpl<Enterprise, EnterpriseParameter>
		implements EnterpriseService {


	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	
	
	@Override
	public List<Enterprise> getListByName(EnterpriseParameter parameter) {
		SearchCriteriaBuilder<Enterprise> searchCriteriaBuilder = new SearchCriteriaBuilder<Enterprise>(
				Enterprise.class);
		SearchCriteriaBuilder<Enterprise> builder = searchCriteriaBuilder.addQueryCondition("type", RestrictionExpression.EQUALS_OP,parameter.getType());
		List<Enterprise> list = getGeneralService().getObjects(builder.build());
		return list;
	}



	@Override
	public List<Composition> getListByid(int uid,Integer lv,Integer upId) {
		SearchCriteriaBuilder<Composition> searchCriteriaBuilder = new SearchCriteriaBuilder<Composition>(
				Composition.class);
		searchCriteriaBuilder.addQueryCondition("cid", RestrictionExpression.EQUALS_OP,uid);
		if(lv != null && lv !=0){
		searchCriteriaBuilder.addQueryCondition("levelid", RestrictionExpression.EQUALS_OP,lv);
		}
		if(upId != null && upId !=0){
			searchCriteriaBuilder.addQueryCondition("prient_id", RestrictionExpression.EQUALS_OP,upId);
		}
		
		List<Composition> list = getGeneralService().getObjects(searchCriteriaBuilder.build());
		return list;
	}



	@SuppressWarnings("deprecation")
	@Override
	public Long getServiceTypeId(String serviceType) {
		String sql = "select t.id from omp_service_type t where t.serviceName = '"+serviceType+"'";
		long id = JdbcTemplate.queryForLong(sql);
		return id;
	}



	@Override
	public String getRegionId(String city, int i,String pid) {
		String psql = "";
		if(!"0".equals(pid)){
			psql= " and PARENTID = "+pid;
		}
		String sql = "select t.id from omp_region t where t.name = '"+city+"' and levelid=0"+i+psql+" and USE_FLAG = 1";
		@SuppressWarnings("deprecation")
		long id = JdbcTemplate.queryForLong(sql);
		return String.valueOf(id);
	}

}
