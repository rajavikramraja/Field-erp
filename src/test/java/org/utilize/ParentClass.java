package org.utilize;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
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
//public static Object[][] getexcel(String loc, String sheet) throws IOException {
//FileInputStream files=new FileInputStream(loc);
//XSSFWorkbook xssfWorkbook =new XSSFWorkbook();
//XSSFSheet sheets = xssfWorkbook.getSheet(sheet);
//int no_of_row = sheets.getPhysicalNumberOfRows();
//int no_of_col = sheets.getRow(0).getPhysicalNumberOfCells();
//Object[][] obj= new Object[no_of_row-1][no_of_col];
//for (int i= 1; i < no_of_row; i++) {
//	XSSFRow row = sheets.getRow(i);
//	for (int j = 0; j < no_of_col; j++) {
//		XSSFCell cell = row.getCell(j);
//		if (cell==null) {
//		obj[i-1][0]="";
//		continue;
//		}
//		switch (cell.getCellType()) {
//		case STRING:
//			obj[i-1][j]=cell.getStringCellValue();
//			break;
//		case NUMERIC:
//			if (DateUtil.isCellDateFormatted(cell)) {
//				obj[i-1][j]=String.valueOf(cell.getDateCellValue());
//			} else {
//				obj[i-1][j]=String.valueOf(cell.getNumericCellValue());
//			}
//		break;
//		case BOOLEAN:
//			obj[i-1][j]=String.valueOf(cell.getBooleanCellValue());
//			break;
//		case FORMULA:
//			obj[i-1][j]=String.valueOf(cell.getCellFormula());
//			break;
//		default:
//
//			obj[i-1][j]="";
//			break;
//		}
//	}
//}
//xssfWorkbook.close();
//files.close();
//return obj;
//}
public static Object[][] getexcel(String loc, String sheet) throws IOException {
    FileInputStream files = new FileInputStream(loc);                 // 1
    XSSFWorkbook workbook = new XSSFWorkbook(files);                  // 2
    XSSFSheet sheets = workbook.getSheet(sheet);                      // 3

    int no_of_row = sheets.getPhysicalNumberOfRows();                 // 4
    int no_of_col = sheets.getRow(0).getPhysicalNumberOfCells();      // 5

    List<Object[]> dataList = new ArrayList();                      // 6

    for (int i = 1; i < no_of_row; i++) {                             // 7 (start from 1 = skip header)
        XSSFRow row = sheets.getRow(i);                               // 8
        if (row == null) continue;                                   // 9

        Object[] rowData = new Object[no_of_col];                    // 10
        boolean allEmpty = true;                                     // 11

        for (int j = 0; j < no_of_col; j++) {                        // 12
            XSSFCell cell = row.getCell(j);                          // 13
            String cellValue = "";                                   // 14

            if (cell == null || cell.getCellType() == CellType.BLANK) {
                cellValue = "";
            } else {
                switch (cell.getCellType()) {                        // 15
                    case STRING:
                        cellValue = cell.getStringCellValue().trim();
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            cellValue = String.valueOf(cell.getDateCellValue());
                        } else {
                            cellValue = String.valueOf((long) cell.getNumericCellValue());
                        }
                        break;
                    case BOOLEAN:
                        cellValue = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        cellValue = cell.getCellFormula();
                        break;
                    default:
                        cellValue = cell.toString().trim();
                }
            }

            if (!cellValue.isEmpty()) {                             // 16
                allEmpty = false;
            }
            rowData[j] = cellValue;                                 // 17
        }

        if (!allEmpty) {                                             // 18
            dataList.add(rowData);
        }
    }

    workbook.close();                                                // 19
    files.close();                                                   // 20

    // Convert List<Object[]> to Object[][]
    Object[][] obj = new Object[dataList.size()][no_of_col];         // 21
    for (int i = 0; i < dataList.size(); i++) {                      // 22
        obj[i] = dataList.get(i);                                    // 23
    }
    return obj;                                                      // 24
}
}
