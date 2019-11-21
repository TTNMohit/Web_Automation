package com.ttn.automation.Utils;


import com.ttn.automation.core.DriverSupport;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.HashSet;
import java.util.Set;

public class CookieHandler {

    public static Set<Cookie> cookieSet = new HashSet<Cookie>();

    public static void setCookies(Set<Cookie> cookies) {
        cookieSet.addAll(cookies);
    }

    public static WebDriver setDriverCookies(WebDriver driver) {
        for (Cookie cookie : cookieSet) {
            driver.manage().addCookie(cookie);
        }
        return driver;
    }
}
