package com.capinfo.omp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.capinfo.assistant.platform.ws.card.model.CardPersonMessageBack;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.framework.model.StatusEnabled;

@Entity
@Table(name = "card_person")
public class CardPerson1 extends CardPersonMessageBack implements BaseEntity{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 姓名
	 * */
	private String name;
	/**
	 * 姓别
	 */
	private String sex;
	/**
	 * 证件类型
	 */
	private String certificatesType;
	/**
	 * 证件号码
	 */
	private String certificatesNumber;
	/**
	 * 发证机关
	 */
	private String idcradDept;
	/**
	 * 证件有郊期
	 */
	private String disabilityCardDate;
	/**
	 * 出生日期
	 */

	private String birthdate;
	/**
	 * 居住地所在区县
	 */
	private String residenceCounty;
	/**
	 * 居住地所在街道
	 */
	private String residenceStreet;
	/**
	 * 居住地所在社区
	 */
	private String residenceCommunity;

	/**
	 * 居住地地址
	 */
	private String residenceAddress;

	/**
	 * 制卡推送时间
	 */
	private String creatCardPushDate;
	/**
	 * 制卡回盘时间
	 */
	private String createCardInDate;
	/**
	 * 制卡是否成功
	 */
	private String createCardSuccess;
	/**
	 * 制卡失败原因
	 */
	private String createCardFailInfo;

	/**
	 * 区县是否推送
	 */
	private String cityPushed;

	/**
	 * 区县推送时间
	 */
	private String cityPushedDate;

	/**
	 * 核查推送时间
	 */
	private String hcPushedDate;
	/**
	 * 核查回盘时间
	 */
	private String hcInTime;
	/**
	 * 核查是否成功
	 */
	private String hcSuccess;
	/**
	 * 核查失败原因
	 */
	private String hcFailInfo;

	/**
	 * 卡类型
	 */
	private String cardType;

	/**
	 * 数据采集状态
	 */
	private String gatherStatus;
	/**
	 * 系统审核状态
	 */
	private String auditStatus;

	/**
	 * 制卡
	 */
	private String creatCardStatus;

	/**
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 一本通号
	 */
	private String bookId;

	/**
	 * 北京通号
	 */

	private String bjtNumber;

	/**
	 * 一卡通号
	 */

	private String yktNumber;

	/**
	 * 制卡时间
	 */

	private String creatCardDate;

	/**
	 * 民族
	 */

	private String nation;
	/**
	 * 邮编
	 */
	private String postalCode;

	/**
	 * 户口在区县
	 */
	private String householdCounty;
	/**
	 * 户口所在街道
	 */
	private String householdStreet;
	/**
	 * 户口所在社区
	 */
	private String householdCommunity;

	/**
	 * 户口地址
	 */
	private String householdAddress;
	/**
	 * 报纸收取方式
	 */

	private String newspaperGetWayType;

	/**
	 * 联系人
	 */
	private String contacter;
	/**
	 * 联系人证件类型
	 */

	private String contacterIdcardType;

	/**
	 * 联系人证件号码
	 */
	private String contacterIdcardNumber;

	/**
	 * 联系人手机号
	 */

	private String contacterMobile;
	/**
	 * 与联系人关系
	 */

	private String contacterRelation;
	/**
	 * 居住情况
	 */
	private String resideType;

	/**
	 * 文化程度
	 */
	private String degreeType;

	/**
	 * 医疗保障类型
	 */
	private String healthCareType;

	/**
	 * 月收入
	 */
	private String revenueType;

	/**
	 * 享受金额
	 */

	private String treatmentOney;

	/**
	 * 主要经济来源
	 */

	private String mainSourceIncomeType;

	/**
	 * 人员类型
	 */
	private String personType;

	/**
	 * 生活自理情况
	 */

	private String selfCareAbilityType;

	/**
	 * 婚姻状况
	 */

	private String marryStateType;

	/**
	 * 居住小区类型
	 */
	private String biotope;
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(name = "certificatesType")
	public String getCertificatesType() {
		return certificatesType;
	}

	public void setCertificatesType(String certificatesType) {
		this.certificatesType = certificatesType;
	}
	@Column(name = "certificatesNumber")
	public String getCertificatesNumber() {
		return certificatesNumber;
	}

	public void setCertificatesNumber(String certificatesNumber) {
		this.certificatesNumber = certificatesNumber;
	}
	@Column(name = "idcradDept")
	public String getIdcradDept() {
		return idcradDept;
	}

	public void setIdcradDept(String idcradDept) {
		this.idcradDept = idcradDept;
	}
	@Column(name = "disabilityCardDate")
	public String getDisabilityCardDate() {
		return disabilityCardDate;
	}

	public void setDisabilityCardDate(String disabilityCardDate) {
		this.disabilityCardDate = disabilityCardDate;
	}
	@Column(name = "birthdate")
	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	@Column(name = "residenceCounty")
	public String getResidenceCounty() {
		return residenceCounty;
	}

	public void setResidenceCounty(String residenceCounty) {
		this.residenceCounty = residenceCounty;
	}
	@Column(name = "residenceStreet")
	public String getResidenceStreet() {
		return residenceStreet;
	}

	public void setResidenceStreet(String residenceStreet) {
		this.residenceStreet = residenceStreet;
	}
	@Column(name = "residenceCommunity")
	public String getResidenceCommunity() {
		return residenceCommunity;
	}

	public void setResidenceCommunity(String residenceCommunity) {
		this.residenceCommunity = residenceCommunity;
	}
	@Column(name = "residenceAddress")
	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}
	@Column(name = "creatCardPushDate")
	public String getCreatCardPushDate() {
		return creatCardPushDate;
	}

	public void setCreatCardPushDate(String creatCardPushDate) {
		this.creatCardPushDate = creatCardPushDate;
	}
	@Column(name = "createCardInDate")
	public String getCreateCardInDate() {
		return createCardInDate;
	}

	public void setCreateCardInDate(String createCardInDate) {
		this.createCardInDate = createCardInDate;
	}
	@Column(name = "createCardSuccess")
	public String getCreateCardSuccess() {
		return createCardSuccess;
	}

	public void setCreateCardSuccess(String createCardSuccess) {
		this.createCardSuccess = createCardSuccess;
	}
	@Column(name = "createCardFailInfo")
	public String getCreateCardFailInfo() {
		return createCardFailInfo;
	}

	public void setCreateCardFailInfo(String createCardFailInfo) {
		this.createCardFailInfo = createCardFailInfo;
	}
	@Column(name = "cityPushed")
	public String getCityPushed() {
		return cityPushed;
	}

	public void setCityPushed(String cityPushed) {
		this.cityPushed = cityPushed;
	}
	@Column(name = "cityPushedDate")
	public String getCityPushedDate() {
		return cityPushedDate;
	}

	public void setCityPushedDate(String cityPushedDate) {
		this.cityPushedDate = cityPushedDate;
	}
	@Column(name = "hcPushedDate")
	public String getHcPushedDate() {
		return hcPushedDate;
	}

	public void setHcPushedDate(String hcPushedDate) {
		this.hcPushedDate = hcPushedDate;
	}
	@Column(name = "hcInTime")
	public String getHcInTime() {
		return hcInTime;
	}

	public void setHcInTime(String hcInTime) {
		this.hcInTime = hcInTime;
	}
	@Column(name = "hcSuccess")
	public String getHcSuccess() {
		return hcSuccess;
	}

	public void setHcSuccess(String hcSuccess) {
		this.hcSuccess = hcSuccess;
	}
	@Column(name = "hcFailInfo")
	public String getHcFailInfo() {
		return hcFailInfo;
	}

	public void setHcFailInfo(String hcFailInfo) {
		this.hcFailInfo = hcFailInfo;
	}
	@Column(name = "cardType")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	@Column(name = "gatherStatus")
	public String getGatherStatus() {
		return gatherStatus;
	}

	public void setGatherStatus(String gatherStatus) {
		this.gatherStatus = gatherStatus;
	}
	@Column(name = "auditStatus")
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	@Column(name = "creatCardStatus")
	public String getCreatCardStatus() {
		return creatCardStatus;
	}

	public void setCreatCardStatus(String creatCardStatus) {
		this.creatCardStatus = creatCardStatus;
	}
	@Column(name = "bankCard")
	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	@Column(name = "bookId")
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	@Column(name = "bjtNumber")
	public String getBjtNumber() {
		return bjtNumber;
	}

	public void setBjtNumber(String bjtNumber) {
		this.bjtNumber = bjtNumber;
	}
	@Column(name = "yktNumber")
	public String getYktNumber() {
		return yktNumber;
	}

	public void setYktNumber(String yktNumber) {
		this.yktNumber = yktNumber;
	}
	@Column(name = "creatCardDate")
	public String getCreatCardDate() {
		return creatCardDate;
	}

	public void setCreatCardDate(String creatCardDate) {
		this.creatCardDate = creatCardDate;
	}
	@Column(name = "nation")
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	@Column(name = "postalCode")
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	@Column(name = "householdCounty")
	public String getHouseholdCounty() {
		return householdCounty;
	}

	public void setHouseholdCounty(String householdCounty) {
		this.householdCounty = householdCounty;
	}
	@Column(name = "householdStreet")
	public String getHouseholdStreet() {
		return householdStreet;
	}

	public void setHouseholdStreet(String householdStreet) {
		this.householdStreet = householdStreet;
	}
	@Column(name = "householdCommunity")
	public String getHouseholdCommunity() {
		return householdCommunity;
	}

	public void setHouseholdCommunity(String householdCommunity) {
		this.householdCommunity = householdCommunity;
	}
	@Column(name = "householdAddress")
	public String getHouseholdAddress() {
		return householdAddress;
	}

	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress;
	}
	@Column(name = "newspaperGetWayType")
	public String getNewspaperGetWayType() {
		return newspaperGetWayType;
	}

	public void setNewspaperGetWayType(String newspaperGetWayType) {
		this.newspaperGetWayType = newspaperGetWayType;
	}
	@Column(name = "contacter")
	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}
	@Column(name = "contacterIdcardType")
	public String getContacterIdcardType() {
		return contacterIdcardType;
	}

	public void setContacterIdcardType(String contacterIdcardType) {
		this.contacterIdcardType = contacterIdcardType;
	}
	@Column(name = "contacterIdcardNumber")
	public String getContacterIdcardNumber() {
		return contacterIdcardNumber;
	}

	public void setContacterIdcardNumber(String contacterIdcardNumber) {
		this.contacterIdcardNumber = contacterIdcardNumber;
	}
	@Column(name = "contacterMobile")
	public String getContacterMobile() {
		return contacterMobile;
	}

	public void setContacterMobile(String contacterMobile) {
		this.contacterMobile = contacterMobile;
	}
	@Column(name = "contacterRelation")
	public String getContacterRelation() {
		return contacterRelation;
	}

	public void setContacterRelation(String contacterRelation) {
		this.contacterRelation = contacterRelation;
	}
	@Column(name = "resideType")
	public String getResideType() {
		return resideType;
	}

	public void setResideType(String resideType) {
		this.resideType = resideType;
	}
	@Column(name = "degreeType")
	public String getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}
	@Column(name = "healthCareType")
	public String getHealthCareType() {
		return healthCareType;
	}

	public void setHealthCareType(String healthCareType) {
		this.healthCareType = healthCareType;
	}
	@Column(name = "revenueType")
	public String getRevenueType() {
		return revenueType;
	}

	public void setRevenueType(String revenueType) {
		this.revenueType = revenueType;
	}
	@Column(name = "treatmentOney")
	public String getTreatmentOney() {
		return treatmentOney;
	}

	public void setTreatmentOney(String treatmentOney) {
		this.treatmentOney = treatmentOney;
	}
	@Column(name = "mainSourceIncomeType")
	public String getMainSourceIncomeType() {
		return mainSourceIncomeType;
	}

	public void setMainSourceIncomeType(String mainSourceIncomeType) {
		this.mainSourceIncomeType = mainSourceIncomeType;
	}
	@Column(name = "personType")
	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}
	@Column(name = "selfCareAbilityType")
	public String getSelfCareAbilityType() {
		return selfCareAbilityType;
	}

	public void setSelfCareAbilityType(String selfCareAbilityType) {
		this.selfCareAbilityType = selfCareAbilityType;
	}
	@Column(name = "marryStateType")
	public String getMarryStateType() {
		return marryStateType;
	}

	public void setMarryStateType(String marryStateType) {
		this.marryStateType = marryStateType;
	}
	@Column(name = "biotope")
	public String getBiotope() {
		return biotope;
	}

	public void setBiotope(String biotope) {
		this.biotope = biotope;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
