package com.ttn.automation.model_classes.continue_watching.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("sectionType")
    @Expose
    private String sectionType;
    @SerializedName("layoutType")
    @Expose
    private String layoutType;
    @SerializedName("contentList")
    @Expose
    private List<ContentList> contentList = null;
    @SerializedName("pagingState")
    @Expose
    private String pagingState;
    @SerializedName("continueWatchingRail")
    @Expose
    private Boolean continueWatchingRail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    public List<ContentList> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentList> contentList) {
        this.contentList = contentList;
    }

    public String getPagingState() {
        return pagingState;
    }

    public void setPagingState(String pagingState) {
        this.pagingState = pagingState;
    }

    public Boolean getContinueWatchingRail() {
        return continueWatchingRail;
    }

    public void setContinueWatchingRail(Boolean continueWatchingRail) {
        this.continueWatchingRail = continueWatchingRail;
    }

}

