package test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.ExcelUtils;

public class LoginByExcelData {
	WebDriver driver;

	@BeforeTest
	public void openChrome() {
		System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterTest
	public void closeChrome() {
		driver.close();
	}

	@DataProvider(name = "loginData1")
	public Object[][] loginTestData1() throws IOException {
		String filePath = "D:\\DataDriverFramework\\Login.xlsx";
		String sheetName = "sheet1";
		return ExcelUtils.readExcelDatas(filePath, sheetName);

	}
	
	//We can resue the ExcelUtils >> readExcelDatas() method.
	@DataProvider(name = "loginData2")
	public Object[][] loginTestData2() throws IOException {
		String filePath = "D:\\DataDriverFramework\\OrangeHRMLoginTestCase2.xlsx";
		String sheetName = "sheet1";
		return ExcelUtils.readExcelDatas(filePath, sheetName);

	}
   
	@Test(dataProvider = "loginData2")
	public void loginFunction(String username, String password) {
		System.out.println("Username: " + username + ", Password: " + password);

		WebElement uname = driver.findElement(By.xpath("//input[@name='username']"));
		WebElement pword = driver.findElement(By.xpath("//input[@name='password']"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

		uname.sendKeys(username);
		pword.sendKeys(password);
		loginButton.click();
	}

}
