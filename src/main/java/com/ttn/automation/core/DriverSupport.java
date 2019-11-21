package com.ttn.automation.core;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.SkipException;

import java.util.LinkedList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * The class defines all gesture related methods is a subclass of Core.
 *
 * @author abhishek sharma
 */
public class DriverSupport extends Core {

    private final static Logger logger = LoggerFactory.getLogger(DriverSupport.class);

    /**
     * Performs Tap on element
     *
     * @param elementName takes the UI element name as per page object repository
     */
    public void tapOnElement(String elementName) throws ElementClickInterceptedException {
        long startTime = System.currentTimeMillis();
        By locator = getPageLocatorBy(pageName, elementName);
        WebElement element = getPageElement(locator);
        if (!element.isEnabled() && !element.isDisplayed()) {
            Assert.assertFalse(element.isEnabled(), "Element:" + element + "not enabled!");
        }
        element.click();
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Action: Tapping on, " + elementName + " in page: " + pageName + " - " + duration);
    }

    public void tapOnElement(WebElement element) throws ElementClickInterceptedException {

        if (!element.isEnabled() && !element.isDisplayed()) {
            Assert.assertFalse(element.isEnabled(), "Element:" + element + "not enabled!");
        }
        element.click();
    }

    public List<WebElement> getWebElements(String elementName) {
        List<WebElement> list = null;
        long startTime = System.currentTimeMillis();
        By byLocator = getPageLocatorBy(pageName, elementName);
        list = driver.findElements(byLocator);
        return list;
    }

    public WebElement getWebElement(String elementName) {

        long startTime = System.currentTimeMillis();
        By byLocator = getPageLocatorBy(pageName, elementName);
        WebElement element = driver.findElement(byLocator);
        return element;
    }

    public WebElement getWebElement(String locator, String locatorType) {

        long startTime = System.currentTimeMillis();
        if (locatorType.equalsIgnoreCase("accessibilityId")) {
            pageElement = MobileBy.AccessibilityId(locator);
        } else if (locatorType.equalsIgnoreCase("id")) {
            pageElement = By.id(locator);
        } else if (locatorType.equalsIgnoreCase("xpath")) {
            pageElement = By.xpath(locator);
        } else if (locatorType.equalsIgnoreCase("name")) {
            pageElement = By.name(locator);
        } else if (locatorType.equalsIgnoreCase("css")) {
            pageElement = By.cssSelector(locator);
        } else if (locatorType.equalsIgnoreCase("className")) {
            pageElement = By.className(locator);
        } else if (locatorType.equalsIgnoreCase("linkText")) {
            pageElement = By.linkText(locator);
        } else if (locatorType.equalsIgnoreCase("tagName")) {
            pageElement = By.tagName(locator);
        } else {
            pageElement = null;
        }
        WebElement element = driver.findElement(pageElement);
        return element;
    }

    // overloaded tapOnElement method

    public void tapOnElement(String pageName, String elementName) {
        long startTime = System.currentTimeMillis();
        By locator = getPageLocatorBy(pageName, elementName);
        WebElement element = getPageElement(locator);
        if (!element.isEnabled()) {
            Assert.assertFalse(element.isEnabled(), "Element:" + element + "not enabled!");
        }
        element.click();
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Action: Tapping on, " + elementName + " in page: " + pageName + " - " + duration);
    }

    /**
     * Types in String in value in the textbox
     *
     * @param value       takes the string to be typed in the textbox
     * @param elementName takes the name of the element
     */
    public void typeValueInTextBox(String value, String elementName) {
        long startTime = System.currentTimeMillis();
        By locator = getPageLocatorBy(pageName, elementName);
        WebElement element = getPageElement(locator);
        if (!element.isEnabled()) {
            throw new SkipException(elementName + " element was not found or not enabled to conduct User Action");
        }
        element.clear();
        element.sendKeys(value);
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Action: Typing [" + value + "] in: " + elementName + " in page: " + pageName + " - " + duration);
    }


    public String fetchAttribute(String elementName, String attributeName) {
        long startTime = System.currentTimeMillis();
        By locator = getPageLocatorBy(pageName, elementName);
        WebElement element = getPageElement(locator);
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Attribute value for : " + attributeName + " for element : " + elementName + " fetched - " + duration);
        return element.getAttribute(attributeName);
    }

    public void clickUsingJavascript(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();"
                , element);
    }

    public void clickUsingJavascript(String elementName) {
        long startTime = System.currentTimeMillis();
        By byLocator = getPageLocatorBy(pageName, elementName);
        WebElement element = driver.findElement(byLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();"
                , element);
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Action: Tapping using javascript on, " + elementName + " in page: " + pageName + " - " + duration);
    }


	/*public static WebElement getPageElement(By locator, int maxtimeWaitInSec) {
		return WaitUtils.waitForElementToLoad(locator,maxtimeWaitInSec, driver);
    }*/


    /**
     * check if config element is visible on screen and return webelement
     *
     * @param text
     * @return
     */
    public WebElement findElementInListView(String text) {
        try {
            logger.info("Find config element in list, Config: " + text);

            By byLocator = null;
            try {
                byLocator = getPageLocatorBy(pageName, "ParentConfig");

            } catch (Exception e) {
                Assert.fail();
            }

            By byLocator1 = getPageLocatorBy(pageName, "ChildConfig");

            @SuppressWarnings("unchecked")
            List<WebElement> parent = getPageElements(byLocator);
            List<WebElement> child = parent.get(0).findElements(byLocator1);
            for (int i = 0; i < child.size(); i++) {
                if (child.get(i).getText().equals(text)) {
                    return child.get(i);
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("Could not find config element in list");
            return null;
        }
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript
                ("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollToElement(String elementName) {

        By locator = getPageLocatorBy(pageName, elementName);
        WebElement element = getPageElement(locator);
        ((JavascriptExecutor) driver).executeScript
                ("arguments[0].scrollIntoView(true);", element);
    }

    public boolean verifyElementPresent(String elementName) {
        boolean elementStatus = false;
        By locator = getPageLocatorBy(pageName, elementName);
        WebElement element = getPageElement(locator);
        if (!element.isDisplayed()) {
            logger.error("The element " + elementName + " is not present");
            Assert.assertFalse(element.isEnabled(), "Element:" + element + "not enabled!");
        } else if (element.isDisplayed()) {
            logger.info("The element " + elementName + " is present");
            return true;
        }

        return elementStatus;
    }

    public String getText(String elementName) {

        By locator = getPageLocatorBy(pageName, elementName);
        WebElement element = getPageElement(locator);
        if (!element.isDisplayed()) {
            logger.error("The element " + elementName + " is not present");
            Assert.assertFalse(element.isEnabled(), "Element:" + element + "not enabled!");
        } else if (element.isDisplayed()) {
            logger.info("The element " + elementName + " is present");
        }

        return element.getText();
    }

    public boolean waitForPageToLoad() {
        return new WebDriverWait(driver,
                Long.parseLong(testProp.get("maxElementLookupTimeout").toString())).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public void openUrl(String url) {
        driver.get(url);
    }


    public void refreshScreen() {
        driver.navigate().refresh();
    }

    public void pressEnter() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER).build().perform();
    }


    public void hoverOverElement(String elementName) {
        Actions action = new Actions(driver);
        boolean elementStatus = false;
        By locator = getPageLocatorBy(pageName, elementName);
        WebElement element = getPageElement(locator);
        action.moveToElement(element).build().perform();
    }

    public boolean verifyPageLoaded() {
        boolean isPageLoaded = false;
        long startTime = System.currentTimeMillis();
        logger.info("Waiting for page to load...");
        if (new WebDriverWait(driver, 60).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"))) {
            isPageLoaded = true;
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Page loaded successfully - " + duration);
        }

        return isPageLoaded;
    }

    public void navigateBack() throws InterruptedException {
        driver.navigate().back();
        Thread.sleep(2000);
    }

    public void navigateForward() throws InterruptedException {
        driver.navigate().forward();
        Thread.sleep(2000);
    }
}
/*
new WebDriverWait(firefoxDriver, pageLoadTimeout).until(
        webDriver -> ((JavascriptExecutor) webDriver)
        .executeScript("return document.readyState").equals("complete"));*/
