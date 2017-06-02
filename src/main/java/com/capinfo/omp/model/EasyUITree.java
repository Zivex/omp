package com.capinfo.omp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class EasyUITree implements Serializable {

	private String id;
	private String text;
	private String pid;
	private Boolean checked = false;
	private String state = "open";
	private Long attributes = 0L;
	
	

	private List<EasyUITree> children;

	

	public Long getAttributes() {
		return attributes;
	}

	public void setAttributes(Long attributes) {
		this.attributes = attributes;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	
	public void setState(String state) {
		this.state = state;
	}
	
	public List<EasyUITree> getChildren() {
		return children;
	}

	public void setChildren(List<EasyUITree> children) {
		this.children = children;
	}


}
