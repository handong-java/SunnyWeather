package com.chen.sunnyweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.chen.sunnyweather.MyApplication;

public class SharedUtils {
    private static SharedPreferences sharedPreferences = MyApplication.context.getSharedPreferences("IsFirstInstall", Context.MODE_PRIVATE);
    public static void saveFirstInstallFlag(){
        sharedPreferences.edit()
                .putBoolean("isFirstInstall",false)
                .apply();
    }
     public static boolean isFirstInstall(){
        return sharedPreferences.getBoolean("isFirstInstall",true);
    }
}
