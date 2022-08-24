package com.chen.sunnyweather.ui.place;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chen.sunnyweather.R;


public class PlaceFragment extends Fragment {
    private PlaceViewModel placeViewModel;
    private PlaceAdapter adapter;
    private EditText searchPlaceEdit;
    private ImageView placeBG;
    private RecyclerView recyclerView;

    void init(){
        placeViewModel = new ViewModelProvider(this).get(PlaceViewModel.class);
        searchPlaceEdit = getActivity().findViewById(R.id.searchPlaceEdit);
        placeBG = getActivity().findViewById(R.id.PlaceBG);
        recyclerView = getActivity().findViewById(R.id.placeRecycleView);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        init();//不能在onCreateView()方法中调用，否则getActivity()出现空值，所以控件的初始化为空值
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PlaceAdapter(this,placeViewModel.placeList);
        recyclerView.setAdapter(adapter);

        searchPlaceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!"".equals(s.toString())){
                    placeViewModel.searchPlaces(s.toString());//给LiveData的value赋值，即setValue()
                }else{
                    //用户输入“”时相当于清空操作
                    recyclerView.setVisibility(View.GONE);
                    placeBG.setVisibility(View.VISIBLE);
                    placeViewModel.placeList.clear();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //观察数据变化
        placeViewModel.placeLiveData.observe(getViewLifecycleOwner(), places -> {
            if (places!=null){
                recyclerView.setVisibility(View.VISIBLE);
                placeBG.setVisibility(View.GONE);
                placeViewModel.placeList.clear();
                placeViewModel.placeList.addAll(places);
                adapter.notifyDataSetChanged(); //显示查询结果
            } else {
                Toast.makeText(getActivity(), "查询结果为空,请更换城市", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
