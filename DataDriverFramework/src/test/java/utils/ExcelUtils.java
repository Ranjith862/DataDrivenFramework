package utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	// This is best practice:
	public static Object[][] readExcelDatas(String filePath, String sheetName) throws IOException {
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);

		int rowCount = sheet.getPhysicalNumberOfRows();
		int cellCount = sheet.getRow(0).getPhysicalNumberOfCells();

		Object[][] datas = new Object[rowCount - 1][cellCount];

		// This is the best way and best practice:
		// This way is used for dynamic excel sheet its sutable for all excel datas:
		for (int i = 1; i < rowCount; i++) { // Start from 1 to skip the header row
			Row row = sheet.getRow(i);
			for (int j = 0; j < cellCount; j++) { // Cell index starts from 0
				if (row != null) {
					Cell cell = row.getCell(j);
					if (cell != null) {
						datas[i - 1][j] = cell.getStringCellValue();
					} else {
						datas[i - 1][j] = ""; // Handle empty cell
					}
				} else {
					datas[i - 1][j] = ""; // Handle empty row
				}
			}
		}

		workbook.close();
		fis.close();
		return datas;

	}
}
