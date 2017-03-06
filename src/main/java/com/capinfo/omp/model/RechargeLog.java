package com.capinfo.omp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.capinfo.framework.model.BaseEntity;
/**
 * 充值记录
 * @author Rivex
 *
 */
@Entity
@Table(name = "recharge_log")
public class RechargeLog implements BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long rid;
	private Long money;
	private String user_name;
	private Date time;
	private int success_stat;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "rid")
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	@Column(name = "money")
	public Long getMoney() {
		return money;
	}
	
	public void setMoney(Long money) {
		this.money = money;
	}
	@Column(name = "user_name")
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time")
	public Date getTime() {
		
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Column(name = "success_stat")
	public int getSuccess_stat() {
		return success_stat;
	}
	public void setSuccess_stat(int success_stat) {
		this.success_stat = success_stat;
	}
	
	
	
	
	
	

}
