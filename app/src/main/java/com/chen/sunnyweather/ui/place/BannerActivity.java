package com.chen.sunnyweather.ui.place;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chen.sunnyweather.utils.SharedUtils;
import com.chen.sunnyweather.logic.model.DataBean;
import com.chen.sunnyweather.MainActivity;
import com.chen.sunnyweather.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

public class BannerActivity extends AppCompatActivity {
    private Banner<DataBean,BannerImageAdapter<DataBean>> banner;
    private Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        initView();
        //首次安装应用的引导轮播图
        if (SharedUtils.isFirstInstall()) {
            SharedUtils.saveFirstInstallFlag();
            initData();
        } else {
            banner.setVisibility(View.GONE);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void initData() {
        //banner自带的图片轮播适配器
        banner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData()) {
            @Override
            public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
                Glide.with(holder.imageView)
                        .load(data.imageId)
                        .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))//加载成功前显示一个loading的加载
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))//圆角
                        .into(holder.imageView);
            }
        }).addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIntercept(false) //是否要拦截事件
                .setBannerRound(10f) //圆角
                .setIndicator(new CircleIndicator(this)) //圆形指示器 还支持条形指示器等
                .setOnBannerListener((data, position) ->
                        Toast.makeText(BannerActivity.this,"位置"+position+"",Toast.LENGTH_SHORT).show()) ;
    }

    private void initView() {
        banner = findViewById(R.id.banner);
        skipButton = findViewById(R.id.skipButton);
        skipButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
//        button.getBackground().setAlpha(0);//设置按钮背景透明，但是会有奇怪的轮廓
    }
}