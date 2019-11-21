package com.ttn.automation.model_classes.see_all;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContentList {

    @SerializedName("id")
    @Expose

    private Long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("contentType")
    @Expose
    private String contentType;
    @SerializedName("subTitles")
    @Expose
    private List<String> subTitles = null;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("contractName")
    @Expose
    private String contractName;
    @SerializedName("entitlements")
    @Expose
    private List<String> entitlements = null;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("genre")
    @Expose
    private List<String> genre = null;
    @SerializedName("language")
    @Expose
    private List<String> language = null;
    @SerializedName("allowedOnBrowsers")
    @Expose
    private Boolean allowedOnBrowsers;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("providerContentId")
    @Expose
    private String providerContentId;
    @SerializedName("blackOut")
    @Expose
    private Boolean blackOut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<String> getSubTitles() {
        return subTitles;
    }

    public void setSubTitles(List<String> subTitles) {
        this.subTitles = subTitles;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public List<String> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(List<String> entitlements) {
        this.entitlements = entitlements;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    public Boolean getAllowedOnBrowsers() {
        return allowedOnBrowsers;
    }

    public void setAllowedOnBrowsers(Boolean allowedOnBrowsers) {
        this.allowedOnBrowsers = allowedOnBrowsers;
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

    public Boolean getBlackOut() {
        return blackOut;
    }

    public void setBlackOut(Boolean blackOut) {
        this.blackOut = blackOut;
    }

}
