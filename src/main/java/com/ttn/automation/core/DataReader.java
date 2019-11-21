package com.ttn.automation.core;

import com.ttn.automation.Utils.PropertyFileUtils;
import io.appium.java_client.MobileBy;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class DataReader {


    public static String suiteName;
    private final static Logger logger = LoggerFactory.getLogger(Core.class);
    private static List<Data> dataObjectRepo = new ArrayList<Data>();
    File baseDirectory = new File(".");

    protected void setupDataSheet() throws IOException {

        String testDataPath = baseDirectory.getCanonicalPath() + File.separator + "testdata" + File.separator + "testdata_";

        if (System.getProperty("env") == null) {
            setDataObject(testDataPath + "uat.xlsx");
        } else if (System.getProperty("env").equalsIgnoreCase("UAT")) {
            setDataObject(testDataPath + System.getProperty("env").toLowerCase()
                    + ".xlsx");
        } else if (System.getProperty("env").equalsIgnoreCase("PROD")) {
            setDataObject(testDataPath + System.getProperty("env").toLowerCase()
                    + ".xlsx");
        }
    }

    /**
     * Presets all page objects at once from the excel sheet and loads it to memory for faster page object reference.
     * <br> This should be only be referenced in before scenario methods.
     *
     * @author abhishek sharma
     */
    private void setDataObject(String dataObjectFilePath) throws IOException {
        long startTime = System.currentTimeMillis();
        File file = new File(dataObjectFilePath);
        if (file.exists() && !file.isDirectory()) {
            FileInputStream inputStream = new FileInputStream(file);
            Workbook workBook = new XSSFWorkbook(inputStream);
            int totalSheetCount = workBook.getNumberOfSheets();
            for (int sheetNumber = 0; sheetNumber < totalSheetCount; sheetNumber++) {
                Sheet sheet = workBook.getSheetAt(sheetNumber);
                Map<String, DataElements> dataElementsMap = getDataElements(sheet);
                dataObjectRepo.add(new Data(sheet.getSheetName(), dataElementsMap));
            }
        } else {
            logger.error("Data object file not found at: " + file.getAbsolutePath());
            throw new SkipException("Data object file not found at: " + file.getAbsolutePath());
        }
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Data sheet Initialized! - " + duration);
    }

    /**
     * support method for getPageObjects
     * <br>Fetches all page elements in a page
     *
     * @param sheet takes the Sheet object returned from the workbook
     * @return return the map of page elements with element name and object of pageElement for the same.
     * @author abhishek sharma
     */
    private Map<String, DataElements> getDataElements(Sheet sheet) {
        Map<String, DataElements> dataElementsMap = new HashMap<String, DataElements>();
        //Ignoring title row [0] starting from row [1]
        for (int row = 1; row <= sheet.getLastRowNum(); row++) {
            Row dataRow = sheet.getRow(row);
            //int titleValueIndex = 0;
            dataElementsMap.put(dataRow.getCell(0).getStringCellValue().trim(),
                    new DataElements(dataRow.getCell(0).getStringCellValue().trim(),
                            dataRow.getCell(1).getStringCellValue().trim(),
                            dataRow.getCell(2).getStringCellValue().trim())
            );
        }
        return dataElementsMap;
    }

    /**
     * Fetches page locators from in-memory pageObjectRepo
     * Improved for performance considerations.
     *
     * @param testSuiteName name of the page where the element will be queried
     * @return org.openqa.selenium.By pageElement
     * @author abhishek sharma
     */
    protected Map<String, DataElements> getClassData(String testSuiteName) {
        logger.debug("Finding" + testSuiteName + "in data sheet");
        Map<String, DataElements> dataElementMap = new HashMap<String, DataElements>();
        for (Data data : dataObjectRepo) {
            if (data.getSuiteName().equalsIgnoreCase(testSuiteName)) {
                dataElementMap = data.getElementList();
            }
        }
        return dataElementMap;
    }

    public void setSuiteName(String suite_name) {
        suiteName = suite_name;
    }


}
