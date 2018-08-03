package com.xing.imhere;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by xinG on 2018/8/2 0002.
 */
public class ImHereApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
