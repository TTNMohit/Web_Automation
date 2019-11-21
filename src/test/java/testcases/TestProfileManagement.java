package testcases;

import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.LogStatus;
import com.ttn.automation.PageModels.*;
import com.ttn.automation.Utils.CookieHandler;
import com.ttn.automation.Utils.Utility;
import com.ttn.automation.core.DataElements;
import com.ttn.automation.core.TestBase;
import com.ttn.automation.model_classes.login_management.response.LoginWithSidPasswordResponseModel;
import com.ttn.automation.model_classes.profile_management.response.ListProfileResponseModel;
import com.ttn.automation.model_classes.profile_management.response.Profile;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class TestProfileManagement extends TestBase {

    Map<String, DataElements> dataElementMap = null;

    @BeforeClass
    public void dataSetter() {
        //System.out.println("Class name: " + this.getClass().getSimpleName());
        dataElementMap = getClassData(this.getClass().getSimpleName());
    }

    @Test
    public void testProfileCreation(Method method) throws InterruptedException {

        HashMap<String, String> data = new HashMap<String, String>();

        //*********************** Setting the driver cokkies to maintain login session

        driver = CookieHandler.setDriverCookies(driver);
        driver.get("https://tatasky-uat-web-app.videoready.tv/profile");

        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        //************************ Hit the login api to get the auth token
        //************************ Hit the list profile api with auth token to count profile

        // hitting the login api to get auth token.
        testReport.log(LogStatus.INFO, "Getting the auth Token from the login API for SID - " + data.get("sid"));
        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = Utility.loginApiWithUsernamePassword(data.get("sid"), data.get("password"));
        Thread.sleep(1000);
        String authToken = oLoginWithSidPasswordResponseModel.getData().getAccessToken();
        System.out.println("debug" + authToken);
        if (authToken != null)
            testReport.log(LogStatus.INFO, "Response for login API fetched successfully, Auth Token = " + authToken);
        else
            testReport.log(LogStatus.INFO, "Unable to fetch the response for login API, test case failed!!");


        //Hit the profile list api to check if the profile count is <= 5
        testReport.log(LogStatus.INFO, "Getting the reponse for << profile listing >> API");
        ListProfileResponseModel oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));
        int size = oListProfileResponseModel.getData().getProfiles().size();

        testReport.log(LogStatus.INFO, "Checking the profile count to be less than 5 ( Max profile count) ");
        if (oListProfileResponseModel.getData().getProfiles().size() >= 5) {
            // delete profile
            testReport.log(LogStatus.INFO, "Profile count is greater than 5, deleting profiles");
            String profileId = null;
            for (Profile profileDetails : oListProfileResponseModel.getData().getProfiles()) {
                if (profileDetails.getIsDefaultProfile() == false) {
                    profileId = profileDetails.getId();
                    break;
                }

            }
            Utility.deleteProfileApi(authToken, profileId, data.get("sid"));
            System.out.println("The size of list is: " + size);
        } else {
            testReport.log(LogStatus.INFO, "Profile count is less than 5, continuing with the test case...");
        }


        //********************** Create a new profile from the web application

        Thread.sleep(5000);

        tapOnElement(ProfileManagementPOM.get_add_profile_button());

        String randomProfileName = "test_profile_" + Utility.generateRandomNumber();

        typeValueInTextBox(randomProfileName, ProfileManagementPOM.profile_name_input_text_field_edit_mode());

        scrollToElement(ProfileManagementPOM.get_create_profile_button());

        testReport.log(LogStatus.INFO, "Creating a new profile - " + randomProfileName);
        tapOnElement(ProfileManagementPOM.get_create_profile_button());

        // check the profile is reflected in the list api
        testReport.log(LogStatus.INFO, "Verifying the profile is created, from the list API");
        oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));
        boolean isProfilePresentInApi = false;
        for (Profile profileName : oListProfileResponseModel.getData().getProfiles()) {
            if (profileName.getProfileName().equalsIgnoreCase(randomProfileName)) {
                isProfilePresentInApi = true;
                testReport.log(LogStatus.INFO, "Profile - << " + randomProfileName + " >> creation tested successfully in the API");
                break;
            }
        }
        //*************** check the profile is present in the frontend

        // scroll up
        testReport.log(LogStatus.INFO, "Checking the profile on the web app");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, -250);");
        boolean isProfilePresent = false;

        for (WebElement element : getWebElements(ProfileManagementPOM.get_profile_image())) {
            while (!element.isDisplayed()) {
                System.out.println("Waiting for the + icon to be displayed..");
            }
            element.click();
            try {
                if (driver.findElement(By.xpath
                        ("//strong[text()='" + randomProfileName + "']")).isDisplayed()) {

                    isProfilePresent = true;
                    testReport.log(LogStatus.INFO, "Profile is present on the web app - created successfully");
                    break;
                }

            } catch (NoSuchElementException e) {
                System.out.println("Exception");
            } catch (Exception e) {

                System.out.println("Could not locate the profile name....Looking further");
            }
        }
        System.out.println(isProfilePresent);
        System.out.println(isProfilePresentInApi);
    }

    @Test
    public void testDeleteProfile(Method method) throws InterruptedException {
        HashMap<String, String> data = new HashMap<String, String>();

        //*********************** Setting the driver cokkies to maintain login session

        driver = CookieHandler.setDriverCookies(driver);
        driver.get("https://tatasky-uat-web-app.videoready.tv/profile");

        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        boolean isNonDefaultProfilePresent = false;
        String authToken = null;
        boolean isNonDefaultProfileCreated = false;

        // get the auth token from login API
        // check if a non default profile exists
        String nonDefautProfileName = null;
        testReport.log(LogStatus.INFO, "Logging in the application to get the Auth Token for SID - " + data.get("sid"));
        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = Utility.loginApiWithUsernamePassword(data.get("sid"), data.get("password"));
        authToken = oLoginWithSidPasswordResponseModel.getData().getAccessToken();
        testReport.log(LogStatus.INFO, "Logged in successfully, the Auth token is - " + authToken);
        testReport.log(LogStatus.INFO, "Looking for a NON - DEFAULT profile to test delete functionality");
        ListProfileResponseModel oListProfileResponseModel =
                Utility.getProfileListing(authToken, data.get("sid"));
        for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
            if (profile.getIsDefaultProfile() == false) {
                isNonDefaultProfilePresent = true;
                nonDefautProfileName = profile.getProfileName();
                testReport.log(LogStatus.INFO, "NON-DEFAULT Profile - " + nonDefautProfileName + " found!");
                break;
            }
        }
        // creating a new non default profile as it does not exists
        if (isNonDefaultProfilePresent == false) {
            nonDefautProfileName = "test_profie";
            testReport.log(LogStatus.INFO, "NON-DEFAULT profile not present, creating a new profile");
            Utility
                    .createNewProfile(authToken, nonDefautProfileName, false, data.get("sid"));
            testReport.log(LogStatus.INFO, "Creating new profile - << " + nonDefautProfileName + " >> ");

            // checking if the non default profile has been created successfully
            oListProfileResponseModel =
                    Utility.getProfileListing(authToken, data.get("sid"));
            for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
                if (profile.getProfileName().equalsIgnoreCase("test_profile")) ;
                {
                    isNonDefaultProfileCreated = true;
                    testReport.log(LogStatus.INFO, "New test profile - << " + nonDefautProfileName + " >> created successfully");
                    break;
                }
            }

            // refreshing the page to reload profiles
            refreshScreen();
            if (isNonDefaultProfileCreated == false) {
                testReport.log(LogStatus.INFO, "Unable to create a new test profile for test, Test case failed!!");
            }
        }


        // wait for the page to refresh and reload
        Thread.sleep(10000);
        testReport.log(LogStatus.INFO, "Looking for << " + nonDefautProfileName + " >>on the application...");
        for (WebElement element : getWebElements(ProfileManagementPOM.get_profile_image())) {
            element.click();

            String profileName
                    = getText(ProfileManagementPOM.get_profile_name_input_text_field());
            if (profileName.equalsIgnoreCase(nonDefautProfileName)) {
                testReport.log(LogStatus.INFO, "Profile found, Deleting profile!! ");
                scrollToElement
                        (getWebElement(ProfileManagementPOM.get_remove_profile_button()));
                break;
            }
        }

        tapOnElement(ProfileManagementPOM.get_remove_profile_button());

        tapOnElement(ProfileManagementPOM.get_yes_button_on_dialog());

        // verify profile is removed from API
        oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));

        boolean isProfilePresent = false;
        for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
            if (profile.getProfileName().equalsIgnoreCase(nonDefautProfileName)) {
                isProfilePresent = true;
                break;
            }
        }
        if (isProfilePresent == false) {
            testReport.log(LogStatus.INFO, "Profile Deleted successfully! Test case passed!!");
        } else
            testReport.log(LogStatus.INFO, "Unable to Delete profile name, Test case failed!!");
    }

    @Test
    public void testProfileUpdate(Method method) throws InterruptedException {

        HashMap<String, String> data = new HashMap<String, String>();

        //***************Setting the driver cookies to maintain login session

        driver = CookieHandler.setDriverCookies(driver);
        driver.get("https://tatasky-uat-web-app.videoready.tv/profile");

        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        // Check atleast 1 profile is present on the homescreen.
        // Hit the login api with sid password and get authtoken

        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = Utility.loginApiWithUsernamePassword(data.get("sid"), data.get("password"));
        String authToken = null;
        boolean isProfilePresent = false;
        String profileNameForTest = null;
        authToken = oLoginWithSidPasswordResponseModel.getData().getAccessToken();
        testReport.log(LogStatus.INFO, "Logging into the application to get the AUTH Token for SID - " + data.get("sid"));
        testReport.log(LogStatus.INFO, "Checking if atleast 1 regular profile is present");
        ListProfileResponseModel oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));
        if (oListProfileResponseModel.getData().getProfiles().size() > 0
                && oListProfileResponseModel.getData().getProfiles().size() < 6) {
            for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
                if (profile.getIsKidsProfile() == false) {
                    isProfilePresent = true;
                    profileNameForTest = profile.getProfileName();
                    testReport.log(LogStatus.INFO, "Regular profile - " + profileNameForTest + " found! Continuing with test case..");
                    break;

                }
            }
        } else {
            //create a new profile
            testReport.log(LogStatus.INFO, "No regular profile present, creating a new regular profile");
            profileNameForTest = "test_profile_" + Utility.generateRandomNumber();
            Response apiResponse = Utility.createNewProfile(authToken, profileNameForTest, false, data.get("sid"));

        }

        //*******************************************************

        Thread.sleep(5000);
        testReport.log(LogStatus.INFO, "Looking for profile - " + profileNameForTest + " on the application");
        boolean isProfilePresentOnApp = false;
        for (WebElement element : getWebElements(ProfileManagementPOM.get_profile_image())) {
            element.click();
            String profileNameFromApplication = getWebElement
                    (ProfileManagementPOM.get_profile_name_input_text_field()).getText();
            if (profileNameFromApplication.equalsIgnoreCase(profileNameForTest)) {
                testReport.log(LogStatus.INFO, "Profile - " + profileNameForTest + " found on the Application!! ");
                isProfilePresentOnApp = true;
                break;
            }
        }
        if (isProfilePresentOnApp == false) {
            testReport.log(LogStatus.INFO, "Profile not present on the application.");
        }


        scrollToElement(ProfileManagementPOM.get_update_profile_button());

        tapOnElement(ProfileManagementPOM.get_update_profile_button());

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, -250);");

        String newProfileName = "auto_test_" + Utility.generateRandomNumber();

        if (newProfileName.length() > 20) {
            newProfileName = newProfileName.substring(0, 20);
        }
        testReport.log(LogStatus.INFO, "Updating the profile name to - " + newProfileName);

        typeValueInTextBox(newProfileName, ProfileManagementPOM.profile_name_input_text_field_edit_mode());

        scrollToElement(ProfileManagementPOM.get_save_profile_button());

        tapOnElement(ProfileManagementPOM.get_save_profile_button());

        String profileNameFromApp =
                fetchAttribute(ProfileManagementPOM.get_profile_name_input_text_field(), "innerHTML");

        if (profileNameFromApp.equalsIgnoreCase(newProfileName)) {
            testReport.log(LogStatus.INFO, "Profile name on the app updated successfully!");
            testReport.log(LogStatus.INFO, "Updated profile name on app is - " + profileNameFromApp);
        } else {
            testReport.log(LogStatus.INFO, "Profile name on the app NOT updated! Test case failed");
        }

        oListProfileResponseModel =
                Utility.getProfileListing(authToken, data.get("sid"));

        boolean profileUpdatedCheck_Api = false;
        for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
            if (profile.getProfileName().equalsIgnoreCase(newProfileName.toString())) {
                profileUpdatedCheck_Api = true;
                break;
            }

        }
        if (profileUpdatedCheck_Api == true) {
            testReport.log(LogStatus.INFO, "Profile name on the API updated successfully!");
            testReport.log(LogStatus.INFO, "Updated profile name on API is - " + profileNameFromApp);
        } else {
            testReport.log(LogStatus.INFO, "Profile name on the API NOT updated successfully!");
        }

    }

    @Test
    public void testKidsProfileCreation(Method method) throws InterruptedException {

        HashMap<String, String> data = new HashMap<String, String>();

        //***************Setting the driver cookies to maintain login session

        driver = CookieHandler.setDriverCookies(driver);
        driver.get("https://tatasky-uat-web-app.videoready.tv/profile");
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        // getting the auth token
        String authToken = null;
        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = Utility.loginApiWithUsernamePassword(data.get("sid"), data.get("password"));
        authToken = oLoginWithSidPasswordResponseModel.getData().getAccessToken();


        // checking if the profile count < 6
        testReport.log(LogStatus.INFO, "Getting the reponse for << profile listing >> API");
        ListProfileResponseModel oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));
        int size = oListProfileResponseModel.getData().getProfiles().size();

        testReport.log(LogStatus.INFO, "Checking the profile count to be less than 5 ( Max profile count) ");
        if (oListProfileResponseModel.getData().getProfiles().size() >= 5) {
            // delete profile
            testReport.log(LogStatus.INFO, "Profile count = " +
                    oListProfileResponseModel.getData().getProfiles().size() + ", deleting profiles");
            String profileId = null;
            for (Profile profileDetails : oListProfileResponseModel.getData().getProfiles()) {
                if (profileDetails.getIsDefaultProfile() == false) {
                    profileId = profileDetails.getId();
                    break;
                }
            }
            Utility.deleteProfileApi(authToken, profileId, data.get("sid"));
        } else {
            testReport.log(LogStatus.INFO, "Profile count is less than 5, continuing with the test case...");
        }

        // wait for 10 seconds
        Thread.sleep(5000);

        // Click on + button
        tapOnElement(ProfileManagementPOM.get_add_profile_button());
        // enter the Kids profile name
        String kidsProfileName = "kids_profile_" + Utility.generateRandomNumber();
        typeValueInTextBox(kidsProfileName, ProfileManagementPOM.profile_name_input_text_field_edit_mode());
        // select the kids option from "profile type"

        testReport.log(LogStatus.INFO, "Creating kids profile - " + kidsProfileName);
        tapOnElement(ProfileManagementPOM.get_profile_type_kids());

        // scroll to the bottom of the screen and click save.
        scrollToElement(ProfileManagementPOM.get_create_profile_button());

        // click on save button
        tapOnElement(ProfileManagementPOM.get_create_profile_button());

        // scroll to top
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, -250);");

        Thread.sleep(2000);
        // navigate through the created profiles and validate the
        String profileNameOnApplication = null;
        boolean isKidsProfileCreated = false;

        testReport.log(LogStatus.INFO, "Looking up for << " + kidsProfileName + " >> profile, on the application");
        for (WebElement element : getWebElements(ProfileManagementPOM.get_profile_image())) {
            element.click();
            profileNameOnApplication = getText(ProfileManagementPOM.get_profile_name_input_text_field());
            if (profileNameOnApplication.equalsIgnoreCase(kidsProfileName)) {
                isKidsProfileCreated = true;
                break;
            }
        }

        if (isKidsProfileCreated) {
            testReport.log(LogStatus.INFO, kidsProfileName + " found on the application ");
        } else if (isKidsProfileCreated == false) {
            testReport.log(LogStatus.INFO, kidsProfileName + " not present on the application");
        }


        // Validate the profile exists in the API with kids tag = true.
        testReport.log(LogStatus.INFO, "Looking up for - << " + kidsProfileName + " >> profile in the API response.");
        boolean isProfilePresentInApi = false;
        oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));

        for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
            if (profile.getProfileName().equalsIgnoreCase(kidsProfileName)
                    && profile.getIsKidsProfile() == true) {
                isProfilePresentInApi = true;
                testReport.log(LogStatus.INFO, kidsProfileName + " Present in the API Response!");
                break;
            }
        }

        Assert.assertTrue(true);
    }

    @Test
    public void testSwitchAndExitKidsProfile(Method method) throws InterruptedException {


        HashMap<String, String> data = new HashMap<String, String>();

        //***************Setting the driver cookies to maintain login session
        testReport.log(LogStatus.INFO, "Setting up the test data...");
        driver = CookieHandler.setDriverCookies(driver);
        driver.get("https://tatasky-uat-web-app.videoready.tv/profile");
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        String kidsProfileName = null;
        String authToken = null;
        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = Utility.loginApiWithUsernamePassword(data.get("sid"), data.get("password"));
        authToken = oLoginWithSidPasswordResponseModel.getData().getAccessToken();
        ListProfileResponseModel oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));
        boolean isKidsProfilePresent = false;
        testReport.log(LogStatus.INFO, "Looking for a kids profile ...");
        for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
            if (profile.getIsKidsProfile()) {
                kidsProfileName = profile.getProfileName();
                isKidsProfilePresent = true;
                testReport.log(LogStatus.INFO, "Kids profile << " + kidsProfileName + " >> found!");
                break;
            }
        }

        boolean isKidsProfileCreationSuccessfull = false;
        if (isKidsProfilePresent == false) {
            //create a new Kids profile
            kidsProfileName = "kids_test_" + Utility.generateRandomNumber();
            testReport.log(LogStatus.INFO, "No kids profile exists, creating a new kids profile - " + kidsProfileName);
            Utility.createNewProfile(authToken, kidsProfileName, true, data.get("sid"));

            // validate kids profileis created successfully
            oListProfileResponseModel
                    = Utility.getProfileListing(authToken, data.get("sid"));
            for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
                if (profile.getProfileName().equalsIgnoreCase(kidsProfileName)) {
                    isKidsProfileCreationSuccessfull = true;
                    break;
                }

                if (isKidsProfileCreationSuccessfull == true) {
                    testReport.log(LogStatus.INFO, "kids profile - " + kidsProfileName + " created successfully");
                } else {
                    testReport.log(LogStatus.INFO, "Unable to create kids profile - " + kidsProfileName);
                }
            }
        }

        driver.get(URL + SettingsPOM.get_settings_url());
        Thread.sleep(5000);

        testReport.log(LogStatus.INFO, "Setting up the kids PIN to - " + data.get("Pin"));
        // scroll to bottom
        scrollToElement(SettingsPOM.save_changes_button());

        typeValueInTextBox(data.get("Pin"), SettingsPOM.kids_pin_text_field());

        tapOnElement(SettingsPOM.save_changes_button());

        Thread.sleep(7000);

        testReport.log(LogStatus.INFO, "Switching to kids profile - " + kidsProfileName);
        tapOnElement(HomeScreenModel.get_user_logo());

        //click on the kids profile from navigation menu
        Thread.sleep(2000);
        List<WebElement> elementList = driver.findElements(By.xpath("//small"));
        String profileName = "";
        for (WebElement element : elementList) {
            profileName = element.getAttribute("innerHTML");
            if (profileName.equalsIgnoreCase(kidsProfileName)) {
                testReport.log(LogStatus.INFO, "Clicking on - << " + kidsProfileName + " >> profile from Navigation menu.");
                break;
            }
        }

        driver.findElement
                (By.xpath("//small[text()='" + profileName + "']/..//div[@class='pro_bac_img']")).click();

        // verify the kids logo and exit with pin
        Thread.sleep(2000);

        boolean switchedToKidsProfile =
                verifyElementPresent(Kids_POM.kids_image_logo());

        if (switchedToKidsProfile) {
            testReport.log(LogStatus.INFO, "Switched to kids profile - " + kidsProfileName + " successfully");
        } else {
            testReport.log(LogStatus.INFO, "Unable to switch to kids profile - " + kidsProfileName);
        }
        testReport.log(LogStatus.INFO, "Exiting the kids profile");
        tapOnElement(Kids_POM.exit_button());

        typeValueInTextBox(data.get("Pin"), Kids_POM.pin_input_field());

        tapOnElement(Kids_POM.ok_button());

        boolean testResult = verifyElementPresent(HomeScreenModel.get_user_logo());

        if (testResult) {
            testReport.log(LogStatus.INFO, "Kids profile - " + kidsProfileName + " exited successfully!");
        } else
            testReport.log(LogStatus.INFO, "Unable to exit kids profile - " + kidsProfileName);
        Assert.assertTrue(testResult);

    }
}

