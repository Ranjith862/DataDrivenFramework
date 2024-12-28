package byExcel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class WriteDatasInExcel {

	@Test
	public void writeDatasOnExcel() throws IOException {

		String filePath = "D:\\DataDriverFramework\\OrangeHRMLoginTestCase2.xlsx";

		// Open the file for reading
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		// Create a new sheet (or open if exists)
		XSSFSheet sheet = workbook.createSheet("Sheet");

		// Data to be written into Excel
		Object[][] data = { { "Admin", "wrong" }, { "wrong", "admin123" }, { "wrong", "wrong" },
				{ "Admin", "admin123" } };

		// Loop through the data array and write to Excel
		for (int i = 0; i < data.length; i++) {
			Row row = sheet.createRow(i); // Create a new row for each test data set

			for (int j = 0; j < data[i].length; j++) { // Iterate through columns
				Cell cell = row.createCell(j); // Create a new cell for each data value
				cell.setCellValue(data[i][j].toString()); // Set the cell value
			}
		}

		// Write the changes to the Excel file
		FileOutputStream fos = new FileOutputStream(filePath);
		workbook.write(fos);

		// Close all resources
		fos.close();
		workbook.close();
		fis.close();

		System.out.println("Data written to Excel file successfully!");
	}
}
