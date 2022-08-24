package com.chen.sunnyweather.logic.model.daily;

public class DailyResult {
    private Daily daily;

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public DailyResult() {
    }

    public DailyResult(Daily daily) {
        this.daily = daily;
    }
}
