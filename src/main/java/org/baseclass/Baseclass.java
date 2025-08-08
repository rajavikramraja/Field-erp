package org.baseclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Baseclass {
public static WebDriver driver;

	


	public static void LaunchBrowserfire() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
	}
	public static void LaunchBrowseredge() {
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
	}
	public static void LaunchBrowserChrome() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	public static void windowMaximize() {
		driver.manage().window().maximize();
	}

	public static void LaunchUrl(String url) {
		driver.get(url);
	}

	public static String pageTitle() {
		String title = driver.getTitle();
		return title;
	}

	public static void pageUrl() {
		String url = driver.getCurrentUrl();
		System.out.println(url);
	}

	public static void passText(String txt, WebElement ele) {
		ele.sendKeys(txt);

	}

	public static void closeEntireBrowser() {
		driver.quit();
	}

	public static void clickBtn(WebElement el) {
		el.click();
	}

	public static void screenShot(String imgLocation) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File image = ts.getScreenshotAs(OutputType.FILE);
		File f = new File(imgLocation);
		FileUtils.copyFile(image, f);
	}

	public static Actions a;

	public static void moveTheCursor(WebElement targetWebElement) {
		a = new Actions(driver);
		a.moveToElement(targetWebElement).perform();
	}

	public static void dragDrop(WebElement dragWebElement, WebElement dropWebElement) {
		a = new Actions(driver);
		a.dragAndDrop(dragWebElement, dropWebElement).perform();

	}

	public static JavascriptExecutor js;

	public static void scrollThePage(WebElement tarWebElement) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", tarWebElement);
	}

	 public static void excelRead(String location,String sheetName,int rowNo, int cellNo,String date) throws IOException {
		File f = new File(location);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet mysheet = wb.getSheet(sheetName);
		Row r = mysheet.getRow(rowNo);
		Cell c = r.getCell(cellNo);
		CellType cellType = c.getCellType();
		if (cellType == CellType.STRING) {
			String value = c.getStringCellValue();
			System.out.println(value);
		}

		else if (DateUtil.isCellDateFormatted(c)) {
			Date dd = c.getDateCellValue();
			SimpleDateFormat s = new SimpleDateFormat(date);
			String format = s.format(dd);
			System.out.println(format);
		} else {
			double d = c.getNumericCellValue();
			long l = (long) d;
			String valueOf = String.valueOf(l);
			System.out.println(valueOf);
		}

	}
	

	//public static WebElement f;

	public static WebElement findElement(By ab) {
	 WebElement findElement = driver.findElement(ab);
	 return findElement;
	}
	
	public static void createExcel(String location,String sheetName,int rowint,int cellint,String data) throws IOException {
		File f=new File(location);
		Workbook wb =new XSSFWorkbook();
		Sheet createSheet = wb.createSheet(sheetName);
		Row createRow = createSheet.createRow(rowint);
		Cell createCell = createRow.createCell(cellint);
		createCell.setCellValue(data);
		FileOutputStream fos =new FileOutputStream(f);
		wb.write(fos);
		
	}
	
	
	public static void createCell(String location,String sheetName,int rowint,int cellint,String data) throws IOException {
		File f =new File(location);
		FileInputStream fis =new FileInputStream(f);
		Workbook wb=new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheet(sheetName);
		Row r = sheet.getRow(rowint);
		Cell c = r.createCell(cellint);
		c.setCellValue(data);
		FileOutputStream fos=new FileOutputStream(f);
		wb.write(fos);
	}
	
	public static void createRow(String location,String sheetName,int rowint,int cellint,String data) throws IOException  {
		File f =new File(location);
		FileInputStream fis =new FileInputStream(f);
		Workbook wb=new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheet(sheetName);
		Row r = sheet.createRow(rowint);
		Cell c = r.createCell(cellint);
		c.setCellValue(data);
		FileOutputStream fos=new FileOutputStream(f);
		wb.write(fos);
	}
	public static void update(String location,String sheetName,int rowNo, int cellNo,String existingData,String newData) throws IOException {
		File f = new File(location);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet mysheet = wb.getSheet(sheetName);
		Row r = mysheet.getRow(rowNo);
		Cell c = r.getCell(cellNo);
		String scv = c.getStringCellValue();
		if (scv.equals(existingData)) {
			c.setCellValue(newData);
		}
		FileOutputStream fos=new FileOutputStream(f);
		wb.write(fos);
			
		}
	public static Object[][] getValue(String location,String sheet1) throws IOException {
		File f = new File(location);
		FileInputStream fis = new FileInputStream(f);
		Workbook sheet = new XSSFWorkbook(fis);
		Sheet sheet2 = sheet.getSheet(sheet1);
		for (int i = 0; i < sheet2.getPhysicalNumberOfRows(); i++) {
			Row row = sheet2.getRow(i);
			for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
				Cell cell = row.getCell(j);
System.out.println(cell);
			}
		}
		return null;

	}
	
	public static Object[][] getTestData(String excelPath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows(); // total rows
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells(); // total columns

        Object[][] data = new Object[rowCount - 1][colCount]; // exclude header

        for (int i = 1; i < rowCount; i++) { // skip header row
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                data[i - 1][j] = cell.getStringCellValue(); // store data into array
            }
        }

        workbook.close();
        fis.close();
        return data; // return 2D array
    }
public static void windowshandle() {
	

	// TODO Auto-generated method stub
String windowHandl = driver.getWindowHandle();
Set<String> windowHandles = driver.getWindowHandles();
for (String winid : windowHandles) {
	if (winid!=windowHandl) {
		driver.switchTo().window(winid);
		
	}
}
}
}
