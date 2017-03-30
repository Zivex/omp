package com.capinfo.omp.parameter;

import com.capinfo.framework.web.parameter.DataListParameter;
import com.capinfo.omp.model.ServiceProvider;

/**
 * 服务商参数模板
 */
public class ServiceProviderParameter extends DataListParameter<ServiceProvider> {

	private ServiceProvider entity = new ServiceProvider();
	private Class<ServiceProvider> entityClazz = ServiceProvider.class;
	private int current;
	private int pageSize;

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public ServiceProvider getEntity() {
		return entity;
	}
	public void setEntity(ServiceProvider entity) {
		this.entity = entity;
	}
	public Class<ServiceProvider> getEntityClazz() {
		return entityClazz;
	}
	public void setEntityClazz(Class<ServiceProvider> entityClazz) {
		this.entityClazz = entityClazz;
	}

}
