package com.capinfo.omp.utils.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>Title:title</p>
 * <p>Description:excel 结构定义</p>
 * <p>Copyright: Copyrigth (c) 2012</p> 
 * <p>Company: CAPINFO</p>
 * @author zhyj
 * @Date 2013-11-28 上午9:47:45
 * @Version v1.0
 */
public class Schema {

	private String title;                               //标题

	private String subTitle;                            //副标题

	private List<List<ColHeaderCell>> colHeaderRowList;      //列标题列表，可以有多表头，最外层List是行,里层是单元格列表

	private List<Column> columnList;                   //列属性
	
	private int defaultRowHeight;                       //行高
	
	private List<ColFooterCell> colFooterCellList;                //表尾

	public Schema(){
		colHeaderRowList = new ArrayList<List<ColHeaderCell>>();
		colFooterCellList = new ArrayList<ColFooterCell>();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public List<List<ColHeaderCell>> getColHeaderRowList() {
		return colHeaderRowList;
	}

	public void setColHeaderRowList(List<List<ColHeaderCell>> colHeaderRowList) {
		this.colHeaderRowList = colHeaderRowList;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	public int getDefaultRowHeight() {
		return defaultRowHeight;
	}

	public void setDefaultRowHeight(int defaultRowHeight) {
		this.defaultRowHeight = defaultRowHeight;
	}

	public List<ColFooterCell> getColFooterCellList() {
		return colFooterCellList;
	}

	public void setColFooterCellList(List<ColFooterCell> colFooterCellList) {
		this.colFooterCellList = colFooterCellList;
	}

	/**
	 * 添加表头，可一个，也可多个
	 */
	public void addColHeaderCell(List<ColHeaderCell> colHeaderCellList){
		colHeaderRowList.add(colHeaderCellList);
	}
	
	/**
	 * 添加表尾，可一个，也可多个
	 */
	public void addColFooterCell(ColFooterCell colFooterCell){
		colFooterCellList.add(colFooterCell);
	}
}
