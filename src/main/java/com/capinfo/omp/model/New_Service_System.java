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
@Table(name = "service_system")
public class New_Service_System implements BaseEntity {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long skid;
    //键位
    private Long key_state;
    private Omp_key ok;
    //服务商所对应的键位
    private Long sp_id;
    private ServiceProvider serviceProvider;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "skid")
	public Long getSkid() {
		return skid;
	}


	public void setSkid(Long skid) {
		this.skid = skid;
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


	@ManyToOne(targetEntity = Omp_key.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "key_state", insertable = false, updatable = false)
	public Omp_key getOk() {
		return ok;
	}


	public void setOk(Omp_key ok) {
		this.ok = ok;
	}








}
