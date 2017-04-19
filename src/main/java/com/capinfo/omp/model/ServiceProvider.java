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
@Table(name = "SERVICE_PROVIDER")
public class ServiceProvider implements BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;

	//市
	private Long city_id;
	private OmpRegion city;
	//区县
	private Long county_id;
	private OmpRegion county;
	//街道
	private Long street_id;
	private OmpRegion street;

//	private Long community_id;
//	private OmpRegion community;
	//服务单位名称
	private String serviceName;
	//营业执照名称
	private String charterName;
	//营业执照编码
	private String charterNumber;
	//服务电话
//	private Long serviceTell;
	//服务地址
	private String serviceAddress;

	//服务类型
	private Long serviceTypeId;
	private ServiceType serviceType;

	//服务区域描述
	private String addressDescribe;

	private String serviceCounty_id;
	private OmpRegion serviceCounty;
	private String serviceStreet_id;
	private OmpRegion serviceStreet;
	private String serviceCommunity_id;
	private OmpRegion serviceCommunity;

	//渠道发展来源
	private Long channels;
	
	private SystemUser user;

	//服务电话
	private String serviceTell;

	//联系人
	private String contact;

	//联系人手机号
	private String contactPhone;

	/**
	 * 是否能刷养老卡
	 *0:否  1:是
	 */
	private int is_pensionCard;

	/**
	 *是否能刷跨年
	 *0:否  1:是
	 */
	private int is_AcrossYears;

	/**
	 *是否能刷失能
	 *0:否  1:是
	 */
	private int is_anergy;

	//上级服务单位名称
	private String superiorServiceName;

	//总负责人
	private String principal;

	//总负责人联系电话
	private String principalPhone;
	//售后对接人
	private String aftermarketPerson ;
	//售后电话
	private String aftermarketPhone ;
	//服务内容
	private String serviceContent;
	/**
	 *折扣信息
	 *0:否  1:是
	 */
	private int discountInfo;
	/**
	 * 核实状态
	 * 1:未审核
	 * 2:无效
	 * 3:有效
	 */
	private int verify;
	/**
	 *是否签约
	 *0:否  1:是
	 */
	private int is_signing;
	//签约时间
	private Date signingDate;
	//服务状态
	private String serviceState;
	//邮箱
	private String email;
	
	
	private Date createTime;
	private Date updateTime;
	private int user_falg;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "city_id")
	public Long getCity_id() {
		return city_id;
	}


	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}
	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id", insertable = false, updatable = false)
	public OmpRegion getCity() {
		return city;
	}


	public void setCity(OmpRegion city) {
		this.city = city;
	}

	@Column(name = "county_id")
	public Long getCounty_id() {
		return county_id;
	}


	public void setCounty_id(Long county_id) {
		this.county_id = county_id;
	}
	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "county_id", insertable = false, updatable = false)
	public OmpRegion getCounty() {
		return county;
	}


	public void setCounty(OmpRegion county) {
		this.county = county;
	}

	@Column(name = "street_id")
	public Long getStreet_id() {
		return street_id;
	}


	public void setStreet_id(Long street_id) {
		this.street_id = street_id;
	}

	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "street_id", insertable = false, updatable = false)
	public OmpRegion getStreet() {
		return street;
	}


	public void setStreet(OmpRegion street) {
		this.street = street;
	}

	@Column(name = "serviceName")
	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "charterName")
	public String getCharterName() {
		return charterName;
	}


	public void setCharterName(String charterName) {
		this.charterName = charterName;
	}

	@Column(name = "charterNumber")
	public String getCharterNumber() {
		return charterNumber;
	}


	public void setCharterNumber(String charterNumber) {
		this.charterNumber = charterNumber;
	}

	@Column(name = "serviceAddress")
	public String getServiceAddress() {
		return serviceAddress;
	}


	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	@Column(name = "serviceTypeId")
	public Long getServiceTypeId() {
		return serviceTypeId;
	}


	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	@ManyToOne(targetEntity = ServiceType.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "serviceTypeId", insertable = false, updatable = false)
	public ServiceType getServiceType() {
		return serviceType;
	}


	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	@Column(name = "addressDescribe")
	public String getAddressDescribe() {
		return addressDescribe;
	}


	public void setAddressDescribe(String addressDescribe) {
		this.addressDescribe = addressDescribe;
	}

	@Column(name = "serviceCounty_id")
	public String getServiceCounty_id() {
		return serviceCounty_id;
	}


	public void setServiceCounty_id(String serviceCounty_id) {
		this.serviceCounty_id = serviceCounty_id;
	}
	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "serviceCounty_id", insertable = false, updatable = false)
	public OmpRegion getServiceCounty() {
		return serviceCounty;
	}


	public void setServiceCounty(OmpRegion serviceCounty) {
		this.serviceCounty = serviceCounty;
	}

	@Column(name = "serviceStreet_id")
	public String getServiceStreet_id() {
		return serviceStreet_id;
	}


	public void setServiceStreet_id(String serviceStreet_id) {
		this.serviceStreet_id = serviceStreet_id;
	}
	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "serviceStreet_id", insertable = false, updatable = false)
	public OmpRegion getServiceStreet() {
		return serviceStreet;
	}


	public void setServiceStreet(OmpRegion serviceStreet) {
		this.serviceStreet = serviceStreet;
	}

	@Column(name = "serviceCommunity_id")
	public String getServiceCommunity_id() {
		return serviceCommunity_id;
	}


	public void setServiceCommunity_id(String serviceCommunity_id) {
		this.serviceCommunity_id = serviceCommunity_id;
	}
	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "serviceCommunity_id", insertable = false, updatable = false)
	public OmpRegion getServiceCommunity() {
		return serviceCommunity;
	}


	public void setServiceCommunity(OmpRegion serviceCommunity) {
		this.serviceCommunity = serviceCommunity;
	}

	@Column(name = "channels")
	public Long getChannels() {
		return channels;
	}


	public void setChannels(Long channels) {
		this.channels = channels;
	}
	
	
	

	@Column(name = "serviceTell")
	public String getServiceTell() {
		return serviceTell;
	}


	public void setServiceTell(String serviceTell) {
		this.serviceTell = serviceTell;
	}

	@Column(name = "contact")
	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "contactPhone")
	public String getContactPhone() {
		return contactPhone;
	}


	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(name = "is_pensionCard")
	public int getIs_pensionCard() {
		return is_pensionCard;
	}


	public void setIs_pensionCard(int is_pensionCard) {
		this.is_pensionCard = is_pensionCard;
	}

	@Column(name = "is_AcrossYears")
	public int getIs_AcrossYears() {
		return is_AcrossYears;
	}


	public void setIs_AcrossYears(int is_AcrossYears) {
		this.is_AcrossYears = is_AcrossYears;
	}

	@Column(name = "is_anergy")
	public int getIs_anergy() {
		return is_anergy;
	}


	public void setIs_anergy(int is_anergy) {
		this.is_anergy = is_anergy;
	}

	@Column(name = "superiorServiceName")
	public String getSuperiorServiceName() {
		return superiorServiceName;
	}


	public void setSuperiorServiceName(String superiorServiceName) {
		this.superiorServiceName = superiorServiceName;
	}

	@Column(name = "principal")
	public String getPrincipal() {
		return principal;
	}


	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@Column(name = "principalPhone")
	public String getPrincipalPhone() {
		return principalPhone;
	}


	public void setPrincipalPhone(String principalPhone) {
		this.principalPhone = principalPhone;
	}

	@Column(name = "aftermarketPerson")
	public String getAftermarketPerson() {
		return aftermarketPerson;
	}


	public void setAftermarketPerson(String aftermarketPerson) {
		this.aftermarketPerson = aftermarketPerson;
	}

	@Column(name = "aftermarketPhone")
	public String getAftermarketPhone() {
		return aftermarketPhone;
	}


	public void setAftermarketPhone(String aftermarketPhone) {
		this.aftermarketPhone = aftermarketPhone;
	}

	@Column(name = "serviceContent")
	public String getServiceContent() {
		return serviceContent;
	}


	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	@Column(name = "discountInfo")
	public int getDiscountInfo() {
		return discountInfo;
	}


	public void setDiscountInfo(int discountInfo) {
		this.discountInfo = discountInfo;
	}

	@Column(name = "verify")
	public int getVerify() {
		return verify;
	}


	public void setVerify(int verify) {
		this.verify = verify;
	}

	@Column(name = "is_signing")
	public int getIs_signing() {
		return is_signing;
	}


	public void setIs_signing(int is_signing) {
		this.is_signing = is_signing;
	}
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "signingDate")
	public Date getSigningDate() {
		return signingDate;
	}


	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	@Column(name = "serviceState")
	public String getServiceState() {
		return serviceState;
	}


	public void setServiceState(String serviceState) {
		this.serviceState = serviceState;
	}




	@Column(name = "email")
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "user_falg")
	public int getUser_falg() {
		return user_falg;
	}


	public void setUser_falg(int user_falg) {
		this.user_falg = user_falg;
	}

	@ManyToOne(targetEntity = SystemUser.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "channels", insertable = false, updatable = false)
	public SystemUser getUser() {
		return user;
	}


	public void setUser(SystemUser user) {
		this.user = user;
	}


	





}
