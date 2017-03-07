package com.capinfo.omp.utils.excel;

/**
 * 列标标题单元格
 * <p>Title:title</p>
 * <p>Description:</p>
 * <p>Copyright: Copyrigth (c) 2012</p> 
 * <p>Company: CAPINFO</p>
 * @author zhyj
 * @Date 2013-11-26 下午7:11:16
 * @Version v1.0
 */
public class ColHeaderCell {
	
	private int colSpan = 1;                 //跨几列
	
	private int rowSpan = 1;                 //跨几行
	
	private String label;                    //内容

	public int getColSpan() {
		return colSpan;
	}

	public void setColSpan(int colSpan) {
		if (colSpan > 0){
			this.colSpan = colSpan;
		}
	}

	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		if(rowSpan > 0){
			this.rowSpan = rowSpan;
		}
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	

}
