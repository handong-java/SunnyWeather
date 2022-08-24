package com.chen.sunnyweather.logic.model.realtime;

public class AirQuality {
    private Aqi aqi;

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    public AirQuality(Aqi aqi) {
        this.aqi = aqi;
    }

    public AirQuality() {
    }
}
