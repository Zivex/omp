package com.capinfo.omp.utils.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * Excel输出类
 * <p>Title:title</p>
 * <p>Description:</p>
 * <p>Copyright: Copyrigth (c) 2012</p> 
 * <p>Company: CAPINFO</p>
 * @author zhyj
 * @Date 2013-11-20 下午1:54:27
 * @Version v1.0
 */
public class ExcelBuilder {

	private Workbook wb;                                //工作簿 
    private int curRow = 0;                             //列表当前行
    private Schema curSchema=null;                      //当前Schema
    private Sheet  curSheet = null;                     //当前工作表
    private List<ExcelSheet> excelSheetList;            //excel文件，sheet数组
    private Theme theme;                                //主题

	public ExcelBuilder() {
		super();
		init();
	}
	
	public ExcelBuilder(List<ExcelSheet> excelSheetList,Theme theme) {
		super();
		init();
		this.excelSheetList=excelSheetList;
		this.theme = theme;
	}
	
	public List<ExcelSheet> getExcelSheetList() {
		return excelSheetList;
	}

	public void setExcelSheetList(List<ExcelSheet> excelSheetList) {
		this.excelSheetList = excelSheetList;
	}
	
	//只有一个sheet时用这个方便点
	public void setExcelSheet(ExcelSheet excelSheet) {
		this.excelSheetList = new ArrayList<ExcelSheet>();
		this.excelSheetList.add(excelSheet);
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	
	public Workbook getWorkbook() {
		return wb;
	}

	/**
	* @Title: writeToFile
	* @Description: 输出到文件系统
	* @param @param fileName
	* @param @throws IOException
	* @return void
	* @throws
	*/
	public void writeToFile(String fileName) throws IOException{
		createSheets();
		FileOutputStream fileOut = new FileOutputStream(fileName);
	    wb.write(fileOut);
	    fileOut.close();

	}

	public void writeToStream(OutputStream outputStream) throws IOException{
		createSheets();
		wb.write(outputStream);
	}
	
	private void init(){
		this.wb = new XSSFWorkbook();
		this.theme = new DefaultTheme(wb);
	}
	
	/**
	 * 创建sheet，有几个创建几个
	 * @author dinglei
	 */
	private void createSheets(){
		for (ExcelSheet excelSheet:excelSheetList) {
			curSchema = excelSheet.getSchema();
			createSheet(excelSheet.getSheetName());
			createTitle();
			createSubTitle();
			createColHeader();
			if(null!=excelSheet.getDataList()&&!excelSheet.getDataList().isEmpty()){
				try {
					createTable(excelSheet.getDataList());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.createColFooter();
		}
	}
	
	/**
	 * 创建sheet
	 * @author dinglei
	 */
	private void createSheet(String sheetTitle){
		curSheet = wb.createSheet(sheetTitle);
		this.curRow = 0;
	}
	
	private void createTitle(){
		if (curSchema.getTitle() != null ){
			Row row = createRowCell(this.theme.getCellStyle(Theme.CELL_STYLE_TABLE), 0);
			curSheet.addMergedRegion(new CellRangeAddress(0,0,0,curSchema.getColumnList().size()-1));
			row.getCell(0).setCellValue(curSchema.getTitle());
			this.curRow ++;
		}
	}

	
	private void createSubTitle(){
		if (curSchema.getSubTitle() != null ){
			Row row = createRowCell(this.theme.getCellStyle(Theme.CELL_STYLE_SUBTITLE), curRow);
			curSheet.addMergedRegion(new CellRangeAddress(0,0,0,curSchema.getColumnList().size()));
			row.getCell(0).setCellValue(curSchema.getSubTitle());
			this.curRow ++;
		}
	}
	
	private Row createRowCell(CellStyle cellStyle, int rowIndex){
		Row row = curSheet.createRow(rowIndex);
		for (int i = 0; i < curSchema.getColumnList().size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
		}
		return row;
	}
	
	private void createColHeader(){
		int headerBeginRow = this.curRow;
		//循环每行表头，创建单元格，赋值，合并行
		for (int i = 0; i < curSchema.getColHeaderRowList().size(); i++) {
			Row row = curSheet.createRow(this.curRow);
			List<ColHeaderCell> headerRow = curSchema.getColHeaderRowList().get(i);
			int cellIndex = 0;
			for (int j = 0; j < headerRow.size(); j++) {
				ColHeaderCell colCell = headerRow.get(j);
				//cellIndex = createHeaderCellAndMergeCols(colCell, row, j);
				//2013-12-2日dinglei修改，如有错误请通知
				cellIndex = createHeaderCellAndMergeCols(colCell, row, j==0?0:(cellIndex+1));
			}
			this.curRow++;
		}
		//纵向合并
		mergeColHeaderVCells(headerBeginRow);
	}	
		
	
	//创建列单元格、赋值并合并单元格
	private int createHeaderCellAndMergeCols(ColHeaderCell colHeaderCell, Row row, int colIndex){
		Cell cell = row.createCell(colIndex);
		cell.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_COL_HEADER));
		cell.setCellValue(colHeaderCell.getLabel());
		//判断是否需要做横向合并
		if (colHeaderCell.getColSpan() > 1){
			int lastColIndex = colIndex + colHeaderCell.getColSpan() -1;
			for (int i = colIndex + 1 ; i < lastColIndex + 1; i++) {
				Cell tmpCell = row.createCell(i);
				tmpCell.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_COL_HEADER));
			}
			curSheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), colIndex, lastColIndex));
		}
		return colIndex + colHeaderCell.getColSpan() -1;
	}
	
	//纵向合并表头单元格
	private void mergeColHeaderVCells(int beginRow){
		for (int i = 0; i < curSchema.getColHeaderRowList().size(); i++) {
			Row row = curSheet.getRow(i + beginRow);
			List<ColHeaderCell> headerRow = curSchema.getColHeaderRowList().get(i);
			int colIndex = 0;  //实际的id
			
			for (Iterator<ColHeaderCell> iterator = headerRow.iterator(); iterator.hasNext();) {
				ColHeaderCell colCell = iterator.next();
				if (colCell.getRowSpan() > 1){
					curSheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum() + colCell.getRowSpan() - 1, colIndex, colIndex));
				}
				colIndex = colIndex + colCell.getColSpan() - 1;
			}
		}			
	}
	
	private void createTable(List<? extends Object> list) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		int tableBeginRow = this.curRow;
		for (Iterator<? extends Object> iterator = list.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			createTableRow(object);
			this.curRow ++;
		}
		this.mergeColDataTableVCells(tableBeginRow);
	}
	private void createTableRow(Object obj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Row row = curSheet.createRow(this.curRow);
		if (curSchema.getDefaultRowHeight() > 0){ //没值就不赋值
			row.setHeight((short)curSchema.getDefaultRowHeight());
		}
		List<Column> columnList =  curSchema.getColumnList();
		for (int i = 0; i <  columnList.size(); i++) {
			Column column = columnList.get(i);
			Cell cell = row.createCell(i);
			//设置列宽,没值就不赋值
			if (column.getWidth() > 0){
				curSheet.setColumnWidth(i, column.getWidth());
			}
			if (column.getIsIndexColumn()){
				cell.setCellValue(i + 1);
				continue;
			}
			Class<?> type = null;
			Object value = null;
			try{//防止因嵌套的属性值为空时，抛出的异常。如"supplier.name",当supplier为空时，PropertyUtils会抛出异常-liuyuanzhi
			    value = PropertyUtils.getProperty(obj, column.getColumnName());
			    type = PropertyUtils.getPropertyType(obj, column.getColumnName());
			}catch (IllegalAccessException e){
			    value = "";
			}catch (NestedNullException nestedEx){
			    type = String.class;
			}catch (InvocationTargetException invocationEx){
			    value = "";
			    type = String.class;
			}
			if ("byte[]".equals(type.getSimpleName())){
				cell.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_TABLE));
				int pictureIdx  = wb.addPicture((byte[])value, Workbook.PICTURE_TYPE_PNG);
			    CreationHelper helper = wb.getCreationHelper();
			    Drawing drawing = curSheet.createDrawingPatriarch();
			    
			    //add a picture shape
			    ClientAnchor anchor = helper.createClientAnchor();
			    anchor.setCol1(cell.getColumnIndex());
			    anchor.setRow1(row.getRowNum());
			    anchor.setCol2(cell.getColumnIndex() + 1);
			    anchor.setRow2(row.getRowNum()+1);
			    anchor.setAnchorType(0);
			    Picture pict = drawing.createPicture(anchor, pictureIdx);
				
			} else if ("String".equals(type.getSimpleName())) {
			    cell.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_TABLE));
			    if(null != value){//增加非空校验，防止数据转换发生的异常-liuyuanzhi
			        cell.setCellValue(String.valueOf(value));
			    } else {
			        cell.setCellValue("");
			    }
			} else if ("Date".equals(type.getSimpleName())) {
			    cell.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_DATE));
                if(null != value){//增加非空校验，防止数据转换发生的异常-liuyuanzhi
                    cell.setCellValue((Date)value);
                } else {
                    cell.setCellValue("");
                }
			} else if (Double.TYPE.equals(type)||"Double".equals(type.getSimpleName())) {
			    cell.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_DOUBLE));
                if(null != value){//防止java.lang.NumberFormatException-liuyuanzhi
                    cell.setCellValue(Double.valueOf(String.valueOf(value)));
                } else {
                    cell.setCellValue("");
                }
			} else if (Integer.TYPE.equals(type)||"Integer".equals(type.getSimpleName())) {
			    cell.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_INT));
                if(null != value){//增加非空校验，防止数据转换发生的异常-liuyuanzhi
                		Integer val = (Integer)value;
                        if(val==1 || val==2205){
                            cell.setCellValue("是");
                        } else if((val==0 || val==2206)){
                            cell.setCellValue("否");
                        }else{
                        	cell.setCellValue(Integer.valueOf(String.valueOf(value)));
                        }
                    
                } else {
                    cell.setCellValue("");
                }
			} else if(Map.class.getName().equals(type)||"Map".equals(type.getSimpleName())){//添加对map数据的写出功能-liuyuanzhi
			    cell.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_DOUBLE));
			    if(null!=value){
			        cell.setCellValue(value.toString());
			    } else {
			        cell.setCellValue("");
			    }
			}else if(Boolean.TYPE.equals(type) || "Boolean".equals(type.getSimpleName())){ 
			    cell.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_DOUBLE));
			    Boolean val = (Boolean)value;
                if(null!=value && val){
                    cell.setCellValue("是");
                } else {
                    cell.setCellValue("否");
                }
                
			}
			else {//添加else，对未匹配到的数据，以普通的文本类型写出-liuyuanzhi
			    cell.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_DOUBLE));
			    if(null!=value){
                    cell.setCellValue(value.toString());
                } else {
                    cell.setCellValue("");
                }
			}
			
		}
	}

	/**
	 * 纵向合并数据表单元格
	 * 
	 * @param tableBeginRow
	 * @author dinglei
	 */
	private void mergeColDataTableVCells(int tableBeginRow){
		Map<Integer,Boolean> columnIsGroup = new HashMap<Integer,Boolean>();
		List<Column> columnList =  curSchema.getColumnList();
		for(int i=0;i<columnList.size();i++){
			Column column = columnList.get(i);
			columnIsGroup.put(i, column.getIsGroupColumn());
		}
		for(int i=0;i<curSheet.getRow(0).getPhysicalNumberOfCells();i++){
			int beginRow = 0;
			int rowIndex = 0;
			Object previousValue = null;
			for(int j=tableBeginRow;j<curSheet.getLastRowNum()+1;j++){
				Row row = curSheet.getRow(j);
				Cell cell = row.getCell(i);
				if(columnIsGroup.get(i)){
					Object cellValue = null;
					//日期类型
					if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC&&HSSFDateUtil.isCellDateFormatted(cell)){
						cellValue = cell.getDateCellValue();
					}
					//字符串类型
					if(cell.getCellType()==Cell.CELL_TYPE_STRING){
						cellValue = cell.getStringCellValue();
					}
					//boolean值
					if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN){
						cellValue = cell.getBooleanCellValue();
					}
					//数值型
					if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
						cellValue = cell.getNumericCellValue();
					}
					//System.out.println("row="+j+",col="+i+",value="+cellValue);
					if(j==tableBeginRow){
						beginRow=j;
						previousValue = cellValue;
					}
					else{
						if(cellValue.equals(previousValue)){
							rowIndex++;
							previousValue = cellValue;
						}
						else{
							if(rowIndex>0){
								curSheet.addMergedRegion(new CellRangeAddress(beginRow, beginRow + rowIndex, i, i));
							}
							rowIndex=0;
							beginRow=j;
							previousValue = cellValue;
						}
					}
				}
			}
			//只有一行时，不能合并。否则打开是时有错
			if (rowIndex > 0){
				curSheet.addMergedRegion(new CellRangeAddress(beginRow, beginRow + rowIndex, i, i));
			}
		}
	}
	
	/**
	 * 创建表尾
	 * @author dinglei
	 */
	private void createColFooter(){
		//创建单行表尾
		for (int i = 0; i < curSchema.getColFooterCellList().size(); i++) {
			Integer startColIndex = 0;
			Integer flag = null;
			Row row = curSheet.createRow(this.curRow);
			ColFooterCell footerRow = curSchema.getColFooterCellList().get(i);
			Map<String,Object> map = footerRow.getValueMap();
			if(map!=null&&!map.isEmpty()){
				for(int j=0;j<curSchema.getColumnList().size();j++){
					Column column = curSchema.getColumnList().get(j);
					Object value = map.get(column.getColumnName());
					if(j==0){
						Cell cellName = row.createCell(j);
						cellName.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_FOOTER));
						cellName.setCellValue(footerRow.getCellName());
					}
					else{
						Cell cellValue = row.createCell(j);
						cellValue.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_FOOTER));
						cellValue.setCellValue(String.valueOf(value));
					}
					if(value!=null){
						flag = j;
						curSheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), startColIndex, j-1));
					}
					if(null!=flag&&j==flag+1&&value==null){
						startColIndex=j;
					}
				}
			}
			else{
				for(int j=0;j<curSchema.getColumnList().size();j++){
					Cell cell = row.createCell(j);
					cell.setCellStyle(theme.getCellStyle(Theme.CELL_STYLE_FOOTER));
					if(j==0){
						cell.setCellValue(footerRow.getCellName());
					}
				}
				curSheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), startColIndex, curSchema.getColumnList().size()-1));
			}
		}
	}	
}
