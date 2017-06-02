package com.capinfo.region.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.capinfo.framework.model.BaseEntity;

@Entity
@Table(name = "OMP_REGION")
// @SequenceGenerator(name = "OmpRegion", sequenceName = "SEQ_OMP_REGION",
// allocationSize = 1)
public class OmpRegion implements BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Long levelid;

	private Date createdate;
	private Date updatedate;

	private Long parentid;

	private Short useFlag;

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "OmpRegion")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "LEVELID")
	public Long getLevelid() {
		return levelid;
	}

	public void setLevelid(Long levelid) {
		this.levelid = levelid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME")
	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	@Column(name = "PARENTID")
	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	@Column(name = "USE_FLAG")
	public Short getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Short useFlag) {
		this.useFlag = useFlag;
	}

}