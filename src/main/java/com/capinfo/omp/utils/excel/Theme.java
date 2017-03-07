package com.capinfo.omp.utils.excel;

import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * excel主题，提供一组CellStyle
 * <p>Title:title</p>
 * <p>Description:</p>
 * <p>Copyright: Copyrigth (c) 2012</p> 
 * <p>Company: CAPINFO</p>
 * @author zhyj
 * @Date 2013-11-27 下午11:47:59
 * @Version v1.0
 */
public interface Theme {
	
	public static String CELL_STYLE_TABLE = "TABLE_CELL_STYLE";
	
	public static String CELL_STYLE_TITLE = "TITLE_CELL_STYLE";
	
	public static String CELL_STYLE_SUBTITLE = "SUBTITLE_CELL_STYLE";
	
	public static String CELL_STYLE_COL_HEADER = "COL_HEADER_CELL_STYLE";
	
	public static String CELL_STYLE_FOOTER = "FOOTER_CELL_STYLE";
	
	public static String CELL_STYLE_DATE = "DATE_CELL_STYLE";
	
	public static String CELL_STYLE_DOUBLE = "DOUBLE_CELL_STYLE";
	
	public static String CELL_STYLE_INT = "INT_CELL_STYLE";
	
	public static String CELL_STYLE_BOOL = "BOOL_CELL_STYLE";
	
	
	/**
	* @Title: getCellStyle
	* @Description: 取单元格样式
	* @param @param type
	* @param @return
	* @return CellStyle
	* @throws
	*/
	public CellStyle getCellStyle(String type);
	
//
//	public abstract CellStyle getTableStyle();
//
//	public abstract CellStyle getTitleStyle();
//
//	public abstract CellStyle getSubTitleStyle();
//
//	public abstract CellStyle getColHeaderStyle();
//
//	public abstract CellStyle getFooterStyle();
//
//	public abstract CellStyle getDateStyle();
//
//	public abstract CellStyle getNumberStyle();

}