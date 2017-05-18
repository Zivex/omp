package com.capinfo.omp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.incrementer.HsqlMaxValueIncrementer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.model.system.User;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.framework.web.service.impl.CommonsDataOperationServiceImpl;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.Omp_old_order;
import com.capinfo.omp.model.Omp_voice_order;
import com.capinfo.omp.parameter.OldParameter;
import com.capinfo.omp.parameter.OrderParameter;
import com.capinfo.omp.service.OldService;
import com.capinfo.omp.service.OrderService;
import com.capinfo.omp.utils.Page;
import com.capinfo.omp.ws.client.ClientGetDataService;
import com.capinfo.omp.ws.model.ImKey;

@Service
public class OrderServiceImpl extends
CommonsDataOperationServiceImpl<Omp_old_order, OrderParameter>  implements
		OrderService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private OldService oldService;

	@Override
	public int getOrderCount(OrderParameter parameter,SystemUser user) {
		// 查询用户区域
		String rname = "";
		if("g".equals(user.getAccount_type())){
			rname =" and yiji is null";
			switch (user.getLeave()) {
			case 3:
				rname += " and i.HOUSEHOLD_COUNTY_ID = " + user.getRid();
				break;
			case 4:
				rname += " and i.HOUSEHOLD_STREET_ID = " + user.getRid();
				break;
			case 5:
				rname += " and i.HOUSEHOLD_COMMUNITY_ID = " + user.getRid();
				break;
			}
		}
		String ji = "";
		if("m".equals(user.getAccount_type()) || "b".equals(user.getAccount_type())){
			switch (user.getLeave()) {
			case 1: String idSsql = "select t.id from composition t where t.prient_id is null and t.cid="+user.getId();
			int id = jdbcTemplate.queryForInt(idSsql);
			ji = " and i.yiji = " + id;
			break;
			case 2:
				ji = " and i.yiji = " + user.getYiji();
				break;
			case 3:
				ji = " and i.erji = " + user.getErji();
				break;
			case 4:
				ji = " and i.sjji = " + user.getSjji();
				break;
			case 5:
				ji = " and i.siji = " + user.getSiji();
				break;
			}
		}
		
		String name = "";
		String idCard = "";
		String zjNumber = "";
		String county = "";
		String street = "";
		String community = "";
		String send_flag = "";
		String execute_flag = "";
		if (!StringUtils.isEmpty(parameter.getName())) {
			name = " AND oldo.`NAME` LIKE '%" + parameter.getName() + "%'";
		}
		if (idCard != null &&!StringUtils.isEmpty(parameter.getIdCard())) {
			idCard = " AND oldo.idCard = '" + parameter.getIdCard() + "'";
		}
		if (!StringUtils.isEmpty(parameter.getZjNumber())) {
			zjNumber = " AND oldo.ZJNUMBER = '" + parameter.getZjNumber() + "'";
		}
		if (!StringUtils.isEmpty(parameter.getCounty())) {
			county = " AND i.HOUSEHOLD_COUNTY_ID = '" + parameter.getCounty() + "'";
		}
		if (!StringUtils.isEmpty(parameter.getStreet())) {
			street = " AND i.HOUSEHOLD_STREET_ID = '" + parameter.getStreet() + "'";
		}
		if (!StringUtils.isEmpty(parameter.getCommunity())) {
			community = " AND i.HOUSEHOLD_COMMUNITY_ID = '" + parameter.getCommunity() + "'";
		}
		if (parameter.getSend_flag()!=null) {
			send_flag = " AND oldo.send_flag = '" + parameter.getSend_flag() + "'";
		}
		if (parameter.getExecute_flag()!=null) {
			execute_flag = " AND oldo.execute_flag = '" + parameter.getExecute_flag() + "'";
		}
		String sql = "SELECT count(oldo.id) FROM "
				+ "(SELECT old.id,old.`NAME`,old.idcard,old.community,old.county,old.street,"
				+ "old.ZJNUMBER,o.send_flag,o.execute_flag FROM omp_old_order o,"
				+ "(SELECT i.id,i.`NAME`,i.CERTIFICATES_NUMBER idcard,r1.`NAME` community,r2.`NAME`"
				+ " county,r3.`NAME` street,i.ZJNUMBER FROM omp_old_info i,omp_region r1,omp_region r2,"
				+ "omp_region r3	WHERE	i.HOUSEHOLD_COMMUNITY_ID = r1.ID"
				+ " AND i.HOUSEHOLD_COUNTY_ID = r2.ID	AND i.HOUSEHOLD_STREET_ID = r3.ID "
				+ county + street + community + ji + rname + ") old"
				+ "	WHERE	o.oldId = old.id) oldo WHERE 1=1 " + name + idCard
				+ zjNumber + send_flag + execute_flag;
		int forInt = jdbcTemplate.queryForInt(sql);
		return forInt;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String , Object> getOrderList(OrderParameter p,SystemUser user) {
		List<Omp_old_order> orderList = null;
		OldParameter parameter = new OldParameter();
		parameter.setName(p.getName());
		parameter.setIdCard(p.getIdCard());
		parameter.setZjNumber(p.getZjNumber());
		parameter.setCity(p.getCity());
		parameter.setCounty(p.getCounty());
		parameter.setStreet(p.getStreet());
		parameter.setCommunity(p.getCommunity());
		parameter.setIsGenerationOrder("order");
		parameter.setPerPieceSize(p.getPerPieceSize());
		parameter.setCurrentPieceNum(p.getCurrentPieceNum());
		//int count = oldService.getCount(parameter, user);
		List<Omp_Old_Info> oldList = oldService.getOldContextList(parameter, user);
		
		String ids = "";
		if(oldList.size()>0){
		SearchCriteriaBuilder<Omp_old_order> searchCriteriaBuilder = new SearchCriteriaBuilder<Omp_old_order>(
				Omp_old_order.class);
		
		searchCriteriaBuilder.addQueryCondition("send_flag",
				RestrictionExpression.EQUALS_OP, p.getSend_flag());
		searchCriteriaBuilder.addQueryCondition("execute_flag",
				RestrictionExpression.EQUALS_OP, p.getExecute_flag());
		for (Omp_Old_Info old : oldList) {
			ids+=old.getId()+",";
		}
		ids = ids.substring(0,ids.length() - 1);
		System.out.println(ids);
		String sql = "";
		 sql +=" oldId In ("+ids+")";
		if (!"".equals(sql)) {
			searchCriteriaBuilder.addAdditionalRestrictionSql(sql);
		}
		orderList = getGeneralService().getObjects(
				searchCriteriaBuilder.build());
		}
		Map<String , Object> map = new HashMap<String , Object>();
		
		String send_flag= "";
		if(p.getSend_flag() != null){
			send_flag += " and od.send_flag="+p.getSend_flag();
		}
		String execute_flag= "";
		if(p.getExecute_flag()!= null){
			execute_flag += " and od.execute_flag="+p.getExecute_flag();
		}
		String name = "";
		if(p.getName()!= null && !p.getName().isEmpty()){
			name += " and oi.`NAME`='"+p.getName()+"'";
		}
		String idCard = "";
		if(p.getIdCard()!= null && !p.getIdCard().isEmpty()){
			idCard += " and oi.CERTIFICATES_NUMBER="+p.getIdCard();
		}
		String zjNUmber = "";
		if(p.getZjNumber()!= null && !p.getZjNumber().isEmpty()){
			zjNUmber += " and oi.ZJNUMBER="+p.getZjNumber();
		}
		String city = "";
		if(p.getCity()!= null  && !p.getCity().isEmpty()){
			city += " and oi.HOUSEHOLD_CITY_ID="+p.getCity();
		}
		String country = "";
		if(p.getCounty()!= null  && !p.getCounty().isEmpty()){
			country += " and oi.HOUSEHOLD_COUNTY_ID="+p.getCounty();
		}
		String stree = "";
		if(p.getStreet()!= null  && !p.getStreet().isEmpty()){
			stree += " and oi.HOUSEHOLD_STREET_ID="+p.getStreet();
		}
		String community = "";
		if(p.getCommunity()!= null  && !p.getCommunity().isEmpty()){
			community += " and oi.HOUSEHOLD_COMMUNITY_ID="+p.getCommunity();
		}
		
		String countSql = "select count(*) from omp_old_order od INNER JOIN omp_old_info oi on od.oldId=oi.ID where 1=1 and oi.call_id = 1 "+send_flag+execute_flag
				+name+idCard+zjNUmber+city+country+stree+community+" and od.oldId in("+ids+")";
		int count = jdbcTemplate.queryForInt(countSql);
		
		map.put("orderList", orderList);
		map.put("count", count);
		return map;
	}

	@Override
	public String sendOrder(String id) {
		String sql = "SELECT CASE WHEN f.updNumber = 0 THEN '1' ELSE  '2' END 'instructionsType',0 'changeType',f.ZJNUMBER 'landLineNumber',f.`NAME` 'name',f.HOUSEHOLD_COMMUNITY_ID 'residenceCommunity',CASE WHEN f.TELTYPE = '2' THEN '1' WHEN f.TELTYPE = '3' THEN '2' WHEN f.TELTYPE = '1' THEN '3' END 'userType',f.updNumber 'changeTimes',o.keyPointMessage 'keyPointMessage' FROM omp_old_info f,omp_old_order o WHERE o.oldId = f.ID AND f.ID = '"
				+ id + "'";
		// TODO Auto-generated method stub
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		JSONObject jsonObject = JSONObject.fromObject(map);
		String string = jsonObject.toString();
		String replace = string.replace("\\", "");
		StringBuffer stringBuffer = new StringBuffer(replace);
		int x;// 定义两变量
		Random ne = new Random();// 实例化一个random的对象ne
		x = ne.nextInt(99999 - 10000 + 1) + 1000;// 为变量赋随机值10000-99999
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String t = time + x;
		StringBuffer insert = stringBuffer.insert(1,
				"\"generateSerialNumber\":\"" + t + "\",");

		System.out.println(insert);
		return insert.toString();
	}

	@Override
	public void toupdete(String string, ImKey imKey) {
		// String sql =
		// "UPDATE `omp_old_order` SET `execute_flag`= (select n.execute_lag 'execute_flag' from omp_order_number n where n.number='"+imKey.getGenerateSerialNumber()+"' and n.oid ='"+string+"') WHERE oldId= '"+string+"'";
		String sql = "UPDATE `omp_old_order` SET `send_flag`='"
				+ imKey.getStatusCode() + "' WHERE oldId='" + string + "'";
//		+"1' WHERE oldId='" + string + "'";
		jdbcTemplate.update(sql);
		String sqls = "UPDATE `omp_old_order` SET `execute_flag`= 3 WHERE oldId='"
				+ string + "'";
		jdbcTemplate.update(sqls);

	}

	@Override
	public void delect(String id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM `omp_old_order` WHERE oldId=" + id;
		String sql1 = "UPDATE `omp_old_info` SET `isGenerationOrder`='0' WHERE (`ID`='"
				+ id + "')";
		jdbcTemplate.update(sql);
		jdbcTemplate.update(sql1);
	}

	@Override
	public String checkDeBatchSendInstructions() {
		// TODO Auto-generated method stub
		String sql = "select GROUP_CONCAT(oldId) as id from (SELECT oldId from omp_old_order where send_flag = 0 LIMIT 0,10) o ";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		return map.get("id").toString();
	}

	@Override
	public boolean getcount() {
		// TODO Auto-generated method stub
		String sql = "SELECT count(id) from omp_old_order where send_flag = 0";
		int i = jdbcTemplate.queryForInt(sql);
		return i > 0;
	}

	@Override
	public void upoder(String id) {
		String sql = "UPDATE `omp_old_order` SET `send_flag`='1',`execute_flag`='1' WHERE communityOrderId='"
				+ id + "'";
		jdbcTemplate.update(sql);

	}

	@Override
	public boolean upsendOrder(String id) throws Exception {
		int result = 0;
		ClientGetDataService c = new ClientGetDataService();
		StringBuffer insert = null;
		String sql = "SELECT CASE WHEN f.updNumber = 0 THEN '1' ELSE  '2' END 'instructionsType',0 'changeType',f.ZJNUMBER 'landLineNumber',f.`NAME` 'name',f.HOUSEHOLD_COMMUNITY_ID 'residenceCommunity',CASE WHEN f.TELTYPE = '2' THEN '1' WHEN f.TELTYPE = '3' THEN '2' WHEN f.TELTYPE = '1' THEN '3' END 'userType',f.updNumber 'changeTimes',o.keyPointMessage 'keyPointMessage' FROM omp_old_info f,omp_old_order o WHERE o.oldId = f.ID AND o.communityOrderId = '"
				+ id + "'";
		List<Map<String, Object>> map = jdbcTemplate.queryForList(sql);
		for (int i = 0; i < map.size(); i++) {
			System.out.println(map.get(i));
			JSONArray jsar = JSONArray.fromObject(map.get(i));
			// JSONObject jsonObject = JSONObject.fromObject(map);
			String string = jsar.toString();
			String replace = string.replace("\\", "");
			StringBuffer stringBuffer = new StringBuffer(replace);
			int x;// 定义两变量
			Random ne = new Random();// 实例化一个random的对象ne
			x = ne.nextInt(99999 - 10000 + 1) + 1000;// 为变量赋随机值10000-99999
			String time = new SimpleDateFormat("yyyyMMddHHmmss")
					.format(new Date());
			String t = time + x;
			insert = stringBuffer.insert(1, "\"generateSerialNumber\":\"" + t
					+ "\",");
			String json2 = insert.toString();
			String strs = json2.replaceAll("\\[\"", "\\{\"");
			String strs2 = strs.replaceAll(",\\{", ",");
			String star3 = strs2.replaceAll("\"\\[\\{", "\\[\\{");
			String json = star3.replaceAll("\\}\\]\"\\}\\]", "\\}\\]\\}");
			System.out.println(json);
			ImKey imKey = c.sendOrder(json);
			if (!"".equals(imKey.getStatusCode())
					&& "1".equals(imKey.getStatusCode())) {
				result++;
			}
			if (result == 1) {
				// 全部成功后修改指令状态
				upoder(id);
			}

		}

		return result > 0;
	}

	@Override
	public void upMessg(ImKey imKey, String id) {
		String sql = "UPDATE `omp_old_order` SET `errorMessage`='"
				+ imKey.getErrorMessage() + "' WHERE Id='" + id + "'";
		jdbcTemplate.update(sql);
	}

	@Override
	public List<Map<String, Object>> getOList(String id) {
		String sql = "SELECT i.id,i.`NAME`,i.ZJNUMBER,i.TELTYPE,r2.`NAME` q,r3.`NAME` j,r1.`NAME` s,r5.id typeid,o.errorMessage,o.execute_flag FROM omp_region r1,omp_region r2,omp_region r3,omp_phone_type r5,omp_old_info i,omp_old_order o WHERE STATE = 1 AND r1.id = i.HOUSEHOLD_COMMUNITY_ID AND r2.id = i.HOUSEHOLD_COUNTY_ID AND r5.phoneType = i.TELTYPE AND r3.ID = i.HOUSEHOLD_STREET_ID AND i.ID = o.oldId AND i.ID = '"
				+ id + "'";
		List<Map<String, Object>> getList = jdbcTemplate.queryForList(sql);
		return getList;
	}

	@Override
	public void resultOrder(ImKey imKey, String id, SystemUser user) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		String date = df.format(new Date());
		String sql = "insert into omp_order_number (oid,send_date,number,send_flag,returnType,agent_id) VALUES("
				+ id
				+ ",'"
				+ date
				+ "','"
				+ imKey.getGenerateSerialNumber()
				+ "'," + imKey.getStatusCode() + ",'1','" + user.getId() + "');";
//				+ "123"
//				+ "'," + "1" + ",'1','" + username + "');";
		// String sql =
		// "UPDATE omp_order_number n SET n.send_date = '"+date+"' ,n.returntype = '1' where n.number = '"+imKey.getGenerateSerialNumber()+"'";
		oldService.saveLogger("5", "按键指令表", "" + user.getName() + "",
//				"" + imKey.getStatusCode() + "");
				"" + Integer.toString(1) + "");
		jdbcTemplate.update(sql);
	}

	@Override
	public String RequestZJ(String zj) {
		String sql = "select COUNT(id) 'id' from omp_old_info i where i.ZJNUMBER = '"
				+ zj + "'";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		return map.get("vid").toString();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Boolean queryCount(String ids) {
		String userName = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		String uName = "";
		if ("admin".equals(userName)) {
			return true;
		} else {
			uName = " AND u.USER_NAME  =  '" + userName + "'";
		}

		String sqlRest = "";
		String sql1 = "select sum(o.ordernum) from omp_old_info o where o.id in ("
				+ ids + ") ";
		int i = jdbcTemplate.queryForInt(sql1);
		String sql2 = "select count(*) from omp_old_info o where o.id in ("
				+ ids + ") ";
		int j = jdbcTemplate.queryForInt(sql2);
		if (i == j) {
			sqlRest = "update omp_old_info o set o.ordernum = 0 where o.id in ("
					+ ids + ") ";
			return true;
		} else {
			String sql3 = "select u.num from users u where 1=1  " + uName;
			int k = jdbcTemplate.queryForInt(sql3);
			if (i + k >= j) {
				return true;
			}
			return false;
		}

	}
	
	@Override
	public String numRest(String id) {
		String sqlRest = "";
		String count = "";
		String userName = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		String uName = "";
		if ("admin".equals(userName)) {
			return null;
		} else {
			uName = " AND o.user_name  =  '" + userName + "'";
		}

		count = "select sum(o.ordernum) from omp_old_info o where o.id = " + id;
		int int1 = jdbcTemplate.queryForInt(count);
		if (1 == int1) {
			sqlRest = "update omp_old_info o set o.ordernum = 0 where o.id in ("
					+ id + ") ";
			jdbcTemplate.update(sqlRest);
			// sata = 1;
			// 老人
			return "old";
		} else {
			sqlRest = "select sum(o.num) from users o where 1=1 " + uName;
			int i = jdbcTemplate.queryForInt(sqlRest);
			i = i - 1;
			sqlRest = "update users o set o.num = " + i + " where 1=1" + uName;
			jdbcTemplate.update(sqlRest);
			// sata = 2;
			// 用户
			return Integer.toBinaryString(i);
		}
	}

	@Override
	public void rollback(String id, SystemUser user, String voiceSata) {
		if (voiceSata != null) {
			String sql = "";
			if ("old".equals(voiceSata)) {
				// 老人
				sql = "update omp_old_info o set o.ordernum = 1 where o.id in ("
						+ id + ") ";
			} else {
				// 客户
				int i = Integer.parseInt(voiceSata);
				i = i + 1;
				sql = "update users o set o.num = " + i
						+ " where 1=1 and o.id = "+user.getId();
			}
			jdbcTemplate.update(sql);
		}

	}

}
