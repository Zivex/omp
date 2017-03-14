package com.capinfo.omp.parameter;

import com.capinfo.framework.web.parameter.DataListParameter;
import com.capinfo.omp.model.Composition;
/*
 * 查询老人接收参数
 */
public class CompositionParameter extends DataListParameter<Composition> {
	private Composition entity = new Composition();
	private Class<Composition> entityClazz = Composition.class;
	private Long prient_id;
	private Long id;
	public Long getPrient_id() {
		return prient_id;
	}
	public void setPrient_id(Long prient_id) {
		this.prient_id = prient_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Composition getEntity() {
		return entity;
	}
	public void setEntity(Composition entity) {
		this.entity = entity;
	}
	public Class<Composition> getEntityClazz() {
		return entityClazz;
	}
	public void setEntityClazz(Class<Composition> entityClazz) {
		this.entityClazz = entityClazz;
	}




}
