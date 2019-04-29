package utils;

/**
 *
 */

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author thinkpad
 */
public class ReadExcelUtil {

	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

	public static final String EMPTY = "";
	public static final String POINT = ".";

	/**
	 * read the Excel file
	 *
	 * @param path the path of the Excel file
	 * @return
	 * @throws IOException
	 */
	public static List<Row> readExcel(String path) throws IOException {
		if (path == null || EMPTY.equals(path)) {
			return null;
		} else {
			String postfix = getPostfix(path);
			if (!EMPTY.equals(postfix)) {
				if (OFFICE_EXCEL_2003_POSTFIX.equalsIgnoreCase(postfix)) {
					return readXls(path);
				} else if (OFFICE_EXCEL_2010_POSTFIX.equalsIgnoreCase(postfix)) {
					return readXlsx(path);
				}
			}
		}
		return null;
	}

	/**
	 * Read the Excel 2010
	 *
	 * @param path the path of the excel file
	 * @return
	 * @throws IOException
	 */
	public static List<Row> readXlsx(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<Row> list = new ArrayList<Row>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					list.add(xssfRow);
				}
			}
		}

		return list;
	}

	/**
	 * Read the Excel 2003-2007
	 *
	 * @param path the path of the Excel
	 * @return
	 * @throws IOException
	 */
	public static List<Row> readXls(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<Row> list = new ArrayList<Row>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					list.add(hssfRow);
				}
			}
		}
		return list;
	}

	@SuppressWarnings("static-access")
	private String getValue(XSSFCell xssfRow) {
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

	@SuppressWarnings("static-access")
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	public static String getPostfix(String path) {
		if (path == null || EMPTY.equals(path.trim())) {
			return EMPTY;
		}
		if (path.contains(POINT)) {
			return path.substring(path.lastIndexOf(POINT) + 1, path.length());
		}
		return EMPTY;
	}

	public static void main(String[] args) throws IOException {
		String path = "D:\\document\\项目文档\\比邻公从号1.2\\岭南最新价格（新格式）.xlsx";
		List<Row> listRow = readExcel(path);

		int colNum = 12;
		for (Row row : listRow) {
			for (int i = 0; i < colNum; i++) {
				System.out.print(row.getCell(i));
			}
			System.out.println();
		}
	}

	public static String getCellStringValue(Cell cell) {
		String value = null;
		if (cell == null) {
			return value;
		}
		if (cell.getCellType() == 0) {
			Double d = new Double(cell.getNumericCellValue());
			value = Integer.toString(d.intValue());
		} else {
			value = cell.toString();
		}
		return value;
	}

	public String getValue(Cell cell) {
		if (cell == null) {
			return "";
		} else if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
			String value = "";
			//检验是否为日期格式的数值类型
			if (DateUtil.isCellDateFormatted(cell)) {
				value = DateUtils.formatTimestamp("yyyy-MM-dd HH:mm:ss", cell.getDateCellValue().getTime());
			} else {
				value = new DecimalFormat("0").format(cell.getNumericCellValue());
			}
			return value;
			//String.valueOf(cell.getNumericCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}

	}

}