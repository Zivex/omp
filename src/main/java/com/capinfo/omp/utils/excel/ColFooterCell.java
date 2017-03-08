package com.capinfo.omp.utils.excel;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * 表尾：主要统计合计等
 * @author dinglei
 *
 */
public class ColFooterCell {

	private String cellName;//列名

	private Map<String,Object> valueMap;//值
	
	public ColFooterCell(){
		valueMap = new HashMap<String,Object>();
	}
	
	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	
	public Map<String, Object> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, Object> valueMap) {
		this.valueMap = valueMap;
	}

	public void addCellValue(String columnName,Object objValue){
		valueMap.put(columnName, objValue);
	}
}
