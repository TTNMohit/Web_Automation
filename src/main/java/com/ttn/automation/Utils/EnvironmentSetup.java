package com.ttn.automation.Utils;

import com.jayway.restassured.response.Cookies;
import com.ttn.automation.core.TestBase;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class EnvironmentSetup {


    static ChromeOptions chrome_options = new ChromeOptions();
    static FirefoxBinary firefoxBinary = new FirefoxBinary();
    static FirefoxOptions firefoxOptions = new FirefoxOptions();
    static FirefoxProfile custom_profile = new FirefoxProfile();
    final static Logger logger = LoggerFactory.getLogger(EnvironmentSetup.class);
    static String browserName = System.getProperty("browser");
    static String browserType = System.getProperty("browser_type");
    static String osName = System.getProperty("os.name");
    static File baseDirectory = new File(".");
    static DesiredCapabilities cap = new DesiredCapabilities();

    public static String initiateURL(Properties testProperty) {
        String URL = null;
        if (System.getProperty("env") == null) {
            URL = testProperty.getProperty("WEB_UAT_URL");
            Utility.setApiBaseUrl(testProperty.getProperty("API_UAT_URL"));
            /*URL = "https://tatasky-uat-web-app.videoready.tv/";
            Utility.setApiBaseUrl("https://tatasky-uat-kong.videoready.tv/");*/
        } else if (System.getProperty("env").equalsIgnoreCase("UAT")) {
            URL = testProperty.getProperty("WEB_UAT_URL");
            Utility.setApiBaseUrl(testProperty.getProperty("API_UAT_URL"));
            /*URL = "https://tatasky-uat-web-app.videoready.tv/";
            Utility.setApiBaseUrl("https://tatasky-uat-kong.videoready.tv/");*/
        } else if (System.getProperty("env").equalsIgnoreCase("PROD")) {
            /*URL = "https://watch.tatasky.com/";
            Utility.setApiBaseUrl("https://kong-tatasky.videoready.tv/");*/
            URL = testProperty.getProperty("WEB_PROD_URL");
            Utility.setApiBaseUrl(testProperty.getProperty("API_PROD_URL"));
        }
        return URL;
    }


    // check for OS
    // check for browser, check for NULL values
    // check for browser type
    public static WebDriver initiateWebDriver(WebDriver driver) throws IOException {

        String driverPath = baseDirectory.getCanonicalPath()
                + File.separator
                + "drivers"
                + File.separator;


        if (osName.toLowerCase().contains("linux")) {
            if (browserName == null) {
            	  System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
                  if (browserType.toLowerCase().contains("headless")) {
                      chrome_options.addArguments("--headless");
                      driver = new ChromeDriver(chrome_options);
                  } else if (browserType.toLowerCase().equalsIgnoreCase("gui")) {
                      driver = new ChromeDriver();
                  }
            }

            /*// initiate the docker tests
            else if (System.getProperty("docker").equalsIgnoreCase("true")) {
                if (browserName.toLowerCase().contains("firefox")) {
                    cap.setBrowserName("firefox");
                } else if (browserName.equalsIgnoreCase("chrome")) {
                    cap.setBrowserName("chrome");

                }
                System.out.println("remote webdriver initalized successfully");*/
                /*cap.setPlatform(Platform.LINUX);
                cap.setCapability("recordVideo", true);
                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);}*/
            else if (browserName.toLowerCase().contains("firefox")) {
                System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver");
                if (browserType.toLowerCase().contains("headless")) {
                    firefoxBinary = new FirefoxBinary();
                    firefoxBinary.addCommandLineOptions("--headless");
                    firefoxOptions.setBinary(firefoxBinary);
                    driver = new FirefoxDriver(firefoxOptions);
                } else if (browserType.toLowerCase().equalsIgnoreCase("gui")) {
                    driver = new FirefoxDriver();
                }
            } else if (browserName.toLowerCase().contains("chrome")) {
                System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
                if (browserType.toLowerCase().contains("headless")) {
                    chrome_options.addArguments("--headless");
                    driver = new ChromeDriver(chrome_options);
                } else if (browserType.toLowerCase().equalsIgnoreCase("gui")) {
                    driver = new ChromeDriver();
                }
            }

        } else if (osName.toLowerCase().contains("window")) {
            if (browserName == null) {
            	 System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
                
                     driver = new ChromeDriver();
                
            } else if (browserName.toLowerCase().contains("firefox")) {
                System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
                if (browserType.toLowerCase().contains("headless")) {
                    firefoxBinary = new FirefoxBinary();
                    firefoxBinary.addCommandLineOptions("--headless");
                    firefoxOptions.setBinary(firefoxBinary);
                    driver = new FirefoxDriver(firefoxOptions);
                } else if (browserType.toLowerCase().equalsIgnoreCase("gui")) {
                    driver = new FirefoxDriver();
                }
            } else if (browserName.toLowerCase().contains("chrome")) {
                System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
                if (browserType.toLowerCase().contains("headless")) {
                    chrome_options.addArguments("--headless");
                    driver = new ChromeDriver(chrome_options);
                } else if (browserType.toLowerCase().equalsIgnoreCase("gui")) {
                    driver = new ChromeDriver();
                }
            }
        }
        if (driver != null) {
            logger.info("The driver has been initiated Sucessfully");
        } else
            logger.error("The driver is NULL, driver not initiated!");
        driver.manage().window().maximize();
        return driver;
    }

    public static WebDriver initiateDriver(WebDriver driver) {
        custom_profile.setPreference("dom.webnotifications.enabled", false);
        try {
            if (browserName == null || browserType == null) {
                logger.info("No browser params found, initializing firefox browser");
                System.setProperty("webdriver.gecko.driver", "drivers" +
                        File.separator + "geckodriver.exe");

                driver = new FirefoxDriver();
                logger.info("Initialized firefox browser successfully");
            } else {
                if (browserName.equalsIgnoreCase("firefox")) {
                    System.setProperty("webdriver.gecko.driver",
                            "drivers" + File.separator + "geckodriver.exe");

                    logger.info("Initializing firefox driver ");
     /*               if (browserType.equalsIgnoreCase("H")) {
                        firefoxBinary.addCommandLineOptions("--headless");
                        firefoxOptions.setBinary(firefoxBinary);
                        firefoxOptions.setProfile(custom_profile);
                        driver = new FirefoxDriver(firefoxOptions);
                        logger.info("Firefox driver - headless, initialized successfully !!");
                    }else {
                        driver = new FirefoxDriver(custom_profile);
                        logger.info("Firefox driver - GUI, initialized successfully !!");
                    }*/
                } else if (browserName.equalsIgnoreCase("chrome")) {
                    System.setProperty("webdriver.chrome.driver", "drivers" + File.separator + "chromedriver.exe");
                    logger.info("Initializing chrome driver");
                    Map prefs = new HashMap();
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    ChromeOptions chrome_options = new ChromeOptions();
                    chrome_options.setExperimentalOption("prefs", prefs);
                    if (browserType.equalsIgnoreCase("H")) {
                        //ChromeOptions chromeOptions = new ChromeOptions();
                        chrome_options.addArguments("--headless");
                        driver = new ChromeDriver(chrome_options);
                        logger.info("Chrome driver - headless, initialized successfully !!");
                    } else {
                        driver = new ChromeDriver(chrome_options);
                        logger.info("Chrome driver - GUI, initialized successfully !!");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Could not instantiate the web browser");
        }
        driver.manage().window().maximize();
        return driver;
    }
}


