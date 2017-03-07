package com.capinfo.omp.utils.excel;

import java.util.List;


/**
 * 
 * 导出EXCEL的sheet
 * 在你的EXCEL文件中，有几个SHEET，就应该有几个这样的对象
 * @author dinglei
 *
 */
public class ExcelSheet {

	private String sheetName;//sheet的名称
	private List<? extends Object> dataList;//数据集
	private Schema schema;//表格结构
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public List<? extends Object> getDataList() {
		return dataList;
	}
	public void setDataList(List<? extends Object> dataList) {
		this.dataList = dataList;
	}
	public Schema getSchema() {
		return schema;
	}
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
}
