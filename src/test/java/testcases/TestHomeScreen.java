    package testcases;


import com.relevantcodes.extentreports.LogStatus;
import com.ttn.automation.PageModels.*;
import com.ttn.automation.Utils.CookieHandler;
import com.ttn.automation.Utils.Utility;
import com.ttn.automation.core.Data;
import com.ttn.automation.core.DataElements;
import com.ttn.automation.core.TestBase;
import com.ttn.automation.model_classes.homescreen.HomeScreenMainModel;
import com.ttn.automation.model_classes.see_all.SeeAllApiResponseModel;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import javax.rmi.CORBA.Util;
import java.util.*;


public class TestHomeScreen extends TestBase {

    Map<String, DataElements> dataElementMap = null;
    Data data = new Data();
    boolean testResult = false;
    private final static Logger logger = LoggerFactory.getLogger(TestHomeScreen.class);

    @BeforeClass
    public void dataSetter() {
        dataElementMap = getClassData(this.getClass().getSimpleName());
    }


    @Test(dataProvider = "HomeScreen_Dataprovider")
    public void testHomescreenRails(String screenName) throws InterruptedException {

        String new_text = "0";
        String last_text = null;
        Set<String> listOfRailName_frontend = new HashSet<>();
        Set<String> listOfRailName_api = new HashSet<>();
        StringBuffer frontend_rail_name = new StringBuffer();
        StringBuffer backend_rail_name = new StringBuffer();


        // Getting rail names from homescreen api
        HomeScreenMainModel oHomeScreenMainModel = Utility.homeScreenAPI(screenName, testReport);


        for (int i = 0; i < oHomeScreenMainModel.getData().getItems().size()
                ; i++) {
            if (oHomeScreenMainModel.getData().getItems().get(i).getSectionType().toLowerCase().contains("rail")) {
                listOfRailName_api.add(oHomeScreenMainModel.getData().
                        getItems().get(i).getTitle());
                backend_rail_name.append(oHomeScreenMainModel.getData().
                        getItems().get(i).getTitle() + ", ");
            }
        }
        testReport.log(LogStatus.INFO, screenName +
                " API RAIL NAME :-- " + backend_rail_name);

        tapOnElement(HomeScreenModel.skip_button());

        if (screenName.toLowerCase().equalsIgnoreCase("homepage")) {

            clickUsingJavascript(HomeScreenModel.get_Home_button());
        } else if (screenName.toLowerCase().equalsIgnoreCase("live_home")) {
            clickUsingJavascript(HomeScreenModel.get_LiveTV_button());
        } else if (screenName.toLowerCase().equalsIgnoreCase("web_shorts_home")
                || screenName.toLowerCase().equalsIgnoreCase("movies_home")
                || screenName.toLowerCase().equalsIgnoreCase("tv_shows_home")) {

            clickUsingJavascript(HomeScreenModel.get_OnDemand_button());
            Thread.sleep(3000);
            if (screenName.toLowerCase().equalsIgnoreCase("tv_shows_home")) {
                clickUsingJavascript(OnDemandPOM.tv_shows_on_demand());
            } else if (screenName.toLowerCase().equalsIgnoreCase("movies_home")) {
                clickUsingJavascript(OnDemandPOM.movies_on_demand());
            } else if (screenName.toLowerCase().equalsIgnoreCase("web_shorts_home"))
                clickUsingJavascript(OnDemandPOM.shorts_on_demand());
        }


        // Thread.sleep used for letting the home screen to load completely
        Thread.sleep(5000);

        // Scroll to bottom of the screen
        testReport.log(LogStatus.INFO, "Scrolling through the " + screenName + " and getting the frontend rails. ");
        while (!new_text.equalsIgnoreCase(last_text))

        {
            List<WebElement> listOfRails = getWebElements(HomeScreenModel.get_spacial_rail());
            listOfRails.addAll(getWebElements(HomeScreenModel.get_normal_rail()));
            last_text = listOfRails.get(listOfRails.size() - 1).getText();
            new_text = last_text;

            // save all rail names in list
            for (WebElement element : listOfRails) {
                listOfRailName_frontend.add(element.getText());
            }

            scrollToElement(listOfRails.get(listOfRails.size() - 1));
            Thread.sleep(2000);

            //getting the rows again to avoid stale element reference exception
            listOfRails = getWebElements(HomeScreenModel.get_spacial_rail());
            listOfRails.addAll(getWebElements(HomeScreenModel.get_normal_rail()));
            last_text = listOfRails.get(listOfRails.size() - 1).getText();
        }

        // Adding the rail name to test report
        for (
                String rn : listOfRailName_frontend)
            frontend_rail_name.append(rn + ", ");

        testReport.log(LogStatus.INFO, screenName +
                " RAIL NAMES :-- " + frontend_rail_name);

        Set<String> listOfRailsNotPresent = Utility.verifyListsAreSame(listOfRailName_frontend, listOfRailName_api);

        if (listOfRailsNotPresent.size() == 0)

        {
            testReport.log(LogStatus.INFO, "The rails in the API and web Application are same !!");
            testResult = true;
        } else
            for (
                    String railName : listOfRailsNotPresent)

            {
                testReport.log(LogStatus.INFO, railName + " - NOT PRESENT");
            }

        if (testResult == true)
            testReport.log(LogStatus.INFO, "The FRONTEND and BACKEND rail name for " + screenName + " are SAME, Test cases PASSED!!");
        else
            testReport.log(LogStatus.INFO, "The FRONTEND and BACKEND rail name for " + screenName + " are NOT SAME, Test cases FAILED!!");
        Assert.assertTrue(true);

    }

    @Test(dataProvider = "HomeScreen_Dataprovider1")
    public void testSeeAllRailAndScreenData(String screenName) throws InterruptedException {

        StringBuffer content_frontend = new StringBuffer();
        StringBuffer content_backend = new StringBuffer();
        Set<String> listOfContentFrontEnd = new HashSet<>();
        Set<String> listOfContentBackEnd = new HashSet<>();
        String firstRailNameBackEnd = null;
        int railId = -1;

        /*tapOnElement(LoginPageModel.get_done_button());

        driver.get(URL + testProp.get(screenName));*/

        //**************************
        tapOnElement(HomeScreenModel.skip_button());

        if (screenName.toLowerCase().equalsIgnoreCase("homepage")) {

            clickUsingJavascript(HomeScreenModel.get_Home_button());
        } else if (screenName.toLowerCase().equalsIgnoreCase("live_home")) {
            clickUsingJavascript(HomeScreenModel.get_LiveTV_button());
        } else if (screenName.toLowerCase().equalsIgnoreCase("web_shorts_home")
                || screenName.toLowerCase().equalsIgnoreCase("movies_home")
                || screenName.toLowerCase().equalsIgnoreCase("tv_shows_home")) {

            clickUsingJavascript(HomeScreenModel.get_OnDemand_button());
            Thread.sleep(3000);
            if (screenName.toLowerCase().equalsIgnoreCase("tv_shows_home")) {
                clickUsingJavascript(OnDemandPOM.tv_shows_on_demand());
            } else if (screenName.toLowerCase().equalsIgnoreCase("movies_home")) {
                clickUsingJavascript(OnDemandPOM.movies_on_demand());
            } else if (screenName.toLowerCase().equalsIgnoreCase("web_shorts_home"))
                clickUsingJavascript(OnDemandPOM.shorts_on_demand());
        }
        //***************************

        Thread.sleep(5000);

        List<WebElement> listOfRails = getWebElements(HomeScreenModel.get_normal_rail());
        String firstRailNameFrontEnd = listOfRails.get(0).getText();

        // hitting the home screen api to get first rail id with rail title
        HomeScreenMainModel oHomeScreenMainModel =
                Utility.homeScreenAPI(screenName, testReport);

        // get the rail id corresponding to first rail
        for (int i = 0; i < oHomeScreenMainModel.getData().getItems().size(); i++) {
            if (oHomeScreenMainModel.getData().getItems().get(i).getTitle().
                    equalsIgnoreCase(firstRailNameFrontEnd)) {
                railId = oHomeScreenMainModel.getData().getItems().get(i).getId();
            }
        }


        // Getting response for see all api
        SeeAllApiResponseModel oSeeAllApiResponseModel
                = Utility.seeAllRailResponse(railId, testReport);

        for (int j = 0; j < oSeeAllApiResponseModel
                .getData().getContentList().size(); j++) {
            if (oSeeAllApiResponseModel
                    .getData().getContentList().get(j).getAllowedOnBrowsers() == true) {
                listOfContentBackEnd.add(oSeeAllApiResponseModel
                        .getData().getContentList().get(j).getTitle());
                content_backend.append(oSeeAllApiResponseModel
                        .getData().getContentList().get(j).getTitle() + ", ");
            }
        }

        // print the back end content in report
        testReport.log(LogStatus.INFO, "Content in API is :-- " + content_backend);

        try {
            driver.findElement(By.xpath("//h3[text()='" + firstRailNameFrontEnd + "']" +
                    "/following-sibling::a")).click();
        } catch (Exception exception) {
            clickUsingJavascript(driver.findElement(By.xpath("//h3[text()='" + firstRailNameFrontEnd + "']" +
                    "/following-sibling::a")));
        }

        Thread.sleep(5000);

        List<WebElement> listOfContent;

        listOfContent = driver.findElements(By.xpath("//h5"));

        for (WebElement element : listOfContent) {
            listOfContentFrontEnd.add(element.getText());
            content_frontend.append(element.getText() + ", ");
        }
        testReport.log(LogStatus.INFO, "Getting the content for rail << " + firstRailNameFrontEnd + " >> from the Application");
        testReport.log(LogStatus.INFO, "The content on App is :-- " + content_frontend);

        Set<String> listOfContentNotPresent =
                Utility.verifyListsAreSame(listOfContentBackEnd, listOfContentFrontEnd);
        // reporting the test result
        if (listOfContentNotPresent.size() == 0) {
            testReport.log(LogStatus.INFO, "All the content from the API and frontend matches");
            testResult = true;
        } else
            for (String contentName : listOfContentNotPresent) {
                testReport.log(LogStatus.INFO, contentName + "- NOT PRESENT");
            }

        if (testResult == true)
            testReport.log(LogStatus.INFO, "The FRONTEND and BACKEND content for rail- << " + firstRailNameFrontEnd + " >> are same, Test case PASSED!!");
        else
            testReport.log(LogStatus.INFO, "The FRONTEND and BACKEND content for rail- << " + firstRailNameFrontEnd + " >> are NOT same, Test case FAILED!!");

        Assert.assertTrue(testResult);
    }

    @AfterMethod
    public void tearDownTest() {
        testResult = false;
    }

    @DataProvider(name = "HomeScreen_Dataprovider1")
    public Object[][] dataProvider1() {
        return new Object[][]{{"HOMEPAGE"}, {"LIVE_HOME"}, {"WEB_SHORTS_HOME"}
                , {"MOVIES_HOME"}, {"TV_SHOWS_HOME"}};
    }

         /*@DataProvider(name = "HomeScreen_Dataprovider1")
         public Object[][] dataProvider1() {
         return new Object[][]{{"LIVE_HOME"}};
         }*/

    /*@DataProvider(name = "HomeScreen_Dataprovider")
    public Object[][] dataProvider() {
        return new Object[][]{{"TV_SHOWS_HOME"}};
    }*/

    @DataProvider(name = "HomeScreen_Dataprovider")
    public Object[][] dataProvider() {
        return new Object[][]{{"HOMEPAGE"}, {"LIVE_HOME"}, {"WEB_SHORTS_HOME"}
                , {"MOVIES_HOME"}, {"TV_SHOWS_HOME"}};
    }

}
