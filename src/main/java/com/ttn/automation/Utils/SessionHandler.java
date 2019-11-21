package com.ttn.automation.Utils;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.ttn.automation.PageModels.HomeScreenModel;
import com.ttn.automation.PageModels.LoginPageModel;
import com.ttn.automation.core.Core;
import com.ttn.automation.core.DriverSupport;
import com.ttn.automation.core.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SessionHandler {

    DriverSupport oDriverSupport = new DriverSupport();

    static Set<Cookie> cookieSet = new HashSet<Cookie>();

    public WebDriver getLoggedInSessionDriver(String rmn, String password, WebDriver driver) throws InterruptedException, AWTException {

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        //driver.get("https://tatasky-uat-web-app.videoready.tv/");
        driver.get(TestBase.URL);
        Thread.sleep(5000);
        while (!driver.findElement(By.xpath("//a[text()='DONE']")).isDisplayed()) {
        }
        driver.findElement(By.xpath("//a[text()='DONE']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[text()='Login']")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".loginRmn > li:nth-child(2) > a:nth-child(1) > img:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".paddingR5")).sendKeys(rmn);
        driver.findElement(By.xpath("//button[text()='Continue']")).click();
        driver.findElement(By.xpath("//a[text()='Login with My Tata Sky Password']")).click();
        /*driver.findElement(By.cssSelector(".form-group > input:nth-child(1)"))
                .sendKeys(password);*/
        driver.findElement(By.xpath("//input[@type='password'and@placeholder='My Tata Sky Password']"))
                .sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Continue']")).click();
        Thread.sleep(5000);

        cookieSet = driver.manage().getCookies();
        CookieHandler.setCookies(cookieSet);

        driver.close();
        return driver;
    }
}
