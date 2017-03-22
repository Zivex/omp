package com.capinfo.voice.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.capinfo.framework.model.system.User;
import com.capinfo.framework.service.GeneralService;
import com.capinfo.omp.service.OldService;
import com.capinfo.omp.utils.Page;
import com.capinfo.omp.ws.model.ImKey;
import com.capinfo.voice.parameter.UserInfoParameter;
import com.capinfo.voice.service.VoiceService;

@Service
public class VoiceServiceImpl implements VoiceService {

	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Autowired
	private OldService oldService;
	@Autowired
	private GeneralService generalService;

	@Override
	public List<Map<String, Object>> getOldContextList(Page page, String name,
			String idCard, String zjNumber, String county, String street,
			String community,SystemUser user) {
		
		String uName = "";
		if (!"admin".equals(user.getLogonName())) {
			uName = " AND I.agent_id  =  '" + user.getId() + "'";
		}
		if (!StringUtils.isEmpty(name)) {
			name = " AND I.`NAME` LIKE '%" + name + "%'";
		}
		if (!StringUtils.isEmpty(idCard)) {
			idCard = " AND I.CERTIFICATES_NUMBER = '" + idCard + "'";
		}
		if (!StringUtils.isEmpty(zjNumber)) {
			zjNumber = "  AND I.ZJNUMBER = '" + zjNumber + "'";
		}
		if (!StringUtils.isEmpty(county)) {
			county = " AND I.HOUSEHOLD_COUNTY_ID = '" + county + "'";
		}
		if (!StringUtils.isEmpty(street)) {
			street = " AND I.HOUSEHOLD_STREET_ID = '" + street + "'";
		}
		if (!StringUtils.isEmpty(community)) {
			community = " AND I.HOUSEHOLD_COMMUNITY_ID = '" + community + "'";
		}
		String sql = "select i.id,i.`NAME`,i.ZJNUMBER,i.PHONE,i.TELTYPE,r2.`NAME` q,r3.`NAME` j,r1.`NAME` s,i.address,i.CERTIFICATES_NUMBER from omp_region r1,omp_region r2,omp_region r3,omp_old_info i "
				+ "where STATE = 1 "
				+ name
				+ idCard
				+ zjNumber
				+ uName
				+ county
				+ street
				+ community
				+ " and  r1.id = i.HOUSEHOLD_COMMUNITY_ID and r2.id = i.HOUSEHOLD_COUNTY_ID and r3.ID = i.HOUSEHOLD_STREET_ID LIMIT "
				+ (page.getCurrentPage() - 1)
				* page.getPageSize()
				+ ", "
				+ page.getPageSize();
		List<Map<String, Object>> queryForList = JdbcTemplate.queryForList(sql);
		return queryForList;
	}

	@Override
	public int getCount(String name, String idCard, String zjNumber,
			String county, String street, String community) {
		if (!StringUtils.isEmpty(name)) {
			name = " AND I.`NAME` LIKE '%" + name + "%'";
		}
		if (!StringUtils.isEmpty(idCard)) {
			idCard = " AND I.CERTIFICATES_NUMBER = '" + idCard + "'";
		}
		if (!StringUtils.isEmpty(zjNumber)) {
			zjNumber = "  AND I.ZJNUMBER = '" + zjNumber + "'";
		}
		if (!StringUtils.isEmpty(county)) {
			county = " AND I.HOUSEHOLD_COUNTY_ID = '" + county + "'";
		}
		if (!StringUtils.isEmpty(street)) {
			street = " AND I.HOUSEHOLD_STREET_ID = '" + street + "'";
		}
		if (!StringUtils.isEmpty(community)) {
			community = " AND I.HOUSEHOLD_COMMUNITY_ID = '" + community + "'";
		}

		String sql = "SELECT count(i.ID) FROM	OMP_OLD_INFO i WHERE i.STATE = 1 "
				+ name + idCard + zjNumber + county + street + community;
		int maxRows = JdbcTemplate.queryForInt(sql);
		return maxRows;
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
	public List<Map<String, Object>> getvoicelist(Page page, String name) {
		String userName = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		String uName = "";
		if (!"admin".equals(userName)) {
			uName = " AND v.agent_id  =  '" + userName + "'";
		}

		if (!StringUtils.isEmpty(name)) {
			name = " AND v.`voiceName` LIKE '%" + name + "%'";
		} else {
			name = "";
		}
		String sql = "select v.id 'id',v.voiceName 'n',v.voiceTime 't',v.remark 'r' from omp_voice_info v where v.stat = '1' "
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
	public int getvoicelist(String name) {
		if (!StringUtils.isEmpty(name)) {
			name = " AND v.`voiceName` LIKE '%" + name + "%'";
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
	public List<Map<String, Object>> getOldList(Page page, String name,
			String idCard, String zjNumber, String county, String street,
			String community, String send_flag, String execute_flag,SystemUser user) {
		String uName = "";
//		if (!"admin".equals(user.getLogonName())) {
//			uName = " AND o.agent_id  =  '" + user.getId() + "'";
//		}
		if (!StringUtils.isEmpty(name)) {
			name = " AND oldo.`NAME` LIKE '%" + name + "%'";
		}
		if (!StringUtils.isEmpty(idCard)) {
			idCard = " AND oldo.idCard = '" + idCard + "'";
		}
		if (!StringUtils.isEmpty(zjNumber)) {
			zjNumber = " AND oldo.ZJNUMBER = '" + zjNumber + "'";
		}
		if (!StringUtils.isEmpty(county)) {
			county = " AND i.HOUSEHOLD_COUNTY_ID = '" + county + "'";
		}
		if (!StringUtils.isEmpty(street)) {
			street = " AND i.HOUSEHOLD_STREET_ID = '" + street + "'";
		}
		if (!StringUtils.isEmpty(community)) {
			community = " AND i.HOUSEHOLD_COMMUNITY_ID = '" + community + "'";
		}
		if (!StringUtils.isEmpty(send_flag)) {
			send_flag = " AND oldo.send_flag = '" + send_flag + "'";
		}
		if (!StringUtils.isEmpty(execute_flag)) {
			execute_flag = " AND oldo.execute_flag = '" + execute_flag + "'";
		}
		String sql = "SELECT oldo.* FROM "
				+ "(SELECT o.startTime,old.id,o.id orderId,old.`NAME`,old.idcard,old.community,old.county,old.street,"
				+ "old.ZJNUMBER,o.send_flag,o.execute_flag FROM omp_voice_order o,"
				+ "(SELECT i.id,i.`NAME`,i.CERTIFICATES_NUMBER idcard,r1.`NAME` community,r2.`NAME`"
				+ " county,r3.`NAME` street,i.ZJNUMBER FROM omp_old_info i,omp_region r1,omp_region r2,"
				+ "omp_region r3	WHERE	i.HOUSEHOLD_COMMUNITY_ID = r1.ID"
				+ " AND i.HOUSEHOLD_COUNTY_ID = r2.ID	AND i.HOUSEHOLD_STREET_ID = r3.ID "
				+ county + street + community + ") old"
				+ "	WHERE	o.oldId = old.id " + uName
				+ "AND o.send_flag = 1 ) oldo WHERE 1=1"
				+ name + idCard + zjNumber + send_flag + execute_flag
				+" order BY oldo.startTime deSC"
				+ " LIMIT " + (page.getCurrentPage() - 1) * page.getPageSize()
				+ ", " + page.getPageSize();
		List<Map<String, Object>> list = JdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public int getOlCount(String name, String idCard, String zjNumber,
			String county, String street, String community, String send_flag,
			String execute_flag,SystemUser user) {
		if (!StringUtils.isEmpty(name)) {
			name = " AND oldo.`NAME` LIKE '%" + name + "%'";
		}
		if (!StringUtils.isEmpty(idCard)) {
			idCard = " AND oldo.idCard = '" + idCard + "'";
		}
		if (!StringUtils.isEmpty(zjNumber)) {
			zjNumber = " AND oldo.ZJNUMBER = '" + zjNumber + "'";
		}
		if (!StringUtils.isEmpty(county)) {
			county = " AND i.HOUSEHOLD_COUNTY_ID = '" + county + "'";
		}
		if (!StringUtils.isEmpty(street)) {
			street = " AND i.HOUSEHOLD_STREET_ID = '" + street + "'";
		}
		if (!StringUtils.isEmpty(community)) {
			community = " AND i.HOUSEHOLD_COMMUNITY_ID = '" + community + "'";
		}
		if (!StringUtils.isEmpty(send_flag)) {
			send_flag = " AND oldo.send_flag = '" + send_flag + "'";
		}
		if (!StringUtils.isEmpty(execute_flag)) {
			execute_flag = " AND oldo.execute_flag = '" + execute_flag + "'";
		}
		String sql = "SELECT count(oldo.id) FROM "
				+ "(SELECT old.id,old.`NAME`,old.idcard,old.community,old.county,old.street,"
				+ "old.ZJNUMBER,o.send_flag,o.execute_flag FROM omp_voice_order o,"
				+ "(SELECT i.id,i.`NAME`,i.CERTIFICATES_NUMBER idcard,r1.`NAME` community,r2.`NAME`"
				+ " county,r3.`NAME` street,i.ZJNUMBER FROM omp_old_info i,omp_region r1,omp_region r2,"
				+ "omp_region r3	WHERE	i.HOUSEHOLD_COMMUNITY_ID = r1.ID"
				+ " AND i.HOUSEHOLD_COUNTY_ID = r2.ID	AND i.HOUSEHOLD_STREET_ID = r3.ID "
				+ county
				+ street
				+ community
				+ ") old"
				+ "	WHERE	o.oldId = old.id AND o.send_flag = 1) oldo WHERE 1=1 "
				+ name + idCard + zjNumber + send_flag + execute_flag;
		int forInt = JdbcTemplate.queryForInt(sql);
		return forInt;
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
			String startTime, String endTime, String t) {
		String userName = SecurityContextHolder.getContext()
				.getAuthentication().getName();

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
				+ vid + "),'" + t + "','" + userName + "')";
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
		// int x;// 定义两变量
		// Random ne = new Random();// 实例化一个random的对象ne
		// x = ne.nextInt(99999 - 10000 + 1) + 1000;// 为变量赋随机值10000-99999
		// String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new
		// Date());
		// String t = time + x;
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
	public void resultVOrders(ImKey imKey, String id, String username) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		String date = df.format(new Date());
		String sql = "insert into omp_order_number (oid,send_date,send_flag,number,returnType,operator) VALUES("
				+ id
				+ ",'"
				+ date
				+ "',"
				+ imKey.getStatusCode()
				+ ",'"
				+ imKey.getGenerateSerialNumber() + "','2','" + username + "')";
		// + "123" + "','2','" + username + "')";
		oldService.saveLogger("5", "语音指令表", "" + username + "",
				"" + imKey.getStatusCode() + "");
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
		String[] ids = vid.split(",");
		for (String id : ids) {
			String sql = "UPDATE omp_voice_info SET stat='0' WHERE (`ID`='"
					+ id + "')";
			JdbcTemplate.update(sql);
		}

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
			return Integer.toBinaryString(i);
		}
	}

	@Override
	public void rollback(String id, String username, String voiceSata) {
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
						+ " where 1=1 and o.user_name = '" + username + "'";
			}
			JdbcTemplate.update(sql);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public UserInfoParameter getUserInfo(SystemUser user) {
		if (user != null) {
			// 发送成功
			String sql1 = "select count(*) from omp_voice_order o where o.send_flag = 1 and o.agent_id = '"
					+ user.getLogonName() + "'";
			int sendSuccess = JdbcTemplate.queryForInt(sql1);
			// 发送失败
			String sql2 = "select count(*) from omp_voice_order o where o.send_flag = 2 and o.agent_id = '"
					+ user.getLogonName() + "'";
			int sendFail = JdbcTemplate.queryForInt(sql2);

			// 执行成功
			String sql3 = "select count(*) from omp_voice_order o where o.execute_flag = 1 and o.agent_id = '"
					+ user.getLogonName() + "'";
			int executeSuc = JdbcTemplate.queryForInt(sql3);

			String sql4 = "select count(*) from omp_voice_order o where o.execute_flag = 0 and o.agent_id = '"
					+ user.getLogonName() + "'";
			int executeFail = JdbcTemplate.queryForInt(sql4);

			// 未接听
			String sql5 = "select count(*) from omp_voice_order o where o.execute_flag = 3 and o.agent_id = '"
					+ user.getLogonName() + "'";
			int notAnswer = JdbcTemplate.queryForInt(sql5);

			// 未返回
			String sql6 = "select count(*) from omp_voice_order o where o.execute_flag is null and o.agent_id = '"
					+ user.getLogonName() + "'";
			int notReturn = JdbcTemplate.queryForInt(sql6);

			// 剩余语音发送次数
			String sql7 = "select u.num from users u where u.USER_NAME = '"
					+ user.getLogonName() + "'";
			int voiceCount = JdbcTemplate.queryForInt(sql7);

			// 语音发送总次数
			String sql8 = "select count(*) from omp_voice_order o where o.agent_id  = '"
					+ user.getLogonName() + "'";
			int sumCount = JdbcTemplate.queryForInt(sql8);

			UserInfoParameter userInfo = new UserInfoParameter();
			userInfo.setExecuteFail(executeFail);
			userInfo.setExecuteSuc(executeSuc);
			userInfo.setNotAnswer(notAnswer);
			userInfo.setNotReturn(notReturn);
			userInfo.setSendFail(sendFail);
			userInfo.setSendSuccess(sendSuccess);
			userInfo.setVoiceCount(voiceCount);
			userInfo.setSumCount(sumCount);
			return userInfo;

		}

		return null;
	}

}
