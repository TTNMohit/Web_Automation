package com.ttn.automation.core;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.ttn.automation.Utils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestBase extends DriverSupport {


    private final static Logger logger = LoggerFactory.getLogger(TestBase.class);
    public static String URL = null;
    public static Set<Cookie> cookieSet = new HashSet<>();
    public static ExtentReports extent;
    public static ExtentTest testReport;
    String reportPath = null;
    File directory = new File(".");
    Data data = new Data();
    Map<String, DataElements> dataElementMap = null;

    @BeforeSuite
    public void testSetup() throws IOException, InterruptedException, AWTException {

        setupDataSheet();
        setupTestProperties("." + File.separator + "ObjectRespository"
                + File.separator + "PageObjectRepository.xlsx", "." + File.separator + "properties" +
                File.separator + "Test.properties");

        // ****************** initiate URL, Driver and handle Session ********************

        URL = EnvironmentSetup.initiateURL(testProp);


        // **************************** initiate Reporting ********************************

        if (System.getProperty("env") == null) {
            reportPath = directory.getCanonicalPath() + File.separator +
                    "Reports" + File.separator + "Web_Automation_Report.html";
        } else {
            reportPath = directory.getCanonicalPath() + File.separator +
                    "Reports" + File.separator + "Web_Automation_Report_" + System.getProperty("env").toUpperCase() + ".html";
        }

        extent = new ExtentReports(reportPath, true);

        try {
            String extentConfigPath = "." + File.separator + "extent-config.xml";
            extent.loadConfig(new File(extentConfigPath));
        } catch (Exception e) {
            logger.error("Could not load the extent config, reporting failed!");
        }

        // getting the login session
//        testReport.log(LogStatus.INFO, "Logging in the application to maintain login session");
        driver = EnvironmentSetup.initiateWebDriver(driver);
        dataElementMap = getClassData("TestBase");
        HashMap<String, String> data =
                Utility.getRequestParams(dataElementMap.get("testSetup").getParams());
        driver = new SessionHandler().getLoggedInSessionDriver(data.get("SID")
                , data.get("PASSWORD"), driver);
    }

    @BeforeMethod
    public void methodSetup(Method method) throws IOException {
        testReport = extent.startTest(
                (this.getClass().getSimpleName() + " :: " + method.getName()),
                method.getName());
        driver = EnvironmentSetup.initiateWebDriver(driver);
        driver.get(URL);
    }

    @AfterMethod
    public void tearDownTest(ITestResult oResult, Method method) throws IOException {

        if (oResult.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = null;
            if (System.getProperty("env") == null) {
                screenshotPath = directory.getCanonicalPath() + File.separator + "screenshots"
                        + File.separator
                        + "GENERIC"
                        + File.separator
                        + oResult.getMethod().getMethodName() + ".png";
            } else {
                screenshotPath = directory.getCanonicalPath() + File.separator + "screenshots"
                        + File.separator + System.getProperty("env").toUpperCase()
                        + File.separator + oResult.getMethod().getMethodName() + ".png";
            }
            ScreenshotHandler.takeScreenShot(driver, screenshotPath);
            /*testReport.log(LogStatus.FAIL,
                    testReport.addScreenCapture(screenshotPath));*/

            testReport.log(LogStatus.FAIL,
                    testReport.addScreenCapture(screenshotPath));
        } else if (oResult.getStatus() == ITestResult.SKIP) {
            testReport.log(LogStatus.SKIP,
                    "Test skipped " + oResult.getThrowable());
        } else {
            testReport.log(LogStatus.PASS, "Test passed");
        }
        extent.endTest(testReport);
        driver.close();

    }

    @AfterSuite
    public void tearDownSuite() {
        extent.flush();
    }

    public static void waitForSeconds(int time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            logger.error("Wait state inturrupted. Wait cancelled.");
        }
    }
}
