package com.capinfo.omp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.omp.parameter.ServiceSystemParameter;

/**
 * 用户查询权限
 * @author Rivex
 *
 */
public class Permissions {

	public static Map<String, Long> mapKeys(ServiceSystemParameter p){
		 Map<String, Long> mapKey = new HashMap<String, Long>();
			mapKey.put("M1", p.getM1());
			mapKey.put("M2", p.getM2());
			mapKey.put("M3", p.getM3());
			mapKey.put("M4", p.getM4());
			mapKey.put("M5", p.getM5());
			mapKey.put("M6", p.getM6());
			mapKey.put("M7", p.getM7());
			mapKey.put("M8", p.getM8());
			mapKey.put("M9", p.getM9());
			mapKey.put("M10", p.getM10());
			mapKey.put("M11", p.getM11());
			mapKey.put("M12", p.getM12());
			mapKey.put("M13", p.getM13());
			mapKey.put("M14", p.getM14());
			mapKey.put("M15", p.getM15());
			mapKey.put("M16", p.getM16());
			return mapKey;
	}
















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
