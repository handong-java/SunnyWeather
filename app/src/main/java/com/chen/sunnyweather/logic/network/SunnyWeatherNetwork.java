package com.chen.sunnyweather.logic.network;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.chen.sunnyweather.MyApplication;
import com.chen.sunnyweather.logic.Repository;
import com.chen.sunnyweather.logic.model.Weather;
import com.chen.sunnyweather.logic.model.daily.Daily;
import com.chen.sunnyweather.logic.model.daily.DailyResponse;
import com.chen.sunnyweather.logic.model.place.Place;
import com.chen.sunnyweather.logic.model.place.PlaceResponse;
import com.chen.sunnyweather.logic.model.realtime.Realtime;
import com.chen.sunnyweather.logic.model.realtime.RealtimeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 统一的网络数据访问入口
 * 单实例类
 */
public class SunnyWeatherNetwork {
    //获得网络请求的动态代理
    private final PlaceService placeService = ServiceCreator.getInstance().create(PlaceService.class);
    private PlaceResponse placeResponse;
    public final static MutableLiveData<List<Place>> placeLiveData = Repository.placeLiveData;//

    private final WeatherService weatherService = ServiceCreator.getInstance().create(WeatherService.class);
    private RealtimeResponse realtimeResponse;
    private DailyResponse dailyResponse;
    public final static MutableLiveData<Weather> weatherLiveData = Repository.weatherLiveData;



    /**
     * 发起搜索城市的网络请求
     * @param query：城市名
     * @return 响应经过Retrofit解析之后的List<PlaceResponse>集合
     */
    public void searchPlaces(String query){
        //searchPlace
        placeService.searchPlaces(query).enqueue(new Callback<PlaceResponse>() {    //会在内部开启一个子线程来发起请求
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                if (response.body()!=null) {
                    placeResponse = response.body();

                    if (placeResponse.getStatus().equals("ok")){
                        //核心：收到服务器响应后。改变LiveData的值，出发观察者逻辑
                        placeLiveData.setValue(placeResponse.getPlaces());//可以证明响应函数会回到主线程中执行
                    }else{
                        placeLiveData.setValue(new ArrayList<>());//赋空值也可以触发观察者逻辑
                        Log.d("Repository: response status is ",placeResponse.getStatus());
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {

            }
        });
    }

    //getRealtime
    public void getRealtimeWeather(String lng ,String lat){
        weatherService.getRealtimeWeather(lng,lat).enqueue(new Callback<RealtimeResponse>() {
            @Override
            public void onResponse(Call<RealtimeResponse> call, Response<RealtimeResponse> response) {
                RealtimeResponse body = response.body();
                if (body!=null){
                    realtimeResponse = body;

                }
            }

            @Override
            public void onFailure(Call<RealtimeResponse> call, Throwable t) {

            }
        });
    }

    //getDaily
    public void getDailyWeather(String lng ,String lat){
        weatherService.getDailyWeather(lng,lat).enqueue(new Callback<DailyResponse>() {
            @Override
            public void onResponse(Call<DailyResponse> call, Response<DailyResponse> response) {
                DailyResponse body = response.body();
                if (body!=null){
                    dailyResponse = body;
                    if (Objects.equals(dailyResponse.getStatus(), "ok") && Objects.equals(realtimeResponse.getStatus(), "ok")){
                        weatherLiveData.setValue(
                                new Weather(realtimeResponse.getRealtimeResult().getRealtime(),
                                dailyResponse.getDailyResult().getDaily()));
                    } else {
                        weatherLiveData.setValue(new Weather());
                        Log.d("Repository: response status is ",
                                "dailyResponse: "+dailyResponse.getStatus()
                                        +"realtimeResponse: "+realtimeResponse.getStatus());
                    }
                }
            }

            @Override
            public void onFailure(Call<DailyResponse> call, Throwable t) {

            }
        });
    }

    private SunnyWeatherNetwork() {
    }
    /**
     *
     * @return 单实例的SunnyWeatherNetwork
     */
    public static SunnyWeatherNetwork getInstance(){
        return SingletonSunnyWeatherNetWork.sunnyWeatherNetwork;
    }
    public static class SingletonSunnyWeatherNetWork {
        private final static SunnyWeatherNetwork sunnyWeatherNetwork = new SunnyWeatherNetwork();
    }
}
