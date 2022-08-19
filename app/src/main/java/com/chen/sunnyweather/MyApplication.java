package com.chen.sunnyweather;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * 一个提供全局context的工具类。
 * 代替系统的Application作为主应用的启动类
 */
public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context ;
    public static final String TOKEN = "kkvaaUaB34d617DQ"; //令牌

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();//获取应用的context，本身就只有一个，所以不存在内存溢出！
    }
}
