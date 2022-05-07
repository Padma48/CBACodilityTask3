package superherosregression;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import common.Utilities;
import excelUtilities.ExcelUtilities;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;


import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebRegression {
	//public String s = RandomStringUtils.randomAlphabetic(6);
	String Pw="Password@1";
	WebDriver driver;
	@BeforeTest
	@Parameters("browser")
	public void beforeTest(String Browser) {
		if (Browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.get("https://responsivefight.herokuapp.com/");
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		} else if (Browser.equalsIgnoreCase("firefox")) {
			// System.setProperty("webdriver.gecko.driver",
			// "C:\Users\BACS\Downloads\geckodriver-v0.31.0-win64");
			// System.setProperty("webdriver.gecko.driver",
			// "D:\\Automation\\CBACodilityTask3API\\Driver\\geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.get("https://responsivefight.herokuapp.com/");
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		}
	}
	@Test
	public void Verify_dashboard_with_registration() {

		String title = driver.getTitle();
		System.out.print("User is on " + title);
		driver.findElement(By.id("rego")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String s = RandomStringUtils.randomAlphabetic(6);
		System.out.println("New username: " + s);
		WebElement element=driver.findElement(By.name("uname"));
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		//String s1 = RandomStringUtils.randomAlphabetic(6);
		driver.findElement(By.name("uname")).clear();
		driver.findElement(By.name("uname")).sendKeys(s);
		driver.findElement(By.name("psw")).clear();
		driver.findElement(By.name("psw")).sendKeys(Pw);
		driver.findElement(By.name("psw-repeat")).clear();
		driver.findElement(By.name("psw-repeat")).sendKeys(Pw);
		//JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", driver.findElement(By.id("signupbtn")));
		//driver.findElement(By.id("signupbtn")).click();
		System.out.println("Registration successful");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String title1 = driver.getTitle();
		System.out.print("User is on " + title1);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		Utilities.Login(driver, s, Pw );
		Utilities.verify_bus_challange(driver);
		Utilities.verify_user_score(driver);
	}

	@DataProvider
	public Object[][] login() throws IOException {
		String path = System.getProperty("user.dir") + "/src/test/java/webData/webData.xlsx";
		  String loginsheetname = "login";
		  int rownum = ExcelUtilities.getRowCount(path, loginsheetname);
		  int colnum = ExcelUtilities.getCellCount(path, loginsheetname, 1);
		  String logindata[][] = new String[rownum][colnum];
		  for (int i = 1; i <= rownum; i++) 
		  {
		  for (int j = 0; j < colnum; j++) 
		  {
			  logindata[i - 1][j] = ExcelUtilities.getCellData(path, loginsheetname, i, j);
		  }
		  }
		  return logindata;
	}
	
	@DataProvider
	public Object[][] invalidlogin() throws IOException {
		String path = System.getProperty("user.dir") + "/src/test/java/webData/webData.xlsx";
		  String loginsheetname = "invalidlogin";
		  int rownum = ExcelUtilities.getRowCount(path, loginsheetname);
		  int colnum = ExcelUtilities.getCellCount(path, loginsheetname, 1);
		  String logindata[][] = new String[rownum][colnum];
		  for (int i = 1; i <= rownum; i++) 
		  {
		  for (int j = 0; j < colnum; j++) 
		  {
			  logindata[i - 1][j] = ExcelUtilities.getCellData(path, loginsheetname, i, j);
		  }
		  }
		  return logindata;
	}
	

	@Test(dataProvider = "login")
	public void Verity_dashboard_with_login(String username, String password) {
		//driver.get("https://responsivefight.herokuapp.com/");
		driver.get("https://responsivefight.herokuapp.com/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	      driver.findElement(By.linkText("Login")).click();
	      Utilities.Login(driver, username, password );
	      Utilities.verify_bus_challange(driver);
	      Utilities.verify_user_score(driver);
	      
	}
	
	@Test(dataProvider = "invalidlogin")
	public void Verity_errormessage_invalilogin(String username, String password) {
		//driver.get("https://responsivefight.herokuapp.com/");
		driver.get("https://responsivefight.herokuapp.com/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	      driver.findElement(By.linkText("Login")).click();
	      Utilities.Login(driver, username, password );
	      String errmsg= driver.findElement(By.id("login_popup")).getText();
		  System.out.println("Error message displayed for invalid login with "+username +password +"is" +errmsg);
	      
	}

	

	@AfterTest
	public void afterTest() {

		driver.quit();
	}

}
