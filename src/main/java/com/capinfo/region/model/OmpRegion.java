package com.capinfo.region.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.capinfo.framework.model.BaseEntity;

@Entity
@Table(name = "OMP_REGION")
//@SequenceGenerator(name = "OmpRegion", sequenceName = "SEQ_OMP_REGION", allocationSize = 1)
public class OmpRegion implements BaseEntity {

	private static final long serialVersionUID = 1L;


	private Long id;


	private String name;


    private Long levelid;


    private String description;


    private String createdate;


    private Long creatorid;


    private Long parentid;


    private String num;


    private String shortname;


    private String disporder;


    private String standardNo;


    private Short useFlag;


    private String oldDictionaryId;


    private Long convertId;


    private Long peopleNum;


	public static void sortList(List list, List<OmpRegion> sourcelist,
			Long convertId) {
		for (int i = 0; i < sourcelist.size(); i++) {
			OmpRegion e = sourcelist.get(i);
			if (e.getParentid()==convertId) {
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j = 0; j < sourcelist.size(); j++) {
					OmpRegion child = sourcelist.get(j);
					if (child.getParentid()==e.getConvertId()) {
						sortList(list, sourcelist, e.getConvertId());
						break;
					}
				}
			}
		}
	}
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OmpRegion")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "LEVELID")
	public Long getLevelid() {
		return levelid;
	}
	public void setLevelid(Long levelid) {
		this.levelid = levelid;
	}
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "CREATEDATE")
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	@Column(name = "CREATORID")
	public Long getCreatorid() {
		return creatorid;
	}
	public void setCreatorid(Long creatorid) {
		this.creatorid = creatorid;
	}
	@Column(name = "PARENTID")
	public Long getParentid() {
		return parentid;
	}
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	@Column(name = "NUM")
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@Column(name = "SHORTNAME")
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	@Column(name = "DISPORDER")
	public String getDisporder() {
		return disporder;
	}

	public void setDisporder(String disporder) {
		this.disporder = disporder;
	}
	@Column(name = "STANDARD_NO")
	public String getStandardNo() {
		return standardNo;
	}

	public void setStandardNo(String standardNo) {
		this.standardNo = standardNo;
	}
	@Column(name = "USE_FLAG")
	public Short getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(Short useFlag) {
		this.useFlag = useFlag;
	}
	@Column(name = "OLD_DICTIONARY_ID")
	public String getOldDictionaryId() {
		return oldDictionaryId;
	}
	public void setOldDictionaryId(String oldDictionaryId) {
		this.oldDictionaryId = oldDictionaryId;
	}
	@Column(name = "CONVERT_ID")
	public Long getConvertId() {
		return convertId;
	}
	public void setConvertId(Long convertId) {
		this.convertId = convertId;
	}
	@Column(name = "PEOPLE_NUM")
	public Long getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(Long peopleNum) {
		this.peopleNum = peopleNum;
	}










}