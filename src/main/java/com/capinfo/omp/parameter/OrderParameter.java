package com.capinfo.omp.parameter;

import com.capinfo.framework.web.parameter.DataListParameter;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.Omp_old_order;
/*
 * 查询老人接收参数
 */
public class OrderParameter extends DataListParameter<Omp_old_order> {
	private Omp_old_order entity = new Omp_old_order();
	private Class<Omp_old_order> entityClazz = Omp_old_order.class;
	private String name;
	private String current;
	private String idCard;
	private String zjNumber;
	private String city;
	private String county;
	private String street;
	private String community;
	private String isGenerationOrder;
	private String isindividuation;
	private String creationTime;
	private Integer call_id;
	private Long execute_flag;
	private Long send_flag;

	
	
	
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getExecute_flag() {
		return execute_flag;
	}

	public void setExecute_flag(Long execute_flag) {
		this.execute_flag = execute_flag;
	}

	public Long getSend_flag() {
		return send_flag;
	}

	public void setSend_flag(Long send_flag) {
		this.send_flag = send_flag;
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

	public Omp_old_order getEntity() {
		return entity;
	}

	public void setEntity(Omp_old_order entity) {
		this.entity = entity;
	}

	public Class<Omp_old_order> getEntityClazz() {
		return entityClazz;
	}

	public void setEntityClazz(Class<Omp_old_order> entityClazz) {
		this.entityClazz = entityClazz;
	}

	public Integer getCall_id() {
		return call_id;
	}

	public void setCall_id(Integer call_id) {
		this.call_id = call_id;
	}

}
