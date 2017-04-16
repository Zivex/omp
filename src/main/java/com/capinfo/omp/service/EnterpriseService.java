package com.capinfo.omp.service;

import java.util.List;
import java.util.Map;

import com.capinfo.framework.web.service.CommonsDataOperationService;
import com.capinfo.omp.model.Composition;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.model.ServiceProvider;
import com.capinfo.omp.parameter.EnterpriseParameter;
import com.capinfo.omp.parameter.ServiceProviderParameter;
import com.capinfo.omp.utils.Page;
import com.capinfo.region.model.OmpRegion;


public interface EnterpriseService extends CommonsDataOperationService<Enterprise, EnterpriseParameter>  {

	List<Enterprise> getListByName(EnterpriseParameter parameter);

	List<Composition> getListByid(int uid,Integer lv,Integer upId);

	Long getServiceTypeId(String serviceType);

	String getRegionId(String city, int i, String cityId);

	Map getMerchantsList(ServiceProviderParameter parameter);

	int getMerchantsCount(ServiceProviderParameter parameter);

	List<OmpRegion> queryRegions(String ids);

	List<OmpRegion> queryCounty(long l);

	List<Map<String,Object>> queryAllRegions(int i);

	int queryLv(String string);

	boolean queryForTell(String serviceTell, Long id);



}
