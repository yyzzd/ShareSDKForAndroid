package com.example.sharesdkforandroid;

import android.app.Application;

import com.davdian.service.dvdshare.CommonShareService;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CommonShareService.getInstance().init(getApplicationContext(), "b210bba5e742", "7c0658bb484ab958d0d75fefaeffddf9");
    }
}
