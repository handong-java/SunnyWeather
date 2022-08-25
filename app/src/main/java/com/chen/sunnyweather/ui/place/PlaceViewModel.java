package com.chen.sunnyweather.ui.place;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.chen.sunnyweather.logic.Repository;
import com.chen.sunnyweather.logic.dao.PlaceDao;
import com.chen.sunnyweather.logic.model.place.Place;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * ViewModel层
 * 职责：提供UI层获取数据层数据、缓存数据和监听对象的转换
 */
public class PlaceViewModel extends ViewModel {
    private final MutableLiveData<String> searchLiveData = new MutableLiveData<>();
    private final MutableLiveData<Place> saveLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> getLiveData = new MutableLiveData<>();
    public List<Place> placeList = new ArrayList<>();

    //作为fragment中被监听的对象
    //在网络请求响应逻辑中，改变它的值
    //调用仓库层的API进行数据获取
    public LiveData<List<Place>> placeLiveData = Transformations.switchMap(searchLiveData,
            Repository::searchPlaces);
    public LiveData<Place> savePlaceLiveData = Transformations.switchMap(saveLiveData,
            Repository::savePlace);
    /*public LiveData<Place> getPlaceLiveData = Transformations.switchMap(getLiveData,
            place -> Repository.getPlace());*/

    //提供UI层访问数据层的接口
    public void searchPlaces(String query){
        searchLiveData.setValue(query);//改变值，出发观察者：Transformations的逻辑
    }
    public void savePlace(Place place){
        saveLiveData.setValue(place);
    }
    //当处于START 和 RESUMED 这些活跃状态时，才能激活 LiveData 的使用使其通知数据变化
    //刚进入APP目前的activity还不是活跃状态
    //开启子线程逻辑也不合适：刚进入App，就去用子线程获取首页的资源，那么此时主线程无法展示，既然都要等待子线程的结果，那么不如直接在主线程中执行
    /*public void gePlace(){
        //        getLiveData.setValue("place");//生命周期问题？
    }*/
    public Place getPlace(){
        return Repository.getPlace();
    }
    public boolean isSavedPlace(){
        return Repository.isSavedPlace();
    }
}
