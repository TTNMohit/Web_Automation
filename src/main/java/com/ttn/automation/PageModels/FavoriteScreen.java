package com.ttn.automation.PageModels;

public class FavoriteScreen {

    public static String pageUrl = "watch-list/favorites?pageType=favorites";

    private static final String titles_on_favorite_screen
            = "titles_on_favorite_screen";
    private static final String delete_button
            = "delete_button";

    public static String delete_button() {
        return delete_button;
    }

    public static String titles_on_favorite_screen() {
        return titles_on_favorite_screen;
    }

}
