package com.capinfo.serviceprovider.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "service_providers_navigation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
// @SequenceGenerator(name = "OldMatch", sequenceName = "SEQ_OMP_OLD_MATCH",
// allocationSize = 1)
public class ServiceProviders implements Serializable {

	private static final long serialVersionUID = 8723112805122184479L;

	private Long id;

	private String server_id;

	//服务商名称
	private String server_name;
	//YL
	private Long service_providers_identify;

	//街道
	private String scope_delivery;
	//服务类型
	private String server_type;
	//服务电话
	private String server_tel;
	//是否有效
	private String is_valid;

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "OldMatch")
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SERVER_ID", length = 100)
	public String getServer_id() {
		return server_id;
	}

	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}

	@Column(name = "SERVER_NAME", length = 100)
	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	@Column(name = "SERVICE_PROVIDERS_IDENTIFY", unique = true, nullable = false)
	public Long getService_providers_identify() {
		return service_providers_identify;
	}

	public void setService_providers_identify(Long service_providers_identify) {
		this.service_providers_identify = service_providers_identify;
	}

	@Column(name = "SCOPE_DELIVERY", length = 100)
	public String getScope_delivery() {
		return scope_delivery;
	}

	public void setScope_delivery(String scope_delivery) {
		this.scope_delivery = scope_delivery;
	}

	@Column(name = "SERVER_TYPE", length = 100)
	public String getServer_type() {
		return server_type;
	}

	public void setServer_type(String server_type) {
		this.server_type = server_type;
	}

	@Column(name = "SERVER_TEL", length = 100)
	public String getServer_tel() {
		return server_tel;
	}

	public void setServer_tel(String server_tel) {
		this.server_tel = server_tel;
	}

	@Column(name = "IS_VALID", length = 100)
	public String getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}

}
