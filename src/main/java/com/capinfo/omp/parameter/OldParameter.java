package com.capinfo.omp.parameter;

import com.capinfo.framework.web.parameter.DataListParameter;
import com.capinfo.omp.model.Omp_Old_Info;
/*
 * 查询老人接收参数
 */
public class OldParameter extends DataListParameter<Omp_Old_Info> {
	private Omp_Old_Info entity = new Omp_Old_Info();
	private Class<Omp_Old_Info> entityClazz = Omp_Old_Info.class;
	private String name;
	private String current;
	private String pageSize;
	private String idCard;
	private String zjNumber;
	private String county;
	private String street;
	private String community;
	private String isGenerationOrder;
	private String isindividuation;
	private String creationTime;
	private Integer call_id;

	
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getZjNumber() {
		return zjNumber;
	}

	public void setZjNumber(String zjNumber) {
		this.zjNumber = zjNumber;
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

	public String getIsGenerationOrder() {
		return isGenerationOrder;
	}

	public void setIsGenerationOrder(String isGenerationOrder) {
		this.isGenerationOrder = isGenerationOrder;
	}

	public String getIsindividuation() {
		return isindividuation;
	}

	public void setIsindividuation(String isindividuation) {
		this.isindividuation = isindividuation;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public Omp_Old_Info getEntity() {
		return entity;
	}

	public void setEntity(Omp_Old_Info entity) {
		this.entity = entity;
	}

	public Class<Omp_Old_Info> getEntityClazz() {
		return entityClazz;
	}

	public void setEntityClazz(Class<Omp_Old_Info> entityClazz) {
		this.entityClazz = entityClazz;
	}

	public Integer getCall_id() {
		return call_id;
	}

	public void setCall_id(Integer call_id) {
		this.call_id = call_id;
	}

}
