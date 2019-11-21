package com.ttn.automation.model_classes.my_box;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Epg {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("startTime")
    @Expose
    private Long startTime;
    @SerializedName("endTime")
    @Expose
    private Long endTime;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("seriesId")
    @Expose
    private String seriesId;
    @SerializedName("epgState")
    @Expose
    private String epgState;
    @SerializedName("epgSlotId")
    @Expose
    private String epgSlotId;
    @SerializedName("catchup")
    @Expose
    private Boolean catchup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getEpgState() {
        return epgState;
    }

    public void setEpgState(String epgState) {
        this.epgState = epgState;
    }

    public String getEpgSlotId() {
        return epgSlotId;
    }

    public void setEpgSlotId(String epgSlotId) {
        this.epgSlotId = epgSlotId;
    }

    public Boolean getCatchup() {
        return catchup;
    }

    public void setCatchup(Boolean catchup) {
        this.catchup = catchup;
    }

}
