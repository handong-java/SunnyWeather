package com.chen.sunnyweather.logic.model.realtime;

import com.google.gson.annotations.SerializedName;

public class RealtimeResponse {
    private String status;
    @SerializedName("result")
    private RealtimeResult realtimeResult;

    public RealtimeResponse() {
    }

    public RealtimeResponse(String status, RealtimeResult realtimeResult) {
        this.status = status;
        this.realtimeResult = realtimeResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RealtimeResult getRealtimeResult() {
        return realtimeResult;
    }

    public void setRealtimeResult(RealtimeResult realtimeResult) {
        this.realtimeResult = realtimeResult;
    }
}
