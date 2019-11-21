package com.ttn.automation.model_classes.login_management.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginWithSidPasswordRequestModel {

    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("pwd")
    @Expose
    private String pwd;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}