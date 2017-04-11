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
@Table(name = "service_system_copy")
public class New_Service_System implements BaseEntity {

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
	private Long telltype;
	private TellType type;
	//键位
	private Long key_state;
	
	//服务商所对应的键位
	private Long sp_id;
	private ServiceProvider serviceProvider;

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

	@Column(name = "uid")
	public Long getUid() {
		return uid;
	}



	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Column(name = "tellType_id")
	public Long getTelltype() {
		return telltype;
	}


	public void setTelltype(Long telltype) {
		this.telltype = telltype;
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


	@ManyToOne(targetEntity = SystemUser.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "uid", insertable = false, updatable = false)
	public SystemUser getUser() {
		return user;
	}


	public void setUser(SystemUser user) {
		this.user = user;
	}

	@Column(name = "county_id")
	public Long getCounty_id() {
		return county_id;
	}


	public void setCounty_id(Long county_id) {
		this.county_id = county_id;
	}

	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTY_ID", insertable = false, updatable = false)
	public OmpRegion getCounty() {
		return county;
	}


	public void setCounty(OmpRegion county) {
		this.county = county;
	}

	@Column(name = "STREET_ID")
	public Long getStreet_id() {
		return street_id;
	}


	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "STREET_ID", insertable = false, updatable = false)
	public OmpRegion getStreet() {
		return street;
	}


	public void setStreet(OmpRegion street) {
		this.street = street;
	}

	@Column(name = "COMMUNITY_ID")
	public Long getCommunity_id() {
		return community_id;
	}


	public void setCommunity_id(Long community_id) {
		this.community_id = community_id;
	}

	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "COMMUNITY_ID", insertable = false, updatable = false)
	public OmpRegion getCommunity() {
		return community;
	}


	public void setCommunity(OmpRegion community) {
		this.community = community;
	}

	@ManyToOne(targetEntity = TellType.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "tellType_id", insertable = false, updatable = false)
	public TellType getType() {
		return type;
	}


	public void setType(TellType type) {
		this.type = type;
	}

	@Column(name = "key_state")
	public Long getKey_state() {
		return key_state;
	}


	public void setKey_state(Long key_state) {
		this.key_state = key_state;
	}

	@Column(name = "sp_id")
	public Long getSp_id() {
		return sp_id;
	}


	public void setSp_id(Long sp_id) {
		this.sp_id = sp_id;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "sp_id", insertable = false, updatable = false)
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}


	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	@Column(name = "user_falg")
	public Long getUser_falg() {
		return user_falg;
	}


	public void setUser_falg(Long user_falg) {
		this.user_falg = user_falg;
	}
	
	




}
