package com.chen.sunnyweather.logic.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 单实例的ServiceCreator类
 * 获取Retrofit的工具类
 */
public class ServiceCreator {
    private final String BASE_URL = "https://api.caiyunapp.com/";
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     *
     * @return 单实例的ServiceCreator
     */
    public static ServiceCreator getInstance(){
        return SingletonServiceCreator.serviceCreator;
    }

    public static class SingletonServiceCreator{
        private static final ServiceCreator serviceCreator = new ServiceCreator();
    }

    /**
     *
     * @param serviceClass
     * @param <T>
     * @return 返回一个ServiceClass接口的动态代理
     */
    public <T> T create(Class<T> serviceClass){
        return retrofit.create(serviceClass);
    }
}
