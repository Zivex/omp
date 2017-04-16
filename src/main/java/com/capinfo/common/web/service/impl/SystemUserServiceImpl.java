package com.capinfo.common.web.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.capinfo.common.model.Resource;
import com.capinfo.common.model.Role;
import com.capinfo.common.model.SystemUser;
import com.capinfo.common.web.parameter.SystemUserParameter;
import com.capinfo.common.web.service.SystemUserService;
import com.capinfo.framework.dao.SearchCriteria.OrderRow;
import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.model.system.SecureRole;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.framework.web.service.impl.CommonsDataOperationServiceImpl;
import com.capinfo.omp.model.Composition;
import com.capinfo.omp.model.RechargeLog;
import com.capinfo.omp.model.UserRecharge;
import com.capinfo.omp.parameter.CompositionParameter;
import com.capinfo.region.model.OmpRegion;

public class SystemUserServiceImpl extends CommonsDataOperationServiceImpl<SystemUser, SystemUserParameter> implements SystemUserService {

	private static final Log LOGGER = LogFactory.getLog(SystemUserServiceImpl.class);

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public SearchCriteriaBuilder<SystemUser> getSearchCriteriaBuilder(SystemUserParameter parameter) {
		SearchCriteriaBuilder<SystemUser> searchCriteriaBuilder = super.getSearchCriteriaBuilder(parameter);
		SystemUser user = parameter.getEntity();
		searchCriteriaBuilder.addQueryCondition("name", RestrictionExpression.LIKE_OP, user.getName());
		searchCriteriaBuilder.addQueryCondition("logonName", RestrictionExpression.LIKE_OP, user.getLogonName());
		searchCriteriaBuilder.addQueryCondition("enabled", RestrictionExpression.EQUALS_OP, true);
		searchCriteriaBuilder.addQueryCondition("account_type", RestrictionExpression.EQUALS_OP, parameter.getEncode());
		searchCriteriaBuilder.addQueryCondition("encoding", RestrictionExpression.LIKE_OP, parameter.getEntity().getEncoding());
		// 角色
		String inRoleStr = parameter.getRolesIdStrs();
		if (StringUtils.isNotBlank(inRoleStr) && !inRoleStr.equals("0")) {
			searchCriteriaBuilder.addAdditionalRestrictionSql(" this_.id in (select USER_ID from USER_ROLES where ROLE_ID in(" + inRoleStr + "))");
		}
		searchCriteriaBuilder.addOrderCondition("id", OrderRow.ORDER_DESC);

		return searchCriteriaBuilder;
	}

	@Override
	public boolean updateUserInfo(SystemUserParameter parameter) {
		boolean suc = true;
		try {
			SystemUser entity = parameter.getEntity();
			Long id = parameter.getEntity().getId();
			SystemUser user = getGeneralService().getObjectById(SystemUser.class, id);
			user.updateUserInfo(entity);
			getGeneralService().merge(user);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			suc = false;
		}
		return suc;
	}

	@Override
	public boolean saveUser(SystemUser user) {
		boolean suc = true;
		try {
			GeneralService g = getGeneralService();
			g.saveOrUpdate(user);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			suc = false;
		}
		return suc;
	}

	@Override
	public SystemUser getUserById(Long id) {
		SystemUser user = null;
		try {
			user = getGeneralService().getObjectById(SystemUser.class, id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			user = null;
		}
		return user;
	}

	public boolean isExistUser(String userName) {
		SearchCriteriaBuilder<SystemUser> searchCriteriaBuilder = new SearchCriteriaBuilder<SystemUser>(SystemUser.class);
		searchCriteriaBuilder.addQueryCondition("logonName", RestrictionExpression.EQUALS_OP, userName);
		searchCriteriaBuilder.addQueryCondition("enabled", RestrictionExpression.EQUALS_OP, Boolean.TRUE);
		return getGeneralService().getCount(searchCriteriaBuilder.build()) > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public SystemUser loadUserByUsername(String userName) {
		SystemUser user = null;
		SearchCriteriaBuilder<SystemUser> searchCriteriaBuilder = new SearchCriteriaBuilder<SystemUser>(SystemUser.class);
		searchCriteriaBuilder.addQueryCondition("logonName", RestrictionExpression.EQUALS_OP, userName);
		searchCriteriaBuilder.addQueryCondition("enabled", RestrictionExpression.EQUALS_OP, Boolean.TRUE);
		try {
			user = getGeneralService().getObjectByCriteria(searchCriteriaBuilder.build());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			user = null;
		}

		return user;
	}

	@Override
	public boolean deleteUser(SystemUserParameter parameter) {
		try {
			SystemUser user = (SystemUser) getGeneralService().getObjectById(parameter.getEntityClazz(), parameter.getEntity().getId());
			user.setEnabled(false);
			getGeneralService().merge(user);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean resetPassword(SystemUserParameter parameter) {
		try {
			SystemUser user = (SystemUser) getGeneralService().getObjectById(parameter.getEntityClazz(), parameter.getEntity().getId());
			//String encodePass = passwordEncoder.encode("123456");
			//user.setPassword(encodePass);
			user.setPassword("123456");
			getGeneralService().merge(user);
			return true;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return false;
		}
	}

	@Override
	public List<Resource> getResources() {
		List<Resource> resources = new ArrayList<Resource>();
		SearchCriteriaBuilder<Resource> searchCriteriaBuilder = new SearchCriteriaBuilder<Resource>(Resource.class);
		searchCriteriaBuilder.addQueryCondition("enabled", RestrictionExpression.EQUALS_OP, Boolean.TRUE);
		resources = getGeneralService().getObjects(searchCriteriaBuilder.build());
		return resources;
	}

	@Override
	public boolean changePassword(SystemUserParameter parameter) {
		try {
			String password = parameter.getEntity().getPassword();
			SystemUser user = (SystemUser) getGeneralService().getObjectById(parameter.getEntityClazz(), parameter.getEntity().getId());
			String encodePass = passwordEncoder.encode(password);
			user.setPassword(encodePass);
			getGeneralService().merge(user);
			return true;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return false;
		}
	}

	private String fitIds(Collection roles) {
		StringBuilder sb = new StringBuilder();
		for (Iterator iter = roles.iterator(); iter.hasNext();) {
			SecureRole role = (SecureRole) iter.next();
			sb.append(role.getId()).append(",");
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}

	private List<Role> getRolesByIds(String ids) {
		SearchCriteriaBuilder<Role> searchCriteriaBuilder = new SearchCriteriaBuilder<Role>(Role.class);
		searchCriteriaBuilder.addAdditionalRestrictionSql("ID in(" + ids + ")");
		List<Role> roles = getGeneralService().getObjects(searchCriteriaBuilder.build());
		return roles;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.capinfo.smart.web.service.admin.system.SystemUserService#isUserPassword
	 * (com.capinfo.smart.web.parameter.admin.system.SystemUserParameter)
	 */
	@Override
	public boolean isUserPassword(SystemUserParameter parameter) {
		SystemUser user = (SystemUser) getGeneralService().getObjectById(parameter.getEntityClazz(), parameter.getEntity().getId());
		String oldRawPassword = parameter.getOldPassword();
		String olEencodedPassword = user.getPassword();
		return passwordEncoder.matches(oldRawPassword, olEencodedPassword);
	}

	@Override
	public OmpRegion getbiRegoinid(long rid) {
		OmpRegion entity = getGeneralService().getObjectById(OmpRegion.class, rid);
		return entity;
	}

	@Override
	public void recharge(SystemUser admin, Long money,Long id) {
		SystemUser user = getGeneralService().getObjectById(SystemUser.class, id);
		Long holdCount = user.getNum();	//当前持有条数
		Long afterCount = holdCount+money;	//充值后的条数
		RechargeLog log = new RechargeLog();
		user.setNum(afterCount);
		log.setOldmoney(holdCount);
		log.setNewmoney(afterCount);
		log.setRid(user.getId());
		log.setTime(new Date());
		log.setSuccess_stat(1);
		log.setUser_name(String.valueOf(admin.getId()));
		getGeneralService().saveOrUpdate(log);
		getGeneralService().saveOrUpdate(user);

	}

	@Override
	public void addMechanism(Composition composition,SystemUser user) {
		Date date = new Date();		//创建时间
		composition.setCreatedate(date);
		composition.setUse_flag(1L);
		composition.setCid(user.getId());
		getGeneralService().saveOrUpdate(composition);
	}

	@Override
	public List<Composition> getCompositionList(SystemUser user) {
		List<Composition> resources = new ArrayList<Composition>();
		SearchCriteriaBuilder<Composition> searchCriteriaBuilder = new SearchCriteriaBuilder<Composition>(Composition.class);
		searchCriteriaBuilder.addQueryCondition("cid", RestrictionExpression.EQUALS_OP, user.getId());
		resources = getGeneralService().getObjects(searchCriteriaBuilder.build());
		return resources;
	}


	@Override
	public boolean validationMechanism(long rid,String f ) {
		boolean flag = false;
		int i = 0;
		if("g".equals(f)){
			String sql ="select count(*) from users t where t.rid="+rid;
			i = jdbcTemplate.queryForInt(sql);
		}else{
			String sql ="select count(*) from users t where t.ji="+rid;
			i = jdbcTemplate.queryForInt(sql);
		}
		if(i>0){
			flag = true;	//机构已存在
		}

		return flag;
	}


}
