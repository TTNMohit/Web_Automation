package com.ttn.automation.model_classes.profile_management.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subscriberId")
    @Expose
    private String subscriberId;
    @SerializedName("profileName")
    @Expose
    private String profileName;
    @SerializedName("ageGroup")
    @Expose
    private Object ageGroup;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("profilePic")
    @Expose
    private Object profilePic;
    @SerializedName("isDefaultProfile")
    @Expose
    private Boolean isDefaultProfile;
    @SerializedName("isKidsProfile")
    @Expose
    private Boolean isKidsProfile;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("languages")
    @Expose
    private List<Object> languages = null;
    @SerializedName("categories")
    @Expose
    private List<Object> categories = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Object getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(Object ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Object profilePic) {
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<Object> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Object> languages) {
        this.languages = languages;
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

}
