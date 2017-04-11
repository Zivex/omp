package com.capinfo.omp.util;

import java.util.List;

import org.hibernate.loader.custom.Return;
import org.springframework.jdbc.core.JdbcTemplate;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.service.GeneralService;

/**
 * 用户查询权限
 * @author Rivex
 *
 */
public class Permissions {
	
	public static SearchCriteriaBuilder checkPer(SystemUser user,SearchCriteriaBuilder searchCriteriaBuilder,GeneralService generalService){
		if (user.getLeave() > 1) {
			if("g".equals(user.getAccount_type())){
			String ids = "";
			serchG(user, searchCriteriaBuilder, generalService, ids);
			System.out.println(ids);
			}else if("m".equals(user.getAccount_type()) || "b".equals(user.getAccount_type())){
				String ji = "";
				Integer uJi=0;
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
				searchCriteriaBuilder.addQueryCondition(ji,
						RestrictionExpression.EQUALS_OP, ji);
				}
		}
		List<SystemUser> userList = generalService.getObjects(searchCriteriaBuilder.build());
		return searchCriteriaBuilder;
	}
	
	//用户id查询用户(政府)
	public static String serchG(SystemUser user,SearchCriteriaBuilder<?> sb,GeneralService generalService,String ids){
		ids+=user.getRid()+",";
		sb.addQueryCondition("parentid", RestrictionExpression.EQUALS_OP,user.getRid());
		List<SystemUser> users = generalService.getObjects(sb.build());
		for (SystemUser systemUser : users) {
			if(users.size()==0){
				break;
			}
			serchG(systemUser, sb,  generalService, ids);
		}
		return ids;
	}
}
