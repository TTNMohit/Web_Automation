package com.ttn.automation.model_classes.profile_management.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("profiles")
    @Expose
    private List<Profile> profiles = null;
    @SerializedName("isPLExist")
    @Expose
    private Boolean isPLExist;

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Boolean getIsPLExist() {
        return isPLExist;
    }

    public void setIsPLExist(Boolean isPLExist) {
        this.isPLExist = isPLExist;
    }

}