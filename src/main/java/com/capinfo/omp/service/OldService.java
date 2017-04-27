package com.capinfo.omp.service;

import java.util.List;
import java.util.Map;

import com.capinfo.assistant.platform.ws.card.model.CardPersonMessageBack;
import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.web.service.CommonsDataOperationService;
import com.capinfo.omp.model.OmpOldMatch;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.parameter.OldParameter;
import com.capinfo.omp.utils.Page;
import com.capinfo.omp.utils.excel.ExcelBuilder;


public interface OldService extends CommonsDataOperationService<Omp_Old_Info, OldParameter>  {

	/**
	 * 查询
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
	Map<String,Object> getOldContextList(OldParameter parameter,SystemUser user);

	//List getOldContextList(Page page, String name, String idCard, String zjNumber, String county, String street, String community, String isGenerationOrder,String isindividuation,SystemUser user);

	/**
	 * 增加
	 * @param format
	 * @param user
	 *
	 * @return
	 */
	boolean addOld(Omp_Old_Info ompOldInfo, SystemUser user);

	/**
	 * 修改
	 *
	 * @return
	 */
	boolean updOldById(Omp_Old_Info newOld);

	/**
	 * 删除
	 *
	 * @return
	 */
	int delOldById(String id);

	int getCount(String name, String idCard, String zjNumber, String county, String street, String community, String isGenerationOrder,String isindividuation,SystemUser user
		);

	String getIdByName(String area, int i);

	Omp_Old_Info getOldById(String id);

	List<Map<String, Object>> getOldById1(String id);

	Map getRegionList(Omp_Old_Info old);
	
	Map getRegionList1(Map<String, Object> map);

	List<Map<String, Object>> getRegionById(String id);

	List<Map<String, Object>> queryCommunityOrder(String id);

	boolean saveOrder(String id, String pname, String comId, String keyPointMessage);

	void updOldState(String id);

	List<Map<String, Object>> getOldKeyPointMessage(String oid);

	boolean checkRe(String idCard, String zjnumber);

	boolean checkId(String string);

	List<Map<String, Object>> getOmpKeyByType(String pyId);

	List<Map<String, Object>> getOldMyPointMessage(String oid);

	Boolean uploadOldIndividuation(String id, String json);

	Boolean addOmpOldOrderInfo(String id, String json);

	Integer checkOldIsHave(String phoneid, String cardID);

	List<Map<String, Object>> getOldKeyInfoById(String id);

	boolean saveLogger(String type,String content,String creater,String state);

	void saveolInfo(String carid);

	boolean getcountid();

	String checkDeBatchSendInstructions();

	String HOUSEHOLD_COUNTY_ID(String add);

	String HOUSEHOLD_STREET_ID(String add);

	String HOUSEHOLD_COMMUNITY_ID(String add);

	List<Map<String, Object>> getPerson(String cardIds);

	ExcelBuilder exportExcel(OldParameter parameter, SystemUser user);

	String getIdByComCod(String community, int i);

	List getCuser(SystemUser user);

	int getTel_type(String tel_type);


}
