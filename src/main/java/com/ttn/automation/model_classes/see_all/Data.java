package com.ttn.automation.model_classes.see_all;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    private  List<ContentList> contentList = null;
    @SerializedName("autoScroll")
    @Expose
    private Boolean autoScroll;
    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("configType")
    @Expose
    private String configType;
    @SerializedName("specialRail")
    @Expose
    private Boolean specialRail;

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

    public Boolean getAutoScroll() {
        return autoScroll;
    }

    public void setAutoScroll(Boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public Boolean getSpecialRail() {
        return specialRail;
    }

    public void setSpecialRail(Boolean specialRail) {
        this.specialRail = specialRail;
    }

}
