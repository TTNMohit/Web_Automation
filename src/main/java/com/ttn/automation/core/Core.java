package com.ttn.automation.core;

import com.ttn.automation.Utils.PropertyFileUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * The core component of automation framework. Parent class to all framework components.
 * Defines framework level static parameters like driver, pageObjectRepo, framework properties, test properties, page name and scenario name
 *
 * @author abhishek sharma
 */
public class Core extends DataReader {

    private final static Logger logger = LoggerFactory.getLogger(Core.class);
    private static List<Page> pageObjectRepo = new ArrayList<Page>();
    protected static WebDriver driver;
    protected static WebDriver driver2;
    public static Properties testProp;
    public static String pageName;
    public static String scenarioName;
    public static String URL = null;
    public static String resourcePath = null;
    public static File directory = new File(".");
    By pageElement = null;
    /**
     * This must be the first method to be called to setup the framework.
     * This method initializes the pageObjectRepository.
     */
    protected void setupTestProperties(String testDataFilePath, String propFilePath) throws IOException {

        //  initiate locator data sheet
        setPageObjects(testDataFilePath);

        // initiate property file
        testProp = PropertyFileUtils.readProperty(propFilePath);

        // set resource folder path
        resourcePath = directory.getCanonicalPath()
                + File.separator
                + "src"
                + File.separator
                + "main"
                + File.separator
                + "resources";

        setPageName(testProp.getProperty("locator_sheet_name"));
    }

    /**
     * Presets all page objects at once from the excel sheet and loads it to memory for faster page object reference.
     * <br> This should be only be referenced in before scenario methods.
     *
     * @author abhishek sharma
     */
    private void setPageObjects(String pageObjectFilePath) throws IOException {
        long startTime = System.currentTimeMillis();
        File file = new File(pageObjectFilePath);
        if (file.exists() && !file.isDirectory()) {
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workBook = new XSSFWorkbook(inputStream);
            int totalSheetCount = workBook.getNumberOfSheets();
            for (int sheetNumber = 0; sheetNumber < totalSheetCount; sheetNumber++) {
                Sheet sheet = workBook.getSheetAt(sheetNumber);
                Map<String, PageElement> pageElementMap = getPageElements(sheet);
                pageObjectRepo.add(new Page(sheet.getSheetName(), pageElementMap));
            }
        } else {
            logger.error("Page object file not found at: " + file.getAbsolutePath());
            throw new SkipException("Page object file not found at: " + file.getAbsolutePath());
        }
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Page objects Initialized! - " + duration);
    }

    /**
     * support method for getPageObjects
     * <br>Fetches all page elements in a page
     *
     * @param sheet takes the Sheet object returned from the workbook
     * @return return the map of page elements with element name and object of pageElement for the same.
     * @author abhishek sharma
     */
    private Map<String, PageElement> getPageElements(Sheet sheet) {
        Map<String, PageElement> pageElementMap = new HashMap<String, PageElement>();
        //Ignoring title row [0] starting from row [1]
        System.out.println(sheet.getLastRowNum());
        for (int row = 1; row <= sheet.getLastRowNum(); row++) {
            Row dataRow = sheet.getRow(row);
            //int titleValueIndex = 0;
            pageElementMap.put(dataRow.getCell(0).getStringCellValue().trim(),
                    new PageElement(dataRow.getCell(0).getStringCellValue().trim(),
                            dataRow.getCell(1).getStringCellValue().trim(),
                            dataRow.getCell(2).getStringCellValue().trim())
            );
        }
        return pageElementMap;
    }

    /**
     * Fetches page locators from in-memory pageObjectRepo
     * Improved for performance considerations.
     *
     * @param pageName name of the page where the element will be queried
     * @return org.openqa.selenium.By pageElement
     * @author abhishek sharma
     */
    protected By getPageLocatorBy(String pageName, String elementName) {
        logger.debug("Finding element: " + elementName + ", in page: " + pageName);
        Map<String, PageElement> pageElementMap = new HashMap<String, PageElement>();
        for (Page page : pageObjectRepo) {
            if (page.getPageName().equalsIgnoreCase(pageName)) {
                pageElementMap = page.getElementList();
            }
        }
        //By pageElement = null;
        String element = pageElementMap.get(elementName).getLocatorValue();
        String locaterType = pageElementMap.get(elementName).getLocatorType();

        if (locaterType.equalsIgnoreCase("accessibilityId")) {
            pageElement = MobileBy.AccessibilityId(element);
        } else if (locaterType.equalsIgnoreCase("id")) {
            pageElement = By.id(element);
        } else if (locaterType.equalsIgnoreCase("xpath")) {
            pageElement = By.xpath(element);
        } else if (locaterType.equalsIgnoreCase("name")) {
            pageElement = By.name(element);
        } else if (locaterType.equalsIgnoreCase("css")) {
            pageElement = By.cssSelector(element);
        } else if (locaterType.equalsIgnoreCase("className")) {
            pageElement = By.className(element);
        } else if (locaterType.equalsIgnoreCase("linkText")) {
            pageElement = By.linkText(element);
        } else if (locaterType.equalsIgnoreCase("tagName")) {
            pageElement = By.tagName(element);
        } else {
            pageElement = null;
        }
        return pageElement;
    }

    /**
     * Fetches the web element explicitly with the locator in argument
     *
     * @param locator Takes the Selenium By object for the element lookup.
     * @return WebElement
     */
    protected WebElement getPageElement(By locator) {
        long startTime = System.currentTimeMillis();
        WebElement element = WaitUtils.waitForElementToLoad(locator, driver);
        long duration = System.currentTimeMillis() - startTime;
        logger.debug("Element By locator: " + locator.toString() + " found! Time - " + duration);
        return element;
    }

    protected List<WebElement> getPageElements(By locator) {
        long startTime = System.currentTimeMillis();
        List<WebElement> elements = WaitUtils.waitForElementsToLoad(locator, driver);
        long duration = System.currentTimeMillis() - startTime;
        logger.debug("Element By locator: " + locator.toString() + " found! Time - " + duration);
        return elements;
    }


    /**
     * Fetches the web element from in-memory page object Repo
     *
     * @param pageName    takes the page name to lookup
     * @param elementName takes UI element name to lookup
     * @return WebElement
     */
    protected WebElement getPageElement(String pageName, String elementName) {
        By objBy = getPageLocatorBy(pageName, elementName);
        return getPageElement(objBy);
    }

//	protected  WebElement getPageElement(By locator,int maxtimeWaitInSec) {
//    	return WaitUtils.waitForElementToLoad(locator,maxtimeWaitInSec);
//    }


    /**
     * Gets the current pageName in the application.
     * <br> All Element lookup will happen based on the the current page name.
     *
     * @return the pageName
     */
    public String getPageName() {
        return pageName;
    }

    /**
     * Sets the current pageName in the application
     * <br> All Element lookup will happen based on the the current page name.
     *
     * @param pageName the pageName to set
     */
    public void setPageName(String pageName) {
        Core.pageName = pageName;
    }
}


