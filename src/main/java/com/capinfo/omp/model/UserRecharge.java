package com.capinfo.omp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.capinfo.framework.model.BaseEntity;
/**
 * 
 * 持有资金
 * @author Rivex
 *
 */
@Entity
@Table(name = "user_recharge")
public class UserRecharge implements BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long rid;
	private String rname;
	private Long money;
	
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
	@Column(name = "rname")
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	@Column(name = "money")
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
	}
	
	
	
}
