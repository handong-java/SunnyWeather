package com.chen.sunnyweather.logic.network;

import com.chen.sunnyweather.MyApplication;
import com.chen.sunnyweather.logic.model.place.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 访问彩云天气城市搜索API的retrofit接口，命名习惯就是XXXService
 */
public interface PlaceService {
    /**
     *
     * @param query 需要搜索的城市名
     * @return Call<PlaceResponse> 会将响应的JSON字符串解析为PlaceResponse对象
     */
    @GET("v2/place?token="+ MyApplication.TOKEN +"&lang=zh_CN")
    public Call<PlaceResponse> searchPlaces(@Query("query") String query);
}
