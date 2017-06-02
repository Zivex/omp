package com.capinfo.common.web.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.capinfo.common.web.service.RegionManageService;
import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.SearchCriteria.OrderRow;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.web.service.impl.CommonsDataOperationServiceImpl;
import com.capinfo.omp.parameter.RegionManageParameter;
import com.capinfo.region.model.OmpRegion;

public class RegionManageServiceImpl extends
		CommonsDataOperationServiceImpl<OmpRegion, RegionManageParameter>
		implements RegionManageService {
	
	@Resource(name = "regionManageService")
	private RegionManageService service;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> getregionList(RegionManageParameter parameter) {
		SearchCriteriaBuilder<OmpRegion> searchCriteriaBuilder = new SearchCriteriaBuilder<OmpRegion>(
				OmpRegion.class);

		// 根据区域名称查询
		searchCriteriaBuilder.addQueryCondition("name",
				RestrictionExpression.LIKE_OP, parameter.getEntity().getName());
		
		searchCriteriaBuilder.addLimitCondition(
				(parameter.getCurrentPieceNum() - 1)
						* parameter.getPerPieceSize(),
				parameter.getPerPieceSize());
		searchCriteriaBuilder.addOrderCondition("levelid", OrderRow.ORDER_ASC);
		searchCriteriaBuilder.addOrderCondition("createdate", OrderRow.ORDER_DESC);
		searchCriteriaBuilder.addOrderCondition("updatedate", OrderRow.ORDER_DESC);
		
		String sql = "";
		sql +=" 1=1 and this_.use_flag = 1 and levelid >=0";
		// "and this_.CREATE_CARD_SUCCESS = 1 and this_.has_pushed = 1 and this_.residence_county_id !=31381";
		
		if (!"".equals(sql)) {
			searchCriteriaBuilder.addAdditionalRestrictionSql(sql);
		}
		 int count = getGeneralService().getCount(searchCriteriaBuilder.build());
		
		List<OmpRegion> regionList = getGeneralService().getObjects(
				searchCriteriaBuilder.build());
		
		 Map<String, Object> map = new HashMap<String, Object>();  
		 map.put("list", regionList);
		 map.put("count", count);

		return map;
	}

	@Override
	public void addRegion(RegionManageParameter parameter) {
		OmpRegion entity = parameter.getEntity();
		entity.setCreatedate(new Date());
		entity.setLevelid(0L);
		entity.setParentid(1l);
		entity.setUseFlag((short)1);
		getGeneralService().saveOrUpdate(entity);
	}

	@Override
	public void updateRegion(RegionManageParameter parameter) {
		OmpRegion region = getGeneralService().getObjectById(OmpRegion.class, parameter.getEntity().getId());
		region.setName(parameter.getEntity().getName());
		region.setUpdatedate(new Date());
		getGeneralService().saveOrUpdate(region);
		
	}

	@Override
	public void addChildRegion(RegionManageParameter parameter) {
		Long parentid = parameter.getEntity().getParentid();
		
		OmpRegion region = getGeneralService().getObjectById(OmpRegion.class, parentid);
		OmpRegion entity = parameter.getEntity();
		if(region.getLevelid()==0){
			entity.setLevelid(2L);
		}else{
			entity.setLevelid(region.getLevelid()+1L);
		}
		entity.setCreatedate(new Date());
		entity.setUseFlag((short)1);
		getGeneralService().saveOrUpdate(entity);
	}

	@Override
	public void deleteRegion(RegionManageParameter parameter) {
		OmpRegion region = getGeneralService().getObjectById(OmpRegion.class, parameter.getRid());
		region.setUseFlag((short)0);
		getGeneralService().saveOrUpdate(region);
	}

}
