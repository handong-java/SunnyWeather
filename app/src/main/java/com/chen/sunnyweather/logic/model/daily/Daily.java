package com.chen.sunnyweather.logic.model.daily;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Daily {
    @SerializedName("temperature")
    private List<Temperature> temperatureList;
    @SerializedName("skycon")
    private List<Skycon> skyconList;
    @SerializedName("life_index")
    private LifeIndex lifeIndex;

    public List<Temperature> getTemperatureList() {
        return temperatureList;
    }

    public void setTemperatureList(List<Temperature> temperatureList) {
        this.temperatureList = temperatureList;
    }

    public List<Skycon> getSkyconList() {
        return skyconList;
    }

    public void setShyconList(List<Skycon> shyconList) {
        this.skyconList = shyconList;
    }

    public LifeIndex getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(LifeIndex lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

    public Daily() {
    }

    public Daily(List<Temperature> temperatureList, List<Skycon> skyconList, LifeIndex lifeIndex) {
        this.temperatureList = temperatureList;
        this.skyconList = skyconList;
        this.lifeIndex = lifeIndex;
    }
}
