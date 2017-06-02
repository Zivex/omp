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
import com.capinfo.common.model.SystemUser;
import com.capinfo.framework.model.BaseEntity;
import com.capinfo.region.model.OmpRegion;

/**
 * 用户信息
 * 
 * @author zx
 * 
 */
@Entity
@Table(name = "omp_old_info")
public class Omp_Old_Info  implements BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id;

	private String household_city_id;
	private OmpRegion household_city;
	
	private String household_county_id;
	private OmpRegion household_county;

	private String household_street_id;
	private OmpRegion household_street;

	private String household_community_id;
	private OmpRegion household_community;

	private String workername;
	private String workertel;
	private String name;
	private String certificates_number;
	private String zjnumber;
	private String phone;
	private String address;
	private String emergencycontact;
	private String emergencycontacttle;
	private String tel;
	private String teltype;
	private Omp_phone_type phoneType;
	private String usertype;
	private Long state;
	private Long ispersonalized;
	private Long updNumber;
	private Long isGenerationOrder;
	private Date creationTime;
	private Date updateTime;
	private int isindividuation;
	private Long sync;
	private Long agent_id;
	private SystemUser user;
	private Long num;
	private Long ordernum;
	private Long call_id;
	private String account_type;
	// 用户名称
	private String user_name;
	
	
	private Integer yiji;
	private Integer erji;
	private Integer sjji;
	private Integer siji;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	
	
	
	
	
	@Column(name = "HOUSEHOLD_CITY_ID", length = 256)
	public String getHousehold_city_id() {
		return household_city_id;
	}

	public void setHousehold_city_id(String household_city_id) {
		this.household_city_id = household_city_id;
	}
	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "HOUSEHOLD_CITY_ID", insertable = false, updatable = false)
	public OmpRegion getHousehold_city() {
		return household_city;
	}

	public void setHousehold_city(OmpRegion household_city) {
		this.household_city = household_city;
	}

	@Column(name = "household_county_id", length = 256)
	public String getHousehold_county_id() {
		return household_county_id;
	}

	public void setHousehold_county_id(String household_county_id) {
		this.household_county_id = household_county_id;
	}

	@Column(name = "household_street_id", length = 256)
	public String getHousehold_street_id() {
		return household_street_id;
	}

	public void setHousehold_street_id(String household_street_id) {
		this.household_street_id = household_street_id;
	}

	@Column(name = "household_community_id", length = 256)
	public String getHousehold_community_id() {
		return household_community_id;
	}

	public void setHousehold_community_id(String household_community_id) {
		this.household_community_id = household_community_id;
	}

	@Column(name = "workername", length = 256)
	public String getWorkername() {
		return workername;
	}

	public void setWorkername(String workername) {
		this.workername = workername;
	}

	@Column(name = "workertel", length = 256)
	public String getWorkertel() {
		return workertel;
	}

	public void setWorkertel(String workertel) {
		this.workertel = workertel;
	}

	@Column(name = "name", length = 256)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "certificates_number", length = 256)
	public String getCertificates_number() {
		return certificates_number;
	}

	public void setCertificates_number(String certificates_number) {
		this.certificates_number = certificates_number;
	}

	@Column(name = "zjnumber", length = 256)
	public String getZjnumber() {
		return zjnumber;
	}

	public void setZjnumber(String zjnumber) {
		this.zjnumber = zjnumber;
	}

	@Column(name = "phone", length = 256)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address", length = 256)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "emergencycontact", length = 256)
	public String getEmergencycontact() {
		return emergencycontact;
	}

	public void setEmergencycontact(String emergencycontact) {
		this.emergencycontact = emergencycontact;
	}

	@Column(name = "emergencycontacttle", length = 256)
	public String getEmergencycontacttle() {
		return emergencycontacttle;
	}

	public void setEmergencycontacttle(String emergencycontacttle) {
		this.emergencycontacttle = emergencycontacttle;
	}

	@Column(name = "tel", length = 256)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "teltype", length = 256)
	public String getTeltype() {
		return teltype;
	}

	public void setTeltype(String teltype) {
		this.teltype = teltype;
	}

	@Column(name = "usertype", length = 256)
	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	@Column(name = "state")
	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	@Column(name = "ispersonalized")
	public Long getIspersonalized() {
		return ispersonalized;
	}

	public void setIspersonalized(Long ispersonalized) {
		this.ispersonalized = ispersonalized;
	}

	@Column(name = "updNumber")
	public Long getUpdNumber() {
		return updNumber;
	}

	public void setUpdNumber(Long updNumber) {
		this.updNumber = updNumber;
	}

	@Column(name = "isGenerationOrder")
	public Long getIsGenerationOrder() {
		return isGenerationOrder;
	}

	public void setIsGenerationOrder(Long isGenerationOrder) {
		this.isGenerationOrder = isGenerationOrder;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creationTime", length = 256)
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime", length = 256)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "isindividuation", length = 256)
	public int getIsindividuation() {
		return isindividuation;
	}

	public void setIsindividuation(int isindividuation) {
		this.isindividuation = isindividuation;
	}

	@Column(name = "sync")
	public Long getSync() {
		return sync;
	}

	public void setSync(Long sync) {
		this.sync = sync;
	}

	@Column(name = "agent_id")
	public Long getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}

	@Column(name = "num")
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	@Column(name = "ordernum")
	public Long getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}

	
		

	@Column(name = "call_id", length = 11)
	public Long getCall_id() {
		return call_id;
	}

	
	public void setCall_id(Long call_id) {
		this.call_id = call_id;
	}

	@Column(name = "account_type", length = 11)
	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}


	public Omp_Old_Info() {
		super();
	}

	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "household_county_id", insertable = false, updatable = false)
	public OmpRegion getHousehold_county() {
		return household_county;
	}

	public void setHousehold_county(OmpRegion household_county) {
		this.household_county = household_county;
	}

	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "household_street_id", insertable = false, updatable = false)
	public OmpRegion getHousehold_street() {
		return household_street;
	}

	public void setHousehold_street(OmpRegion household_street) {
		this.household_street = household_street;
	}

	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "household_community_id", insertable = false, updatable = false)
	public OmpRegion getHousehold_community() {
		return household_community;
	}

	public void setHousehold_community(OmpRegion household_community) {
		this.household_community = household_community;
	}
	
	
	@ManyToOne(targetEntity = Omp_phone_type.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "teltype", insertable = false, updatable = false)
	public Omp_phone_type getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(Omp_phone_type phoneType) {
		this.phoneType = phoneType;
	}

	@Column(name = "user_name", length = 11)
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	@Column(name = "yiji")
	public Integer getYiji() {
		return yiji;
	}

	public void setYiji(Integer yiji) {
		this.yiji = yiji;
	}
	@Column(name = "erji")
	public Integer getErji() {
		return erji;
	}

	public void setErji(Integer erji) {
		this.erji = erji;
	}
	@Column(name = "sjji")
	public Integer getSjji() {
		return sjji;
	}

	public void setSjji(Integer sjji) {
		this.sjji = sjji;
	}
	@Column(name = "siji")
	public Integer getSiji() {
		return siji;
	}

	public void setSiji(Integer siji) {
		this.siji = siji;
	}
	@ManyToOne(targetEntity = SystemUser.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_id", insertable = false, updatable = false)
	public SystemUser getUser() {
		return user;
	}

	public void setUser(SystemUser user) {
		this.user = user;
	}
	
	

	
	

}
