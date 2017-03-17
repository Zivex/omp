package com.capinfo.omp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;
import com.capinfo.framework.web.service.impl.CommonsDataOperationServiceImpl;
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

}
