package com.capinfo.omp.parameter;

import com.capinfo.framework.web.parameter.DataListParameter;
import com.capinfo.omp.model.Service_System;

/**
 * 服务商参数
 * @author Rivex
 *
 */
public class ServiceSystemParameter extends DataListParameter<Service_System>  {
	
	private Service_System entity = new Service_System();
	private Class<Service_System> entityClazz = Service_System.class;
	
	//区域
	private int city;
	private int street;
	private int county;
	private int community;
	private int telltype;
	
	public int getTelltype() {
		return telltype;
	}
	public void setTelltype(int telltype) {
		this.telltype = telltype;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public int getStreet() {
		return street;
	}
	public void setStreet(int street) {
		this.street = street;
	}
	public int getCounty() {
		return county;
	}
	public void setCounty(int county) {
		this.county = county;
	}
	public int getCommunity() {
		return community;
	}
	public void setCommunity(int community) {
		this.community = community;
	}
	public Service_System getEntity() {
		return entity;
	}
	public void setEntity(Service_System entity) {
		this.entity = entity;
	}
	public Class<Service_System> getEntityClazz() {
		return entityClazz;
	}
	public void setEntityClazz(Class<Service_System> entityClazz) {
		this.entityClazz = entityClazz;
	}
	
	
	
	
	
	
	
	
	
	
	


}
