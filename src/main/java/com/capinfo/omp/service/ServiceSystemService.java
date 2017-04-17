package com.capinfo.omp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.web.service.CommonsDataOperationService;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.model.Sys_key;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.parameter.ServiceProviderParameter;
import com.capinfo.omp.parameter.ServiceSystemParameter;


public interface ServiceSystemService extends CommonsDataOperationService<Enterprise, EnterpriseParameter>  {

	public List<Map<String, Object>> getQueryarchitecture(SystemUser user, Long stId);

	public List<Map<String, Object>> serchService(ServiceProviderParameter parameter);

	public void addServiceSystem(ServiceSystemParameter parameter, SystemUser user);

	public HashMap<String, Object> getSSList(SystemUser user, ServiceSystemParameter parameter);

	public void updateServiceSystem(ServiceSystemParameter parameter,
			SystemUser user);

	public List<Map<String, Object>> getupdateSys(Sys_key ss);

}
