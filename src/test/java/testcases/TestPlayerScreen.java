package testcases;

import com.ttn.automation.Utils.CookieHandler;
import com.ttn.automation.Utils.Utility;
import com.ttn.automation.core.DataElements;
import com.ttn.automation.core.TestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.ttn.automation.PageModels.HomeScreenModel;
import com.ttn.automation.PageModels.SearchScreenPOM;
import com.ttn.automation.PageModels.PlayerScreenPOM;
import org.openqa.selenium.JavascriptExecutor;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertTrue;


public class TestPlayerScreen extends TestBase {

    Map<String, DataElements> dataElementMap = null;

    @BeforeClass
    public void dataSetter() {
        //System.out.println("Class name: " + this.getClass().getSimpleName());
        dataElementMap = getClassData(this.getClass().getSimpleName());
    }

    @Test

    public void testLiveChannelPlay(Method method) throws InterruptedException {
        HashMap<String, String> data = new HashMap<String, String>();

        //*********************** Setting the driver cokkies to maintain login session

        driver = CookieHandler.setDriverCookies(driver);
        driver.get("https://tatasky-uat-web-app.videoready.tv");

        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        //************** Click on search button
        Thread.sleep(10000);
        tapOnElement(HomeScreenModel.search_button());
        typeValueInTextBox(data.get("content_name"), HomeScreenModel.search_text_field());
        pressEnter();
        Thread.sleep(5000);

        //scrollToElement(SearchScreenPOM.find_On_Demand());
        tapOnElement(SearchScreenPOM.find_On_Demand());


        System.out.println("value found");

        Thread.sleep(5000);
        try {
            verifyElementPresent(PlayerScreenPOM.fetch_start_time());
            System.out.println("Element found ");
        } catch (Exception e) {
            verifyElementPresent(PlayerScreenPOM.continue_pop_up_overlay());
            tapOnElement(PlayerScreenPOM.start_button());
        }
        System.out.println(getText(PlayerScreenPOM.fetch_start_time()));


        // Hover to player screen

        //WebElement hover = getWebElement(PlayerScreenPOM.hover_player_screen());
        //Actions action = new Actions(driver);
        //action.moveToElement(hover).build().perform();

        //  WebElement hidden =  getWebElement(PlayerScreenPOM.hover_player_screen());

        // driver.execute_script("arguments[0].click();", hidden);

        hoverOverElement(PlayerScreenPOM.mouseHover());
        System.out.println("hover done");
        System.out.println(getText(PlayerScreenPOM.fetch_start_time()));


        Thread.sleep(2000);
        verifyElementPresent(PlayerScreenPOM.eventSlider());
        WebElement slider = getWebElement(PlayerScreenPOM.eventSlider());
        // Dimension sliderSize = slider.getSize();


        Actions builder = new Actions(driver);
        builder.moveToElement(slider)
                .click()
                .dragAndDropBy(slider, 50, 0)
                .build()
                .perform();
        Thread.sleep(2000);
        System.out.println(getText(PlayerScreenPOM.fetch_start_time()));


        hoverOverElement(PlayerScreenPOM.mouseHover());
        System.out.println("hover done");
        waitForSeconds(2);


        // Rewind Button
        //verifyElementPresent(PlayerScreenPOM.customRewindButton());

//
        tapOnElement(PlayerScreenPOM.customRewindButton());
        System.out.println("Rewind button pressed one time" + '\t' + "New time" + getText(PlayerScreenPOM.fetch_start_time()));
        waitForSeconds(2);
        tapOnElement(PlayerScreenPOM.customRewindButton());
        System.out.println("Rewind button pressed second time" + '\t' + "New time" + getText(PlayerScreenPOM.fetch_start_time()));


        // Forward Button

        verifyElementPresent(PlayerScreenPOM.customForwardButton());

        waitForSeconds(2);
        tapOnElement(PlayerScreenPOM.customForwardButton());
        System.out.println("Forward button pressed one time" + '\t' + "New time" + getText(PlayerScreenPOM.fetch_start_time()));
        waitForSeconds(2);
        tapOnElement(PlayerScreenPOM.customForwardButton());
        System.out.println("Forward button pressed second time" + '\t' + "New time" + getText(PlayerScreenPOM.fetch_start_time()));

        // Full Screen

        verifyElementPresent(PlayerScreenPOM.switchToFullScreen());
        tapOnElement(PlayerScreenPOM.switchToFullScreen());


/*        waitForSeconds(2);
        //hoverOverElement(PlayerScreenPOM.mouseHover());
        hoverOverElement(PlayerScreenPOM.mouseHoverHidden());
        tapOnElement(PlayerScreenPOM.switchToNormalScreen());*/

        // press escape
        waitForSeconds(2);
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).perform();

        // Pause the content

        verifyElementPresent(PlayerScreenPOM.pauseTheContent());

        System.out.println("Time before pause " + '\t' + getText(PlayerScreenPOM.fetch_start_time()));

        tapOnElement(PlayerScreenPOM.pauseTheContent());

        waitForSeconds(1);

        //hoverOverElement(PlayerScreenPOM.mouseHover());

        tapOnElement(PlayerScreenPOM.playTheContent());

        waitForSeconds(3);

        System.out.println("Time After play " + '\t' + getText(PlayerScreenPOM.fetch_start_time()));

    }

}



