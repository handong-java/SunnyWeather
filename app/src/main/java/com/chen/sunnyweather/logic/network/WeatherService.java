package com.chen.sunnyweather.logic.network;

import com.chen.sunnyweather.MyApplication;
import com.chen.sunnyweather.logic.model.daily.Daily;
import com.chen.sunnyweather.logic.model.daily.DailyResponse;
import com.chen.sunnyweather.logic.model.realtime.RealtimeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {
    @GET("v2.5/"+ MyApplication.TOKEN+"/{lng},{lat}/realtime.json")
    Call<RealtimeResponse> getRealtimeWeather(@Path("lng") String lng , @Path("lat") String lat);
    @GET("v2.5/"+MyApplication.TOKEN+"/{lng},{lat}/daily.json")
    Call<DailyResponse> getDailyWeather(@Path("lng") String lng , @Path("lat") String lat);
}
