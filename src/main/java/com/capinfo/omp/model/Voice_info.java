package com.capinfo.omp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "omp_voice_info")
public class Voice_info {

	private Long id;
	private Long voiceFIleId;
	private String voiceName;
	private String voiceFileAddress;
	private String remark;
	private String voiceTime;
	private String stat;
	private String agent_id;
	private String content;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "voiceFIleId")
	public Long getVoiceFIleId() {
		return voiceFIleId;
	}
	public void setVoiceFIleId(Long voiceFIleId) {
		this.voiceFIleId = voiceFIleId;
	}
	@Column(name = "voiceName")
	public String getVoiceName() {
		return voiceName;
	}
	public void setVoiceName(String voiceName) {
		this.voiceName = voiceName;
	}
	@Column(name = "voiceFileAddress")
	public String getVoiceFileAddress() {
		return voiceFileAddress;
	}
	public void setVoiceFileAddress(String voiceFileAddress) {
		this.voiceFileAddress = voiceFileAddress;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "voiceTime")
	public String getVoiceTime() {
		return voiceTime;
	}
	public void setVoiceTime(String voiceTime) {
		this.voiceTime = voiceTime;
	}
	@Column(name = "stat")
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	@Column(name = "agent_id")
	public String getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
