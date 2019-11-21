package testcases;

import com.relevantcodes.extentreports.LogStatus;
import com.ttn.automation.PageModels.*;
import com.ttn.automation.Utils.CookieHandler;
import com.ttn.automation.Utils.Utility;
import com.ttn.automation.core.DataElements;
import com.ttn.automation.core.TestBase;
import com.ttn.automation.model_classes.continue_watching.response.ContinueWatchingResponseModel;
import com.ttn.automation.model_classes.login_management.response.LoginWithSidPasswordResponseModel;
import com.ttn.automation.model_classes.profile_management.response.ListProfileResponseModel;
import com.ttn.automation.model_classes.profile_management.response.Profile;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class TestContinueWatching extends TestBase {

    Map<String, DataElements> dataElementMap = null;

    @BeforeClass
    public void dataSetter() {
        dataElementMap = getClassData(this.getClass().getSimpleName());
    }

    @Test
    public void testContinueWatchingContent(Method method) throws InterruptedException {

        HashMap<String, String> data = new HashMap<String, String>();
        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        testReport.log(LogStatus.INFO, "Navigating to HomeScreen URL");
        //*********************** Setting the driver cokkies to maintain login session

        testReport.log(LogStatus.INFO, "Setting up the login session using cookies...");
        driver = CookieHandler.setDriverCookies(driver);
        driver.get(URL);

        Thread.sleep(10000);

        testReport.log(LogStatus.INFO, "Scrolling through home screen to find the continue watching rail...");
        List<WebElement> railElements = new LinkedList<>();
        boolean isContinueWatchingPresent = false;
        WebElement continueWatchingElement = null;
        int pagination = -1;
        while (isContinueWatchingPresent != true && pagination < 10) {
            railElements = getWebElements(HomeScreenModel.get_normal_rail());
            for (WebElement element : railElements) {
                if (element.getText().toLowerCase().contains("continue")) {
                    isContinueWatchingPresent = true;
                    continueWatchingElement = element;
                    break;
                }
            }

            scrollToElement(continueWatchingElement);
            pagination++;
        }
        try {
            testReport.log(LogStatus.INFO, "Clicking on the see all link corresponding to " +
                    "the continue watching rail");
            driver.findElement(By.xpath("//h3[text()='" + continueWatchingElement.getText() + "']" +
                    "/following-sibling::a")).click();
        } catch (Exception exception) {
            testReport.log(LogStatus.INFO, "Could not click on the link, using javascript to click on the see all link");
            clickUsingJavascript(driver.findElement(By.xpath("//h3[text()='" + continueWatchingElement.getText() + "']" +
                    "/following-sibling::a")));
        }
        Thread.sleep(5000);
        testReport.log(LogStatus.INFO, "" +
                "Getting the Continue watching content from the Continue watching screen");
        List<String> contentOnContinueWatchingScreen = new LinkedList<>();
        List<WebElement> continueWatchingContentWebElements =
                getWebElements(GenericPOM.continue_watching_content_seeall_screen());
        StringBuilder continueWatchingContentOnApp = new StringBuilder("");
        for (WebElement element : continueWatchingContentWebElements) {
            contentOnContinueWatchingScreen.add(element.getText());
            continueWatchingContentOnApp.append(element.getText()).append(", ");
        }

        testReport.log(LogStatus.INFO, "Continue Watching content on app - " + continueWatchingContentOnApp);
        List<String> contentOfContinueWatching_Api = new LinkedList<>();


        // get the content from continue watching api
        testReport.log(LogStatus.INFO, "Getting the auth Token from the Login API");
        String authToken = "";
        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = Utility.loginApiWithUsernamePassword(data.get("sid"), data.get("password"));
        authToken = oLoginWithSidPasswordResponseModel.getData().getAccessToken();
        testReport.log(LogStatus.INFO, "Auth Token is - " + authToken);
        ListProfileResponseModel oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));
        String profileId = "";
        for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
            profileId = profile.getId();
            break;
        }
        ContinueWatchingResponseModel oContinueWatchingResponseModel
                = Utility.continueWatchingContent(data.get("sid"), authToken, profileId, Boolean.getBoolean(data.get("isContinueWatching")), Integer.parseInt(data.get("Max")));

        StringBuilder continueWatchingOnApi = new StringBuilder("");
        for (int i = 0; i < oContinueWatchingResponseModel.getData().getContentList().size(); i++) {
            contentOfContinueWatching_Api.add(oContinueWatchingResponseModel.getData().getContentList().get(i).getTitle());
            continueWatchingOnApi.append(oContinueWatchingResponseModel.getData().getContentList().get(i).getTitle()).append(", ");
        }

        testReport.log(LogStatus.INFO, "The continue watching content from API is - " + contentOfContinueWatching_Api);
        //Utility.verifyListsAreSame(contentOfContinueWatching_Api, contentOnContinueWatchingScreen);
    }

    @Test
    public void testViewingHistoryContent(Method method) throws InterruptedException {
        HashMap<String, String> data = new HashMap<String, String>();
        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        testReport.log(LogStatus.INFO, "Navigating to Home screen URL");
        //*********************** Setting the driver cokkies to maintain login session
        driver = CookieHandler.setDriverCookies(driver);
        driver.get(URL);

        Thread.sleep(10000);

        tapOnElement(HomeScreenModel.get_user_logo());

        testReport.log(LogStatus.INFO, "Navigating to Viewing history screen");
        tapOnElement(NavigationDrawerPOM.get_viewing_history());

        //Thread.sleep(5000);

        String last_content = "";
        String new_last_content = " ";
        List<WebElement> listOfViewingHistoryContent = new LinkedList<>();
        HashSet<String> setOfViewingHistory = new HashSet<>();
        while (!new_last_content.equalsIgnoreCase(last_content)) {
            new_last_content = last_content;
            listOfViewingHistoryContent
                    = getWebElements(ViewingHistoryPOM.viewing_history_content());
            scrollToElement(listOfViewingHistoryContent.get(listOfViewingHistoryContent.size() - 1));
            Thread.sleep(1500);
            listOfViewingHistoryContent = getWebElements(ViewingHistoryPOM.viewing_history_content());
            last_content = listOfViewingHistoryContent.get(listOfViewingHistoryContent.size() - 1).getText();
        }

        StringBuffer viewingHistoryOnApp = new StringBuffer("");
        for (WebElement element : listOfViewingHistoryContent) {
            setOfViewingHistory.add(element.getText());
            viewingHistoryOnApp.append(element.getText()).append(", ");
        }
        testReport.log(LogStatus.INFO, "Viewing history on app is - " + viewingHistoryOnApp);

        String authToken = "";
        testReport.log(LogStatus.INFO, "Getting the Auth Token...");
        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = Utility.loginApiWithUsernamePassword(data.get("sid"), data.get("password"));

        authToken = oLoginWithSidPasswordResponseModel.getData().getAccessToken();
        testReport.log(LogStatus.INFO, "Auth token is - " + authToken);
        ListProfileResponseModel oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));
        String profileId = "";
        for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
            profileId = profile.getId();
            break;
        }
        ContinueWatchingResponseModel oContinueWatchingResponseModel
                = Utility.continueWatchingContent(data.get("sid"), authToken, profileId, Boolean.getBoolean(data.get("isContinueWatching")), Integer.parseInt(data.get("Max")));


        Set<String> setOfViewingHistory_api = new HashSet<>();

        StringBuilder viewingHistoryOnApi = new StringBuilder("");
        for (int j = 0; j < oContinueWatchingResponseModel.getData().getContentList().size(); j++) {
            setOfViewingHistory_api.add(oContinueWatchingResponseModel.getData().getContentList().get(j).getTitle());
            viewingHistoryOnApi.append(oContinueWatchingResponseModel.getData().getContentList().get(j).getTitle()).append(", ");
        }

        testReport.log(LogStatus.INFO, "Viewing History from API response is - " + viewingHistoryOnApi);

        Set<String> set = Utility.verifyListsAreSame(setOfViewingHistory_api, setOfViewingHistory);
    }

    @Test
    public void testDeleteViewingHistoryContent(Method method) throws InterruptedException {

        HashMap<String, String> data = new HashMap<String, String>();
        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        testReport.log(LogStatus.INFO, "Navigating to Home screen ");
        //*********************** Setting the driver cokkies to maintain login session
        driver = CookieHandler.setDriverCookies(driver);
        driver.get(URL);

        Thread.sleep(5000);

        System.out.println(verifyPageLoaded());

        tapOnElement(HomeScreenModel.get_user_logo());

        testReport.log(LogStatus.INFO, "Navigating to Viewing History screen");
        tapOnElement(NavigationDrawerPOM.get_viewing_history());

        verifyPageLoaded();
        Thread.sleep(3000);

        List<WebElement> listViewingHistory = new LinkedList<>();

        try {
            listViewingHistory =
                    getWebElements(ViewingHistoryPOM.viewing_history_content());
            testReport.log(LogStatus.INFO, "Validating if content is present in viewing history");
        } catch (Exception e) {
            testReport.log(LogStatus.INFO, "No content present in viewing history, Test case failed. ");
            Assert.assertTrue(false);
        }

        String viewingHistoryContent = "";

        viewingHistoryContent = listViewingHistory.get(1).getText();

        testReport.log(LogStatus.INFO, "Deleting the content - " + viewingHistoryContent + "...");
        // deleting the viewing history content
        tapOnElement(driver.findElement(By.xpath("//h2[text()='" + viewingHistoryContent + "']//following-sibling::div//button")));

        tapOnElement(ProfileManagementPOM.get_yes_button_on_dialog());

        Thread.sleep(2000);

        testReport.log(LogStatus.INFO, "Verifying content - " + viewingHistoryContent + " is deleted successfully");
        listViewingHistory = getWebElements(ViewingHistoryPOM.viewing_history_content());

        boolean isDeleted = true;
        for (WebElement element : listViewingHistory) {
            if (element.getText().toLowerCase().equalsIgnoreCase(viewingHistoryContent)) {
                isDeleted = false;
                break;
            }
        }

        if (isDeleted == false) {
            testReport.log(LogStatus.INFO, "Content - " + viewingHistoryContent + " NOT deleted successfully");
        } else if (isDeleted == true) {
            testReport.log(LogStatus.INFO, "Content - " + viewingHistoryContent + " deleted successfully");
        }
    }

    @Test
    public void testContinueWatchingUpdateContent(Method method) throws InterruptedException {

        HashMap<String, String> data = new HashMap<String, String>();
        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        testReport.log(LogStatus.INFO, "Navigating to HomeScreen URL");
        //*********************** Setting the driver cokkies to maintain login session
        driver = CookieHandler.setDriverCookies(driver);
        driver.get(URL);

        Thread.sleep(10000);

        testReport.log(LogStatus.INFO, "Scrolling to continue watching rail and playing content");
        scrollToElement(GenericPOM.continue_watching_rail());

        String continueWatchingContent = getWebElement("//h3[text()" +
                "='Continue Watching']/../../..//following-sibling::div" +
                "//div[@class='owl-item active'][2]//h5", "xpath").getText();

        testReport.log(LogStatus.INFO, "Playing the second content from the continue watching rail");
        tapOnElement(driver.findElement(By.xpath("//h3[text()='Continue Watching']" +
                "/../../..//following-sibling::div//div[@class='owl-item active'][2]")));

        tapOnElement(PlayerScreenPOM.start_button());

        waitForSeconds(15);

        navigateBack();

        waitForSeconds(1);

        // SCROLL TO TOP


        String contentOnFirstPosition = getWebElement("//h3[text()" +
                "='Continue Watching']/../../..//following-sibling::div" +
                "//div[@class='owl-item active'][1]//h5", "xpath").getText();
        testReport.log(LogStatus.INFO, "Navigating back and verifying the content on the first position");
        boolean testCaseResult = false;
        if (contentOnFirstPosition.equalsIgnoreCase(continueWatchingContent)) {
            testCaseResult = true;
            testReport.log(LogStatus.INFO, "The content is updated successfully, test case passed!!");
        } else {
            testCaseResult = false;
            testReport.log(LogStatus.INFO, "Content not updated successfully, test case failed!");
        }

        Assert.assertTrue(testCaseResult);


    }
}
