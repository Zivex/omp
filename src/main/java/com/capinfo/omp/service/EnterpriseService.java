package com.capinfo.omp.service;

import java.util.List;

import com.capinfo.framework.web.service.CommonsDataOperationService;
import com.capinfo.omp.model.Composition;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.parameter.EnterpriseParameter;


public interface EnterpriseService extends CommonsDataOperationService<Enterprise, EnterpriseParameter>  {

	List<Enterprise> getListByName(EnterpriseParameter parameter);

	List<Composition> getListByid(int uid,Integer lv,Integer upId);



}
