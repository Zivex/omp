package com.capinfo.omp.parameter;

import com.capinfo.framework.web.parameter.DataListParameter;
import com.capinfo.region.model.OmpRegion;
/*
 * 查询老人接收参数
 */
public class RegionManageParameter extends DataListParameter<OmpRegion> {
	private OmpRegion entity = new OmpRegion();
	private Class<OmpRegion> entityClazz = OmpRegion.class;
	private Long rid;
	
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public OmpRegion getEntity() {
		return entity;
	}
	public void setEntity(OmpRegion entity) {
		this.entity = entity;
	}
	public Class<OmpRegion> getEntityClazz() {
		return entityClazz;
	}
	public void setEntityClazz(Class<OmpRegion> entityClazz) {
		this.entityClazz = entityClazz;
	}
	
	

	
	



}
