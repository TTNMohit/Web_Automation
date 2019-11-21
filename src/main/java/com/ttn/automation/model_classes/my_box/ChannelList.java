package com.ttn.automation.model_classes.my_box;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChannelList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("boxCoverImage")
    @Expose
    private String boxCoverImage;
    @SerializedName("posterImage")
    @Expose
    private String posterImage;
    @SerializedName("thumbnailImage")
    @Expose
    private String thumbnailImage;
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
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("channelNumber")
    @Expose
    private String channelNumber;
    @SerializedName("dvbTriplet")
    @Expose
    private String dvbTriplet;
    @SerializedName("epg")
    @Expose
    private List<Epg> epg = null;
    @SerializedName("ott")
    @Expose
    private Boolean ott;

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

    public String getBoxCoverImage() {
        return boxCoverImage;
    }

    public void setBoxCoverImage(String boxCoverImage) {
        this.boxCoverImage = boxCoverImage;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getDvbTriplet() {
        return dvbTriplet;
    }

    public void setDvbTriplet(String dvbTriplet) {
        this.dvbTriplet = dvbTriplet;
    }

    public List<Epg> getEpg() {
        return epg;
    }

    public void setEpg(List<Epg> epg) {
        this.epg = epg;
    }

    public Boolean getOtt() {
        return ott;
    }

    public void setOtt(Boolean ott) {
        this.ott = ott;
    }

}
