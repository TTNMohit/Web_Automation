package testcases;

import com.microsoft.schemas.office.x2006.encryption.CTKeyEncryptor;
import com.relevantcodes.extentreports.LogStatus;
import com.ttn.automation.PageModels.FavoriteScreen;
import com.ttn.automation.PageModels.HomeScreenModel;
import com.ttn.automation.PageModels.PlayerScreenPOM;
import com.ttn.automation.Utils.CookieHandler;
import com.ttn.automation.Utils.Utility;
import com.ttn.automation.core.DataElements;
import com.ttn.automation.core.TestBase;
import com.ttn.automation.model_classes.favorite.FavoriteListingResponseModel;
import com.ttn.automation.model_classes.login_management.response.LoginWithSidPasswordResponseModel;
import com.ttn.automation.model_classes.profile_management.response.ListProfileResponseModel;
import com.ttn.automation.model_classes.profile_management.response.Profile;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class TestProfileFavoriteContent extends TestBase {

    Map<String, DataElements> dataElementMap = null;

    @BeforeClass
    public void dataSetter() {
        dataElementMap = getClassData(this.getClass().getSimpleName());
    }

    @Test(priority = 0)
    public void testMarkContentFavorite(Method method) throws InterruptedException {

        HashMap<String, String> data = new HashMap<String, String>();
        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        //*********************** Setting the driver cokkies to maintain login session
        driver = CookieHandler.setDriverCookies(driver);
        driver.get(URL + HomeScreenModel.pageUrl);
        Thread.sleep(10000);

        testReport.log(LogStatus.INFO, "Searching for content - " + data.get("content_name") + " on application...");
        tapOnElement(HomeScreenModel.search_button());

        typeValueInTextBox(data.get("content_name"), HomeScreenModel.search_text_field());

        pressEnter();

        Thread.sleep(5000);

        testReport.log(LogStatus.INFO, "Scrolling to channel rail on search screen and finding - " + data.get("content_name"));
        scrollToElement(driver.findElement(By.xpath("//h3[contains(text(),'Channel')]")));

        List<WebElement> contentName =
                driver.findElements(By.xpath("//h3[contains(text(),'Channel')]/../../../following-sibling::div//h5"));

        for (WebElement element : contentName) {
            testReport.log(LogStatus.INFO, "Tapping on content - " + data.get("content_name") + " on rail...");
            if (element.getText().toLowerCase().contains("history")) {
                clickUsingJavascript(element);
                break;
            }
        }
        Thread.sleep(5000);

        try {
            scrollToElement(PlayerScreenPOM.favorite_button_unmarked());
        } catch (Exception e) {
            scrollToElement(PlayerScreenPOM.favorite_button_marked());
            tapOnElement(PlayerScreenPOM.favorite_button_marked());
        }

        Thread.sleep(1000);
        testReport.log(LogStatus.INFO, "Marking the content as favorite");
        tapOnElement(PlayerScreenPOM.favorite_button_unmarked());

        driver.get(URL + FavoriteScreen.pageUrl);

        // hitting the favorite listing api to get data
        String authToken = "";
        String profileId = "";

        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = Utility.loginApiWithUsernamePassword(data.get("sid"), data.get("password"));
        authToken = oLoginWithSidPasswordResponseModel.getData().getAccessToken();
        ListProfileResponseModel oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));

        for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
            profileId = profile.getId();
            break;
        }

        FavoriteListingResponseModel oFavoriteListingResponseModel
                = Utility.favoriteListingApi(authToken, profileId);
        boolean isContentPresentOnApi = false;

        // checking the content name in the favorite listing api
        testReport.log(LogStatus.INFO, "Verifying the profile API to verify content added successfully");
        for (int i = 0; i < oFavoriteListingResponseModel.getData().getList().size(); i++) {
            if (oFavoriteListingResponseModel.getData().getList().get(i).getTitle().equalsIgnoreCase(data.get("content_name"))) {
                isContentPresentOnApi = true;
                testReport.log(LogStatus.INFO, "Content found on application");
                break;
            }
        }
        if (isContentPresentOnApi == false) {
            testReport.log(LogStatus.INFO, "Content NOT found on Application!!");
        }
        // getting the favorite titles present on watchlist
        testReport.log(LogStatus.INFO, "Verifying the application, if content - " + data.get("content_name") + " is added successfully");
        boolean isContentPresentOnApp = false;
        String titleName = "";
        List<WebElement> favoriteScreenTitles = getWebElements(FavoriteScreen.titles_on_favorite_screen());
        for (WebElement element : favoriteScreenTitles) {
            titleName = element.getAttribute("innerHTML");
            if (titleName.equalsIgnoreCase(data.get("content_name"))) {
                isContentPresentOnApp = true;
                testReport.log(LogStatus.INFO, "Content found on the API");
                break;
            }
        }
        if (isContentPresentOnApi == false) {
            testReport.log(LogStatus.INFO, "Content not found in API");
        }
    }

    @Test(priority = 1)
    public void testFavoriteListing(Method method) throws InterruptedException {
        HashMap<String, String> data = new HashMap<String, String>();
        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        //*********************** Setting the driver cokkies to maintain login session
        driver = CookieHandler.setDriverCookies(driver);
        driver.get(URL + FavoriteScreen.pageUrl);
        Thread.sleep(10000);

        String authToken = "";
        String profileId = "";

        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = Utility.loginApiWithUsernamePassword(data.get("sid"), data.get("password"));
        authToken = oLoginWithSidPasswordResponseModel.getData().getAccessToken();

        ListProfileResponseModel oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));
        for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
            profileId = profile.getId();
            break;
        }
        FavoriteListingResponseModel oFavoriteListingResponseModel
                = Utility.favoriteListingApi(authToken, profileId);

        Set<String> favoriteContent = new HashSet<>();
        testReport.log(LogStatus.INFO, "Getting the favorite content for profile from API");
        for (int i = 0; i < oFavoriteListingResponseModel.getData().getList().size(); i++) {
            favoriteContent.add(oFavoriteListingResponseModel.getData().getList().get(i).getTitle());
        }

        // Logging the profiles from API response.
        if (favoriteContent.size() == 0) {
            testReport.log(LogStatus.INFO, "No favorite content exists for this profile on API...");
        } else {
            testReport.log(LogStatus.INFO, "The favorite content from the API is - ");
        }

        String favoriteStringApi = "";
        for (String favTitle : favoriteContent) {
            favoriteStringApi += favTitle + ", ";

        }
        testReport.log(LogStatus.INFO, "\t" + favoriteStringApi);

        testReport.log(LogStatus.INFO, "Getting the favorite content from the application");
        Set<String> favoriteContentOnApp = new HashSet<>();
        List<WebElement> favoriteScreenTitles = getWebElements(FavoriteScreen.titles_on_favorite_screen());
        for (WebElement element : favoriteScreenTitles) {
            favoriteContentOnApp.add(element.getAttribute("innerHTML"));
        }

        // Logging the profiles from API response.
        if (favoriteContentOnApp.size() == 0) {
            testReport.log(LogStatus.INFO, "No favorite content exists for this profile on APP...");
        } else {
            testReport.log(LogStatus.INFO, "The favorite content from the Application is - ");
        }

        String favoriteStringApp = "";
        for (String favTitle : favoriteContentOnApp) {
            favoriteStringApp += favTitle + ", ";
        }
        testReport.log(LogStatus.INFO, "\t" + favoriteStringApp);
        testReport.log(LogStatus.INFO, "Comparing the favorite content between the API response and the Application...");
        Set<String> contentNotPresent
                = Utility.verifyListsAreSame(favoriteContent, favoriteContentOnApp);

        if (contentNotPresent.size() == 0) {
            testReport.log(LogStatus.INFO, "The favorite content is same on the application and API response, Test case PASSED!");
        } else {
            testReport.log(LogStatus.INFO, "The favorite content between the Application and the API response is NOT same, Test case FAILED!");
        }
    }

    @Test(priority = 2)
    public void testDeleteFavorite(Method method) throws InterruptedException {
        HashMap<String, String> data = new HashMap<String, String>();
        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        //*********************** Setting the driver cokkies to maintain login session
        driver = CookieHandler.setDriverCookies(driver);
        driver.get(URL + FavoriteScreen.pageUrl);
        Thread.sleep(10000);

        testReport.log(LogStatus.INFO, "Executing the delete Favorite test case");
        List<WebElement> listOfDeleteButton = null;
        List<WebElement> listOfFavoriteTitles = null;
        listOfFavoriteTitles = getWebElements(FavoriteScreen.titles_on_favorite_screen());

        StringBuffer stringOfFavoriteTitle = new StringBuffer("");
        for (WebElement element : listOfFavoriteTitles) {
            stringOfFavoriteTitle.append(element.getText() + ", ");
        }

        testReport.log(LogStatus.INFO, "Favorite content on application BEFORE deletion  - " + stringOfFavoriteTitle);
        String favoriteTitle = listOfFavoriteTitles.get(0).getAttribute("innerHTML");
        testReport.log(LogStatus.INFO, "Deleting the favorite title - " + favoriteTitle);
        listOfDeleteButton =
                getWebElements(FavoriteScreen.delete_button());

        tapOnElement(listOfDeleteButton.get(0));

        refreshScreen();

        Thread.sleep(5000);

        listOfFavoriteTitles = getWebElements(FavoriteScreen.titles_on_favorite_screen());
        stringOfFavoriteTitle = new StringBuffer("");
        for (WebElement element : listOfFavoriteTitles) {
            stringOfFavoriteTitle.append(element.getText() + ", ");
        }

        testReport.log(LogStatus.INFO, "Favorite content on application AFTER deletion is - " + stringOfFavoriteTitle);

        boolean titleDeletedSuccessfully = true;
        for (WebElement element : listOfFavoriteTitles) {
            if (element.getText().equalsIgnoreCase(favoriteTitle)) {
                titleDeletedSuccessfully = false;
                break;
            }
        }

        testReport.log(LogStatus.INFO, "Verifying the Application to validate favorite title - " + favoriteTitle + " has been deleted successfully");
        if (titleDeletedSuccessfully) {
            testReport.log(LogStatus.INFO, "The favorite title << " + favoriteTitle + " >> deleted successfully from the application");
        } else {
            testReport.log(LogStatus.INFO, "Unable to delete the favorite title - " + favoriteTitle + " from the application");
        }

        String authToken = "";
        String profileId = "";

        testReport.log(LogStatus.INFO, "Verifying the API to validate favorite title << " + favoriteTitle + " >> has been deleted successfully from application");
        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = Utility.loginApiWithUsernamePassword(data.get("sid"), data.get("password"));
        authToken = oLoginWithSidPasswordResponseModel.getData().getAccessToken();

        ListProfileResponseModel oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));
        for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
            profileId = profile.getId();
            break;
        }
        FavoriteListingResponseModel oFavoriteListingResponseModel
                = Utility.favoriteListingApi(authToken, profileId);

        boolean titleDeletedFromApi = true;
        for (int i = 0; i < oFavoriteListingResponseModel.getData().getList().size(); i++) {

            if (oFavoriteListingResponseModel.getData().getList().get(i).getTitle().equalsIgnoreCase(favoriteTitle)) {
                titleDeletedFromApi = false;
                break;
            }
        }

        if (titleDeletedSuccessfully) {
            testReport.log(LogStatus.INFO, "Title - " + favoriteTitle + " deleted successfully, test case PASSED!");
        } else {
            testReport.log(LogStatus.INFO, "Unable to delete title << " + favoriteTitle + " >> Test case failed!!");
        }
    }
}
