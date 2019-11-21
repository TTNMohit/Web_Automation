package com.ttn.automation.PageModels;

import com.ttn.automation.core.Core;

public class LoginPageModel {
    private static String pageName = null;
    //private final String login_button = "login_button";
    private static final String sid_image = "sid_image";
    private static final String sid_textbox = "sid_textbox";
    private static final String continue_button = "continue_button";
    private static final String login_with_paswd_text = "login_with_paswd_text";
    private static final String done_button = "done_button";
    private static final String password_textbox = "password_textbox";

    public static String get_password_textbox() {
        return password_textbox.trim();
    }

    public static String get_done_button() {
        return done_button.trim();
    }

    public static String getPageName() {
        return pageName.trim();
    }

    public static String get_sid_image() {
        return sid_image.trim();
    }

    public static String get_sid_textbox() {
        return sid_textbox.trim();
    }

    public static String get_continue_button() {
        return continue_button.trim();
    }

    public static String get_login_with_pswd_text() {
        return login_with_paswd_text.trim();
    }

}
