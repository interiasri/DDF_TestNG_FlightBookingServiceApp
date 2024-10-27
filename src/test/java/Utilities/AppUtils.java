package Utilities;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class AppUtils {
	
	public static WebDriver driver;
	public static Properties p;
	
	
	@BeforeSuite
	public void launchApp() throws Throwable {
		p = new Properties();
		p.load(new FileInputStream("ObjectRepository/OR.properties"));
		if(p.getProperty("browser").equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		}
		else if(p.getProperty("browser").equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		else {
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("url"));
	}
	
	@Parameters({"UserName", "Password"})
	@BeforeTest
	public void Login(String UserName, String Password) {
		driver.findElement(By.xpath(p.getProperty("objUserName"))).sendKeys(UserName);
		driver.findElement(By.xpath(p.getProperty("objPassword"))).sendKeys(Password);
		driver.findElement(By.xpath(p.getProperty("objSignIn"))).click();
	}
	
	@AfterTest
	public void Logout() {
		driver.findElement(By.xpath(p.getProperty("objUserSettings"))).click();
		driver.findElement(By.xpath(p.getProperty("objUserLogout"))).click();
	}
	
	@AfterSuite
	public void closeApp() {
		driver.quit();
	}

}
