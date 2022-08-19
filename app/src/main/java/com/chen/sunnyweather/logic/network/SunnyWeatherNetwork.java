package com.chen.sunnyweather.logic.network;

import com.chen.sunnyweather.ui.place.pojo.PlaceResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 统一的网络数据访问入口
 * 单实例类
 */
public class SunnyWeatherNetwork {
    private final PlaceService placeService = ServiceCreator.getInstance().create(PlaceService.class);

    /**
     * 发起搜索城市的网络请求
     * @param query：城市名
     * @return 响应经过Retrofit解析之后的List<PlaceResponse>集合
     */
    public List<PlaceResponse> searchPlaces(String query){
        final List<PlaceResponse> placeResponseList = new ArrayList<>();
        placeService.searchPlaces(query).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                if (response.body()!=null) {
                    PlaceResponse placeResponse = response.body();
                    placeResponseList.add(placeResponse);
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {

            }
        });
        return placeResponseList;
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
