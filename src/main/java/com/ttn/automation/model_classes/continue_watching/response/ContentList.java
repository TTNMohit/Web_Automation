package com.ttn.automation.model_classes.continue_watching.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContentList {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("contractName")
    @Expose
    private String contractName;
    @SerializedName("entitlements")
    @Expose
    private List<String> entitlements = null;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("channelId")
    @Expose
    private Integer channelId;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("providerContentId")
    @Expose
    private String providerContentId;
    @SerializedName("secondsWatched")
    @Expose
    private Integer secondsWatched;
    @SerializedName("durationInSeconds")
    @Expose
    private Integer durationInSeconds;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("lastWatched")
    @Expose
    private Long lastWatched;
    @SerializedName("vodId")
    @Expose
    private Integer vodId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLastWatched() {
        return lastWatched;
    }

    public void setLastWatched(Long lastWatched) {
        this.lastWatched = lastWatched;
    }

    public Integer getVodId() {
        return vodId;
    }

    public void setVodId(Integer vodId) {
        this.vodId = vodId;
    }

}
