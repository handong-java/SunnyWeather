package com.chen.sunnyweather.ui.place;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.chen.sunnyweather.logic.Repository;
import com.chen.sunnyweather.logic.model.place.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel层
 * 职责：提供UI层获取数据层数据、缓存数据和监听对象的转换
 */
public class PlaceViewModel extends ViewModel {
    private final MutableLiveData<String> searchLiveData = new MutableLiveData<>();
    public List<Place> placeList = new ArrayList<>();

    //作为fragment中被监听的对象
    //在网络请求响应逻辑中，改变它的值
    //调用仓库层的API进行数据获取
    public LiveData<List<Place>> placeLiveData = Transformations.switchMap(searchLiveData,
            Repository::searchPlaces);

    //提供UI层访问数据层的接口
    public void searchPlaces(String query){
        searchLiveData.setValue(query);//改变值，出发观察者：Transformations的逻辑
    }
}
