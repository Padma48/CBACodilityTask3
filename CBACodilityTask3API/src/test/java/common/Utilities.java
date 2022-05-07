package common;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


 public  class Utilities {
	
	public static void Login(WebDriver driver, String username, String password) {
		Print_page_title(driver);
		driver.findElement(By.id("worrior_username")).clear();
		driver.findElement(By.id("worrior_username")).sendKeys(username);
		driver.findElement(By.id("worrior_pwd")).clear();
		driver.findElement(By.id("worrior_pwd")).sendKeys(password);
		driver.findElement(By.id("warrior")).click();
		System.out.println("User is on " + driver.getTitle());
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		System.out.println("Login successful");
	}
	
	public static void verify_bus_challange(WebDriver driver) {
		Print_page_title(driver);
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(20));
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		JavascriptExecutor js = ((JavascriptExecutor) driver);

		//presence in DOM
		driver.manage().timeouts().getImplicitWaitTimeout();

		//scrolling
		WebElement element = driver.findElement(By.id("start"));  
		js.executeScript("arguments[0].scrollIntoView(true);", element);

		//clickable
		driver.manage().timeouts().getImplicitWaitTimeout();
	driver.findElement(By.id("start")).click();
	driver.manage().timeouts().getImplicitWaitTimeout();
	driver.findElement(By.id("bus")).click();
	driver.findElement(By.id("bus_timer_start")).click();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.findElement(By.id("bus_answer_1")).click();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.findElement(By.id("leaderboard_link")).click();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	System.out.println("user is on " + driver.getTitle());
	System.out.println("verified dashboard successfully");
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	public static void Print_page_title(WebDriver driver) {
		String title = driver.getTitle();
		System.out.print("User is on " + title);
	}
	public static void verify_user_score(WebDriver driver) {
		String score = driver.findElement(By.xpath("/html/body/div[3]/div/p/table/tbody/tr[2]/td[2]")).getText();
		System.out.println("User score " + score);
	}
	}