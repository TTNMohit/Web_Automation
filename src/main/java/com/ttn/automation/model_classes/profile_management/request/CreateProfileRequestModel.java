package com.ttn.automation.model_classes.profile_management.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateProfileRequestModel {

    @SerializedName("profileName")
    @Expose
    private String profileName;
    @SerializedName("ageGroup")
    @Expose
    private String ageGroup;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("profilePic")
    @Expose
    private String profilePic;
    @SerializedName("isDefaultProfile")
    @Expose
    private Boolean isDefaultProfile;
    @SerializedName("isKidsProfile")
    @Expose
    private Boolean isKidsProfile;
    @SerializedName("categoryIds")
    @Expose
    private List<Object> categoryIds = null;
    @SerializedName("languageIds")
    @Expose
    private List<Object> languageIds = null;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Boolean getIsDefaultProfile() {
        return isDefaultProfile;
    }

    public void setIsDefaultProfile(Boolean isDefaultProfile) {
        this.isDefaultProfile = isDefaultProfile;
    }

    public Boolean getIsKidsProfile() {
        return isKidsProfile;
    }

    public void setIsKidsProfile(Boolean isKidsProfile) {
        this.isKidsProfile = isKidsProfile;
    }

    public List<Object> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Object> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<Object> getLanguageIds() {
        return languageIds;
    }

    public void setLanguageIds(List<Object> languageIds) {
        this.languageIds = languageIds;
    }

}
