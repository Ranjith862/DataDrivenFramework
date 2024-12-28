package dataProvider;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ByDataProvider {
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

	Object[][] datas1 = { { "Admin", "wrongpword" }, { "wronguname", "admin123" }, { "wronguname", "wrongpword" },
			{ "Admin", "admin123" } };

	@DataProvider(name = "LoginTestCase")
	public Object[][] ProvideDatas() {
		return datas1;
	}

	@DataProvider(name = "LoginTestCase2") //We can return Object[][] and assign values directly also.
	public Object[][] ProvideDatas2() {
		return new Object[][] { { "Admin", "wrongpword" }, { "wronguname", "admin123" }, { "wronguname", "wrongpword" },
				{ "Admin", "admin123" } };
	}

	@Test(dataProvider = "LoginTestCase")
	public void getDataFromDataProvider(String username, String password) {
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
		// Correct username and Correct password:
		WebElement uname = driver.findElement(By.xpath("//input[@name='username']"));
		WebElement pword = driver.findElement(By.xpath("//input[@name='password']"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

		uname.sendKeys(username);
		pword.sendKeys(password);
		loginButton.click();

		try {
			WebElement dashBoard = driver.findElement(By.xpath("//h6[text()='Dashboard']"));
			Assert.assertTrue(dashBoard.isDisplayed(), "Login successfull - Dashboard visible");

		} catch (Exception e) {
			WebElement errorMessage = driver.findElement(By.xpath("//p[text()='Invalid credentials']"));
			Assert.assertTrue(errorMessage.isDisplayed(), "Login Failed - Error message displayed");
		}
	}
}
