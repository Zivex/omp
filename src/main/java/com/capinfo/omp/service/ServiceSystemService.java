package com.capinfo.omp.service;

import java.util.List;
import java.util.Map;

import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.web.service.CommonsDataOperationService;
import com.capinfo.omp.model.Composition;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.model.ServiceProvider;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.parameter.ServiceProviderParameter;
import com.capinfo.omp.parameter.ServiceSystemParameter;
import com.capinfo.omp.utils.Page;
import com.capinfo.region.model.OmpRegion;


public interface ServiceSystemService extends CommonsDataOperationService<Enterprise, EnterpriseParameter>  {

	public List<Map<String, Object>> getQueryarchitecture(int stId);

	public List<Map<String, Object>> serchService(ServiceProviderParameter parameter);

	public void addServiceSystem(ServiceSystemParameter parameter, SystemUser user);

}
