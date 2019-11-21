package com.ttn.automation.model_classes.login_management.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("refreshToken")
    @Expose
    private Object refreshToken;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("expiresIn")
    @Expose
    private Object expiresIn;
    @SerializedName("pubnubChannel")
    @Expose
    private String pubnubChannel;
    @SerializedName("crmId")
    @Expose
    private String crmId;
    @SerializedName("forceChangePwd")
    @Expose
    private Boolean forceChangePwd;
    @SerializedName("isFirstTimeLoggedIn")
    @Expose
    private Boolean isFirstTimeLoggedIn;
    @SerializedName("encryptedPassword")
    @Expose
    private String encryptedPassword;
    @SerializedName("rrmSessionInfo")
    @Expose
    private RrmSessionInfo rrmSessionInfo;
    @SerializedName("userDetails")
    @Expose
    private UserDetails userDetails;
    @SerializedName("userProfile")
    @Expose
    private UserProfile userProfile;
    @SerializedName("deviceDetails")
    @Expose
    private List<DeviceDetail> deviceDetails = null;

    public Object getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(Object refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Object getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Object expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getPubnubChannel() {
        return pubnubChannel;
    }

    public void setPubnubChannel(String pubnubChannel) {
        this.pubnubChannel = pubnubChannel;
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public Boolean getForceChangePwd() {
        return forceChangePwd;
    }

    public void setForceChangePwd(Boolean forceChangePwd) {
        this.forceChangePwd = forceChangePwd;
    }

    public Boolean getIsFirstTimeLoggedIn() {
        return isFirstTimeLoggedIn;
    }

    public void setIsFirstTimeLoggedIn(Boolean isFirstTimeLoggedIn) {
        this.isFirstTimeLoggedIn = isFirstTimeLoggedIn;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public RrmSessionInfo getRrmSessionInfo() {
        return rrmSessionInfo;
    }

    public void setRrmSessionInfo(RrmSessionInfo rrmSessionInfo) {
        this.rrmSessionInfo = rrmSessionInfo;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public List<DeviceDetail> getDeviceDetails() {
        return deviceDetails;
    }

    public void setDeviceDetails(List<DeviceDetail> deviceDetails) {
        this.deviceDetails = deviceDetails;
    }

}
