package com.chen.sunnyweather.ui.place.pojo;

import java.util.List;

/**
 * 地区响应的接收类
 */
public class PlaceResponse {
    private String status;  //代表请求状态
    private List<Place> places; //请求城市的结果集

    public PlaceResponse(String status, List<Place> places) {
        this.status = status;
        this.places = places;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "PlaceResponse{" +
                "status='" + status + '\'' +
                ", places=" + places +
                '}';
    }
}
