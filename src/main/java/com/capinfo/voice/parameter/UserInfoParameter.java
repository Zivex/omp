package com.capinfo.voice.parameter;

public class UserInfoParameter {
	private int sendSuccess;
	
	private int sendFail;
	
	private int executeSuc;
	
	private int executeFail;
	
	//未接听
	private int notAnswer;
	//未返回
	private int notReturn;
	
	//剩余语音发送次数
	private int voiceCount;
	
	//语音发送总次数
	private int sumCount;
	
	

	public int getSumCount() {
		return sumCount;
	}

	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
	}

	public int getVoiceCount() {
		return voiceCount;
	}

	public void setVoiceCount(int voiceCount) {
		this.voiceCount = voiceCount;
	}

	public int getSendSuccess() {
		return sendSuccess;
	}

	public void setSendSuccess(int sendSuccess) {
		this.sendSuccess = sendSuccess;
	}

	public int getSendFail() {
		return sendFail;
	}

	public void setSendFail(int sendFail) {
		this.sendFail = sendFail;
	}

	public int getExecuteSuc() {
		return executeSuc;
	}

	public void setExecuteSuc(int executeSuc) {
		this.executeSuc = executeSuc;
	}

	public int getExecuteFail() {
		return executeFail;
	}

	public void setExecuteFail(int executeFail) {
		this.executeFail = executeFail;
	}

	public int getNotAnswer() {
		return notAnswer;
	}

	public void setNotAnswer(int notAnswer) {
		this.notAnswer = notAnswer;
	}

	public int getNotReturn() {
		return notReturn;
	}

	public void setNotReturn(int notReturn) {
		this.notReturn = notReturn;
	}
	
	
	
}
