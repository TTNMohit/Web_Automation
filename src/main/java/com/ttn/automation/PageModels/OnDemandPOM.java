package com.ttn.automation.PageModels;

public class OnDemandPOM {

    public static String pageUrl = "on-demand/tv-shows-home?pageType=tv-shows-home";
    private static final String tv_shows_on_demand = "tv_shows_on_demand";
    private static final String movies_on_demand = "movies_on_demand";
    private static final String shorts_on_demand = "shorts_on_demand";

    public static String tv_shows_on_demand() {
        return tv_shows_on_demand;
    }

    public static String movies_on_demand() {
        return movies_on_demand;
    }

    public static String shorts_on_demand() {
        return shorts_on_demand;
    }
}
