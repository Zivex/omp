package com.capinfo.omp.service.impl;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.capinfo.assistant.platform.ws.card.model.CardPersonMessageBack;
import com.capinfo.assistant.platform.ws.card.service.CardPersonMessageWsServiceProxy;
import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;
import com.capinfo.framework.web.service.impl.CommonsDataOperationServiceImpl;
import com.capinfo.omp.model.CardPerson;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.parameter.OrderParameter;
import com.capinfo.omp.service.OldService;
import com.capinfo.omp.utils.Page;
import com.capinfo.omp.utils.excel.ColFooterCell;
import com.capinfo.omp.utils.excel.ColHeaderCell;
import com.capinfo.omp.utils.excel.Column;
import com.capinfo.omp.utils.excel.DefaultTheme;
import com.capinfo.omp.utils.excel.ExcelBuilder;
import com.capinfo.omp.utils.excel.ExcelSheet;
import com.capinfo.omp.utils.excel.Schema;
import com.capinfo.omp.utils.excel.Theme;

@SuppressWarnings("rawtypes")
@Service
public class OldServiceImpl extends
		CommonsDataOperationServiceImpl<Omp_Old_Info, OrderParameter> implements
		OldService {

	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Override
	public List<Omp_Old_Info> getOldContextList(Page page, String name,
			String idCard, String zjNumber, String county, String street,
			String community, String isGenerationOrder, String isindividuation,
			SystemUser user) {
		SearchCriteriaBuilder<Omp_Old_Info> searchCriteriaBuilder = new SearchCriteriaBuilder(
				Omp_Old_Info.class);
		// 名字
		searchCriteriaBuilder.addQueryCondition("name",
				RestrictionExpression.EQUALS_OP, name);
		searchCriteriaBuilder.addQueryCondition("certificates_number",
				RestrictionExpression.EQUALS_OP, idCard);
		searchCriteriaBuilder.addQueryCondition("zjnumber",
				RestrictionExpression.EQUALS_OP, zjNumber);
		searchCriteriaBuilder.addQueryCondition("household_county_id",
				RestrictionExpression.EQUALS_OP, county);
		searchCriteriaBuilder.addQueryCondition("household_street_id",
				RestrictionExpression.EQUALS_OP, street);
		searchCriteriaBuilder.addQueryCondition("household_community_id",
				RestrictionExpression.EQUALS_OP, community);
		searchCriteriaBuilder.addQueryCondition("isGenerationOrder",
				RestrictionExpression.EQUALS_OP, isGenerationOrder);
		searchCriteriaBuilder.addQueryCondition("isindividuation",
				RestrictionExpression.EQUALS_OP, isindividuation);
		searchCriteriaBuilder.addLimitCondition(
				(page.getCurrentPage() - 1) * 10, 10);
		// 根据当前用户搜索
		// if ("admin".equals(user.getName())) {
		// searchCriteriaBuilder.addQueryCondition("account_type",
		// RestrictionExpression.EQUALS_OP, user.getAccount_type());
		// }
		if (user.getLeave() > 1) {
			String rname = "";
			switch (user.getLeave()) {
			case 2:
				rname = "household_county_id";
				break;
			case 3:
				rname = "household_street_id";
				break;
			case 4:
				rname = "household_community_id";
				break;
			}
			searchCriteriaBuilder.addQueryCondition(rname,
					RestrictionExpression.EQUALS_OP, user.getRid());
		}
		String sql = "";
		// sql +=
		// "and this_.CREATE_CARD_SUCCESS = 1 and this_.has_pushed = 1 and this_.residence_county_id !=31381";

		if (!"".equals(sql)) {
			searchCriteriaBuilder.addAdditionalRestrictionSql(sql);
		}
		List<Omp_Old_Info> Omp_Old_InfoList = getGeneralService().getObjects(
				searchCriteriaBuilder.build());

		return Omp_Old_InfoList;
	}

	@Override
	public boolean addOld(Omp_Old_Info ompOldInfo, SystemUser user) {
		ompOldInfo.setAccount_type(user.getAccount_type());
		ompOldInfo.setAgent_id(user.getId());
		ompOldInfo.setState(1l);
		ompOldInfo.setSync(0L);
		getGeneralService().saveOrUpdate(ompOldInfo);

		Long autoIncId = ompOldInfo.getId();

		if (autoIncId > 0) {
			addOldKeyInfo(ompOldInfo, autoIncId);
		}

		return autoIncId > 0;

	}

	// 新增老人，自动匹配社区座机信息
	private boolean addOldKeyInfo(Omp_Old_Info ompOldInfo, Long OldID) {
		String CommunityId = ompOldInfo.getHousehold_community_id();
		Map<String, Object> maps = getCommunityInfo(CommunityId);
		String sqlString = "";
		if (!"".equals(ompOldInfo.getTeltype())
				&& "居家型".equals(ompOldInfo.getTeltype())) {
			if (maps.get("jt") != null) {
				sqlString = maps.get("jt").toString();
			}
		}
		if (!"".equals(ompOldInfo.getTeltype())
				&& "农商型".equals(ompOldInfo.getTeltype())) {
			if (maps.get("nt") != null) {
				sqlString = maps.get("nt").toString();
			}
		}
		if (!"".equals(ompOldInfo.getTeltype())
				&& "失能型".equals(ompOldInfo.getTeltype())) {
			if (maps.get("nt") != null) {
				sqlString = maps.get("nt").toString();
			}
		}
		if (sqlString != null && !"".equals(ompOldInfo.getTeltype())) {
			addOmpOldOrderInfo(String.valueOf(OldID), sqlString);
			return true;
		}
		return false;
	}

	// 通过社区编号，获取社区默认键值信息
	private Map getCommunityInfo(String id) {
		Map<String, Object> maps = new HashMap<String, Object>();
		String sql = "SELECT t.ID, t.SNTYPE st, t.JJTYPE jt, t.NSTYPE nt FROM omp_region t WHERE t.ID = "
				+ id;
		maps = JdbcTemplate.queryForMap(sql);

		return maps;
	}

	// 通过座机号，身份证号判断是否存在老人
	@Override
	public Integer checkOldIsHave(String phoneid, String cardID) {
		String sql = "SELECT COUNT(*) FROM  (SELECT * FROM omp_old_info t WHERE  t.ZJNUMBER = "
				+ phoneid + ") a ";
		String sql1 = "SELECT COUNT(*) FROM  (SELECT * FROM omp_old_info t WHERE  t.CERTIFICATES_NUMBER ='"
				+ cardID + "') a1 ";
		int count = JdbcTemplate.queryForInt(sql);// 用queryForInt方法！！！
		int count1 = JdbcTemplate.queryForInt(sql1);// 用queryForInt方法！！！
		if (count * count1 > 0) {
			return 3;
		}
		if (count > 0) {
			return 1;// 大座机号已存在
		}
		if (count1 > 0) {
			return 2;// 老人身份证号已存在
		}
		return 0;
	}

	@Override
	public boolean updOldById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "update omp_old_info " + "set NAME = '" + map.get("name")
				+ "'," + "HOUSEHOLD_COUNTY_ID = '" + map.get("county") + "',"
				+ "HOUSEHOLD_STREET_ID = '" + map.get("street") + "',"
				+ "HOUSEHOLD_COMMUNITY_ID = '" + map.get("community") + "',"
				+ "ZJNUMBER = '" + map.get("zjnumber") + "'," + "PHONE = '"
				+ map.get("phone") + "'," + "ADDRESS = '" + map.get("address")
				+ "'," + "EMERGENCYCONTACT = '" + map.get("emergencycontact")
				+ "'," + "EMERGENCYCONTACTTLE = '"
				+ map.get("emergencycontacttle") + "'," + "TELTYPE = '"
				+ map.get("teltype") + "'" + "where id = " + map.get("id");
		JdbcTemplate.update(sql);
		return false;
	}

	@Override
	public int delOldById(String old) {
		// TODO Auto-generated method stub
		// String sql = "UPDATE OMP_OLD_INFO SET STATE = '0' WHERE ID = " + old;
		String sql = "delete from OMP_OLD_INFO  WHERE ID = " + old;

		int i = JdbcTemplate.update(sql);
		return i;
	}

	@Override
	public int getCount(String name, String idCard, String zjNumber,
			String county, String street, String community,
			String isGenerationOrder, String isindividuation, SystemUser user) {

		// String uName = "";
		// if (!"admin".equals(user.getName())) {
		// uName = " AND I.agent_id  =  '" + user.getName() + "'";
		// }
		// 查询用户区域
		String rname = "";
		switch (user.getLeave()) {
		case 2:
			rname = " and i.HOUSEHOLD_COUNTY_ID = " + user.getRid();
			break;
		case 3:
			rname = " and i.HOUSEHOLD_STREET_ID = " + user.getRid();
			break;
		case 4:
			rname = " and i.HOUSEHOLD_COMMUNITY_ID = " + user.getRid();
			break;
		}

		if (name != null && !StringUtils.isEmpty(name)) {
			name = " AND I.NAME  LIKE  '%" + name + "%'";
		} else {
			name = "";
		}
		if (idCard != null && !StringUtils.isEmpty(idCard)) {
			idCard = " AND I.CERTIFICATES_NUMBER = '" + idCard + "'";
		}
		if (zjNumber != null && !StringUtils.isEmpty(zjNumber)) {
			zjNumber = "  AND I.ZJNUMBER = '" + zjNumber + "'";
		}
		if (county != null && idCard != null && !StringUtils.isEmpty(county)) {
			county = " AND I.HOUSEHOLD_COUNTY_ID = '" + county + "'";
		}
		if (street != null && !StringUtils.isEmpty(street)) {
			street = " AND I.HOUSEHOLD_STREET_ID = '" + street + "'";
		}
		if (community != null && !StringUtils.isEmpty(community)) {
			community = " AND I.HOUSEHOLD_COMMUNITY_ID = '" + community + "'";
		}
		if (isGenerationOrder != null
				&& !StringUtils.isEmpty(isGenerationOrder)) {
			isGenerationOrder = " AND I.isGenerationOrder = '"
					+ isGenerationOrder + "'";
		}

		if (isindividuation != null && !StringUtils.isEmpty(isindividuation)) {
			isindividuation = " AND I.isindividuation = '" + isindividuation
					+ "'";
		}
		/*
		 * if (!StringUtils.isEmpty(creationTime)) { creationTime =
		 * " AND I.creationTime like '%"+creationTime+"%'"; }
		 */
		String sql = "SELECT count(i.ID) FROM	OMP_OLD_INFO i WHERE i.STATE = 1 "
				+ rname
				+ name
				+ idCard
				+ zjNumber
				+ county
				+ street
				+ community + isGenerationOrder + isindividuation;
		int maxRows = JdbcTemplate.queryForInt(sql);
		return maxRows;
	}

	@Override
	public String getIdByName(String parameter, int level) {
		String sql = "select r.id id,r.name name,r.parentid parentid from Omp_Region r where r.name like '%"
				+ parameter + "%' and r.levelid = " + level;
		// String sql =
		// "select  r.id id,r.name name,r.parentid parentid from OMP_REGION r where r.name='"+parameter+"'";
		List<Map<String, Object>> queryForList = JdbcTemplate.queryForList(sql);
		Object object = queryForList.get(0).get("ID");
		return object.toString();
	}

	@Override
	public List<Map<String, Object>> getOldById(String id) {
		String sql = "SELECT I.*,o.keyPointMessage Kp, R.NAME SNAME FROM OMP_OLD_INFO i,OMP_REGION r,omp_old_order o WHERE I.STATE = 1 AND I.HOUSEHOLD_COMMUNITY_ID = R.ID AND o.oldId= i.ID AND i.ID = "
				+ id;
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public List<Map<String, Object>> getOldById1(String id) {
		String sql = "SELECT I.*,R. NAME SNAM FROM OMP_OLD_INFO i, OMP_REGION r WHERE I.STATE = 1 AND I.HOUSEHOLD_COMMUNITY_ID = R.ID AND i.ID = "
				+ id;
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public Map getRegionList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Map<String, Object> maps = new HashMap<String, Object>();
		String sql1 = "select r.id,r.name from Omp_Region r where r.levelid = 3";
		List<Map<String, Object>> list1 = JdbcTemplate.queryForList(sql1);
		String sql2 = "select r.id,r.name from Omp_Region r where r.levelid = 4 and r.parentid = "
				+ map.get("HOUSEHOLD_COUNTY_ID");
		List<Map<String, Object>> list2 = JdbcTemplate.queryForList(sql2);
		String sql3 = "select r.id,r.name from Omp_Region r where r.levelid = 5 and r.parentid = "
				+ map.get("HOUSEHOLD_STREET_ID");
		List<Map<String, Object>> list3 = JdbcTemplate.queryForList(sql3);
		maps.put("county", list1);
		maps.put("street", list2);
		maps.put("community", list3);
		return maps;
	}

	@Override
	public List<Map<String, Object>> getRegionById(String county) {
		String sql = "select r.id,r.name from Omp_Region r where r.USE_FLAG=1 and r.parentid = "
				+ county;
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryCommunityOrder(String id) {
		String sql = "select oldpo.ID,oldpo.pname,oldpo.comId,k.`key`,oldpo.serviceProviderNumber number "
				+ "from(select oldp.ID,oldp.pname,o.keyId,o.serviceProviderNumber,oldp.HOUSEHOLD_COMMUNITY_ID comId  "
				+ "from(select old.ID,old.HOUSEHOLD_COMMUNITY_ID,p.id ptype,p.phoneType pname "
				+ "from(SELECT i.ID,i.HOUSEHOLD_COMMUNITY_ID,i.TELTYPE telTYPE "
				+ "from omp_old_info i where id = "
				+ id
				+ ") old,omp_phone_type p "
				+ "where "
				+ "p.phoneType = old.telTYPE) oldp,omp_community_order o "
				+ "where o.phoneTYPE = oldp.ptype AND o.communityID = oldp.HOUSEHOLD_COMMUNITY_ID) oldpo,omp_key k "
				+ "where k.id = oldpo.keyId";
		System.out.println(sql);
		return JdbcTemplate.queryForList(sql);

	}

	@Override
	public boolean saveOrder(String id, String pname, String comId,
			String keyPointMessage) {
		String sql = "INSERT INTO `omp_old_order` "
				+ "(`oldId`, `phoneName`, `communityOrderId`, `keyPointMessage`) "
				+ "VALUES ('" + id + "', '" + pname + "', '" + comId + "', '"
				+ keyPointMessage + "')";
		int update = JdbcTemplate.update(sql);
		return update > 0;
	}

	@Override
	public void updOldState(String id) {
		// TODO Auto-generated method stub
		String sql = "update omp_old_info set isGenerationOrder = '1' where id = "
				+ id;
		int update = JdbcTemplate.update(sql);
	}

	@Override
	public List<Map<String, Object>> getOldKeyPointMessage(String oid) {
		String sql = "select k.`key`,oldo.serviceProviderNumber number from (SELECT o.keyId,o.serviceProviderNumber from (SELECT i.ID,i.HOUSEHOLD_COMMUNITY_ID from omp_old_info i where i.ID = "
				+ oid
				+ ") old,omp_community_order o where o.communityID = old.HOUSEHOLD_COMMUNITY_ID)oldo,omp_key k where k.id = oldo.keyId";
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public List<Map<String, Object>> getOldMyPointMessage(String oid) {
		String sql = "SELECT t.keyPointMessage FROM omp_old_order t WHERE t.oldId = "
				+ oid;
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);

		return list;
	}

	@Override
	public List<Map<String, Object>> getOmpKeyByType(String pyId) {
		String sql = "select t.* , '' phone FROM omp_key t WHERE t.pyId = "
				+ pyId;
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;
	}

	// 通过老人ID获取老人座机键值信息
	@Override
	public List<Map<String, Object>> getOldKeyInfoById(String id) {
		String sql = "SELECT * FROM omp_old_order t WHERE t.oldId = " + id;
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public Boolean uploadOldIndividuation(String id, String json) {
		String sql = "update omp_old_order set keyPointMessage = '" + json
				+ "', send_flag = 2 ,execute_flag = 3  where oldId = " + id;
		int update = JdbcTemplate.update(sql);
		System.out.println("指令修改");
		if (update > 0) {
			setOldisIndividuation(id, 1);
		}
		return (update == 1);
	}

	// 设置用户是否个性化 0 未个性化 1 已经个性化
	private Boolean setOldisIndividuation(String id, int value) {

		String sql1 = "update omp_old_info set isindividuation = " + value
				+ "  where ID = " + id;
		int update1 = JdbcTemplate.update(sql1);
		return (update1 == 1);
	}

	// 通过老人ID和键值 新增 老人座机键值信息
	@Override
	public Boolean addOmpOldOrderInfo(String id, String json) {
		String sql = "INSERT INTO omp_old_order ("
				+ "oldId, phoneName, communityOrderId, keyPointMessage)"
				+ "  select t.ID,t.TELTYPE,t.HOUSEHOLD_COMMUNITY_ID,'" + json
				+ "' from omp_old_info t WHERE t.ID = " + id;
		int update = JdbcTemplate.update(sql);
		setOldisIndividuation(id, 1);
		return (update == 1);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean checkRe(String idCard, String zjnumber) {
		// TODO Auto-generated method stub
		String sql = "SELECT count(ID) from omp_old_info i where i.CERTIFICATES_NUMBER = '"
				+ idCard + "' OR i.ZJNUMBER = '" + zjnumber + "'";
		int forInt = JdbcTemplate.queryForInt(sql);
		if (forInt > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean checkId(String string) {
		// TODO Auto-generated method stub
		String sql = "SELECT isGenerationOrder from omp_old_info i where  i.id = "
				+ string;
		Map<String, Object> forInt = JdbcTemplate.queryForMap(sql);
		return "0" == forInt.get("isGenerationOrder")
				|| "0".equals(forInt.get("isGenerationOrder"));
	}

	@Override
	public boolean saveLogger(String type, String content, String creater,
			String state) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String createdate = sim.format(date);
		String sql = "INSERT INTO `omp_log_record` "
				+ "(`type`, `content`, `creater`,`createdate`,`state`) "
				+ "VALUES ('" + type + "', '" + content + "', '" + createdate
				+ "' ,'" + creater + "' ,'" + state + "')";
		int update = JdbcTemplate.update(sql);
		return update > 0;
	}

	@Override
	public void saveolInfo(String carid) {
		CardPersonMessageWsServiceProxy d = new CardPersonMessageWsServiceProxy();
		try {
			String[] strs = carid.split(",");
			for (int i = 0, len = strs.length; i < len; i++) {
				String cid = strs[i].toString();
				CardPersonMessageBack m = d.getCardPersonMessageByIDNumber(cid);
				String auditStatus = m.getAuditStatus();
				if ("".equals(auditStatus) || auditStatus == null) {
					auditStatus = "";
				}
				String bankCard = m.getBankCard();
				if ("".equals(bankCard) || bankCard == null) {
					bankCard = "";
				}
				String biotope = m.getBiotope();
				if ("".equals(biotope) || biotope == null) {
					biotope = "";
				}
				String birthdate = m.getBirthdate();
				if ("".equals(birthdate) || birthdate == null) {
					birthdate = "";
				}
				String bookId = m.getBookId();
				if ("".equals(bookId) || bookId == null) {
					bookId = "";
				}
				String cardType = m.getCardType();
				if ("".equals(cardType) || cardType == null) {
					cardType = "";
				}
				String certificatesNumber = m.getCertificatesNumber();
				if ("".equals(certificatesNumber) || certificatesNumber == null) {
					// certificatesNumber = "";
					return;
				}
				String certificatesType = m.getCertificatesType();
				if ("".equals(certificatesType) || certificatesType == null) {
					certificatesType = "";
				}
				String cityPushed = m.getCityPushed();
				if ("".equals(cityPushed) || cityPushed == null) {
					cityPushed = "";
				}
				String cityPushedDate = m.getCityPushedDate();
				if ("".equals(cityPushedDate) || cityPushedDate == null) {
					cityPushedDate = "";
				}
				String contacter = m.getContacter();
				if ("".equals(contacter) || contacter == null) {
					contacter = "";
				}
				String contacterIdcardNumber = m.getContacterIdcardNumber();
				if ("".equals(contacterIdcardNumber)
						|| contacterIdcardNumber == null) {
					contacterIdcardNumber = "";
				}
				String contacterIdcardType = m.getContacterIdcardType();
				if ("".equals(contacterIdcardType)
						|| contacterIdcardType == null) {
					contacterIdcardType = "";
				}
				String contacterMobile = m.getContacterMobile();
				if ("".equals(contacterMobile) || contacterMobile == null) {
					contacterMobile = "";
				}
				String contacterRelation = m.getContacterRelation();
				if ("".equals(contacterRelation) || contacterRelation == null) {
					contacterRelation = "";
				}
				String creatCardDate = m.getCreatCardDate();
				if ("".equals(creatCardDate) || creatCardDate == null) {
					creatCardDate = "";
				}
				String creatCardPushDate = m.getCreatCardPushDate();
				if ("".equals(creatCardPushDate) || creatCardPushDate == null) {
					creatCardPushDate = "";
				}
				String creatCardStatus = m.getCreatCardStatus();
				if ("".equals(creatCardStatus) || creatCardStatus == null) {
					creatCardStatus = "";
				}
				String createCardFailInfo = m.getCreateCardFailInfo();
				if ("".equals(createCardFailInfo) || createCardFailInfo == null) {
					createCardFailInfo = "";
				}
				String createCardInDate = m.getCreateCardInDate();
				if ("".equals(createCardInDate) || createCardInDate == null) {
					createCardInDate = "";
				}
				String createCardSuccess = m.getCreateCardSuccess();
				if ("".equals(createCardSuccess) || createCardSuccess == null) {
					createCardSuccess = "";
				}
				String degreeType = m.getDegreeType();
				if ("".equals(degreeType) || degreeType == null) {
					degreeType = "";
				}
				String disabilityCardDate = m.getDisabilityCardDate();
				if ("".equals(disabilityCardDate) || disabilityCardDate == null) {
					disabilityCardDate = "";
				}
				String gatherStatus = m.getGatherStatus();
				if ("".equals(gatherStatus) || gatherStatus == null) {
					gatherStatus = "";
				}
				String hcFailInfo = m.getHcFailInfo();
				if ("".equals(hcFailInfo) || hcFailInfo == null) {
					hcFailInfo = "";
				}
				String hcInTime = m.getHcInTime();
				if ("".equals(hcInTime) || hcInTime == null) {
					hcInTime = "";
				}
				String hcPushedDate = m.getHcPushedDate();
				if ("".equals(hcPushedDate) || hcPushedDate == null) {
					hcPushedDate = "";
				}
				String hcSuccess = m.getHcSuccess();
				if ("".equals(hcSuccess) || hcSuccess == null) {
					hcSuccess = "";
				}
				String healthCareType = m.getHealthCareType();
				if ("".equals(healthCareType) || healthCareType == null) {
					healthCareType = "";
				}
				String householdAddress = m.getHouseholdAddress();
				if ("".equals(householdAddress) || householdAddress == null) {
					householdAddress = "";
				}
				String householdCommunity = m.getHouseholdCommunity();
				if ("".equals(householdCommunity) || householdCommunity == null) {
					householdCommunity = "";
				}
				String householdCounty = m.getHouseholdCounty();
				if ("".equals(householdCounty) || householdCounty == null) {
					householdCounty = "";
				}
				String householdStreet = m.getHouseholdStreet();
				if ("".equals(householdStreet) || householdStreet == null) {
					householdStreet = "";
				}
				String idcradDept = m.getIdcradDept();
				if ("".equals(idcradDept) || idcradDept == null) {
					idcradDept = "";
				}
				String mainSourceIncomeType = m.getMainSourceIncomeType();
				if ("".equals(mainSourceIncomeType)
						|| mainSourceIncomeType == null) {
					mainSourceIncomeType = "";
				}
				String marryStateType = m.getMarryStateType();
				if ("".equals(marryStateType) || marryStateType == null) {
					marryStateType = "";
				}
				String name = m.getName();
				if ("".equals(name) || name == null) {
					name = "";
				}
				String nation = m.getNation();
				if ("".equals(nation) || nation == null) {
					nation = "";
				}
				String newspaperGetWayType = m.getNewspaperGetWayType();
				if ("".equals(newspaperGetWayType)
						|| newspaperGetWayType == null) {
					newspaperGetWayType = "";
				}
				String personType = m.getPersonType();
				if ("".equals(personType) || personType == null) {
					personType = "";
				}
				String postalCode = m.getPostalCode();
				if ("".equals(postalCode) || postalCode == null) {
					postalCode = "";
				}
				String residenceAddress = m.getResidenceAddress();
				if ("".equals(residenceAddress) || residenceAddress == null) {
					residenceAddress = "";
				}
				String residenceCommunity = m.getResidenceCommunity();
				if ("".equals(residenceCommunity) || residenceCommunity == null) {
					residenceCommunity = "";
				}
				String residenceCounty = m.getResidenceCounty();
				if ("".equals(residenceCounty) || residenceCounty == null) {
					residenceCounty = "";
				}
				String residenceStreet = m.getResidenceStreet();
				if ("".equals(residenceStreet) || residenceStreet == null) {
					residenceStreet = "";
				}
				String resideType = m.getResideType();
				if ("".equals(resideType) || resideType == null) {
					resideType = "";
				}
				String revenueType = m.getRevenueType();
				if ("".equals(revenueType) || revenueType == null) {
					revenueType = "";
				}
				String selfCareAbilityType = m.getSelfCareAbilityType();
				if ("".equals(selfCareAbilityType)
						|| selfCareAbilityType == null) {
					selfCareAbilityType = "";
				}
				String sex = m.getSex();
				if ("".equals(sex) || sex == null) {
					sex = "";
				}
				String treatmentOney = m.getTreatmentOney();
				if ("".equals(treatmentOney) || treatmentOney == null) {
					treatmentOney = "";
				}
				String yktNumber = m.getYktNumber();
				if ("".equals(yktNumber) || yktNumber == null) {
					yktNumber = "";
				}
				String bjtNumber = m.getBjtNumber();
				if ("".equals(bjtNumber) || bjtNumber == null) {
					bjtNumber = "";
				}

				// Serializable save = se.save(cardPerson1);
				// System.out.println(save);
				String sql = "INSERT INTO `card_person` VALUES ('"
						+ auditStatus + "', '" + bankCard + "', '" + biotope
						+ "', '" + birthdate + "', '" + bjtNumber + "', '"
						+ bookId + "', '" + cardType + "', '"
						+ certificatesNumber + "', '" + certificatesType
						+ "', '" + cityPushed + "', '" + cityPushedDate
						+ "', '" + contacter + "', '" + contacterIdcardNumber
						+ "', '" + contacterIdcardType + "', '"
						+ contacterMobile + "', '" + contacterRelation + "', '"
						+ creatCardDate + "', '" + creatCardPushDate + "', '"
						+ creatCardStatus + "', '" + createCardFailInfo
						+ "', '" + createCardInDate + "', '"
						+ createCardSuccess + "', '" + degreeType + "', '"
						+ disabilityCardDate + "', '" + gatherStatus + "', '"
						+ hcFailInfo + "', '" + hcInTime + "', '"
						+ hcPushedDate + "', '" + hcSuccess + "', '"
						+ healthCareType + "', '" + householdAddress + "', '"
						+ householdCommunity + "', '" + householdCounty
						+ "', '" + householdStreet + "', '" + idcradDept
						+ "', '" + mainSourceIncomeType + "', '"
						+ marryStateType + "', '" + name + "', '" + nation
						+ "', '" + newspaperGetWayType + "', '" + personType
						+ "', '" + postalCode + "', '" + resideType + "', '"
						+ residenceAddress + "', '" + residenceCommunity
						+ "', '" + residenceCounty + "', '" + residenceStreet
						+ "', '" + revenueType + "', '" + selfCareAbilityType
						+ "', '" + sex + "', '" + treatmentOney + "', '"
						+ yktNumber + "')";
				System.out.println(sql);
				int update = JdbcTemplate.update(sql);
				if (update > 0) {
					String syncsql = "UPDATE omp_old_info i SET i.sync = '1'WHERE i.CERTIFICATES_NUMBER = '"
							+ cid + "'";
					JdbcTemplate.update(syncsql);
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean getcountid() {
		String sql = "select count(i.CERTIFICATES_NUMBER) as id from omp_old_info i where i.sync = 0";
		int i = JdbcTemplate.queryForInt(sql);
		return i > 0;
	}

	@Override
	public String checkDeBatchSendInstructions() {
		String sql = "select GROUP_CONCAT(i.CERTIFICATES_NUMBER) as id from omp_old_info i where i.sync = 0";
		Map<String, Object> map = JdbcTemplate.queryForMap(sql);
		return map.get("id").toString();
	}

	@Override
	public String HOUSEHOLD_COUNTY_ID(String add) {
		String sql = "select r.id 'id' from omp_region r where r.`NAME` = '"
				+ add + "' limit 1";
		Map<String, Object> map = JdbcTemplate.queryForMap(sql);
		return map.get("id").toString();
	}

	@Override
	public String HOUSEHOLD_STREET_ID(String add) {
		String sql = "select r.id 'id' from omp_region r where r.`NAME` = '"
				+ add + "' limit 1";
		Map<String, Object> map = JdbcTemplate.queryForMap(sql);
		return map.get("id").toString();
	}

	@Override
	public String HOUSEHOLD_COMMUNITY_ID(String add) {
		String sql = "select r.id 'id' from omp_region r where r.`NAME` = '"
				+ add + "' limit 1";
		Map<String, Object> map = JdbcTemplate.queryForMap(sql);
		return map.get("id").toString();
	}

	@Override
	public List<Map<String, Object>> getPerson(String cardIds) {
		String sql = "SELECT * from card_person o WHERE o.certificatesNumber = '"
				+ cardIds + "'";
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;
	}

	/**
	 * 导出
	 */
	@Override
	public ExcelBuilder exportExcel(OrderParameter parameter) {
		System.out.println("ex===============================");
		List<CardPerson> cardPersonlist = getGeneralService().getObjects(
				getSearchCriteriaBuilder(parameter).build());

		ExcelBuilder excelBuilder = new ExcelBuilder();
		Workbook wb = excelBuilder.getWorkbook();// 工作薄
		Theme theme = new DefaultTheme(wb);
		try {
			excelBuilder.setExcelSheetList(this
					.getExcelDataList(cardPersonlist));
			excelBuilder.setTheme(theme);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return excelBuilder;
	}

	private List<ExcelSheet> getExcelDataList(List<CardPerson> cardPersontList) {
		List<ExcelSheet> excelSheetList = new ArrayList<ExcelSheet>();
		if (null != cardPersontList && cardPersontList.size() > 0) {

			ExcelSheet excelSheet = new ExcelSheet();

			excelSheet.setSchema(this.getSchema("")); // 设置表格结构
			excelSheet.setSheetName("老人基本信息"); // sheet的名称
			excelSheet.setDataList(cardPersontList); // 设置数据集
			excelSheetList.add(excelSheet);// sheet添加到结果集
		}
		return excelSheetList;
	}

	/**
	 * 特殊格式的schema配置 获取（定制）供应商订单模板
	 * 
	 * @param totalStr
	 * @return
	 */
	private Schema getSchema(String totalStr) {
		Schema schema = new Schema();
		// 标题
		schema.setTitle("老人基本信息");
		// 行高
		schema.setDefaultRowHeight(500);
		// 列标题列表
		schema.addColHeaderCell(this.getColHeaderCellList());
		// 列属性（内容）
		schema.setColumnList(this.getColumnList());

		totalStr = totalStr == null ? "未查询到相关数据" : totalStr;
		// 表尾
		schema.addColFooterCell(getColFooterCell(totalStr));
		return schema;
	}

	/**
	 * 获取（定制）表头
	 * 
	 * @return
	 */
	private List<ColHeaderCell> getColHeaderCellList() {
		List<ColHeaderCell> colHeaderCellList = new ArrayList<ColHeaderCell>();
		ColHeaderCell colHeaderCell = null;
		String[] headerLabels = null;
		headerLabels = new String[] { "编号", "姓名", "性别", "民族", "证件类型", "证件号",
				"发证机关", "有效期", "生日", "同步时间", "人员类型", "卡类型", "居住地区县", "居住地街道",
				"居住地社区", "居住地地址", "户口在区县", "户口所在街道", "户口所在社区", "户口地址",
				"报纸收取方式", "联系人", "联系人证件类型", "联系人证件号码", "联系人手机号", "与联系人关系",
				"居住情况", "文化程度", "医疗保障类型", "月收入", "享受金额", "主要经济来源", "生活自理情况",
				"婚姻状况", "居住小区类型", "卡的生效时间", "卡是否可用", "银行卡号", "一本通号", "北京通号",
				"一卡通号", "审批状态", "信息采集状态", "申请状态", "制卡申请时间", "制卡成功时间", "补换卡号",
				"补换卡制卡时间", "临时卡号", "临时卡制卡时间", "制卡是否成功", "制卡失败原因", "是否推送",
				"区县是否推送", "区县推送时间", "核查是否推送", "核查推送时间", "核查是否成功", "核查回盘时间",
				"核查失败原因", "制卡推送时间", "制卡回盘时间", "是否死亡", "是否可用" };
		for (int i = 0; i < headerLabels.length; i++) {
			colHeaderCell = new ColHeaderCell();
			colHeaderCell.setLabel(headerLabels[i]);
			colHeaderCellList.add(colHeaderCell);
		}
		return colHeaderCellList;
	}

	/**
	 * 定制每一列属性的配置信息
	 * 
	 * @return
	 */
	private List<Column> getColumnList() {
		List<Column> columnList = new ArrayList<Column>();
		Column column = null;
		String[] columnNames = new String[] {
				// 编号
				"id",
				// 姓名
				"name",
				// 性别
				"sexType.name",
				// 民族
				"cardPersonDetail.nation.name",
				// 证件类型
				"certificatesType.name",
				// 证件号
				"certificatesNumber",
				// 发证机关
				"idcradDept",
				// 有效期
				"cardInfo.disabilityCardDate",
				// 生日
				"birthdate",
				// 同步时间
				"synchronizationTime",
				// 人员类型
				"personType.name",
				// 卡类型
				"cardType.name",
				// 居住地区县
				"residenceCounty.name",
				// 居住地街道
				"residenceStreet.name",
				// 居住地社区
				"residenceCommunity.name",
				// 居住地地址
				"residenceAddress",
				// 户口在区县
				"cardPersonDetail.householdCounty.name",
				// 户口所在街道
				"cardPersonDetail.householdStreet.name",
				// 户口所在社区
				"cardPersonDetail.householdCommunity.name",
				// 户口地址
				"cardPersonDetail.householdAddress",
				// 报纸收取方式
				"cardPersonDetail.newspaperGetWayType.name",
				// 联系人
				"cardPersonDetail.contacter",
				// 联系人证件类型
				"cardPersonDetail.contacterIdcardType.name",
				// 联系人证件号码
				"cardPersonDetail.contacterIdcardNumber",
				// 联系人手机号
				"cardPersonDetail.contacterMobile",
				// 与联系人关系
				"cardPersonDetail.contacterRelation",
				// 居住情况
				"cardPersonDetail.resideType.name",
				// 文化程度
				"cardPersonDetail.degreeType.name",
				// 医疗保障类型
				"cardPersonDetail.healthCareType.name",
				// 月收入
				"cardPersonDetail.revenueType.name",
				// 享受金额
				"cardPersonDetail.treatmentOney",
				// 主要经济来源
				"cardPersonDetail.mainSourceIncomeType.name",
				// 生活自理情况
				"cardPersonDetail.selfCareAbilityType.name",
				// 婚姻状况
				"cardPersonDetail.marryStateType.name",
				// 居住小区类型
				"cardPersonDetail.biotope.name",
				// 卡的生效时间
				"cardInfo.send_card_date",
				// 卡是否可用
				"cardInfo.enabled",
				// 银行卡号
				"cardInfo.bankCard",
				// 一本通号
				"cardInfo.bookId",
				// 北京通号
				"cardInfo.bjtNumber",
				// 一卡通号
				"cardInfo.yktNumber",
				// 审批状态
				"auditStatus.name",
				// 信息采集状态
				"gatherStatus.name",
				// 申请状态
				"application_status.name",
				// 制卡申请时间
				"cardInfo.business_card_application_time",
				// 制卡成功时间
				"cardInfo.business_card_making_time",
				// 补换卡号
				"cardInfo.replace_the_card_number",
				// 补换卡制卡时间
				"cardInfo.replace_card_making_time",
				// 临时卡号
				"cardInfo.temporary_card_number",
				// 临时卡制卡时间
				"cardInfo.temporary_card_making_time",
				// 制卡是否成功
				"createCardSuccess",
				// 制卡失败原因
				"createCardFailInfo",
				// 是否推送
				"hasPushed",
				// 区县是否推送
				"cityPushed",
				// 区县推送时间
				"creatCardPushDate",
				// 核查是否推送
				"hcHasPushed",
				// 核查推送时间
				"hcPushedDate",
				// 核查是否成功
				"hcSuccess",
				// 核查回盘时间
				"hcInTime",
				// 核查失败原因
				"hcInTime",
				// 制卡推送时间
				"creatCardPushDate",
				// 制卡回盘时间
				"createCardInDate",
				// 是否死亡
				"iseffect.name",
				// 是否可用
				"enabled" };
		int defaultWidth = 7000;
		boolean defaultIsAutoSize = true;
		boolean defaultIsGroupColumn = false;
		boolean defaultIsIndexColumn = false;
		column = getColumnInstance(columnNames[0], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[1], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[2], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[3], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[4], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[5], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[6], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[7], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[8], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[9], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[10], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[11], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[12], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[13], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[14], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[15], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[16], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[17], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[18], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[19], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[20], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[21], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[22], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[23], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[24], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[25], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[26], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[27], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[28], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[29], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[30], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[31], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[32], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[33], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[34], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[35], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[36], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[37], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[38], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[39], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[40], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[41], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[42], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[43], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[44], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[45], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[46], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[47], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[48], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[49], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[50], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[51], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[52], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[53], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[54], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[55], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[56], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[57], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[58], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[59], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[60], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[61], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[62], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		column = getColumnInstance(columnNames[63], defaultWidth,
				defaultIsAutoSize, defaultIsGroupColumn, defaultIsIndexColumn);
		columnList.add(column);
		return columnList;
	}

	/**
	 * 定制（设置）Excel中每列的属性
	 * 
	 * @param columnName
	 * @param width
	 * @param isAutoSize
	 * @param isGroupColumn
	 * @param isIndexColumn
	 * @return
	 */
	private Column getColumnInstance(String columnName, int width,
			boolean isAutoSize, boolean isGroupColumn, boolean isIndexColumn) {
		Column column;
		column = new Column();
		column.setColumnName(columnName);
		column.setWidth(width);
		column.setIsAutoSize(isAutoSize);
		column.setIsGroupColumn(isGroupColumn);
		column.setIsIndexColumn(isIndexColumn);
		return column;
	}

	/**
	 * 添加表尾
	 */
	private ColFooterCell getColFooterCell(String totalStr) {
		ColFooterCell colFooterCell = new ColFooterCell();
		colFooterCell.setCellName(totalStr);
		return colFooterCell;
	}

}
