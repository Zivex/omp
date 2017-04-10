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
@Table(name = "service_system")
public class Service_System implements BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long rid;
	private OmpRegion region;
	private Long uid;
	private Long telltype;
	//服务商所对应的键位
	private Long m1;
	private Long m2;
	private Long m3;
	private Long m4;
	private Long m5;
	private Long m6;
	private Long m7;
	private Long m8;
	private Long m9;
	private Long m10;
	private Long m11;
	private Long m12;
	private Long m13;
	private Long m14;
	private Long m15;
	private Long m16;
	
	private ServiceProvider s1Name;
	private ServiceProvider s2Name;
	private ServiceProvider s3Name;
	private ServiceProvider s4Name;
	private ServiceProvider s5Name;
	private ServiceProvider s6Name;
	private ServiceProvider s7Name;
	private ServiceProvider s8Name;
	private ServiceProvider s9Name;
	private ServiceProvider s10Name;
	private ServiceProvider s11Name;
	private ServiceProvider s12Name;
	
	private Date createTime;
	private Date updateTime;
	private Long user_falg;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "uid")
	public Long getUid() {
		return uid;
	}

	
	@Column(name = "rid")
	public Long getRid() {
		return rid;
	}


	public void setRid(Long rid) {
		this.rid = rid;
	}


	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Column(name = "tellType_id")
	public Long getTelltype() {
		return telltype;
	}


	public void setTelltype(Long telltype) {
		this.telltype = telltype;
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
	public Long getUser_falg() {
		return user_falg;
	}


	public void setUser_falg(Long user_falg) {
		this.user_falg = user_falg;
	}


	
	@ManyToOne(targetEntity = OmpRegion.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "rid", insertable = false, updatable = false)
	public OmpRegion getRegion() {
		return region;
	}


	public void setRegion(OmpRegion region) {
		this.region = region;
	}

	@Column(name = "m1")
	public Long getM1() {
		return m1;
	}


	public void setM1(Long m1) {
		this.m1 = m1;
	}

	@Column(name = "m2")
	public Long getM2() {
		return m2;
	}


	public void setM2(Long m2) {
		this.m2 = m2;
	}

	@Column(name = "m3")
	public Long getM3() {
		return m3;
	}


	public void setM3(Long m3) {
		this.m3 = m3;
	}

	@Column(name = "m4")
	public Long getM4() {
		return m4;
	}


	public void setM4(Long m4) {
		this.m4 = m4;
	}

	@Column(name = "m5")
	public Long getM5() {
		return m5;
	}


	public void setM5(Long m5) {
		this.m5 = m5;
	}

	@Column(name = "m6")
	public Long getM6() {
		return m6;
	}


	public void setM6(Long m6) {
		this.m6 = m6;
	}

	@Column(name = "m7")
	public Long getM7() {
		return m7;
	}


	public void setM7(Long m7) {
		this.m7 = m7;
	}

	@Column(name = "m8")
	public Long getM8() {
		return m8;
	}


	public void setM8(Long m8) {
		this.m8 = m8;
	}

	@Column(name = "m9")
	public Long getM9() {
		return m9;
	}


	public void setM9(Long m9) {
		this.m9 = m9;
	}

	@Column(name = "m10")
	public Long getM10() {
		return m10;
	}


	public void setM10(Long m10) {
		this.m10 = m10;
	}

	@Column(name = "m11")
	public Long getM11() {
		return m11;
	}


	public void setM11(Long m11) {
		this.m11 = m11;
	}

	@Column(name = "m12")
	public Long getM12() {
		return m12;
	}


	public void setM12(Long m12) {
		this.m12 = m12;
	}

	@Column(name = "m13")
	public Long getM13() {
		return m13;
	}


	public void setM13(Long m13) {
		this.m13 = m13;
	}

	@Column(name = "m14")
	public Long getM14() {
		return m14;
	}


	public void setM14(Long m14) {
		this.m14 = m14;
	}

	@Column(name = "m15")
	public Long getM15() {
		return m15;
	}


	public void setM15(Long m15) {
		this.m15 = m15;
	}

	@Column(name = "m16")
	public Long getM16() {
		return m16;
	}


	public void setM16(Long m16) {
		this.m16 = m16;
	}
	
	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m1", insertable = false, updatable = false)
	public ServiceProvider getS1Name() {
		return s1Name;
	}


	public void setS1Name(ServiceProvider s1Name) {
		this.s1Name = s1Name;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m2", insertable = false, updatable = false)
	public ServiceProvider getS2Name() {
		return s2Name;
	}


	public void setS2Name(ServiceProvider s2Name) {
		this.s2Name = s2Name;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m3", insertable = false, updatable = false)
	public ServiceProvider getS3Name() {
		return s3Name;
	}


	public void setS3Name(ServiceProvider s3Name) {
		this.s3Name = s3Name;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m4", insertable = false, updatable = false)
	public ServiceProvider getS4Name() {
		return s4Name;
	}


	public void setS4Name(ServiceProvider s4Name) {
		this.s4Name = s4Name;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m5", insertable = false, updatable = false)
	public ServiceProvider getS5Name() {
		return s5Name;
	}


	public void setS5Name(ServiceProvider s5Name) {
		this.s5Name = s5Name;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m6", insertable = false, updatable = false)
	public ServiceProvider getS6Name() {
		return s6Name;
	}


	public void setS6Name(ServiceProvider s6Name) {
		this.s6Name = s6Name;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m7", insertable = false, updatable = false)
	public ServiceProvider getS7Name() {
		return s7Name;
	}


	public void setS7Name(ServiceProvider s7Name) {
		this.s7Name = s7Name;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m8", insertable = false, updatable = false)
	public ServiceProvider getS8Name() {
		return s8Name;
	}


	public void setS8Name(ServiceProvider s8Name) {
		this.s8Name = s8Name;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m9", insertable = false, updatable = false)
	public ServiceProvider getS9Name() {
		return s9Name;
	}


	public void setS9Name(ServiceProvider s9Name) {
		this.s9Name = s9Name;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m10", insertable = false, updatable = false)
	public ServiceProvider getS10Name() {
		return s10Name;
	}


	public void setS10Name(ServiceProvider s10Name) {
		this.s10Name = s10Name;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m11", insertable = false, updatable = false)
	public ServiceProvider getS11Name() {
		return s11Name;
	}


	public void setS11Name(ServiceProvider s11Name) {
		this.s11Name = s11Name;
	}

	@ManyToOne(targetEntity = ServiceProvider.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "m12", insertable = false, updatable = false)
	public ServiceProvider getS12Name() {
		return s12Name;
	}


	public void setS12Name(ServiceProvider s12Name) {
		this.s12Name = s12Name;
	}


	
	
}
