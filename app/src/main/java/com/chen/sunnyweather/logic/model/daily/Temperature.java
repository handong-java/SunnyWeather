package com.chen.sunnyweather.logic.model.daily;

public class Temperature {
    private String max;
    private String min;

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public Temperature() {
    }

    public Temperature(String max, String min) {
        this.max = max;
        this.min = min;
    }
}
