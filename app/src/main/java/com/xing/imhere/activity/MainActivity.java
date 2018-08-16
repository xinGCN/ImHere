package com.xing.imhere.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSNSChangeInfo;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.ext.sns.TIMFriendshipProxyListener;
import com.tencent.imsdk.ext.sns.TIMUserConfigSnsExt;
import com.xing.imhere.ImHereApplication;
import com.xing.imhere.R;
import com.xing.imhere.fragment.MessageFragment;
import com.xing.imhere.fragment.UserFragment;
import com.xing.imhere.fragment.WhocameFragment;
import com.xing.imhere.util.Global;
import com.xing.imhere.util.T;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_bottom_navigation)
    BottomNavigationView bottomView;
    @BindView(R.id.activity_main_fab)
    FloatingActionButton fab;

    private int where = STATE_WHOCAME;
    private static final int STATE_WHOCAME = 1;
    private static final int STATE_USER = 2;

    private Context ctx;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().add(R.id.activity_main_fragment,new WhocameFragment()).commit();
        ctx = this;
        bottomView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_navigation_whocame:
                    //T.s(ctx,"Im whocame");
                    if(where != STATE_WHOCAME){
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment,new WhocameFragment()).commit();
                        fab.setVisibility(View.VISIBLE);
                        where = STATE_WHOCAME;
                    }
                    break;
                case R.id.bottom_navigation_user:
                    //T.s(ctx,"Im user");
                    if(where!=STATE_USER){
//                            getFragmentManager().beginTransaction().replace(R.id.activity_main_fragment,new UserFragment()).commit();
//                            fab.setVisibility(View.INVISIBLE);
//                            where = STATE_USER;
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment,new MessageFragment()).commit();
                        fab.setVisibility(View.INVISIBLE);
                        where = STATE_USER;
                    }
                    break;
            }
            return false;
        });
        
        initTencent();
    }

    private void initTencent() {

        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置群组资料拉取字段
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.e(TAG, "onForceOffline");
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新 userSig 重新登录 SDK
                        Log.e(TAG, "onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.e(TAG, "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.e(TAG, "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.e(TAG, "onWifiNeedAuth");
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.e(TAG, "onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        Log.e(TAG, "onRefreshConversation, conversation size: " + conversations.size());
                    }
                });

//        //消息扩展用户配置
//        userConfig = new TIMUserConfigMsgExt(userConfig)
//                //禁用消息存储
//                .enableStorage(false)
//                //开启消息已读回执
//                .enableReadReceipt(true);

        //将用户配置与通讯管理器进行绑定

        TIMManager.getInstance().setUserConfig(userConfig);
    }

    @OnClick(R.id.activity_main_fab)
    public void imHere(){
        startActivity(new Intent(ctx,ImHereActivity.class));
    }


    @Override
    protected void onDestroy() {
        ((ImHereApplication)getApplication()).stopService();
        super.onDestroy();
    }
}
