package com.chen.sunnyweather.logic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chen.sunnyweather.logic.model.Weather;
import com.chen.sunnyweather.logic.model.daily.Daily;
import com.chen.sunnyweather.logic.model.daily.DailyResponse;
import com.chen.sunnyweather.logic.model.realtime.Realtime;
import com.chen.sunnyweather.logic.network.SunnyWeatherNetwork;
import com.chen.sunnyweather.logic.model.place.Place;

import java.util.List;

/**
 * 单实例的仓库类
 * 职责：判断请求的数据应该从网络中请求，还是从本地仓库缓存中获取
 * 这里简单起见就不设置本地缓存了，直接请求网络
 */
public class Repository {
    private static final SunnyWeatherNetwork sunnyWeatherNetwork = SunnyWeatherNetwork.getInstance();
    //Place
    //声明为static是否会内存泄漏
    public final static MutableLiveData<List<Place>> placeLiveData = SunnyWeatherNetwork.placeLiveData;//暴露给网络请求的响应逻辑中使用

    public static LiveData<List<Place>> searchPlaces(String query){
        try {
            //必须使用子线程，否则主线程会卡死在这里，方法无法返回
            new Thread(()->{
                sunnyWeatherNetwork.searchPlaces(query);
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return placeLiveData;
    }

    //weather
    public final static MutableLiveData<Weather> weatherLiveData = SunnyWeatherNetwork.weatherLiveData;
    public static LiveData<Weather> refreshWeather(String lng ,String lat){
        try{
            new Thread(()->{
                //简单方式，在子线程中顺序请求服务器API
                sunnyWeatherNetwork.getRealtimeWeather(lng,lat);
                sunnyWeatherNetwork.getDailyWeather(lng,lat);
            }).start();
        } catch (Exception e){
            e.printStackTrace();
        }
        return weatherLiveData;
    }

    private Repository() {
    }
    /**
     *
     * @return 单实例的Repository
     */
    public static Repository getInstance(){
        return RepositorySingleton.repository;
    }
    private static class RepositorySingleton {
        public final static Repository repository = new Repository();
    }
}
