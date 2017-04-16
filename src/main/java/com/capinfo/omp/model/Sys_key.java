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
	private Long county_id;
	private OmpRegion county;
	private Long street_id;
	private OmpRegion street;
	private Long community_id;
	private OmpRegion community;
	private Long uid;
	private SystemUser user;
	private Long telltype_id;
	private TellType type;
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



	@Column(name = "county_id")
	public Long getCounty_id() {
		return county_id;
	}


	public void setCounty_id(Long county_id) {
		this.county_id = county_id;
	}

	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "county_id", insertable = false, updatable = false)
	public OmpRegion getCounty() {
		return county;
	}


	public void setCounty(OmpRegion county) {
		this.county = county;
	}

	@Column(name = "street_id")
	public Long getStreet_id() {
		return street_id;
	}


	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "street_id", insertable = false, updatable = false)
	public OmpRegion getStreet() {
		return street;
	}


	public void setStreet(OmpRegion street) {
		this.street = street;
	}

	@Column(name = "community_id")
	public Long getCommunity_id() {
		return community_id;
	}


	public void setCommunity_id(Long community_id) {
		this.community_id = community_id;
	}

	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "community_id", insertable = false, updatable = false)
	public OmpRegion getCommunity() {
		return community;
	}


	public void setCommunity(OmpRegion community) {
		this.community = community;
	}

	@Column(name = "uid")
	public Long getUid() {
		return uid;
	}


	public void setUid(Long uid) {
		this.uid = uid;
	}

	@ManyToOne(targetEntity = SystemUser.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "uid", insertable = false, updatable = false)
	public SystemUser getUser() {
		return user;
	}


	public void setUser(SystemUser user) {
		this.user = user;
	}

	@Column(name = "telltype_id")
	public Long getTelltype_id() {
		return telltype_id;
	}


	public void setTelltype_id(Long telltype_id) {
		this.telltype_id = telltype_id;
	}

	@ManyToOne(targetEntity = TellType.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "telltype_id", insertable = false, updatable = false)
	public TellType getType() {
		return type;
	}


	public void setType(TellType type) {
		this.type = type;
	}











}
