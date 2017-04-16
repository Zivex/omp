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
import com.capinfo.common.model.SystemUser;
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
@Table(name = "omp_key")
public class Omp_key implements BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long pyId;
	private Long stId;
	private String key;
	private String  status;
	private Long government;
	private Long other;



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "pyId")
	public Long getPyId() {
		return pyId;
	}


	public void setPyId(Long pyId) {
		this.pyId = pyId;
	}

	@Column(name = "stId")
	public Long getStId() {
		return stId;
	}


	public void setStId(Long stId) {
		this.stId = stId;
	}

	@Column(name = "key")
	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "government")
	public Long getGovernment() {
		return government;
	}


	public void setGovernment(Long government) {
		this.government = government;
	}

	@Column(name = "other")
	public Long getOther() {
		return other;
	}


	public void setOther(Long other) {
		this.other = other;
	}









}
