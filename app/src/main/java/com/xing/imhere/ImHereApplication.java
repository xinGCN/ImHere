package com.xing.imhere;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.baidu.mapapi.SDKInitializer;
import com.xing.imhere.http.HttpService;
import com.xing.imhere.http.ImHereHttpUrl;
import com.xing.imhere.service.BDLocationService;
import com.xing.imhere.util.L;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xinG on 2018/8/2 0002.
 */
public class ImHereApplication extends Application implements ServiceConnection{
    private Intent service;
    private BDLocationService.Binder mBinder;

    private Retrofit retrofit;
    private HttpService httpService;

    private static final String TAG = "ImHereApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        L.e(TAG,"onCreate");
        SDKInitializer.initialize(getApplicationContext());

        //初始化定位服务
        service = new Intent(this,BDLocationService.class);
        bindService(service, (ServiceConnection) this, Context.BIND_AUTO_CREATE);

        //初始化网络
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ImHereHttpUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        httpService = retrofit.create(HttpService.class);
        L.e(TAG,"httpService init with " + httpService);
    }

    public void stopService() {
        unbindService((ServiceConnection) this);
    }

    public BDLocationService.Binder getmBinder() {
        return mBinder;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public HttpService getHttpService() {
        //L.e(TAG,"httpService init with " + httpService);
        return httpService;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        L.e(TAG,"onServiceConnected");
        mBinder = (BDLocationService.Binder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        L.e(TAG,"onServiceDisconnected");
    }
}
