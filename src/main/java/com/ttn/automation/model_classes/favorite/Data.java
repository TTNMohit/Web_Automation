package com.ttn.automation.model_classes.favorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("pagingState")
    @Expose
    private String pagingState;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("list")
    @Expose
    private List<list> list = null;

    public String getPagingState() {
        return pagingState;
    }

    public void setPagingState(String pagingState) {
        this.pagingState = pagingState;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<com.ttn.automation.model_classes.favorite.list> getList() {
        return list;
    }

    public void setList(List<com.ttn.automation.model_classes.favorite.list> list) {
        this.list = list;
    }
}