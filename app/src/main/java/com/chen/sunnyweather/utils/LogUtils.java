package com.chen.sunnyweather.utils;

import android.util.Log;

public class LogUtils {
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static int LEVEL = VERBOSE;

    private LogUtils(){

    }

    public static void logV(String tag ,String msg){
        Log.v(tag, msg);
    }
    public static void logD(String tag ,String msg){
        Log.d(tag, msg);
    }
    public static void logI(String tag ,String msg){
        Log.i(tag, msg);
    }
    public static void logW(String tag ,String msg){
        Log.w(tag, msg);
    }
    public static void logE(String tag ,String msg){
        Log.e(tag, msg);
    }
}
