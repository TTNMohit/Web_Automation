package com.ttn.automation.PageModels;

public class MyBoxScreen {

    public static String pageUrl = "my-box?pageType=my-box";

    private static final String filters_button = "filters_button";
    private static final String apply_filters = "apply_filters";
    private static final String languages_filter_header = "languages_filter_header";
    private static final String channel_id_mybox = "channel_id_mybox";
    private static final String select_date = "select_date";
    private static final String close_date = "close_date";

    public static String close_date() {
        return close_date;
    }

    public static String select_date() {
        return select_date;
    }

    public static String channel_id_mybox() {
        return channel_id_mybox;
    }

    public static String languages_filter_header() {
        return filters_button;
    }

    public static String filters_button() {
        return filters_button;
    }

    public static String apply_filters() {
        return apply_filters;
    }


}
