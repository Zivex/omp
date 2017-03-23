package com.capinfo.omp.util;

import org.springframework.jdbc.core.JdbcTemplate;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;

/**
 * 用户查询权限
 * @author Rivex
 *
 */
public class Permissions {
	
	public void checkPer(SystemUser user,SearchCriteriaBuilder searchCriteriaBuilder,JdbcTemplate jdbcTemplate){
		if (user.getLeave() > 1) {
			if("g".equals(user.getAccount_type())){
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
			searchCriteriaBuilder.addQueryCondition(rname,
					RestrictionExpression.EQUALS_OP, user.getRid());
			}else if("m".equals(user.getAccount_type()) || "b".equals(user.getAccount_type())){
				String ji = "";
				Integer uJi=0;
				switch (user.getLeave()) {
				case 1: String idSsql = "select t.id from composition t where t.prient_id is null and t.cid="+user.getId();
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
	}
}
