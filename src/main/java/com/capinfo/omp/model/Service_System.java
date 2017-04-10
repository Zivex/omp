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
	private Long uid;
	private int telltype;
	//服务商所对应的键位
	private int s1;
	private int s2;
	private int s3;
	private int s4;
	private int s5;
	private int s6;
	private int s7;
	private int s8;
	private int s9;
	private int s10;
	private int s11;
	private int s12;
	private int s13;
	private int s14;
	private int s15;
	private int s16;
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
	public int getTelltype() {
		return telltype;
	}


	public void setTelltype(int telltype) {
		this.telltype = telltype;
	}

	@Column(name = "m1")
	public int getS1() {
		return s1;
	}


	public void setS1(int s1) {
		this.s1 = s1;
	}

	@Column(name = "m2")
	public int getS2() {
		return s2;
	}


	public void setS2(int s2) {
		this.s2 = s2;
	}

	@Column(name = "m3")
	public int getS3() {
		return s3;
	}


	public void setS3(int s3) {
		this.s3 = s3;
	}

	@Column(name = "m4")
	public int getS4() {
		return s4;
	}


	public void setS4(int s4) {
		this.s4 = s4;
	}

	@Column(name = "m5")
	public int getS5() {
		return s5;
	}


	public void setS5(int s5) {
		this.s5 = s5;
	}

	@Column(name = "m6")
	public int getS6() {
		return s6;
	}


	public void setS6(int s6) {
		this.s6 = s6;
	}

	@Column(name = "m7")
	public int getS7() {
		return s7;
	}


	public void setS7(int s7) {
		this.s7 = s7;
	}

	@Column(name = "m8")
	public int getS8() {
		return s8;
	}


	public void setS8(int s8) {
		this.s8 = s8;
	}

	@Column(name = "m9")
	public int getS9() {
		return s9;
	}


	public void setS9(int s9) {
		this.s9 = s9;
	}

	@Column(name = "m10")
	public int getS10() {
		return s10;
	}


	public void setS10(int s10) {
		this.s10 = s10;
	}

	@Column(name = "m11")
	public int getS11() {
		return s11;
	}


	public void setS11(int s11) {
		this.s11 = s11;
	}

	@Column(name = "m12")
	public int getS12() {
		return s12;
	}


	public void setS12(int s12) {
		this.s12 = s12;
	}

	
	
	
	
	@Column(name = "m13")
	public int getS13() {
		return s13;
	}


	public void setS13(int s13) {
		this.s13 = s13;
	}

	@Column(name = "m14")
	public int getS14() {
		return s14;
	}


	public void setS14(int s14) {
		this.s14 = s14;
	}

	@Column(name = "m15")
	public int getS15() {
		return s15;
	}


	public void setS15(int s15) {
		this.s15 = s15;
	}

	@Column(name = "m16")
	public int getS16() {
		return s16;
	}


	public void setS16(int s16) {
		this.s16 = s16;
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
}
