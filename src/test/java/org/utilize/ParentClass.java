package org.utilize;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ParentClass {
	static WebDriver driver;
public static void chromelaunch() {
	WebDriverManager.chromedriver().setup();
 driver=new ChromeDriver();	
}
public static void windowmax() {
	driver.manage().window().maximize();
	
}
public static void launchurl (String url) {
//	driver.navigate().to(url);
	driver.get(url);
}
public static void sendvalue(String txt, WebElement ele) {
	 ele.sendKeys(txt);
}
public static void clickbtn(WebElement ele) {
	 ele.sendKeys();
}
public static void screenshot(String loc) throws IOException {
	TakesScreenshot screenshots = (TakesScreenshot) driver;
	File screenshotAs = screenshots.getScreenshotAs(OutputType.FILE);
	File file =new File(loc);
	FileUtils.copyFile(screenshotAs, file);
}
public static Object[][] getexcel(String loc, String sheet) throws IOException {
FileInputStream files=new FileInputStream(loc);
XSSFWorkbook xssfWorkbook =new XSSFWorkbook();
XSSFSheet sheets = xssfWorkbook.getSheet(sheet);
int no_of_row = sheets.getPhysicalNumberOfRows();
int no_of_col = sheets.getRow(0).getPhysicalNumberOfCells();
Object[][] obj= new Object[no_of_row-1][no_of_col];
for (int i= 1; i < no_of_row; i++) {
	XSSFRow row = sheets.getRow(i);
	for (int j = 0; j < no_of_col; j++) {
		XSSFCell cell = row.getCell(j);
		if (cell==null) {
		obj[i-1][0]="";
		continue;
		}
		switch (cell.getCellType()) {
		case STRING:
			obj[i-1][j]=cell.getStringCellValue();
			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				obj[i-1][j]=String.valueOf(cell.getDateCellValue());
			} else {
				obj[i-1][j]=String.valueOf(cell.getNumericCellValue());
			}
		break;
		case BOOLEAN:
			obj[i-1][j]=String.valueOf(cell.getBooleanCellValue());
			break;
		case FORMULA:
			obj[i-1][j]=String.valueOf(cell.getCellFormula());
			break;
		default:

			obj[i-1][j]="";
			break;
		}
	}
}
xssfWorkbook.close();
files.close();
return obj;
}
}
