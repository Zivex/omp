package com.capinfo.order.modle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.capinfo.framework.model.BaseEntity;
/**
 * 用户信息
 * @author lenovo
 *
 */
@Entity
@Table(name = "Omp_Old_Info")
public class Omp_Old_Info implements BaseEntity {

	private long id;
	private String household_county_id;
	private String household_street_id;
	private String household_community_id;
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
	private String usertype;
	private String state;
	private String ispersonalized;
	private String updNumber;
	private int isGenerationOrder;
	private String creationTime;
	private String updateTime;
	private int isindividuation;
	private String sync;
	private String agent_id;
	private int num;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	@Override
	public void setId(Long arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
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
	@Column(name = "state", length = 256)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	@Column(name = "ispersonalized", length = 256)
	public String getIspersonalized() {
		return ispersonalized;
	}

	public void setIspersonalized(String ispersonalized) {
		this.ispersonalized = ispersonalized;
	}
	@Column(name = "updNumber", length = 256)
	public String getUpdNumber() {
		return updNumber;
	}

	public void setUpdNumber(String updNumber) {
		this.updNumber = updNumber;
	}
	@Column(name = "isGenerationOrder", length = 256)
	public String getIsGenerationOrder() {
		return isGenerationOrder;
	}

	public void setIsGenerationOrder(String isGenerationOrder) {
		this.isGenerationOrder = isGenerationOrder;
	}
	@Column(name = "creationTime", length = 256)
	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	@Column(name = "updateTime", length = 256)
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "isindividuation", length = 256)
	public String getIsindividuation() {
		return isindividuation;
	}

	public void setIsindividuation(String isindividuation) {
		this.isindividuation = isindividuation;
	}
	@Column(name = "sync", length = 256)
	public String getSync() {
		return sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}
	@Column(name = "agent_id", length = 256)
	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}
	@Column(name = "num", length = 256)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

}
