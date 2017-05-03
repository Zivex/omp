package com.capinfo.omp.service.impl;

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
import com.capinfo.framework.web.service.impl.CommonsDataOperationServiceImpl;
import com.capinfo.omp.model.Composition;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.model.ServiceProvider;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.parameter.ServiceProviderParameter;
import com.capinfo.omp.service.EnterpriseService;
import com.capinfo.omp.utils.Page;
import com.capinfo.region.model.OmpRegion;

@Service
public class EnterpriseServiceImpl extends
		CommonsDataOperationServiceImpl<Enterprise, EnterpriseParameter>
		implements EnterpriseService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Enterprise> getListByName(EnterpriseParameter parameter) {
		SearchCriteriaBuilder<Enterprise> searchCriteriaBuilder = new SearchCriteriaBuilder<Enterprise>(
				Enterprise.class);
		SearchCriteriaBuilder<Enterprise> builder = searchCriteriaBuilder
				.addQueryCondition("type", RestrictionExpression.EQUALS_OP,
						parameter.getType());
		List<Enterprise> list = getGeneralService().getObjects(builder.build());
		return list;
	}

	@Override
	public List<Composition> getListByid(int uid, Integer lv, Integer upId) {
		SearchCriteriaBuilder<Composition> searchCriteriaBuilder = new SearchCriteriaBuilder<Composition>(
				Composition.class);
		searchCriteriaBuilder.addQueryCondition("cid",
				RestrictionExpression.EQUALS_OP, uid);
		if (lv != null && lv != 0) {
			searchCriteriaBuilder.addQueryCondition("levelid",
					RestrictionExpression.EQUALS_OP, lv);
		}
		if (upId != null && upId != 0) {
			searchCriteriaBuilder.addQueryCondition("prient_id",
					RestrictionExpression.EQUALS_OP, upId);
		}

		List<Composition> list = getGeneralService().getObjects(
				searchCriteriaBuilder.build());
		return list;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Long getServiceTypeId(String serviceType) {
		long id = 0L;
		String checksql = "select count(*) from omp_service_type t where t.serviceName = '"
				+ serviceType + "'";
		int check = jdbcTemplate.queryForInt(checksql);
		if (check > 0) {
			String sql = "select t.id from omp_service_type t where t.serviceName = '"
					+ serviceType + "'";
			id = jdbcTemplate.queryForLong(sql);
		}
		return id;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getRegionId(String city, int i, String pid) {
		String psql = "";
		String sql = "";
		long id = 0L;
		if (!"0".equals(pid)) {
			psql = " and PARENTID = " + pid;
		}
		String checksql = "select count(*)from omp_region t where t.name = '"
				+ city + "' and levelid=0" + i + psql + " and USE_FLAG = 1";
		int check = jdbcTemplate.queryForInt(checksql);
		if (check > 0) {
			sql = "select t.id from omp_region t where t.name = '" + city
					+ "' and levelid=0" + i + psql + " and USE_FLAG = 1";
			id = jdbcTemplate.queryForLong(sql);
		}
		return String.valueOf(id);
	}

	@Override
	public Map<String, Object> getMerchantsList(
			ServiceProviderParameter parameter,SystemUser user) {
		Map<String, Object> map = new HashMap<String, Object>();
		SearchCriteriaBuilder<ServiceProvider> searchCriteriaBuilder = new SearchCriteriaBuilder<ServiceProvider>(
				ServiceProvider.class);
		if (parameter.getEntity().getServiceTypeId() != null  && parameter.getEntity().getServiceTypeId() != 0L ) {
			searchCriteriaBuilder.addQueryCondition("serviceTypeId", RestrictionExpression.EQUALS_OP, parameter.getEntity().getServiceTypeId());
		}
		if (parameter.getEntity().getVerify() != 0) {
			searchCriteriaBuilder.addQueryCondition("verify", RestrictionExpression.EQUALS_OP, parameter.getEntity().getVerify());
		}

		searchCriteriaBuilder.addQueryCondition("serviceName", RestrictionExpression.LIKE_OP, parameter.getEntity().getServiceName());
		searchCriteriaBuilder.addQueryCondition("serviceTell", RestrictionExpression.EQUALS_OP, parameter.getEntity().getServiceTell());
		searchCriteriaBuilder.addQueryCondition("contact", RestrictionExpression.EQUALS_OP, parameter.getEntity().getContact());
		searchCriteriaBuilder.addQueryCondition("contactPhone", RestrictionExpression.EQUALS_OP, parameter.getEntity().getContactPhone());

		searchCriteriaBuilder.addQueryCondition("serviceCounty_id", RestrictionExpression.LIKE_OP, parameter.getCounty());
		searchCriteriaBuilder.addQueryCondition("serviceStreet_id", RestrictionExpression.LIKE_OP, parameter.getStreet());
		searchCriteriaBuilder.addQueryCondition("serviceCommunity_id", RestrictionExpression.LIKE_OP, parameter.getCommunity());
		searchCriteriaBuilder.addQueryCondition("user_falg", RestrictionExpression.EQUALS_OP, 1L);
		if(parameter.getG()!=null&&parameter.getG()==1 && user.getLeave() > 2){
			String serviceRegion = "";
			Long rid = 0L;
			if(user.getLeave()==3){
				serviceRegion = "serviceCounty_id";
				rid = user.getRid();
			}else if (user.getLeave()==4){
				serviceRegion = "serviceStreet_id";
				rid = user.getRid();
			}else if (user.getLeave()==5){
				serviceRegion = "serviceStreet_id";
				rid = user.getParentid();
			}
			searchCriteriaBuilder.addQueryCondition(serviceRegion, RestrictionExpression.EQUALS_OP, rid);
		}
		int count = getGeneralService().getCount(searchCriteriaBuilder.build());
		map.put("count", count);
		if (parameter.getPageSize() == 0) {
			parameter.setPageSize(10);
		}
		searchCriteriaBuilder.addLimitCondition((parameter.getCurrent() - 1)* parameter.getPageSize(), parameter.getPageSize());
		List<ServiceProvider> list = getGeneralService().getObjects(searchCriteriaBuilder.build());
		map.put("list", list);
		return map;
	}

	@Override
	public int getMerchantsCount(ServiceProviderParameter parameter) {
		SearchCriteriaBuilder<ServiceProvider> searchCriteriaBuilder = new SearchCriteriaBuilder<ServiceProvider>(
				ServiceProvider.class);
		searchCriteriaBuilder.addQueryCondition("serviceName",
				RestrictionExpression.LIKE_OP, parameter.getEntity()
						.getServiceName());
		searchCriteriaBuilder.addQueryCondition("serviceTell",
				RestrictionExpression.EQUALS_OP, parameter.getEntity()
						.getServiceTell());
		searchCriteriaBuilder.addQueryCondition("serviceTypeId",
				RestrictionExpression.EQUALS_OP, parameter.getEntity()
						.getServiceTypeId());
		searchCriteriaBuilder.addQueryCondition("verify",
				RestrictionExpression.EQUALS_OP, parameter.getEntity()
						.getVerify());
		searchCriteriaBuilder.addQueryCondition("contact",
				RestrictionExpression.EQUALS_OP, parameter.getEntity()
						.getContact());
		searchCriteriaBuilder.addQueryCondition("contactPhone",
				RestrictionExpression.EQUALS_OP, parameter.getEntity()
						.getContactPhone());

		int count = getGeneralService().getCount(searchCriteriaBuilder.build());

		return count;
	}

	@Override
	public List<OmpRegion> queryRegions(String ids) {
		SearchCriteriaBuilder<OmpRegion> searchCriteriaBuilder = new SearchCriteriaBuilder<OmpRegion>(OmpRegion.class);
		String sql = "this_.id in("+ids+") ";
		if (!"".equals(sql)) {
			searchCriteriaBuilder.addAdditionalRestrictionSql(sql);
		}
		List<OmpRegion> list = getGeneralService().getObjects(searchCriteriaBuilder.build());
		return list;
	}

	@Override
	public List<OmpRegion> queryCounty(long id) {
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
		List<OmpRegion> list = getGeneralService().getObjects(searchCriteriaBuilder.build());
		return list;
	}

	@Override
	public List<Map<String,Object>> queryAllRegions(int i) {
		String sql = "select t.id id,t.name name,t.PARENTID pid from omp_region t where t.USE_FLAG=1 and t.LEVELID="+i;
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public int queryLv(String string) {

		return 0;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean queryForTell(String serviceTell,Long id) {
		String idsql ="";
		if(id!=0L){
			idsql = " and t.id <>"+id;
		}
		String sql = "select count(*) from service_provider t where t.serviceTell ='"+serviceTell+"'"+idsql;
		int i = jdbcTemplate.queryForInt(sql);
		return i>0?false:true;
	}

}
