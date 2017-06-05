package com.capinfo.omp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.dao.SearchCriteriaBuilder;
import com.capinfo.framework.dao.impl.restriction.RestrictionExpression;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.framework.web.service.impl.CommonsDataOperationServiceImpl;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.Omp_old_order;
import com.capinfo.omp.model.Omp_voice_order;
import com.capinfo.omp.parameter.OldParameter;
import com.capinfo.omp.parameter.UserInfoParameter;
import com.capinfo.omp.parameter.VoiceParameter;
import com.capinfo.omp.service.OldService;
import com.capinfo.omp.service.VoiceService;
import com.capinfo.omp.utils.Page;
import com.capinfo.omp.ws.client.ClientGetVoiceDataService;
import com.capinfo.omp.ws.model.ImKey;

@Service
public class VoiceServiceImpl extends
		CommonsDataOperationServiceImpl<Omp_voice_order, UserInfoParameter>
		implements VoiceService {

	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Autowired
	private OldService oldService;

	@Override
	public List<Omp_Old_Info> getOldContextList(VoiceParameter p,
			SystemUser user) {
		OldParameter parameter = new OldParameter();
		parameter.setName(p.getName());
		parameter.setIdCard(p.getIdCard());
		parameter.setZjNumber(p.getZjNumber());
		parameter.setCity(p.getCity());
		parameter.setCounty(p.getCounty());
		parameter.setStreet(p.getStreet());
		parameter.setCommunity(p.getCommunity());
		parameter.setPerPieceSize(p.getPerPieceSize());
		parameter.setCurrentPieceNum(p.getCurrentPieceNum());
		parameter.setSource("voice");

		List<Omp_Old_Info> oldlist = oldService.getOldContextList(parameter,
				user);
		return oldlist;
	}

	@Override
	public int getCount(VoiceParameter p, SystemUser user) {
		OldParameter parameter = new OldParameter();
		parameter.setName(p.getName());
		parameter.setIdCard(p.getIdCard());
		parameter.setZjNumber(p.getZjNumber());
		parameter.setCity(p.getCity());
		parameter.setCounty(p.getCounty());
		parameter.setStreet(p.getStreet());
		parameter.setCommunity(p.getCommunity());

		int count = oldService.getCount(parameter, user);
		return count;
	}

	@Override
	public String sendOrder(String id) {
		String sql = "select ov.executeType,ov.startTime,ov.endTime,oi.ZJNUMBER 'landLineNumber',oi.name 'name',oi.HOUSEHOLD_COMMUNITY_ID 'residenceCommunity',ov.voiceFIleId,ov.voiceFileAddress from omp_voice_order ov,omp_old_info oi where ov.id= ? and ov.oldId=oi.ID";
		List<Map<String, Object>> map = JdbcTemplate.queryForList(sql,
				new Object[] { id });
		JSONObject jsonObject = JSONObject.fromObject(map.get(0));
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

	@SuppressWarnings("deprecation")
	@Override
	public void toupdete(String string) {
		String sql = "UPDATE `omp_voice_order` SET `send_flag`='1' WHERE number='"
				+ string + "'";
		JdbcTemplate.update(sql);
		String sql2 = "UPDATE `omp_voice_order` SET `execute_flag`= '3' WHERE number='"
				+ string + "'";
		JdbcTemplate.update(sql2);

	}

	@Override
	public List<Map<String, Object>> getshell() {
		String sql = "SELECT v.upload_flag from omp_voice_order v";
		List<Map<String, Object>> queryForList = JdbcTemplate.queryForList(sql);
		return queryForList;
	}

	@Override
	public List<Map<String, Object>> getvoicelist(Page page, String name,
			SystemUser user) {

		String uName = "";
		if (!"admin".equals(user.getLogonName())) {
			uName = " AND v.agent_id  =  '" + user.getLogonName() + "'";
		}
		if (!StringUtils.isEmpty(name)) {
			name = " AND v.`voiceName` LIKE '%" + name + "%'";
		} else {
			name = "";
		}
		String sql = "select v.id 'id',v.voiceName 'n',v.voiceTime 't',v.remark 'r',v.content from omp_voice_info v where v.stat = '1' "
				+ uName
				+ " order by v.id desc"
				+ name
				+ " LIMIT "
				+ (page.getCurrentPage() - 1)
				* page.getPageSize()
				+ ", "
				+ page.getPageSize();
		List<Map<String, Object>> queryForList = JdbcTemplate.queryForList(sql);
		return queryForList;
	}

	@Override
	public int getvoicelist(String name, SystemUser user) {
		if (!StringUtils.isEmpty(name)) {
			name = " AND v.`voiceName` LIKE '%" + name + "%'";
		} else {
			name = "";
		}
		if (!StringUtils.isEmpty(user.getLogonName())
				&& !"admin".equals(user.getLogonName())) {
			name = " AND v.`agent_id` LIKE '%" + user.getLogonName() + "%'";
		} else {
			name = "";
		}
		String sql = "select count(v.ID) from omp_voice_info v where v.stat = '1' "
				+ name;
		int maxRows = JdbcTemplate.queryForInt(sql);
		return maxRows;
	}

	/*
	 * @Override public List<Map<String, Object>> getvoicelist() { String sql =
	 * "select v.voiceName 'n',v.voiceTime 't',v.remark 'r' from omp_voice_info v"
	 * ; List<Map<String, Object>> querList = JdbcTemplate.queryForList(sql);
	 * return querList; }
	 * 
	 * @Override public List<Map<String, Object>> voicequery(String name) {
	 * List<Map<String, Object>> list = null; String sql =
	 * "select * from omp_voice_info vi where vi.voiceName LIKE '%"+name+"%'";
	 * list = JdbcTemplate.queryForList(sql); return list;
	 * 
	 * }
	 */
	/**
	 * 失效
	 */
	@Override
	public int SenVoice(String vid, String cid) {
		int result = 0;
		String sql = "insert into omp_voice_order (oldId, voiceId,voiceFIleId, send_flag) SELECT t.ID , 1 voiceId, 3 voiceFIleId, 0 send_flag FROM omp_old_info t WHERE t.HOUSEHOLD_COMMUNITY_ID = '"
				+ cid
				+ "' AND 0 = (SELECT COUNT(*) FROM omp_voice_order b WHERE b.oldId = t.ID AND b.voiceId = '"
				+ vid + "' )";
		result = JdbcTemplate.update(sql);
		if (result > 0) {
			result++;
		}

		return result;
	}

	@Override
	public String checkDeBatchSendInstructions() {
		String sql = "SELECT GROUP_CONCAT(o.oldId) as id FROM omp_voice_order o WHERE o.startTime > SYSDATE() AND o.startTime<SYSDATE()+interval 300 second AND o.send_flag=0 LIMIT 0,10";
		Map<String, Object> map = JdbcTemplate.queryForMap(sql);
		return map.get("id").toString();
	}

	@Override
	public List<Omp_old_order> getOldList(VoiceParameter p, SystemUser user) {
		List<Omp_old_order> orderList = null;
		OldParameter parameter = new OldParameter();
		parameter.setName(p.getName());
		parameter.setIdCard(p.getIdCard());
		parameter.setZjNumber(p.getZjNumber());
		parameter.setCity(p.getCity());
		parameter.setCounty(p.getCounty());
		parameter.setStreet(p.getStreet());
		parameter.setCommunity(p.getCommunity());
//		parameter.setIsGenerationOrder("order");
		parameter.setPerPieceSize(p.getPerPieceSize());
		parameter.setCurrentPieceNum(p.getCurrentPieceNum());
		parameter.setSource("voiceorder");	//语音指令

		// List<Omp_Old_Info> oldList = oldService.getOldContextList(page, name,
		// idCard, zjNumber, county, street, community, null, null, user);
		List<Omp_Old_Info> oldList = oldService.getOldContextList(parameter,
				user);

		if (oldList.size() > 0) {
			SearchCriteriaBuilder<Omp_voice_order> searchCriteriaBuilder = new SearchCriteriaBuilder<Omp_voice_order>(
					Omp_voice_order.class);

			searchCriteriaBuilder.addQueryCondition("send_flag",
					RestrictionExpression.EQUALS_OP, p.getEntity()
							.getSend_flag());
			searchCriteriaBuilder.addQueryCondition("execute_flag",
					RestrictionExpression.EQUALS_OP, p.getEntity()
							.getExecute_flag());

			searchCriteriaBuilder.addLimitCondition(
					(parameter.getCurrentPieceNum() - 1)
							* parameter.getPerPieceSize(),
					parameter.getPerPieceSize());

			String sql = "";
			String ids = "";
			for (Omp_Old_Info old : oldList) {
				ids += old.getId() + ",";
			}
			ids = ids.substring(0, ids.length() - 1);
			System.out.println(ids);
			sql += " oldId In (" + ids + ")";
			if (!"".equals(sql)) {
				searchCriteriaBuilder.addAdditionalRestrictionSql(sql);
			}
			GeneralService g = getGeneralService();

			orderList = g.getObjects(searchCriteriaBuilder.build());
		}

		return orderList;

	}

	@Override
	public int getOlCount(VoiceParameter p, SystemUser user) {
		int count = 0;

		OldParameter parameter = new OldParameter();
		parameter.setName(p.getName());
		parameter.setIdCard(p.getIdCard());
		parameter.setZjNumber(p.getZjNumber());
		parameter.setCity(p.getCity());
		parameter.setCounty(p.getCounty());
		parameter.setStreet(p.getStreet());
		parameter.setCommunity(p.getCommunity());
		parameter.setIsGenerationOrder("order");

		List<Omp_Old_Info> oldList = oldService.getOldContextList(parameter,
				user);

		if (oldList.size() > 0) {
			SearchCriteriaBuilder<Omp_voice_order> searchCriteriaBuilder = new SearchCriteriaBuilder<Omp_voice_order>(
					Omp_voice_order.class);

			searchCriteriaBuilder.addQueryCondition("send_flag",
					RestrictionExpression.EQUALS_OP, p.getEntity()
							.getSend_flag());
			searchCriteriaBuilder.addQueryCondition("execute_flag",
					RestrictionExpression.EQUALS_OP, p.getEntity()
							.getExecute_flag());

			String sql = "";
			String ids = "";
			for (Omp_Old_Info old : oldList) {
				ids += old.getId() + ",";
			}
			ids = ids.substring(0, ids.length() - 1);
			System.out.println(ids);
			sql += " oldId In (" + ids + ")";
			if (!"".equals(sql)) {
				searchCriteriaBuilder.addAdditionalRestrictionSql(sql);
			}
			GeneralService g = getGeneralService();

			count = g.getCount(searchCriteriaBuilder.build());
		}

		return count;
	}

	@Override
	public void uploadMiddle(String id) {
		String sql = "INSERT into omp_middle (vid) VALUES('" + id + "')";
		JdbcTemplate.update(sql);
	}

	@Override
	public String middle() {
		String sql = "SELECT vid from omp_middle where id = (SELECT max(id) FROM omp_middle)";
		Map<String, Object> map = JdbcTemplate.queryForMap(sql);
		return map.get("vid").toString();
	}

	@Override
	public void saveviceoder(String id, String vid, String executeType,
			String startTime, String endTime, String t, SystemUser user) {

		String sql = "INSERT into omp_voice_order(oldId,executeType,startTime,endTime,voiceFIleId,voiceFileAddress,number,agent_id) VALUES ("
				+ id
				+ ",'"
				+ executeType
				+ "','"
				+ startTime
				+ "','"
				+ endTime
				+ "','"
				+ vid
				+ "',(select i.voiceFileAddress from omp_voice_info i where id = "
				+ vid + "),'" + t + "'," + user.getId() + ")";
		JdbcTemplate.update(sql);
	}

	@Override
	public String resultVice() {
		String sql = "select MAX(o.id)'id' from omp_voice_order o";
		Map<String, Object> map = JdbcTemplate.queryForMap(sql);
		return map.get("id").toString();
	}

	@Override
	public String sendvice(String id, String t) {
		String sql = "select ov.executeType,ov.startTime,ov.endTime,oi.ZJNUMBER 'landLineNumber',oi.name 'name',oi.HOUSEHOLD_COMMUNITY_ID 'residenceCommunity',ov.voiceFIleId,ov.voiceFileAddress from omp_voice_order ov,omp_old_info oi where ov.id= ? and ov.oldId=oi.ID";
		List<Map<String, Object>> map = JdbcTemplate.queryForList(sql,
				new Object[] { id });
		JSONObject jsonObject = JSONObject.fromObject(map.get(0));
		String string = jsonObject.toString();
		String replace = string.replace("\\", "");
		StringBuffer stringBuffer = new StringBuffer(replace);
		StringBuffer insert = stringBuffer.insert(1,
				"\"generateSerialNumber\":\"" + t + "\",");
		System.out.println(insert);
		return insert.toString();
	}

	@Override
	public void upMessg(ImKey imKey, String id) {
		String sql = "UPDATE `omp_voice_order` SET `errorMessage`='"
				+ imKey.getErrorMessage() + "' WHERE Id='" + id + "'";
		JdbcTemplate.update(sql);
	}

	@Override
	public String resultStrtTime(String id, String vid) {
		String sql = "select o.startTime from omp_voice_order o where o.oldId = "
				+ id + " and o.id = " + vid + "";
		Map<String, Object> map = JdbcTemplate.queryForMap(sql);
		return map.get("startTime").toString();
	}

	@Override
	public String resultendTime(String id, String vid) {
		String sql = "select o.endTime from omp_voice_order o where o.oldId = "
				+ id + " and o.id = " + vid + "";
		Map<String, Object> map = JdbcTemplate.queryForMap(sql);
		return map.get("endTime").toString();
	}

	@Override
	public boolean getcount() {
		String sql = "SELECT count(id) from omp_voice_order where send_flag = 0";
		int i = JdbcTemplate.queryForInt(sql);
		return i > 0;
	}

	@Override
	public String resultOrderId() {
		String sql = "select GROUP_CONCAT(i.id)'id' from omp_old_info i";
		Map<String, Object> map = JdbcTemplate.queryForMap(sql);
		return map.get("id").toString();
	}

	@Override
	public String ResultOderById(String comid) {
		String sql = "SELECT GROUP_CONCAT(oldo.id)'id' FROM (SELECT old.id,old.`NAME`,old.idcard,old.community,old.county,old.street,old.ZJNUMBER FROM omp_old_info i,(SELECT i.id,i.`NAME`,i.CERTIFICATES_NUMBER idcard,r1.`ID` community,r2.`NAME` county,r3.`NAME` street,i.ZJNUMBER FROM omp_old_info i,omp_region r1,omp_region r2,omp_region r3	WHERE	i.HOUSEHOLD_COMMUNITY_ID = r1.ID AND i.HOUSEHOLD_COUNTY_ID = r2.ID	AND i.HOUSEHOLD_STREET_ID = r3.ID) old WHERE	i.id = old.id  AND old.community = "
				+ comid + ") oldo WHERE 1=1";
		Map<String, Object> map = JdbcTemplate.queryForMap(sql);
		return map.get("id").toString();
	}

	@Override
	public void resultVOrders(ImKey imKey, String id, SystemUser user) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		String date = df.format(new Date());
		String sql = "insert into omp_order_number (oid,send_date,send_flag,number,returnType,agent_id) VALUES("
				+ id
				+ ",'"
				+ date
				+ "',"
				+ imKey.getStatusCode()
				+ ",'"
				+ imKey.getGenerateSerialNumber()
				+ "','2',"
				+ user.getId()
				+ ")";
		// + "123" + "','2','" + username + "')";
		oldService.saveLogger("5", "语音指令表", "" + user.getName() + "", ""
				+ imKey.getStatusCode() + "");
		// "1");
		JdbcTemplate.update(sql);
	}

	@Override
	public void deleteVoidByid(String vid) {
		String sql = "UPDATE omp_voice_info SET stat='0' WHERE (`ID`='" + vid
				+ "')";
		JdbcTemplate.update(sql);

	}

	@Override
	public void deleteVoidsByid(String vid) {
		if (vid.contains(",")) {
			String[] ids = vid.split(",");
			System.out.println(ids.length);
			for (String id : ids) {
				String sql = "UPDATE omp_voice_info SET stat='0' WHERE (`ID`='"
						+ id + "')";
				JdbcTemplate.update(sql);
			}
		}else{
			deleteVoidByid(vid);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean queryCount(String ids,SystemUser user) {
		String userName = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		String uName = "";
		if ("admin".equals(userName)) {
			return true;
		} else {
			uName = " AND u.USER_NAME  =  '" + userName + "'";
		}

		String sqlRest = "";
		String sql1 = "select sum(o.num) from omp_old_info o where o.id in ("
				+ ids + ") ";
		int i = JdbcTemplate.queryForInt(sql1);
		String sql2 = "select count(*) from omp_old_info o where o.id in ("
				+ ids + ") ";
		int j = JdbcTemplate.queryForInt(sql2);
		if (i == j) {
			sqlRest = "update omp_old_info o set o.num = 0 where o.id in ("
					+ ids + ") ";
			return true;
		} else {
			String sql3 = "select u.num from users u where 1=1  " + uName;
			int k = JdbcTemplate.queryForInt(sql3);
			if (i + k >= j) {
				return true;
			}
			return false;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public String numRest(String id,SystemUser user) {
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

		count = "select sum(o.num) from omp_old_info o where o.id = " + id;
		int int1 = JdbcTemplate.queryForInt(count);
		if (1 == int1) {
			sqlRest = "update omp_old_info o set o.num = 0 where o.id in ("
					+ id + ") ";
			JdbcTemplate.update(sqlRest);
			// sata = 1;
			// 老人
			return "old";
		} else {
			sqlRest = "select sum(o.num) from users o where 1=1 " + uName;
			int i = JdbcTemplate.queryForInt(sqlRest);
			i = i - 1;
			sqlRest = "update users o set o.num = " + i + " where 1=1" + uName;
			JdbcTemplate.update(sqlRest);
			// sata = 2;
			// 用户
			return i+"";
		}
	}

	@Override
	public void rollback(String id, SystemUser user, String voiceSata) {
		if (voiceSata != null) {
			String sql = "";
			if ("old".equals(voiceSata)) {
				// 老人
				sql = "update omp_old_info o set o.num = 1 where o.id in ("
						+ id + ") ";
			} else {
				// 客户
				int i = Integer.parseInt(voiceSata);
				i = i + 1;
				sql = "update users o set o.num = " + i
						+ " where 1=1 and o.id = " + user.getId();
			}
			JdbcTemplate.update(sql);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public UserInfoParameter getUserInfo(SystemUser user) {
		if (user != null) {
			String agent_id = "";
			if(user.getLeave()>1){
				agent_id = "and o.agent_id = "+ user.getId();
			}
			/**
			 * 语音
			 */
			// 发送成功
			String sql1 = "select count(*) from omp_voice_order o inner join omp_old_info oi on o.oldId = oi.id  where o.send_flag = 1 "+agent_id;
			Long voiceSendSuc = JdbcTemplate.queryForLong(sql1);
			// 发送失败
			String sql2 = "select count(*) from omp_voice_order o inner join omp_old_info oi on o.oldId = oi.id  where o.send_flag = 0 "+agent_id;
			Long voiceSendFail = JdbcTemplate.queryForLong(sql2);

			// // 执行成功
			String sql3 = "select count(*) from omp_voice_order o   where o.execute_flag = 1 "+agent_id;
//			String sql3 = "select count(*) from omp_voice_order o inner join omp_old_info oi on o.oldId = oi.id  where o.execute_flag = 1 "+agent_id;
			Long executeSuc = JdbcTemplate.queryForLong(sql3);
			// 执行失败
			String sql4 = "select count(*) from omp_voice_order o inner join omp_old_info oi on o.oldId = oi.id  where o.execute_flag = 0 "+agent_id;
			Long executeFail = JdbcTemplate.queryForLong(sql4);
			//
			// // 未接听
			String sql5 = "select count(*) from omp_voice_order o inner join omp_old_info oi on o.oldId = oi.id  where o.execute_flag = 3 "+agent_id;
			Long notAnswer = JdbcTemplate.queryForLong(sql5);
			//
			// // 未返回
			String sql6 = "select count(*) from omp_voice_order o inner join omp_old_info oi on o.oldId = oi.id  where o.execute_flag is null "+agent_id;
			Long notReturn = JdbcTemplate.queryForLong(sql6);

			// 语音发送总次数
			String sql8 = "select count(*) from omp_voice_order o where 1=1 "+agent_id;
			Long voiceCount = JdbcTemplate.queryForLong(sql8);

			/**
			 * 指令
			 */

			// 发送成功
			String sql9 = "select count(*) from omp_order_number o inner join omp_old_info oi on o.oid = oi.id  where o.returntype= 1 and o.send_flag= 1  "+agent_id;
			Long orderSuc = JdbcTemplate.queryForLong(sql9);

			// 发送失败
			String sq20 = "select count(*) from omp_order_number o inner join omp_old_info oi on o.oid = oi.id  where o.returntype= 1 and o.send_flag= 0  "+agent_id;
			Long orderFail = JdbcTemplate.queryForLong(sq20);

			// 执行成功
			String sq22 = "select count(*) from omp_order_number o   where o.returntype= 1 and o.execute_flag = 1  "+agent_id;
//			String sq22 = "select count(*) from omp_order_number o inner join omp_old_info oi on o.oid = oi.id  where o.returntype= 1 and o.execute_flag = 1  "+agent_id;
//			String sq22 = "select count(*) from omp_old_order o inner join omp_old_info oi on o.oldId = oi.id  where  o.execute_flag = 1  "+agent_id;
			Long orderexecuteSuc = JdbcTemplate.queryForLong(sq22);

			// 执行失败
//			String sq23 = "select count(*) from omp_order_number o inner join omp_old_info oi on o.oid = oi.id  where o.returntype= 1 and o.execute_flag = 0 "+agent_id;
			String sq23 = "select count(*) from omp_old_order o inner join omp_old_info oi on o.oldId = oi.id  where  o.execute_flag = 0 "+agent_id;
			Long orderexecuteFail = JdbcTemplate.queryForLong(sq23);

			// 总数
			String sq21 = "select count(*) from omp_order_number o where o.returntype= 1  "+agent_id;
			Long orderCount = JdbcTemplate.queryForLong(sq21);

			// 剩余发送次数
			String sql7 = "select u.num from users u where 1=1 and u.id = "
					+ user.getId();
			Long remainCount = JdbcTemplate.queryForLong(sql7);

			UserInfoParameter userInfo = new UserInfoParameter();
			userInfo.setOrderexecuteSuc(orderexecuteSuc);
			userInfo.setOrderexecuteFail(orderexecuteFail);
			userInfo.setExecuteFail(executeFail);
			userInfo.setExecuteSuc(executeSuc);
			userInfo.setNotAnswer(notAnswer);
			userInfo.setNotReturn(notReturn);
			userInfo.setVoiceSendFail(voiceSendFail);
			userInfo.setVoiceSendSuc(voiceSendSuc);

			userInfo.setOrderFail(orderFail);
			userInfo.setOrderSuc(orderSuc);

			userInfo.setRemainCount(remainCount);
			userInfo.setVoiceCount(voiceCount);
			userInfo.setOrderCount(orderCount);

			return userInfo;

		}

		return null;
	}

	@Override
	public String repeatVoice(Long orderId,SystemUser user) {
		 
		Omp_voice_order voice = getGeneralService().getObjectById(Omp_voice_order.class, orderId);
		ClientGetVoiceDataService vice = new ClientGetVoiceDataService();
		ImKey imKey = null;
		if(!queryCount(String.valueOf(voice.getOldId()),user)){
			return "您发送的语音条数已超出，请充值后再行发送，充值电话：褚-84933228";
		}
		//语音发送次数-1
		String voiceSata = numRest(String.valueOf(voice.getOldId()),user);
		
		int x;// 定义两变量
		Random ne = new Random();// 实例化一个random的对象ne
		x = ne.nextInt(99999 - 10000 + 1) + 1000;// 为变量赋随机值10000-99999
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String t = time + x;
		//修改语音发送记录
		Date date=new Date();
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date tomorrow=calendar.getTime();
		voice.setStartTime(date);
		voice.setEndTime(tomorrow);
		voice.setNumber(Long.parseLong(t));
		getGeneralService().saveOrUpdate(voice);
		String json = sendvice(String.valueOf(voice.getId()), t);
		try {
			imKey = vice.svoice(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("语音发送返回数据"+imKey.getStatusCode()+","+imKey.getGenerateSerialNumber()+","+imKey.getExecutionTime()+","+imKey.getReturnType()+","+imKey.getErrorMessage());
		String number = imKey.getGenerateSerialNumber();
		if ("1".equals(imKey.getStatusCode())) {
			//成功	将记录添加到number表
			resultVOrders(imKey, String.valueOf(voice.getOldId()), user);
			toupdete(number);
			upMessg(imKey, String.valueOf(voice.getOldId()));
		}else{
			//失败回滚
			rollback(String.valueOf(voice.getOldId()),user,voiceSata);
			return "发送失败 : /n "+imKey.getErrorMessage();
			
		}
		return "发送成功";
	}

}
