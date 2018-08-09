package com.xing.imhere.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.xing.imhere.util.BaiDuErrorCode;
import com.xing.imhere.util.L;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by xinG on 2018/8/8 0008.
 */
public class BDLocationService extends Service{
    private static final String TAG = "BDLocationService";
    private LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private static final int SECOND = 1000;
    private static final int IMMEDIATE_TIME = SECOND * 10;
    private double lastLat;
    private double lastLon;
    private long lastTime;
    private String[] poi;
    private Timer timer = new Timer();


    @Override
    public void onCreate() {
        L.e(TAG,"onCreate");
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedLocationPoiList(true);
        //option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocationClient.setLocOption(option);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mLocationClient.start();
            }
        },1000,10*SECOND);

    }

    @Override
    public void onDestroy() {
        timer.cancel();
        L.e(TAG,"onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    public class Binder extends android.os.Binder{
        public double getLastLat(){
            return lastLat;
        }

        public double getLastLon(){
            return lastLon;
        }

        public String[] getPoi(){
            return poi;
        }
        /**检测上次更新时间，
         * 如果与当前时间差超过10s则返回false,反之返回true
         **/
        public boolean isImmediate(){
            return (new Date().getTime() - lastTime ) <= IMMEDIATE_TIME;
        }
    }

    //百度定位的回调监听
    class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            int errorCode = location.getLocType();
            if(errorCode == BaiDuErrorCode.LOCATION_SUCCESS_INTERNET || errorCode == BaiDuErrorCode.LOCATION_SUCCESS_GPS){
                //mHandler.obtainMessage(BaiDuErrorCode.LOCATION_SUCCESS_INTERNET,location).sendToTarget();
                L.e(TAG,"location success :" + location.getLatitude() +","+ location.getLongitude());
                lastLat = location.getLatitude();
                lastLon = location.getLongitude();
                lastTime = new Date().getTime();
                List<Poi> poiList = location.getPoiList();
                poi = new String[poiList.size()];
                for (int i = 0 ; i < poiList.size() ;i++)
                    poi[i] = poiList.get(i).getName();
            }else{
                L.e(TAG,"location error code: " + errorCode);
            }
            mLocationClient.stop();
        }
    }


}
