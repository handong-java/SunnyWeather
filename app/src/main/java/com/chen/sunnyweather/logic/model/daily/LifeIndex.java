package com.chen.sunnyweather.logic.model.daily;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LifeIndex {
    @SerializedName("coldRisk")
    private List<Description> coldRisk;
    @SerializedName("carWashing")
    private List<Description> carWashing;
    @SerializedName("ultraviolet")
    private List<Description> ultraviolet;
    @SerializedName("dressing")
    private List<Description> dressing;

    public LifeIndex(List<Description> coldRisk, List<Description> carWashing, List<Description> ultraviolet, List<Description> dressing) {
        this.coldRisk = coldRisk;
        this.carWashing = carWashing;
        this.ultraviolet = ultraviolet;
        this.dressing = dressing;
    }

    public List<Description> getColdRisk() {
        return coldRisk;
    }

    public void setColdRisk(List<Description> coldRisk) {
        this.coldRisk = coldRisk;
    }

    public List<Description> getCarWashing() {
        return carWashing;
    }

    public void setCarWashing(List<Description> carWashing) {
        this.carWashing = carWashing;
    }

    public List<Description> getUltraviolet() {
        return ultraviolet;
    }

    public void setUltraviolet(List<Description> ultraviolet) {
        this.ultraviolet = ultraviolet;
    }

    public List<Description> getDressing() {
        return dressing;
    }

    public void setDressing(List<Description> dressing) {
        this.dressing = dressing;
    }

    public LifeIndex() {
    }
}
