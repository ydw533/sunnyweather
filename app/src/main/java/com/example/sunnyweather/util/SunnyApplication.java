package com.example.sunnyweather.util;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

public class SunnyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    @NonNull
    public static Context getContext() {
        return context;
    }
}
