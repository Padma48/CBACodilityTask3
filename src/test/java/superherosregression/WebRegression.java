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
import org.testng.annotations.AfterTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebRegression {
	public String s = RandomStringUtils.randomAlphabetic(5);
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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("New username: " + s);
		driver.findElement(By.name("uname")).sendKeys(s);
		driver.findElement(By.name("psw")).sendKeys(Pw);
		driver.findElement(By.name("psw-repeat")).sendKeys(Pw);
		driver.findElement(By.id("signupbtn")).click();
		System.out.println("Registration successful");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String title1 = driver.getTitle();
		System.out.print("User is on " + title1);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Write code here that turns the phrase above into concrete actions
		// driver.findElement(By.id("start")).click();
		// Login with registration details
		/*
		 * driver.findElement(By.id("worrior_username")).clear();
		 * driver.findElement(By.id("worrior_username")).sendKeys(s);
		 * driver.findElement(By.id("worrior_pwd")).clear();
		 * driver.findElement(By.id("worrior_pwd")).sendKeys("Test");
		 * driver.findElement(By.id("warrior")).click();
		 * System.out.println("User is on " + driver.getTitle());
		 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 * System.out.println("Login successful");
		 */
		Utilities.Login(driver, s, Pw );
		// Write code here that turns the phrase above into concrete actions
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
