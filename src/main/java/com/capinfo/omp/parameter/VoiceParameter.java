package com.capinfo.omp.parameter;

import com.capinfo.framework.web.parameter.DataListParameter;
import com.capinfo.omp.model.Omp_Old_Info;
import com.capinfo.omp.model.Omp_old_order;
import com.capinfo.omp.model.Omp_voice_order;
/*
 * 查询老人接收参数
 */
public class VoiceParameter extends DataListParameter<Omp_voice_order> {
	private Omp_voice_order entity = new Omp_voice_order();
	private Class<Omp_voice_order> entityClazz = Omp_voice_order.class;
	private String name;
	private String idCard;
	private String zjNumber;
	private String county;
	private String street;
	private String community;
	private String isGenerationOrder;
	private String creationTime;
	private String current;
	private String isindividuation;
	private Long call_id;
	private Long vid;
	public Omp_voice_order getEntity() {
		return entity;
	}
	public void setEntity(Omp_voice_order entity) {
		this.entity = entity;
	}
	public Class<Omp_voice_order> getEntityClazz() {
		return entityClazz;
	}
	public void setEntityClazz(Class<Omp_voice_order> entityClazz) {
		this.entityClazz = entityClazz;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public String getIsindividuation() {
		return isindividuation;
	}
	public void setIsindividuation(String isindividuation) {
		this.isindividuation = isindividuation;
	}
	public Long getCall_id() {
		return call_id;
	}
	public void setCall_id(Long call_id) {
		this.call_id = call_id;
	}
	public Long getVid() {
		return vid;
	}
	public void setVid(Long vid) {
		this.vid = vid;
	}

	
	
	

}
