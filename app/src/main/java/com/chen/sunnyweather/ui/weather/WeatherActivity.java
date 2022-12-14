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
        //?????????????????????????????????
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);   //???????????????

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

        //????????????????????????
        weatherViewModel.weatherLiveData.observe(this,weather->{
            if (weather!=null){
                showWeatherInfo(weather);
            } else {
                Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);//?????????????????????????????????????????????
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.purple_700);
        refreshWeather();//???????????????????????????????????????
        //??????????????????????????????
        swipeRefreshLayout.setOnRefreshListener(this::refreshWeather);

        //????????????
        navBtn.setOnClickListener(v->{
            drawerLayout.openDrawer(GravityCompat.START);//??????????????????
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            //???????????????????????????????????????
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
        //??????now.xml???????????????
        String currentTempText = realtime.getTemperature()+"???";
        currentTemp.setText(currentTempText);   //??????
        currentSky.setText(new Sky().getSky(realtime.getSkycon()).getInfo());   //????????????
        String currentPM25Text = "???????????? "+realtime.getAirQuality().getAqi().getChn();
        currentAQI.setText(currentPM25Text);    //????????????
        nowLayout.setBackgroundResource(new Sky().getSky(realtime.getSkycon()).getBg());    //??????
        //??????forecast.xml???????????????
        forecastLayout.removeAllViews();    //?????????????????????
        int days = daily.getSkyconList().size();
        for (int i = 0; i < days; i++) {
            Skycon skycon = daily.getSkyconList().get(i);
            Temperature temperature = daily.getTemperatureList().get(i);
            View forecastItemView = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            //??????????????????????????????View?????????????????????????????????????????????????????????????????????????????????
            TextView dataInfo = forecastItemView.findViewById(R.id.dateInfo);
            ImageView skyIcon= forecastItemView.findViewById(R.id.skyIcon);
            TextView skyInfo = forecastItemView.findViewById(R.id.skyInfo);
            TextView temperatureInfo= forecastItemView.findViewById(R.id.temperatureInfo);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dataInfo.setText(dateFormat.format(skycon.getDate()));  //??????
            Sky sky = new Sky().getSky(skycon.getValue());
            skyIcon.setImageResource(sky.getIcon());    //????????????
            skyInfo.setText(sky.getInfo()); //????????????
            String tempText = temperature.getMin()+"~"+temperature.getMax()+"???";
            temperatureInfo.setText(tempText);  //????????????
            forecastLayout.addView(forecastItemView);
        }
        //??????life_index.xml???????????????
        LifeIndex lifeIndex = daily.getLifeIndex();
        coldRiskText.setText(lifeIndex.getColdRisk().get(0).getDesc());
        dressingText.setText(lifeIndex.getDressing().get(0).getDesc());
        ultravioletText.setText(lifeIndex.getUltraviolet().get(0).getDesc());
        carWashingText.setText(lifeIndex.getCarWashing().get(0).getDesc());
        //???weatherLayout????????????
        weatherLayout.setVisibility(View.VISIBLE);
    }
}