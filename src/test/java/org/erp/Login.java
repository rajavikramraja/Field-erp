package org.erp;

import java.io.IOException;
import org.utilize.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Login {
	WebDriver driver;
@BeforeMethod
public void lauchbrowser() {
	WebDriverManager.chromedriver().setup();
	 driver=new ChromeDriver();
	driver.get("https://trustedtools.in/field-erp/admin/auth/login");
	System.out.println("Hi");
}
@DataProvider(name="logindata")
public Object[][] logindaObjects() throws IOException {
	return ParentClass.getexcel("C:\\Users\\vikra\\OneDrive\\Documents\\erppassword.xlsx", "Sheet1");	
}
@Test(dataProvider = "logindata")
public void login(String username, String Password) {
	driver.findElement(By.id("name")).sendKeys(username);
	driver.findElement(By.id("password")).sendKeys(Password);
	driver.findElement(By.id("submit")).click();
	
	String expectedurl="https://trustedtools.in/field-erp/admin/dashboard";
	if (username.equals("vikram-kitchens")&& Password.equals("test@123")) {
	assert	driver.getCurrentUrl().equals(expectedurl);
	System.out.println("Login Succefull" + username);
	} else {
boolean displayed = driver.findElement(By.xpath("//div[@class='alert alert-danger']")).isDisplayed();
assert displayed;
System.out.println("Login valid"+ username);

	}
}
@AfterMethod
public void closebrowser() {
	driver.quit();
}
}
