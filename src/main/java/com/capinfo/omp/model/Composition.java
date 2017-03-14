package com.capinfo.omp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.capinfo.assistant.platform.ws.card.model.CardPersonMessageBack;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.region.model.OmpRegion;

/**
 * 用户信息 企业体系结构
 *
 * @author zx
 *
 */
@Entity
@Table(name = "composition")
public class Composition  implements BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long prient_id;
	private String name;
	private Long levelid;
	private Date createdate;
	private Date updatetime;
	private Long use_flag;
	//操作人id
	private Long cid;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "prient_id")
	public Long getPrient_id() {
		return prient_id;
	}

	public void setPrient_id(Long prient_id) {
		this.prient_id = prient_id;
	}

	@Column(name = "levelid")
	public Long getLevelid() {
		return levelid;
	}

	public void setLevelid(Long levelid) {
		this.levelid = levelid;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdate")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}



	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "use_flag")
	public Long getUse_flag() {
		return use_flag;
	}

	public void setUse_flag(Long use_flag) {
		this.use_flag = use_flag;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "cid")
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}


}
