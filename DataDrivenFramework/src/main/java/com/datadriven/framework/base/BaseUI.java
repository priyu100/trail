package com.datadriven.framework.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
//import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseUI {

	public WebDriver driver;
	public Properties prop;

	public void invokeBrowser(String browserName) {

		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);

		ChromeOptions options = new ChromeOptions();

		options.setExperimentalOption("prefs", prefs);

		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "//src//test//resources//drivers//chromedriver.exe");
			options.addArguments("disable-infobars");
			options.addArguments("disable-notification");
			options.addArguments("--disable-popup-blocking");

			driver = new ChromeDriver(options);
		}

		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);

		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(System.getProperty("user.dir")
						+ "//src//test//resources//ObjectRepository//projectConfig.properties");

				prop.load(file);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void openURL(String websiteURLKey) {
		driver.get(prop.getProperty(websiteURLKey));

	}

	public void quitBrowser() {
		driver.quit();
	}

	public void enterlocation(String xpathKey, String data) {

		driver.findElement(By.xpath(prop.getProperty(xpathKey))).clear();

		driver.findElement(By.xpath(prop.getProperty(xpathKey))).sendKeys(data);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Actions act = new Actions(driver);
		act.sendKeys(Keys.DOWN).perform();
		act.sendKeys(Keys.ENTER).perform();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void elementscrollclick(String xpathKey) throws InterruptedException {
		WebElement element = driver.findElement(By.xpath(prop.getProperty(xpathKey)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(500);
		driver.findElement(By.xpath(prop.getProperty(xpathKey))).click();

	}

	public void elementClick(String xpathKey) {

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(xpathKey))));

		driver.findElement(By.xpath(prop.getProperty(xpathKey))).click();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void multiclick(String xpathKey) {
		List<WebElement> cl = driver.findElements(By.xpath(prop.getProperty(xpathKey)));
		for (int i = 0; i < cl.size(); i++) {
			cl.get(i).click();
		}

	}

	public void xlsx(String xpathKey1, String xpathKey2) throws IOException {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		List<WebElement> NAME = driver.findElements(By.xpath(prop.getProperty(xpathKey1)));
		List<WebElement> PRICE = driver.findElements(By.xpath(prop.getProperty(xpathKey2)));

		// EXCEL SHEET FORMATION
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("CAR DETAILS");
		String Data[] = new String[15];
		
		// Entering the title for worksheet
		Row first = sheet.createRow(0);
		Cell Title = first.createCell(0);
		Title.setCellValue("CAR NAME");
		Cell dt = first.createCell(1);
		dt.setCellValue("CAR RENT");
		
		// INSERTING VALUE IN EXCEL SHEET
		for (int i = 0; i < 15; i++) {
			XSSFRow row = sheet.createRow(i + 1);

			Data[0] = NAME.get(i).getText();
			Data[1] = PRICE.get(i).getText();
			String strExpected = "SUV";
			if (strExpected.equals(Data[0])) {

				for (int j = 0; j < 15; j++) {

					XSSFCell cell = row.createCell(j);
					cell.setCellValue(Data[j]);
				}

			}
			
		}

		FileOutputStream fileOutputStream = new FileOutputStream("CAR_RENT DETAILS.xlsx");
		workbook.write(fileOutputStream);
		workbook.close();

	}

}
