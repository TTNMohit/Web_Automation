package com.ttn.automation.Utils;

import com.google.gson.Gson;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.ttn.automation.model_classes.continue_watching.request.ContinueWatchingRequestModel;
import com.ttn.automation.model_classes.continue_watching.response.ContinueWatchingResponseModel;
import com.ttn.automation.model_classes.favorite.FavoriteListingResponseModel;
import com.ttn.automation.model_classes.homescreen.HomeScreenMainModel;
import com.ttn.automation.model_classes.login_management.request.LoginWithSidPasswordRequestModel;
import com.ttn.automation.model_classes.login_management.response.LoginWithSidPasswordResponseModel;
import com.ttn.automation.model_classes.my_box.MyBoxFilterReponseModel;
import com.ttn.automation.model_classes.profile_management.request.CreateProfileRequestModel;
import com.ttn.automation.model_classes.profile_management.response.ListProfileResponseModel;
import com.ttn.automation.model_classes.see_all.SeeAllApiResponseModel;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class Utility {

    static Gson gson = new Gson();
    static String apiBaseUrl;
    public static String authToken = null;

    public static void closeAllowAccessDialog(WebDriver driver) throws AWTException {
        new Robot().keyPress(KeyEvent.VK_ESCAPE);

    }

    public static HashMap<String, String> getRequestParams(String requestParams) {
        HashMap<String, String> requestParamsMap = new HashMap<String, String>();
        String[] splitedString;
        splitedString = splitString(requestParams, ",");

        for (int i = 0; i < splitedString.length; i++) {
            String[] splitArray = splitString(splitedString[i].toString(), ":");
            requestParamsMap.put(splitArray[0].toString(), splitArray[1].toString());
            //requestParamsMap.put("key_"+i , "value");
        }
        return requestParamsMap;
    }


    public static String[] splitString(String stringToSplit, String splitChar) {
        String[] arrayString;
        arrayString = stringToSplit.trim().split(splitChar);
        return arrayString;

    }

    public static HomeScreenMainModel homeScreenAPI(String screenName, ExtentTest testReport) {
        testReport.log(LogStatus.INFO, "Getting the response for : " + screenName + " API");
        Response apiResonse = given()
                .log()
                .all()
                .queryParam("pageLimit", 50)
                .get(apiBaseUrl +
                        "homescreen/pub/api/v1/page/" + screenName);

        HomeScreenMainModel oHomeScreenMainModel =
                gson.fromJson(apiResonse.getBody().asString(), HomeScreenMainModel.class);
        if (oHomeScreenMainModel.getCode() == 0) {
            testReport.log(LogStatus.INFO, "The " + screenName + " API Response fetched successfully with status code - 200");
        } else {
            testReport.log(LogStatus.INFO, "The " + screenName + " API Response was not fetched successfuly, status code - " + oHomeScreenMainModel.getCode());
        }
        return oHomeScreenMainModel;
    }

    public static SeeAllApiResponseModel seeAllRailResponse(int railId, ExtentTest testReport) {
        testReport.log(LogStatus.INFO, "Getting the response for See All API for rail id : " + railId);
        Response apiResponse = given()
                .log()
                .all()
                .queryParam("limit", 100)
                .queryParam("id", railId)
                .get(apiBaseUrl + "homescreen/pub/api/v2/rail");

        SeeAllApiResponseModel oSeeAllApiResponseModel = gson.fromJson(apiResponse.getBody().asString()
                , SeeAllApiResponseModel.class);

        if (oSeeAllApiResponseModel.getCode() == 0) {
            testReport.log(LogStatus.INFO, "Response for See All API fetched successfully with status code - 200");
        } else {
            testReport.log(LogStatus.INFO, "Unable to fetch response for See All API, STATUS CODE -- " + oSeeAllApiResponseModel.getCode());
        }

        return oSeeAllApiResponseModel;
    }

    public static ListProfileResponseModel getProfileListing(String authToken, String SID) {
        Response response = given()
                .log()
                .all()
                .header("authorization", "bearer " + authToken)
                .get(apiBaseUrl + "rest-api/api/v1/subscribers/" + SID + "/profiles");

        Gson gson = new Gson();
        ListProfileResponseModel oListProfileResponseModel
                = gson.fromJson(response.getBody().asString(), ListProfileResponseModel.class);

        return oListProfileResponseModel;
    }

    public static LoginWithSidPasswordResponseModel loginApiWithUsernamePassword(String SID, String password) {
        LoginWithSidPasswordRequestModel oLoginWithSidPasswordRequestModel = new LoginWithSidPasswordRequestModel();
        oLoginWithSidPasswordRequestModel.setSid(SID);
        oLoginWithSidPasswordRequestModel.setPwd(password);

        Response response = given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .body(oLoginWithSidPasswordRequestModel)
                .post(apiBaseUrl + "rest-api/pub/api/v1/pwdLogin");

        LoginWithSidPasswordResponseModel oLoginWithSidPasswordResponseModel
                = gson.fromJson(response.getBody().asString(), LoginWithSidPasswordResponseModel.class);

        return oLoginWithSidPasswordResponseModel;
        // get the auth token from the response

    }

    public static void deleteProfileApi(String authToken, String profileId, String sid) {
        Response response = given()
                .log()
                .all()
                .header("Authorization", "bearer " + authToken)
                .delete(apiBaseUrl + "rest-api/api/v1/subscribers/" + sid + "/profiles/" + profileId);

    }

    public static int generateRandomNumber() {
        Random rand = new Random();
        int number = rand.nextInt(500) + 1;
        return number;
    }

    public static Set<String> verifyListsAreSame(Set<String> f_e, Set<String> b_e) {
        {
            Set<String> railsNotPresent = new HashSet<>();
            boolean testResult = false;
            for (String apiRailName : b_e) {
                for (String frontendRailName : f_e) {
                    if (frontendRailName.equalsIgnoreCase(apiRailName)) {
                        testResult = true;
                        break;
                    } else {
                        testResult = false;
                    }

                }
                if (testResult == false) {
                    railsNotPresent.add(apiRailName);
                }

            }
            return railsNotPresent;
        }

    }

    public static Response createNewProfile(String authToken, String profilelName, boolean isKidsTrue, String SID) {

        // setting the profile parameters
        CreateProfileRequestModel oCreateProfileRequestModel
                = new CreateProfileRequestModel();
        oCreateProfileRequestModel.setProfileName(profilelName);
        oCreateProfileRequestModel.setIsKidsProfile(isKidsTrue);

        Response response = given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .header("Authorization", "bearer " + authToken)
                .body(oCreateProfileRequestModel)
                .post(apiBaseUrl + "rest-api/api/v1/subscribers/" + SID + "/profiles");
        return response;

    }

    public static void setApiBaseUrl(String baseUrl) {
        apiBaseUrl = baseUrl;
    }

    public static FavoriteListingResponseModel favoriteListingApi(String authToken, String profileId) {
        Response response = given()
                .log()
                .all()
                .header("profileId", profileId)
                .header("Authorization", "bearer " + authToken)
                .get(apiBaseUrl + "event-processor/api/v1/subscriber/favourites");

        FavoriteListingResponseModel oProfileListingResponseModel
                = gson.fromJson(response.getBody().asString(), FavoriteListingResponseModel.class);
        return oProfileListingResponseModel;
    }

    public static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.now();
        String date = dtf.format(localDate);
        return date;
    }

    public static MyBoxFilterReponseModel myBoxApi(List<Header> genreList,
                                                   List<Header> languageList,
                                                   String limit, String date) {

        Response reponse = given()
                .log()
                .all()
                .headers(new Headers(genreList))
                .headers(new Headers(languageList))
                .header("limit", limit)
                .header("Date", date)
                .header("platform", "web")
                .get(apiBaseUrl + "portal-search/pub/api/v1/channels/schedule");

        Gson gson = new Gson();
        return gson.fromJson(reponse.getBody().asString(), MyBoxFilterReponseModel.class);
    }

    public static ContinueWatchingResponseModel continueWatchingContent(String sid, String authToken, String profileId, boolean isContinueWatching, int max) {
        ContinueWatchingRequestModel oContinueWatchingRequestModel
                = new ContinueWatchingRequestModel();
        oContinueWatchingRequestModel.setContinueWatching(isContinueWatching);
        oContinueWatchingRequestModel.setMax(max);
        oContinueWatchingRequestModel.setProfileId(profileId);
        oContinueWatchingRequestModel.setSubscriberId(sid);

        Response response = given()
                .log()
                .all()
                .body(oContinueWatchingRequestModel)
                .header("Content-Type", "application/json")
                .header("authorization", "bearer " + authToken)
                .post(apiBaseUrl + "event-processor/api/v1/watch/history");

        ContinueWatchingResponseModel oContinueWatchingResponseModel
                = new Gson().fromJson(response.getBody().asString(), ContinueWatchingResponseModel.class);
        return oContinueWatchingResponseModel;
    }

}
