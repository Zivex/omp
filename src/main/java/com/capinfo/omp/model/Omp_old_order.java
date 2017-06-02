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
 * 指令
 * 
 * @author zx
 * 
 */
@Entity
@Table(name = "omp_old_order")
public class Omp_old_order implements BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long oldId;
	private Omp_Old_Info old;
	private String phoneName;
	private Date executionTime;
	private Long communityOrderId;
	private String keyPointMessage;
	private Long isIndividuality;
	private Long send_flag;

	private Long execute_flag;
	private String errorMessage;

	private String test;
	
	private String k_and_sp_id;
	
	private Long agent_id;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "oldId")
	public Long getOldId() {
		return oldId;
	}

	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}
	@Column(name = "phoneName")
	public String getPhoneName() {
		return phoneName;
	}

	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "executionTime")
	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}
	@Column(name = "communityOrderId")
	public Long getCommunityOrderId() {
		return communityOrderId;
	}
	
	public void setCommunityOrderId(Long communityOrderId) {
		this.communityOrderId = communityOrderId;
	}
	@Column(name = "keyPointMessage")
	public String getKeyPointMessage() {
		return keyPointMessage;
	}

	public void setKeyPointMessage(String keyPointMessage) {
		this.keyPointMessage = keyPointMessage;
	}
	@Column(name = "isIndividuality")
	public Long getIsIndividuality() {
		return isIndividuality;
	}

	public void setIsIndividuality(Long isIndividuality) {
		this.isIndividuality = isIndividuality;
	}
	@Column(name = "send_flag")
	public Long getSend_flag() {
		return send_flag;
	}

	public void setSend_flag(Long send_flag) {
		this.send_flag = send_flag;
	}
	@Column(name = "execute_flag")
	public Long getExecute_flag() {
		return execute_flag;
	}

	public void setExecute_flag(Long execute_flag) {
		this.execute_flag = execute_flag;
	}
	@Column(name = "errorMessage")
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Column(name = "test")
	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	@ManyToOne(targetEntity = Omp_Old_Info.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "oldId", insertable = false, updatable = false)
	public Omp_Old_Info getOld() {
		return old;
	}
	public void setOld(Omp_Old_Info old) {
		this.old = old;
	}

	@Column(name = "k_and_sp_id")
	public String getK_and_sp_id() {
		return k_and_sp_id;
	}


	public void setK_and_sp_id(String k_and_sp_id) {
		this.k_and_sp_id = k_and_sp_id;
	}

	@Column(name = "agent_id")
	public Long getAgent_id() {
		return agent_id;
	}


	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}
	
	
	
	

}
