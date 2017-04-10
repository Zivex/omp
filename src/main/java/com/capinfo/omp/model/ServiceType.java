package com.capinfo.omp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.capinfo.assistant.platform.ws.card.model.CardPersonMessageBack;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.region.model.OmpRegion;

/**
 * 用户信息
 * 
 * @author zx
 * 
 */
/**
 * @author Rivex
 *
 */
@Entity
@Table(name = "omp_service_type")
public class ServiceType implements BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String serviceName;
	private Long status;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name ="serviceName")
	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name ="status")
	public Long getStatus() {
		return status;
	}


	public void setStatus(Long status) {
		this.status = status;
	}
	
	
}
