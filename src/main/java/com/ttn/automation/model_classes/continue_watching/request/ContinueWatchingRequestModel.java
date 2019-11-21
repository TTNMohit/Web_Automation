package com.ttn.automation.model_classes.continue_watching.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContinueWatchingRequestModel {

    @SerializedName("subscriberId")
    @Expose
    private String subscriberId;
    @SerializedName("profileId")
    @Expose
    private String profileId;
    @SerializedName("continueWatching")
    @Expose
    private Boolean continueWatching;
    @SerializedName("max")
    @Expose
    private Integer max;

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public Boolean getContinueWatching() {
        return continueWatching;
    }

    public void setContinueWatching(Boolean continueWatching) {
        this.continueWatching = continueWatching;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

}
