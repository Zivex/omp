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
@Table(name = "omp_voice_order")
public class Omp_voice_order implements BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long oldId;
	private Omp_Old_Info old;
	
	
	private Long executeType;
	private Date startTime;
	private Date endTime;
	private Long voiceFIleId;
	private String voiceFileAddress;
	private Long send_flag;

	private Long upload_flag;
	private Long execute_flag;
	private Long voiceId;
	private String errorMessage;
	private Long number;
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

	@ManyToOne(targetEntity = Omp_Old_Info.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "oldId", insertable = false, updatable = false)
	public Omp_Old_Info getOld() {
		return old;
	}
	public void setOld(Omp_Old_Info old) {
		this.old = old;
	}

	@Column(name = "executeType")
	public Long getExecuteType() {
		return executeType;
	}


	public void setExecuteType(Long executeType) {
		this.executeType = executeType;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startTime")
	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endTime")
	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "voiceFIleId")
	public Long getVoiceFIleId() {
		return voiceFIleId;
	}


	public void setVoiceFIleId(Long voiceFIleId) {
		this.voiceFIleId = voiceFIleId;
	}

	@Column(name = "voiceFileAddress")
	public String getVoiceFileAddress() {
		return voiceFileAddress;
	}


	public void setVoiceFileAddress(String voiceFileAddress) {
		this.voiceFileAddress = voiceFileAddress;
	}

	@Column(name = "upload_flag")
	public Long getUpload_flag() {
		return upload_flag;
	}


	public void setUpload_flag(Long upload_flag) {
		this.upload_flag = upload_flag;
	}

	@Column(name = "voiceId")
	public Long getVoiceId() {
		return voiceId;
	}


	public void setVoiceId(Long voiceId) {
		this.voiceId = voiceId;
	}

	@Column(name = "number")
	public Long getNumber() {
		return number;
	}


	public void setNumber(Long number) {
		this.number = number;
	}
	
	@Column(name = "agent_id")
	public Long getAgent_id() {
		return agent_id;
	}


	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}
	
	

}
