package com.chen.sunnyweather.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.chen.sunnyweather.logic.Repository;
import com.chen.sunnyweather.logic.model.Weather;
import com.chen.sunnyweather.logic.model.place.Location;

public class WeatherViewModel extends ViewModel {
    private MutableLiveData<Location> locationLiveData = new MutableLiveData<>();

    public String lng = "";//赋值为""就不是null，否则第一次跳转WeatherActivity会空指针
    public String lat = "";
    public String placeName = "";

    public LiveData<Weather> weatherLiveData = Transformations.switchMap(locationLiveData,
            location-> Repository.refreshWeather(location.getLng(),location.getLat()));

    public void refreshWeather(String lng ,String lat){
        locationLiveData.setValue(new Location(lng,lat));
    }
}
