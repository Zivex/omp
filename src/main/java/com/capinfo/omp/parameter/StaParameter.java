package com.capinfo.omp.parameter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 统计参数
 */
public class StaParameter  {
	
	private String city;
	private String county;
	private String street;
	private String community;
	private String otype;	//话机类型
	private String stime;	
	private String etime;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getOtype() {
		return otype;
	}
	public void setOtype(String otype) {
		this.otype = otype;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime)  {
			this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
			this.etime = etime;
	}
	
	
	

}
