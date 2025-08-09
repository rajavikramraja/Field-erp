package org.erp;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.utilize.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Login {
	WebDriver driver;
	WebDriverWait wait;
	
@BeforeMethod
public void lauchbrowser() {
	WebDriverManager.chromedriver().setup();
	 driver=new ChromeDriver();
	driver.get("https://trustedtools.in/field-erp/admin/auth/login");
	//System.out.println("Hi");
	wait = new WebDriverWait(driver, Duration.ofSeconds(10));

}
@DataProvider(name="logindata")
public Object[][] logindaObjects() throws IOException {
	return ParentClass.getexcel("C:\\Users\\vikra\\OneDrive\\Documents\\erppassword.xlsx", "Sheet1");	
}
@Test(dataProvider ="logindata")
public void login(String username, String Password) {
	driver.findElement(By.id("name")).sendKeys(username);
	driver.findElement(By.id("password")).sendKeys(Password);
	driver.findElement(By.id("submit")).click();
	
	String expectedurl="https://trustedtools.in/field-erp/admin/dashboard";
	if (username.equals("vikram-kitchens")&& Password.equals("test@123")) {
		Boolean until = wait.until(ExpectedConditions.urlContains(expectedurl));
		System.out.println(driver.getCurrentUrl() );
	assert	driver.getCurrentUrl().equals(expectedurl);
	System.out.println("Login Succefull" + username);
	} else {
//boolean displayed = driver.findElement(By.xpath("//p[normalize-space()='The Username field is required.']")).isDisplayed();
//assert displayed;
//System.out.println("Login valid"+ username);
		
		
		WebElement errorElement = wait.until(
			    ExpectedConditions.visibilityOfElementLocated(
			        By.xpath("//p[normalize-space()='The Username field is required.']"+
			    "| //p[normalize-space()='The Password field is required.']" 
			        		+"| //div[@class='alert alert-danger']")
			    )
			);

		String errorText = errorElement.getText() .replaceAll("[^\\x20-\\x7E]", "")  // remove non-ASCII printable characters
			    .replaceAll("\\s+", " ")  .trim();
		System.out.println("Error Message: " + errorText);

		// List of expected messages
		List<String> expectedMessages = Arrays.asList(
		    "The Username field is required.",
		    "The Password field is required.",
		    "The Password field is required, The Username field is required.",
		    "Invalid Username or Password!"
		);

		assert expectedMessages.contains(errorText) : "Unexpected error message: " + errorText;


	}
}
@AfterMethod
public void closebrowser() {
	driver.quit();
}
}