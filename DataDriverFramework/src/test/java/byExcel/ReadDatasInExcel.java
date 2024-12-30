package byExcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReadDatasInExcel {
	WebDriver driver;

	@BeforeTest
	public void openChrome() {
		System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().window().maximize();

	}

	@AfterTest
	public void closeChrome() {
		if (driver != null) {
			driver.quit();
		}
	}

	@DataProvider(name = "LoginDatas")
	public Object[][] readDatasInExcel() throws IOException {
		String filePath = "D:\\DataDriverFramework\\Login.xlsx";
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Sheet1");

		int rowCount = sheet.getPhysicalNumberOfRows();
		int cellCount = sheet.getRow(0).getPhysicalNumberOfCells();

		// Create a 2D array to hold the data
		Object[][] datas = new Object[rowCount - 1][cellCount];

		// Loop through rows and columns to populate the data array
		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			datas[i - 1][0] = row.getCell(0).getStringCellValue(); // Username
			datas[i - 1][1] = row.getCell(1).getStringCellValue(); // Password
		}
		workbook.close();
		fis.close();
		return datas;
	}

	@Test(dataProvider = "LoginDatas")
	public void loginFunction(String username, String password) {
		System.out.println("Username: " + username + ", Password: " + password);
		driver.manage().timeouts().implicitlyWait(9, TimeUnit.SECONDS);

		WebElement uname = driver.findElement(By.xpath("//input[@name='username']"));
		WebElement pword = driver.findElement(By.xpath("//input[@name='password']"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

		uname.sendKeys(username);
		pword.sendKeys(password);
		loginButton.click();
	}
}
