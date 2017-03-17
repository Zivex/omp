package com.capinfo.omp.parameter;

import com.capinfo.framework.web.parameter.DataListParameter;
import com.capinfo.omp.model.Composition;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.omp.model.Omp_Old_Info;
/*
 * 企业参数
 */
public class EnterpriseParameter extends DataListParameter<Enterprise> {
	
	private Enterprise entity = new Enterprise();
	private Class<Enterprise> entityClazz = Enterprise.class;
	private String type;
	
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Enterprise getEntity() {
		return entity;
	}
	public void setEntity(Enterprise entity) {
		this.entity = entity;
	}
	public Class<Enterprise> getEntityClazz() {
		return entityClazz;
	}
	public void setEntityClazz(Class<Enterprise> entityClazz) {
		this.entityClazz = entityClazz;
	}
	
	


}
