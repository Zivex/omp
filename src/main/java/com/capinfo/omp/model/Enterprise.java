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
 * 企业信息
 *
 * @author zx
 *
 */
@Entity
@Table(name = "enterprise")
public class Enterprise  implements BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Date createdate;
	private Date updatetime;
	private Long use_flag;
	private String type;

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
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
