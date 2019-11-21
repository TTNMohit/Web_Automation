package com.ttn.automation.model_classes.favorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class list {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("contentId")
    @Expose
    private Integer contentId;
    @SerializedName("contentType")
    @Expose
    private String contentType;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("subTitles")
    @Expose
    private java.util.List<String> subTitles = null;
    @SerializedName("contractName")
    @Expose
    private String contractName;
    @SerializedName("entitlements")
    @Expose
    private java.util.List<String> entitlements = null;
    @SerializedName("airedDate")
    @Expose
    private Object airedDate;
    @SerializedName("secondsWatched")
    @Expose
    private Integer secondsWatched;
    @SerializedName("durationInSeconds")
    @Expose
    private Integer durationInSeconds;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("providerContentId")
    @Expose
    private String providerContentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public java.util.List<String> getSubTitles() {
        return subTitles;
    }

    public void setSubTitles(java.util.List<String> subTitles) {
        this.subTitles = subTitles;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public java.util.List<String> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(java.util.List<String> entitlements) {
        this.entitlements = entitlements;
    }

    public Object getAiredDate() {
        return airedDate;
    }

    public void setAiredDate(Object airedDate) {
        this.airedDate = airedDate;
    }

    public Integer getSecondsWatched() {
        return secondsWatched;
    }

    public void setSecondsWatched(Integer secondsWatched) {
        this.secondsWatched = secondsWatched;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderContentId() {
        return providerContentId;
    }

    public void setProviderContentId(String providerContentId) {
        this.providerContentId = providerContentId;
    }

}
