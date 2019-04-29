package utils;

//import constants.IsOnlineType;

import exceptions.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import java.math.BigDecimal;

public class ExcelUtil {

	public static HSSFWorkbook createWorkbook () {
		HSSFWorkbook wb = new HSSFWorkbook();
		return wb;
	}
	
	public static HSSFSheet createSheet (String sheetName) {
		HSSFWorkbook wb = new HSSFWorkbook();
		return createSheet(wb, sheetName);
	}
	
	public static HSSFSheet createSheet (HSSFWorkbook wb, String sheetName) {
		HSSFSheet sheet = wb.createSheet(sheetName);
		return sheet;
	}
	
	public static HSSFRow createRow (HSSFSheet sheet, int index) {
		return sheet.createRow(index);
	}
	
	public static HSSFCell createCell (HSSFRow row, int index) {
		return row.createCell(index);
	}
	
	public static void createCell (HSSFRow row, int index, String value) {
		row.createCell(index).setCellValue(value);
	}
	
	public static void createCell (HSSFRow row, int index, Date value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		row.createCell(index).setCellValue(sdf.format(value));
	}
	
	public static void createBody (HSSFSheet sheet, int rowIndex, String[] titles, String[] keys, List<Map<String, Object>> values ) {
		HSSFRow row = null;
		HSSFCell cell = null;
		
		if (null != titles && titles.length > 0) {
			row = createRow(sheet, rowIndex++);
			for (int i = 0; i < titles.length; i++) {
				row.createCell(i).setCellValue(titles[i]);
			}
		}
		
		if (null != values && values.size() > 0) {
			String key = null;
			for (Map<String, Object> obj : values) {
				row = createRow(sheet, rowIndex++);
				for (int i = 0; i < keys.length; i++) {
					key = keys[i];
					row.createCell(i).setCellValue(dataConverter(key, obj.get(key)));
				}
			}
		}
	}
	
	private static String dataConverter(String key, Object value) {
		if (null == value) {
			return "";
		}
		
		
		if (key.equals("create_time")) {
			return DateUtils.formatTimestamp("yyyyMMdd HH:mm:ss", Long.parseLong(value.toString()));
		} else if (key.equals("finish_time")){
			return DateUtils.formatTimestamp("yyyy-MM-dd HH:mm:ss", Long.parseLong(value.toString()));
		} else if (key.equals("sign_time")){
			return DateUtils.formatTimestamp("yyyy-MM-dd HH:mm", Long.parseLong(value.toString()));
//		} else if (key.equals("is_online")){
//			return IsOnlineType.parse(new BigDecimal(value.toString()).intValue()).getName();
//		}else if (key.equals("is_add")){
//			int v = new BigDecimal(value.toString()).intValue();
//			if (v == 1) {
//				return "加号";
//			}else {
//				return "线上";
//			}
		}
		
		return value.toString();
	}

	public static XSSFWorkbook createXls(List<List<String>> table) throws BusinessException {

		// xls输出
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("sheet1");

		for (int i = 0;i<table.size();i++) {
			XSSFRow row = sheet.createRow(i);
			List<String> rowData = table.get(i);
			for (int j = 0;j<rowData.size();j++) {
				row.createCell(j).setCellValue(rowData.get(j));
			}
		}

		return wb;

	}
	
}
