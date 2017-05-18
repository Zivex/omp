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
	private String regionIds;

	private Long sid;
	private String city;
	private String county;
	private String street;
	private String community;
	private Long serviceId;
	private String serviceName;
	private Long streetId;
	private Long openRegions;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	//是否政府
	private Long g;
	
	
	public Long getG() {
		return g;
	}
	public void setG(Long g) {
		this.g = g;
	}
	public Long getOpenRegions() {
		return openRegions;
	}
	public void setOpenRegions(Long openRegions) {
		this.openRegions = openRegions;
	}
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Long getStreetId() {
		return streetId;
	}
	public void setStreetId(Long streetId) {
		this.streetId = streetId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getRegionIds() {
		return regionIds;
	}
	public void setRegionIds(String regionIds) {
		this.regionIds = regionIds;
	}
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
