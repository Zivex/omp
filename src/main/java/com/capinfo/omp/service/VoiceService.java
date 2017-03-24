package com.capinfo.omp.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.model.system.User;
import com.capinfo.framework.web.service.CommonsDataOperationService;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.Omp_old_order;
import com.capinfo.omp.model.Omp_voice_order;
import com.capinfo.omp.parameter.OrderParameter;
import com.capinfo.omp.parameter.UserInfoParameter;
import com.capinfo.omp.utils.Page;
import com.capinfo.omp.ws.model.ImKey;

@Service
public interface VoiceService extends CommonsDataOperationService<Omp_voice_order, UserInfoParameter> {
	/**
	 * 查询
	 * 
	 * @param page
	 * @param community
	 * @param street
	 * @param county
	 * @param zjNumber
	 * @param idCard
	 * @param name
	 * @param creationTime
	 * 
	 * @return
	 */
	List<Omp_Old_Info> getOldContextList(Page page, String name,
			String idCard, String zjNumber, String county, String street,
			String community,SystemUser user);

	int getCount(String name, String idCard, String zjNumber, String county,
			String street, String community,SystemUser user);

	String sendOrder(String id);

	void toupdete(String string);

	List<Map<String, Object>> getshell();

	/*
	 * List<Map<String, Object>> getvoicelist();
	 * 
	 * List<Map<String, Object>> voicequery(String name);
	 */

	List<Map<String, Object>> getvoicelist(Page page, String name,SystemUser user);

	int getvoicelist(String name,SystemUser user);
	
	int SenVoice(String vid,String cid);
	
	String checkDeBatchSendInstructions();
	
	List<Omp_old_order> getOldList(Page page, String name, String idCard, String zjNumber, String county, String street, String community,String send_flag,String execute_flag, SystemUser user);

	int getOlCount(String name, String idCard, String zjNumber, String county, String street, String community,String send_flag,String execute_flag, SystemUser user);
	
	void uploadMiddle(String id);
	
	String middle();
	
	void saveviceoder(String id, String vid,String executeType,String startTime,String endTime,String t);
	
	String resultVice();
	
	String sendvice(String id,String t);
	
	void upMessg(ImKey imKey,String id);
	
	String resultStrtTime(String id,String vid);
	
	String resultendTime(String id,String vid);
	
	boolean getcount();
	
	String resultOrderId();
	
	String ResultOderById(String comid);
	
	void resultVOrders(ImKey imKey,String id,String username);
	
	//删除声音
	void deleteVoidByid(String vid);
	void deleteVoidsByid(String vid);
	//查询用户语音发送次数
	Boolean queryCount(String ids);

	String numRest(String id);
	//语音次数回滚
	void rollback(String id, String username, String voiceSata);

	UserInfoParameter getUserInfo(SystemUser user);
}
