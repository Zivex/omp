/**
 * 
 */
package com.capinfo.omp.utils.excel;

/**
 * excel 列定义
 * <p>Title:title</p>
 * <p>Description:</p>
 * <p>Copyright: Copyrigth (c) 2012</p> 
 * <p>Company: CAPINFO</p>
 * @author zhyj
 * @Date 2013-11-24 上午1:37:23
 * @Version v1.0
 */
public class Column {
	
	private String dataType;         //数据类型    
	
	private Boolean isAutoSize = false;      //是否自动列宽
	
	private int width;               //列宽  the width in units of 1/256th of a character width
	
	private String columnName;       //列名
	
	private Boolean isIndexColumn = false;   //是否序号列,如果是，自动填充序号
	
	private Boolean isGroupColumn = false;   //是否"分组列" 如果是true 会对这列的单元格合并 

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Boolean getIsAutoSize() {
		return isAutoSize;
	}

	public void setIsAutoSize(Boolean isAutoSize) {
		this.isAutoSize = isAutoSize;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Boolean getIsIndexColumn() {
		return isIndexColumn;
	}

	public void setIsIndexColumn(Boolean isIndexColumn) {
		this.isIndexColumn = isIndexColumn;
	}

	public Boolean getIsGroupColumn() {
		return isGroupColumn;
	}

	public void setIsGroupColumn(Boolean isGroupColumn) {
		this.isGroupColumn = isGroupColumn;
	}
}
