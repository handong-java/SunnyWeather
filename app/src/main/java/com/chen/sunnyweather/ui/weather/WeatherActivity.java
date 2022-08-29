package com.chen.sunnyweather.ui.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.sunnyweather.R;
import com.chen.sunnyweather.Sky;
import com.chen.sunnyweather.logic.model.Weather;
import com.chen.sunnyweather.logic.model.daily.Daily;
import com.chen.sunnyweather.logic.model.daily.LifeIndex;
import com.chen.sunnyweather.logic.model.daily.Skycon;
import com.chen.sunnyweather.logic.model.daily.Temperature;
import com.chen.sunnyweather.logic.model.realtime.Realtime;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {
    private TextView placeName;
    private TextView currentTemp;
    private TextView currentSky;
    private TextView currentAQI;
    private RelativeLayout nowLayout;
    private LinearLayout forecastLayout;
    private TextView coldRiskText;
    private TextView dressingText;
    private TextView ultravioletText;
    private TextView carWashingText;
    private ScrollView weatherLayout;
    public WeatherViewModel weatherViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawerLayout;
    private Button navBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //设置背景图和状态栏融合
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);   //设置为透明

        initView();

        if (weatherViewModel.lng.isEmpty()){
            weatherViewModel.lng = getIntent().getStringExtra("location_lng");
        }
        if (weatherViewModel.lat.isEmpty()){
            weatherViewModel.lat = getIntent().getStringExtra("location_lat");
        }
        if (weatherViewModel.placeName.isEmpty()){
            weatherViewModel.placeName = getIntent().getStringExtra("place_name");
        }

        //观察网络请求结果
        weatherViewModel.weatherLiveData.observe(this,weather->{
            if (weather!=null){
                showWeatherInfo(weather);
            } else {
                Toast.makeText(this, "无法获取天气信息", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);//收到请求结果后需要关闭刷新图标
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.purple_700);
        refreshWeather();//跳转过来的时候自动刷新一次
        //下拉刷新触发网络请求
        swipeRefreshLayout.setOnRefreshListener(this::refreshWeather);

        //滑动菜单
        navBtn.setOnClickListener(v->{
            drawerLayout.openDrawer(GravityCompat.START);//打开方向位置
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            //关闭的时候将输入法一起关闭
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(drawerView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    public void refreshWeather() {
        weatherViewModel.refreshWeather(weatherViewModel.lng, weatherViewModel.lat);
        swipeRefreshLayout.setRefreshing(true);
    }

    private void initView() {
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        placeName = findViewById(R.id.placeName);
        currentTemp = findViewById(R.id.currentTemp);
        currentSky = findViewById(R.id.currentSky);
        currentAQI = findViewById(R.id.currentAQI);
        nowLayout = findViewById(R.id.nowLayout);
        forecastLayout = findViewById(R.id.forecastLayout);
        coldRiskText = findViewById(R.id.coldRiskText);
        dressingText = findViewById(R.id.dressingText);
        ultravioletText = findViewById(R.id.ultravioletText);
        carWashingText = findViewById(R.id.carWashingText);
        weatherLayout = findViewById(R.id.weatherLayout);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        drawerLayout = findViewById(R.id.drawerLayout);
        navBtn = findViewById(R.id.navBtn);
    }

    private void showWeatherInfo(Weather weather) {
        placeName.setText(weatherViewModel.placeName);
        Realtime realtime = weather.getRealtime();
        Daily daily = weather.getDaily();
        //填充now.xml布局中数据
        String currentTempText = realtime.getTemperature()+"℃";
        currentTemp.setText(currentTempText);   //温度
        currentSky.setText(new Sky().getSky(realtime.getSkycon()).getInfo());   //天气状况
        String currentPM25Text = "空气指数 "+realtime.getAirQuality().getAqi().getChn();
        currentAQI.setText(currentPM25Text);    //空气指数
        nowLayout.setBackgroundResource(new Sky().getSky(realtime.getSkycon()).getBg());    //背景
        //填充forecast.xml布局中数据
        forecastLayout.removeAllViews();    //移除原有的数据
        int days = daily.getSkyconList().size();
        for (int i = 0; i < days; i++) {
            Skycon skycon = daily.getSkyconList().get(i);
            Temperature temperature = daily.getTemperatureList().get(i);
            View forecastItemView = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            //注意要使用需要填充的View来获取组件，因为这些组件目前是没有显示的，是动态填充的
            TextView dataInfo = forecastItemView.findViewById(R.id.dateInfo);
            ImageView skyIcon= forecastItemView.findViewById(R.id.skyIcon);
            TextView skyInfo = forecastItemView.findViewById(R.id.skyInfo);
            TextView temperatureInfo= forecastItemView.findViewById(R.id.temperatureInfo);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dataInfo.setText(dateFormat.format(skycon.getDate()));  //时间
            Sky sky = new Sky().getSky(skycon.getValue());
            skyIcon.setImageResource(sky.getIcon());    //天气图标
            skyInfo.setText(sky.getInfo()); //天气信息
            String tempText = temperature.getMin()+"~"+temperature.getMax()+"℃";
            temperatureInfo.setText(tempText);  //温度范围
            forecastLayout.addView(forecastItemView);
        }
        //填充life_index.xml布局的数据
        LifeIndex lifeIndex = daily.getLifeIndex();
        coldRiskText.setText(lifeIndex.getColdRisk().get(0).getDesc());
        dressingText.setText(lifeIndex.getDressing().get(0).getDesc());
        ultravioletText.setText(lifeIndex.getUltraviolet().get(0).getDesc());
        carWashingText.setText(lifeIndex.getCarWashing().get(0).getDesc());
        //将weatherLayout显示出来
        weatherLayout.setVisibility(View.VISIBLE);
    }
}