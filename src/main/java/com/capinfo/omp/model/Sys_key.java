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
@Table(name = "sys_key")
public class Sys_key implements BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long ssid;
	private Long sp_id;
	private Date createTime;
	private Date updateTime;
	private Long user_falg;



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}



	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "user_falg")
	public Long getUser_falg() {
		return user_falg;
	}


	public void setUser_falg(Long user_falg) {
		this.user_falg = user_falg;
	}

	@Column(name = "ssid")
	public Long getSsid() {
		return ssid;
	}


	public void setSsid(Long ssid) {
		this.ssid = ssid;
	}

	@Column(name = "sp_id")
	public Long getSp_id() {
		return sp_id;
	}


	public void setSp_id(Long sp_id) {
		this.sp_id = sp_id;
	}



	
	




}
