package com.ttn.automation.core;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Contains Explicit and implicit wait methods.
 * @author abhishek sharma
 */
public class WaitUtils {

    final static Logger logger = LoggerFactory.getLogger(WaitUtils.class);

    /**
     * Recursively queries page source for and element with timeout defined in {@code maxElementLookupTimeout} test property
     * <br> Test Property must define a parameter <b>maxElementLookupTimeout</b>.
     * @param byLocator
     * @param driver
     * @return
     */
    public static WebElement waitForElementToLoad(By byLocator, WebDriver driver) {
        if (driver == null) {
            logger.error("Driver is passed as null!");
            return null;
        }
        @SuppressWarnings({ "unchecked", "rawtypes" })
        FluentWait<WebDriver> wait = new FluentWait(driver)
                .withTimeout(Integer.parseInt(Core.testProp.getProperty("maxElementLookupTimeout")), TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
    }

    public static List<WebElement> waitForElementsToLoad(By byLocator, WebDriver driver) {
        if (driver == null) {
            logger.error("Driver is passed as null!");
            return null;
        }
        @SuppressWarnings({ "unchecked", "rawtypes" })
        FluentWait<WebDriver> wait = new FluentWait(driver)
                .withTimeout(Integer.parseInt(Core.testProp.getProperty("maxElementLookupTimeout")), TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byLocator));
    }



    public static WebElement waitForElementToLoad(By byLocator,int maxtimeWaitInSec, AppiumDriver<?> driver) {
        if (driver == null) {
            return null;
        }
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maxtimeWaitInSec, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
    }
}
