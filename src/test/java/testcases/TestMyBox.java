package testcases;

import com.jayway.restassured.response.Header;
import com.relevantcodes.extentreports.LogStatus;
import com.ttn.automation.PageModels.MyBoxScreen;
import com.ttn.automation.Utils.CookieHandler;
import com.ttn.automation.Utils.Utility;
import com.ttn.automation.core.DataElements;
import com.ttn.automation.core.TestBase;
import com.ttn.automation.model_classes.my_box.MyBoxFilterReponseModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public class TestMyBox extends TestBase {

    Map<String, DataElements> dataElementMap = null;

    @BeforeClass
    public void dataSetter() {
        dataElementMap = getClassData(this.getClass().getSimpleName());
    }

    @Test
    public void testMyBoxFilters(Method method) throws InterruptedException {

        HashMap<String, String> data = new HashMap<String, String>();
        // getting the test data for this test method
        data = Utility.getRequestParams(dataElementMap.get(method.getName()).getParams());

        testReport.log(LogStatus.INFO, "Navigating to My Box URL");
        //*********************** Setting the driver cokkies to maintain login session
        driver = CookieHandler.setDriverCookies(driver);
        driver.get(URL + MyBoxScreen.pageUrl);

        // enter if gnere filter are entered or not
        String genreFilter = "";
        String languageFilter = "";
        String date = "";

        genreFilter = data.get("genre_filters");
        languageFilter = data.get("Language_filters");
        date = data.get("Date");

        String[] genreFilters = null;
        String[] languageFilters = null;
        testReport.log(LogStatus.INFO, "The genre filters are - " + genreFilter);
        if (genreFilter != null) {
            genreFilters = Utility.splitString(genreFilter, ";");
        }
        testReport.log(LogStatus.INFO, "The language filters are - " + languageFilter);
        if (languageFilter != null) {
            languageFilters = Utility.splitString(languageFilter, ";");
        }
        if (date == null) {
            testReport.log(LogStatus.INFO, "No date provided, using current date as default date");
            date = Utility.getCurrentDate();
        }
        testReport.log(LogStatus.INFO, "EPG Date is - " + date);

        String[] dateArray = Utility.splitString(date, "-");
        String currentDate = dateArray[0];
        Thread.sleep(5000);

        testReport.log(LogStatus.INFO, "Applying the genre and language filters...");
        tapOnElement(MyBoxScreen.filters_button());

        List<Header> genreList = new LinkedList<>();
        List<Header> languageList = new LinkedList<>();

        for (String genre : genreFilters) {
            Thread.sleep(1000);
            genreList.add(new Header("genreFilters", genre));
            driver.findElement(By.xpath
                    ("//*[text()='Categories']//following-sibling::*//*[text()='" + genre + "']")).click();
        }

        scrollToElement(MyBoxScreen.apply_filters());

        for (String languages : languageFilters) {
            languageList.add(new Header("languageFilters", languages));
            driver.findElement(By.xpath("//*[text()='Languages']//following-sibling::*//*[text()='" + languages + "']")).click();
        }

        tapOnElement(MyBoxScreen.apply_filters());


        if (date != null) {
            tapOnElement(MyBoxScreen.select_date());
            testReport.log(LogStatus.INFO, "Selecting date - " + date + " for my box EPG");
            driver.findElement(By.xpath("//p//strong[text()='" + currentDate + "']")).click();
            Thread.sleep(1000);
            tapOnElement(MyBoxScreen.close_date());
        }

        String api_channels = "";
        List<String> channelList_Api = new LinkedList<>();
        MyBoxFilterReponseModel oMyBoxFilterReponseModel
                = Utility.myBoxApi(genreList, languageList, data.get("Limit"), date);
        for (int i = 0; i < oMyBoxFilterReponseModel.getData().getChannelList().size();
             i++) {
            channelList_Api.add(oMyBoxFilterReponseModel.getData().getChannelList().get(i).getChannelNumber());
            api_channels += channelList_Api.get(i) + ", ";
        }

        testReport.log(LogStatus.INFO, "The channels numbers from API are - " + api_channels);

        HashSet<Integer> setOfChannelNumber = new HashSet<Integer>();
        String new_channeId = "";
        String old_channelId = " ";
        List<WebElement> listOfChannelIds = new LinkedList<>();
        while (!new_channeId.equals(old_channelId)) {
            old_channelId = new_channeId;
            listOfChannelIds
                    = getWebElements(MyBoxScreen.channel_id_mybox());
            new_channeId =
                    listOfChannelIds.get(listOfChannelIds.size() - 1).getAttribute("innerHTML");
            scrollToElement(listOfChannelIds.get(listOfChannelIds.size() - 1));
            Thread.sleep(2000);
        }

        StringBuffer app_channel = new StringBuffer("");
        for (WebElement channelNumber : listOfChannelIds) {
            app_channel.append(channelNumber.getAttribute("innerHTML") + ", ");
        }
        testReport.log(LogStatus.INFO, "The channel numbers from app are - " + app_channel);

    }
}
