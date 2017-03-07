package com.capinfo.omp.utils.excel;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * <p>Title:title</p>
 * <p>Description:</p>
 * <p>Copyright: Copyrigth (c) 2012</p> 
 * <p>Company: CAPINFO</p>
 * @author zhyj
 * @Date 2013-11-26 下午11:41:18
 * @Version v1.0
 */
public class DefaultTheme implements Theme {

	
    private Workbook wb;                                //工作簿 
    
    private Map<String, CellStyle> cellStyleMap = new HashMap<String, CellStyle>();
//
//	private CellStyle tableStyle;
//	
//	private CellStyle titleStyle;
//	
//	private CellStyle subTitleStyle;
//	
//	private CellStyle colHeaderStyle;
//	
//	private CellStyle footerStyle;
//	
//	private CellStyle dateStyle;
//	
//	private CellStyle numberStyle;

	public DefaultTheme(Workbook wb) {
		super();
		this.wb = wb;
		this.createAll();
	}
	
	private void createAll(){
		CellStyle tableStyle = wb.createCellStyle();
	    CreationHelper createHelper = wb.getCreationHelper();
		tableStyle.setBorderBottom(CellStyle.BORDER_THIN);
		tableStyle.setBorderLeft(CellStyle.BORDER_THIN);
		tableStyle.setBorderRight(CellStyle.BORDER_THIN);
		tableStyle.setBorderTop(CellStyle.BORDER_THIN);
		tableStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		tableStyle.setAlignment(CellStyle.ALIGN_CENTER);
		Font tableFont = wb.createFont();
		tableFont.setFontName("宋体");
		tableFont.setFontHeightInPoints((short) 14);
		tableStyle.setFont(tableFont);
		cellStyleMap.put(Theme.CELL_STYLE_TABLE, tableStyle);
		
		
		CellStyle titleStyle = wb.createCellStyle();
		titleStyle.cloneStyleFrom(tableStyle);
		cellStyleMap.put(Theme.CELL_STYLE_TITLE, titleStyle);
		
		CellStyle subTitleStyle = wb.createCellStyle();
		subTitleStyle.cloneStyleFrom(tableStyle);
		cellStyleMap.put(Theme.CELL_STYLE_SUBTITLE, subTitleStyle);
		
		CellStyle colHeaderStyle = wb.createCellStyle();
		colHeaderStyle.cloneStyleFrom(tableStyle);
		cellStyleMap.put(Theme.CELL_STYLE_COL_HEADER, colHeaderStyle);
		
		CellStyle footerStyle = wb.createCellStyle();
		footerStyle.cloneStyleFrom(tableStyle);
		cellStyleMap.put(Theme.CELL_STYLE_FOOTER, footerStyle);
		
		CellStyle dateStyle = wb.createCellStyle();
		dateStyle.cloneStyleFrom(tableStyle);		
		dateStyle.setDataFormat(
		        createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
		cellStyleMap.put(Theme.CELL_STYLE_DATE, dateStyle);
		
//		CellStyle intStyle = wb.createCellStyle();
//		intStyle.cloneStyleFrom(tableStyle);
//	    DataFormat format = wb.createDataFormat();
//		intStyle.setDataFormat(format.getFormat("#,##0"));
		
		CellStyle longStyle = wb.createCellStyle();
		longStyle.cloneStyleFrom(tableStyle);
		DataFormat longFormat = wb.createDataFormat();
		longStyle.setDataFormat(longFormat.getFormat("#,##0.00"));
		cellStyleMap.put(Theme.CELL_STYLE_DOUBLE, longStyle);
		
//		CellStyle boolStyle = wb.createCellStyle();
//		intStyle.cloneStyleFrom(tableStyle);
//		DataFormat format = wb.createDataFormat();
//		intStyle.setDataFormat(format.getFormat("#,##0.00"));
//		
		
	}

	public CellStyle getCellStyle(String type) {
		return this.cellStyleMap.get(type);
	}

	
	
}
