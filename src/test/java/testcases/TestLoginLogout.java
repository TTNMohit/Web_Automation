package testcases;

import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.LogStatus;
import com.ttn.automation.PageModels.*;
import com.ttn.automation.Utils.CookieHandler;
import com.ttn.automation.Utils.SessionHandler;
import com.ttn.automation.Utils.Utility;
import com.ttn.automation.core.DataElements;
import com.ttn.automation.core.TestBase;
import com.ttn.automation.model_classes.login_management.response.LoginWithSidPasswordResponseModel;
import com.ttn.automation.model_classes.profile_management.response.ListProfileResponseModel;
import com.ttn.automation.model_classes.profile_management.response.Profile;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestLoginLogout extends TestBase {

    Map<String, DataElements> dataElementMap = null;

    @BeforeClass
    public void dataSetter() {
        dataElementMap = getClassData(this.getClass().getSimpleName());
    }

    @Test(priority = 1)
    public void testLogoutFromSettings(Method method) throws InterruptedException, AWTException {


        HashMap<String, String> data = new HashMap<String, String>();
        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        testReport.log(LogStatus.INFO, "Logging into the application");

        // new code
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Thread.sleep(5000);
        while (!driver.findElement(By.xpath("//a[text()='DONE']")).isDisplayed()) {
        }
        driver.findElement(By.xpath("//a[text()='DONE']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[text()='Login']")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".loginRmn > li:nth-child(2) > a:nth-child(1) > img:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".paddingR5")).sendKeys(data.get("sid"));
        driver.findElement(By.xpath("//button[text()='Continue']")).click();
        driver.findElement(By.xpath("//a[text()='Login with My Tata Sky Password']")).click();
        /*driver.findElement(By.cssSelector(".form-group > input:nth-child(1)"))
                .sendKeys(password);*/
        driver.findElement(By.xpath("//input[@type='password'and@placeholder='My Tata Sky Password']"))
                .sendKeys(data.get("password"));
        driver.findElement(By.xpath("//button[text()='Continue']")).click();
        // end of new code


        //*********************** Setting the driver cookies to maintain login session

        /*testReport.log(LogStatus.INFO, "Setting up the login session using cookies...");
        driver = CookieHandler.setDriverCookies(driver);
        driver.get(URL);*/

        waitForSeconds(7);

        testReport.log(LogStatus.INFO, "Logging out of the application");
        tapOnElement(HomeScreenModel.get_user_logo());

        tapOnElement(NavigationDrawerPOM.get_logout());

        tapOnElement(ProfileManagementPOM.get_yes_button_on_dialog());

        boolean testCaseResult = false;

        testCaseResult = verifyElementPresent(HomeScreenModel.get_login_button());

        Assert.assertTrue(testCaseResult);
    }

    @Test(priority = 0)
    public void testLogoutFromKidsScreen(Method method) throws InterruptedException {
        HashMap<String, String> data = new HashMap<String, String>();
        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        testReport.log(LogStatus.INFO, "Navigating to HomeScreen URL");
        //*********************** Setting the driver cookies to maintain login session

        testReport.log(LogStatus.INFO, "Setting up the login session using cookies...");
        driver = CookieHandler.setDriverCookies(driver);
        driver.get(URL);

        waitForSeconds(10);


        // verifying kids profile is present or not
        testReport.log(LogStatus.INFO, "Verifying if kids profile exists");
        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = Utility.loginApiWithUsernamePassword(data.get("sid"), data.get("password"));
        String authToken = null;
        authToken = oLoginWithSidPasswordResponseModel.getData().getAccessToken();
        ListProfileResponseModel oListProfileResponseModel
                = Utility.getProfileListing(authToken, data.get("sid"));
        boolean isKidsProfilePresent = false;
        String kidsProfileName = null;
        boolean foundKidsProfile = false;
        boolean gotProfileId = false;
        String nonRegularProfileId = null;
        for (Profile profile : oListProfileResponseModel.getData().getProfiles()) {
            isKidsProfilePresent = profile.getIsKidsProfile();
            // checking if kids profile is present
            if (isKidsProfilePresent == true && foundKidsProfile == false) {
                kidsProfileName = profile.getProfileName();
                testReport.log(LogStatus.INFO, "Kids profile - << " + kidsProfileName + " >> is present");
                foundKidsProfile = true;
            }

            // getting the profile id for a non regular profile
            if (profile.getIsDefaultProfile() == false && gotProfileId == false) {
                nonRegularProfileId = profile.getId();
                gotProfileId = true;
                if (foundKidsProfile == true)
                    break;
            }
        }


        int profileCount = oListProfileResponseModel.getData().getProfiles().size();
        if (foundKidsProfile == false) {
            if (profileCount == Integer.parseInt(testProp.getProperty("max_profile"))) {
                // delete any non regular profile
                Utility.deleteProfileApi(authToken, nonRegularProfileId, data.get("sid"));
            }
            testReport.log(LogStatus.INFO, "Kids Profile is not present, creating a new kids profile");
            kidsProfileName = "kids_profile_" + Utility.generateRandomNumber();
            Utility.createNewProfile(authToken, kidsProfileName, true, data.get("sid"));
            testReport.log(LogStatus.INFO, "New kids profile - << " + kidsProfileName + " >> Created!");
        }

        tapOnElement(HomeScreenModel.get_user_logo());

        Thread.sleep(1000);

        driver.findElement
                (By.xpath("//small[text()='" + kidsProfileName + "']" +
                        "//preceding-sibling::a")).click();

        // checking if its first time login and kids PIN is not created.
        try {
            if (verifyElementPresent(Kids_POM.create_parental_lock())) {
                typeValueInTextBox(data.get("Kids_pin"), Kids_POM.kids_pin_text_field_dialog());
                tapOnElement(GenericPOM.save_button());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        waitForSeconds(4);

        tapOnElement(Kids_POM.exit_button());

        tapOnElement(Kids_POM.logout_button());

        tapOnElement(ProfileManagementPOM.get_yes_button_on_dialog());

        waitForSeconds(2);

        boolean testCaseResult
                = verifyElementPresent(HomeScreenModel.get_login_button());

        Assert.assertTrue(testCaseResult);
    }
}
