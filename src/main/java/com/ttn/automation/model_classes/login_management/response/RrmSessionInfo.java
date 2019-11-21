package com.ttn.automation.model_classes.login_management.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RrmSessionInfo {

    @SerializedName("sessionId")
    @Expose
    private String sessionId;
    @SerializedName("sessionTicket")
    @Expose
    private String sessionTicket;
    @SerializedName("ticket")
    @Expose
    private String ticket;
    @SerializedName("nonce")
    @Expose
    private long nonce;
    @SerializedName("expiryTime")
    @Expose
    private long expiryTime;
    @SerializedName("heartbeatInterval")
    @Expose
    private Integer heartbeatInterval;
    @SerializedName("maxMissedHeartbeats")
    @Expose
    private Integer maxMissedHeartbeats;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionTicket() {
        return sessionTicket;
    }

    public void setSessionTicket(String sessionTicket) {
        this.sessionTicket = sessionTicket;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Integer getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(Integer heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public Integer getMaxMissedHeartbeats() {
        return maxMissedHeartbeats;
    }

    public void setMaxMissedHeartbeats(Integer maxMissedHeartbeats) {
        this.maxMissedHeartbeats = maxMissedHeartbeats;
    }

}
