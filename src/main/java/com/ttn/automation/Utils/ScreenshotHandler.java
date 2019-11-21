package com.ttn.automation.Utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotHandler {

    public static void takeScreenShot(WebDriver driver, String filePath) throws IOException {
        TakesScreenshot oScreenShot = ((TakesScreenshot) driver);

        File image_file = oScreenShot.getScreenshotAs(OutputType.FILE);

        File destFile = new File(filePath);

        FileUtils.copyFile(image_file, destFile);

    }
}
