package com.ttn.automation.model_classes.login_management.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDetails {

    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("sName")
    @Expose
    private String sName;
    @SerializedName("rmn")
    @Expose
    private String rmn;
    @SerializedName("isPremium")
    @Expose
    private Boolean isPremium;
    @SerializedName("isPVR")
    @Expose
    private Boolean isPVR;
    @SerializedName("acStatus")
    @Expose
    private String acStatus;
    @SerializedName("entitlements")
    @Expose
    private List<Entitlement> entitlements = null;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getRmn() {
        return rmn;
    }

    public void setRmn(String rmn) {
        this.rmn = rmn;
    }

    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

    public Boolean getIsPVR() {
        return isPVR;
    }

    public void setIsPVR(Boolean isPVR) {
        this.isPVR = isPVR;
    }

    public String getAcStatus() {
        return acStatus;
    }

    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }

    public List<Entitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(List<Entitlement> entitlements) {
        this.entitlements = entitlements;
    }

}
