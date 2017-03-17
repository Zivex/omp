package com.capinfo.common.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.capinfo.framework.model.system.SecureUser;
import com.capinfo.omp.model.Enterprise;
import com.capinfo.region.model.OmpRegion;

@Entity
@Table(name = "USERS")
// @SequenceGenerator(allocationSize = 1, name = "seq", sequenceName =
// "SEQ_USERS")
public class SystemUser implements SecureUser<SystemUser, Role> {

	private Long id;

	private Long parentid;

	private String logonName;

	private int userType;

	private String password;

	private String name;

	private String descritption;

	private Boolean enabled = true;

	private Set<Role> roles = new HashSet<Role>();

	private int num;

	// 账户类型
	private String account_type;

	// 账户等级
	private int leave;

	// 行政信息
	private String regionName;

	// 行政信息
	private Long rid;
	// 是否显示老人打全部信息
	private Long display_all;

	// 用户编码
	private String encoding;
	
	// 企业名称
	private Long type_id;
	private Enterprise enterprise;


	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USER_NAME", length = 256)
	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	@Column(name = "USER_TYPE", length = 256)
	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	@Column(name = "PASSWORD", length = 256)
	@NotEmpty(message = "密码不能为空!")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "NAME_", length = 256)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION", length = 512)
	public String getDescritption() {
		return descritption;
	}

	public void setDescritption(String descritption) {
		this.descritption = descritption;
	}

	@Column(name = "ENABLED")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "num")
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Column(name = "parentid")
	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	@Column(name = "account_type")
	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	@Column(name = "leavel")
	public int getLeave() {
		return leave;
	}

	public void setLeave(int leave) {
		this.leave = leave;
	}

	@Column(name = "regionName")
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Column(name = "rid")
	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	@Column(name = "display_all")
	public Long getDisplay_all() {
		return display_all;
	}

	public void setDisplay_all(Long display_all) {
		this.display_all = display_all;
	}

	@Column(name = "encoding")
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	
	
	@Column(name = "type_id")
	public Long getType_id() {
		return type_id;
	}

	public void setType_id(Long type_id) {
		this.type_id = type_id;
	}
	@ManyToOne(targetEntity = Enterprise.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id", insertable = false, updatable = false)
	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public SystemUser() {
		super();
	}

	public SystemUser(Long id) {
		super();
		this.id = id;
	}

	// @OneToMany(targetEntity = UserRole.class, cascade = CascadeType.ALL,
	// fetch = FetchType.LAZY)
	// @JoinColumn(name = "USER_ID")
	// public List<UserRole> getUserRoles() {
	// return userRoles;
	// }
	//
	// public void setUserRoles(List<UserRole> userRoles) {
	// this.userRoles = userRoles;
	// }

	@ManyToMany(targetEntity = Role.class, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	// 解决FetchType.EAGER数据重复问题
	@JoinTable(name = "USER_ROLES", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Transient
	public void updateUserInfo(SystemUser entity) {
		this.setName(entity.getName());
		this.setUserType(entity.getUserType());
		this.setRoles(entity.getRoles());
		this.setNum(entity.getNum());
	}

}
