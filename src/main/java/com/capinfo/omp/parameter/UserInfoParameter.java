package com.capinfo.omp.parameter;

import com.capinfo.framework.web.parameter.DataListParameter;
import com.capinfo.omp.model.Omp_old_order;
import com.capinfo.omp.model.Omp_voice_order;

public class UserInfoParameter extends DataListParameter<Omp_voice_order> {
	private Omp_voice_order entity = new Omp_voice_order();
	private Class<Omp_voice_order> entityClazz = Omp_voice_order.class;
	private Long sendSuccess;
	
	private Long sendFail;
	
	private Long executeSuc;
	
	private Long executeFail;
	
	//未接听
	private Long notAnswer;
	//未返回
	private Long notReturn;
	
	//剩余发送次数
	private Long remainCount;
	
	private Long voiceCount;
	private Long orderCount;
	
	//发送总次数
	private int sumCount;
	
	
	
	private Long voiceSendFail;
	private Long orderSuc;
	private Long orderFail;
	private Long voiceSendSuc;
	public Omp_voice_order getEntity() {
		return entity;
	}
	public void setEntity(Omp_voice_order entity) {
		this.entity = entity;
	}
	public Class<Omp_voice_order> getEntityClazz() {
		return entityClazz;
	}
	public void setEntityClazz(Class<Omp_voice_order> entityClazz) {
		this.entityClazz = entityClazz;
	}
	public Long getSendSuccess() {
		return sendSuccess;
	}
	public void setSendSuccess(Long sendSuccess) {
		this.sendSuccess = sendSuccess;
	}
	public Long getSendFail() {
		return sendFail;
	}
	public void setSendFail(Long sendFail) {
		this.sendFail = sendFail;
	}
	public Long getExecuteSuc() {
		return executeSuc;
	}
	public void setExecuteSuc(Long executeSuc) {
		this.executeSuc = executeSuc;
	}
	public Long getExecuteFail() {
		return executeFail;
	}
	public void setExecuteFail(Long executeFail) {
		this.executeFail = executeFail;
	}
	public Long getNotAnswer() {
		return notAnswer;
	}
	public void setNotAnswer(Long notAnswer) {
		this.notAnswer = notAnswer;
	}
	public Long getNotReturn() {
		return notReturn;
	}
	public void setNotReturn(Long notReturn) {
		this.notReturn = notReturn;
	}
	public Long getRemainCount() {
		return remainCount;
	}
	public void setRemainCount(Long remainCount) {
		this.remainCount = remainCount;
	}
	public Long getVoiceCount() {
		return voiceCount;
	}
	public void setVoiceCount(Long voiceCount) {
		this.voiceCount = voiceCount;
	}
	public Long getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}
	public int getSumCount() {
		return sumCount;
	}
	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
	}
	public Long getVoiceSendFail() {
		return voiceSendFail;
	}
	public void setVoiceSendFail(Long voiceSendFail) {
		this.voiceSendFail = voiceSendFail;
	}
	public Long getOrderSuc() {
		return orderSuc;
	}
	public void setOrderSuc(Long orderSuc) {
		this.orderSuc = orderSuc;
	}
	public Long getOrderFail() {
		return orderFail;
	}
	public void setOrderFail(Long orderFail) {
		this.orderFail = orderFail;
	}
	public Long getVoiceSendSuc() {
		return voiceSendSuc;
	}
	public void setVoiceSendSuc(Long voiceSendSuc) {
		this.voiceSendSuc = voiceSendSuc;
	}
	
	
	

	
	
	
	
}
