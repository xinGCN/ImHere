package com.xing.imhere.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.xing.imhere.R;
import com.xing.imhere.http.HttpService;
import com.xing.imhere.util.BaiDuErrorCode;
import com.xing.imhere.util.L;
import com.xing.imhere.util.T;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_textview) TextView message;
    @BindView(R.id.activity_main_saysth) EditText saySth;
    @BindView(R.id.activity_main_where) TextView where;

    private static final int CHOOSE_ITEM = 1;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private Context ctx = null;
    private com.xing.imhere.base.Message sendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ctx = MainActivity.this;
        sendMsg = new com.xing.imhere.base.Message();
        sendMsg.setAccount("15996464635");

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedLocationPoiList(true);
        //可选，是否需要周边POI信息，默认为不需要，即参数为false
        //如果开发者需要获得周边POI信息，此处必须为true
        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        //mLocationClient.start();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case BaiDuErrorCode.LOCATION_SUCCESS_INTERNET:
                    StringBuilder strb = new StringBuilder();
                    BDLocation location = (BDLocation) msg.obj;
                    strb.append("latitude"+location.getLatitude());
                    strb.append("\nlongitude:" + location.getLongitude());
                    strb.append("\nradius:" + location.getRadius());
                    where.setText(strb.toString());
                    sendMsg.setLocation_1(location.getLatitude() + "," + location.getLongitude());

                    List<Poi> poiList = location.getPoiList();
                    String[] items = new String[poiList.size()];
                    for(int i = 0 ; i < poiList.size() ;i++)
                        items[i] = poiList.get(i).getName();
                    dialogChoice(items);
                    break;

                case CHOOSE_ITEM:
                    where.setText(where.getText().toString() + '\n' + msg.obj);
                    break;
            }

        }
    };


    @OnClick(R.id.activity_main_submit)
    public void send(){
        if(where.getText().toString().equals("")){
            mLocationClient.start();
        }else{
            T.s(ctx,"我要发送了！");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://169.254.218.145:8080/imheres/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            HttpService httpService = retrofit.create(HttpService.class);

            sendMsg.setMessage(saySth.getText().toString());
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
    }


    //百度定位的回调监听
    class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            int errorCode = location.getLocType();
            if(errorCode == BaiDuErrorCode.LOCATION_SUCCESS_INTERNET){
                mHandler.obtainMessage(BaiDuErrorCode.LOCATION_SUCCESS_INTERNET,location).sendToTarget();
            }else{
                L.e("location error code: " + errorCode);
            }
            mLocationClient.stop();
        }
    }

    private void dialogChoice(final String items[]) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,3);
        builder.setTitle("单选")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHandler.obtainMessage(CHOOSE_ITEM,items[which]).sendToTarget();
                        sendMsg.setLocation_2(items[which]);
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
