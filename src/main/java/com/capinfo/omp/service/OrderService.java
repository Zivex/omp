package com.capinfo.omp.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.web.service.CommonsDataOperationService;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.Omp_old_order;
import com.capinfo.omp.parameter.OldParameter;
import com.capinfo.omp.parameter.OrderParameter;
import com.capinfo.omp.utils.Page;
import com.capinfo.omp.ws.model.ImKey;

@Service
public interface OrderService extends CommonsDataOperationService<Omp_old_order, OrderParameter> {

	int getOrderCount(String name, String idCard, String zjNumber, String county, String street, String community,
			String send_flag, String execute_flag,SystemUser user);

	List<Omp_old_order> getOrderList(Page page, String name, String idCard, String zjNumber, String county,
			String street, String community, String send_flag, String execute_flag,SystemUser user);
	
	String sendOrder(String id);
	
	void toupdete(String string,ImKey imkey);

	void delect(String id);

	String checkDeBatchSendInstructions();

	boolean getcount();
	
    boolean upsendOrder(String id) throws Exception;
    
    void upoder(String id);

    void upMessg(ImKey imKey,String id);
    
    List<Map<String, Object>> getOList(String id);
    
    void resultOrder(ImKey imKey,String id,String username);
    
    String RequestZJ(String zj);
    
    
    
}
