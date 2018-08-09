package com.xing.imhere.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.xing.imhere.ImHereApplication;
import com.xing.imhere.R;
import com.xing.imhere.http.HttpService;
import com.xing.imhere.http.ImHereHttpUrl;
import com.xing.imhere.service.BDLocationService;
import com.xing.imhere.util.BaiDuErrorCode;
import com.xing.imhere.util.L;
import com.xing.imhere.util.T;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImHereActivity extends AppCompatActivity {
    @BindView(R.id.activity_im_here_saysth)
    EditText saysth;
    @BindView(R.id.activity_im_here_location)
    TextView location;

    private static final String TAG = "ImHereActivity";
    private LocationClient mLocationClient = null;
//    private MyLocationListener myListener = new MyLocationListener();
    private Context ctx = null;
    private com.xing.imhere.base.Message sendMsg;
    private String[] poi;
    private BDLocationService.Binder binder;
    private HttpService httpService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_here);
        ButterKnife.bind(this);
        ctx = ImHereActivity.this;

        sendMsg = new com.xing.imhere.base.Message();
        sendMsg.setAccount("15996464635");

        binder = ((ImHereApplication)getApplication()).getmBinder();
        httpService = ((ImHereApplication)getApplication()).getHttpService();

        if(binder != null&&binder.isImmediate()){
            sendMsg.setLat(binder.getLastLat());
            sendMsg.setLon(binder.getLastLon());
            poi = binder.getPoi();
            if(poi != null && poi.length > 0){
                sendMsg.setLocation(poi[0]);
                location.setText(poi[0]);
            }

        }
//        mLocationClient = new LocationClient(getApplicationContext());
//        mLocationClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setIsNeedLocationPoiList(true);
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        mLocationClient.setLocOption(option);
//        mLocationClient.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
//                case BaiDuErrorCode.LOCATION_SUCCESS_INTERNET:
//                    BDLocation loca = (BDLocation) msg.obj;
//                    sendMsg.setLat(loca.getLatitude());
//                    sendMsg.setLon(loca.getLongitude());
//                    List<Poi> poiList = loca.getPoiList();
//                    if(poiList != null && poiList.size() > 0){
//                        sendMsg.setLocation(poiList.get(0).getName());
//                        location.setText(poiList.get(0).getName());
//                        poi = new String[poiList.size()];
//                        for (int i = 0 ; i < poiList.size() ;i++)
//                            poi[i] = poiList.get(i).getName();
//                    }
//                    break;

            }

        }
    };
    @OnClick(R.id.activity_im_here_ensure)
    public void submit(){
        sendMsg.setMessage(saysth.getText().toString());
        sendMsg.setTime(new SimpleDateFormat("yy/MM/dd HH:MM").format(new Date()));
        if(sendMsg.hasNull()){
            L.e(TAG,"信息不完整 : " + sendMsg.toString());
            return;
        }

        T.s(ctx,"我要发送了！");

        Call<Object> objectCall = httpService.addMessage(sendMsg);
        objectCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                T.s(ctx,"成功");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                T.s(ctx,"失败");
            }
        });
    }

    @OnClick(R.id.activity_im_here_cancel)
    public void cancel(){
        finish();
    }

    @OnClick(R.id.activity_im_here_get_in)
    public void getIn(){
        if(poi != null && poi.length > 0 ){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this,3);
            builder.setTitle("单选")
                    .setSingleChoiceItems(poi, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            location.setText(poi[which]);
                            sendMsg.setLocation(poi[which]);
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }else{
            mLocationClient.restart();
        }
    }

//    //百度定位的回调监听
//    class MyLocationListener extends BDAbstractLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            int errorCode = location.getLocType();
//            if(errorCode == BaiDuErrorCode.LOCATION_SUCCESS_INTERNET || errorCode == BaiDuErrorCode.LOCATION_SUCCESS_GPS){
//                mHandler.obtainMessage(BaiDuErrorCode.LOCATION_SUCCESS_INTERNET,location).sendToTarget();
//            }else{
//                L.e(TAG,"location error code: " + errorCode);
//            }
//            mLocationClient.stop();
//        }
//    }
}
