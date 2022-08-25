package com.chen.sunnyweather.logic.dao;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.chen.sunnyweather.MyApplication;
import com.chen.sunnyweather.logic.Repository;
import com.chen.sunnyweather.logic.model.place.Place;
import com.google.gson.Gson;

/**
 * Place保存类,本地数据库保存
 */
public class PlaceDao {

    private static final MutableLiveData<Place> getPlaceLiveData = Repository.getPlaceLiveData;
    private static final MutableLiveData<Place> savePlaceLiveData = Repository.savePlaceLiveData;

    public static void savePlace(Place place){
        SharedPreferences.Editor edit = sharedPreferences().edit();
        edit.putString("place", new Gson().toJson(place));
        edit.apply();
        savePlaceLiveData.postValue(place);//返回保存成功的Place.注意这是在子线程中执行的
    }

    /*public static void getPlace(){
        String placeJson = sharedPreferences().getString("place", "");
        getPlaceLiveData.postValue(new Gson().fromJson(placeJson,Place.class));
    }*/
    public static Place getPlace(){
        String placeJson = sharedPreferences().getString("place", "");
        return new Gson().fromJson(placeJson,Place.class);
    }

    public static boolean isSavedPlace(){
        return sharedPreferences().contains("place");
    }

    static private SharedPreferences sharedPreferences(){
        return MyApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE);
    }
}
