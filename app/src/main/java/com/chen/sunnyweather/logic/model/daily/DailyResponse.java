package com.chen.sunnyweather.logic.model.daily;

import com.google.gson.annotations.SerializedName;

public class DailyResponse {
    private String status;
    @SerializedName("result")
    private DailyResult dailyResult;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DailyResult getDailyResult() {
        return dailyResult;
    }

    public void setDailyResult(DailyResult dailyResult) {
        this.dailyResult = dailyResult;
    }

    public DailyResponse() {
    }

    public DailyResponse(String status, DailyResult dailyResult) {
        this.status = status;
        this.dailyResult = dailyResult;
    }
}
