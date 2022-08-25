package com.chen.sunnyweather.logic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chen.sunnyweather.logic.dao.PlaceDao;
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
 */
public class Repository {
    //Places
    //声明为static是否会内存泄漏
    public final static MutableLiveData<List<Place>> placeLiveData = new MutableLiveData<>();
    public final static MutableLiveData<Weather> weatherLiveData = new MutableLiveData<>();
    public static MutableLiveData<Place> getPlaceLiveData = new MutableLiveData<>();
    public static MutableLiveData<Place> savePlaceLiveData = new MutableLiveData<>();

    //注意不能先获取SunnyWeatherNetwork，否则加载这个类的时候static属性初始化为空
    private static final SunnyWeatherNetwork sunnyWeatherNetwork = SunnyWeatherNetwork.getInstance();

    public static LiveData<List<Place>> searchPlaces(String query){
        try {
            //必须使用子线程，否则主线程会卡死在这里，方法无法返回
            new Thread(()->{
                sunnyWeatherNetwork.searchPlaces(query);
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return placeLiveData;//会直接返回，不会先处理子线程逻辑
    }

    //weather
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

    //从本地数据库中操作Place
        //
    public static LiveData<Place> savePlace(Place place){
        try {
            new Thread(()->{
                PlaceDao.savePlace(place);
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savePlaceLiveData;
    }

    /*public static LiveData<Place> getPlace(){
        try {
            new Thread(PlaceDao::getPlace).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getPlaceLiveData;
    }*/
    public static Place getPlace(){
        return PlaceDao.getPlace();
    }
    public static boolean isSavedPlace(){
        return PlaceDao.isSavedPlace();
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
