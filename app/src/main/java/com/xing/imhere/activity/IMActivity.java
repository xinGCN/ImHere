package com.xing.imhere.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSNSChangeInfo;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUser;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupAssistantListener;
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo;
import com.tencent.imsdk.ext.group.TIMUserConfigGroupExt;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.ext.sns.TIMFriendGroup;
import com.tencent.imsdk.ext.sns.TIMFriendshipProxyListener;
import com.tencent.imsdk.ext.sns.TIMUserConfigSnsExt;
import com.xing.imhere.R;
import com.xing.imhere.util.L;

import java.util.List;

public class IMActivity extends AppCompatActivity {
    private TIMManager timManager = TIMManager.getInstance();
    private static final String tag = "IMActivity";
    private static final String userSig = "eJxlzlFPgzAQwPF3PgXhdUZLSzGY7KE4SM1kMqfOPTWEdnhToYNuiMbvvolGSbx7-P*Suw-Ltm3n7npxmuV5tSuNMJ1Wjn1hO8g5*YtagxSZEaSW-6J601Arka2NqvvoUkoxQkMDUpUG1vArgsD3jkvoADXyWfSXvo2HkIspCrwhgaKPSTS-vAql*4Lj97OHJp3cRKvH4HaSMN6hguwgXLZTzuMZ38KT3kgGbMO47*dJ6YWNljHrQrxYkjbeR1PZ8HS0uh-pWdXOy3RbjMeDkwZe1c-TxMPBOcZ4UPeqbqAqe4CRS11M0Nc41qd1AM6LXOU_";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);

        timManager.addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                //消息的内容解析请参考消息解析
                Log.e(tag,"onNewMessages");
                for (TIMMessage msg:list) {
                    for(int i = 0; i < msg.getElementCount(); ++i) {
                        TIMElem elem = msg.getElement(i);

                        //获取当前元素的类型
                        TIMElemType elemType = elem.getType();
                        Log.e(tag, "elem type: " + elemType.name());
                        if (elemType == TIMElemType.Text) {
                            //处理文本消息
                        } else if (elemType == TIMElemType.Image) {
                            //处理图片消息
                        }//...处理更多消息
                    }
                }


                return true; //返回 true 将终止回调链，不再调用下一个新消息监听器
            }
        });



        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置群组资料拉取字段
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.e(tag, "onForceOffline");
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新 userSig 重新登录 SDK
                        Log.e(tag, "onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.e(tag, "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.e(tag, "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.e(tag, "onWifiNeedAuth");
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.e(tag, "onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        Log.e(tag, "onRefreshConversation, conversation size: " + conversations.size());
                    }
                });

//消息扩展用户配置
        userConfig = new TIMUserConfigMsgExt(userConfig)
                //禁用消息存储
                .enableStorage(false)
                //开启消息已读回执
                .enableReadReceipt(true);

//资料关系链扩展用户配置
        userConfig = new TIMUserConfigSnsExt(userConfig)
                //开启资料关系链本地存储
                .enableFriendshipStorage(true)
                //设置关系链变更事件监听器
                .setFriendshipProxyListener(new TIMFriendshipProxyListener() {
                    @Override
                    public void OnAddFriends(List<TIMUserProfile> users) {
                        Log.e(tag, "OnAddFriends");
                    }

                    @Override
                    public void OnDelFriends(List<String> identifiers) {
                        Log.e(tag, "OnDelFriends");
                    }

                    @Override
                    public void OnFriendProfileUpdate(List<TIMUserProfile> profiles) {
                        Log.e(tag, "OnFriendProfileUpdate");
                    }

                    @Override
                    public void OnAddFriendReqs(List<TIMSNSChangeInfo> reqs) {
                        Log.e(tag, "OnAddFriendReqs");
                    }


                });


//将用户配置与通讯管理器进行绑定
        timManager.setUserConfig(userConfig);

        timManager.login("15996464635", userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.e(tag, "login failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess() {
                Log.e(tag, "login succ");
            }
        });
    }

    public void click(View v){
        String peer = "13004476205";  //获取与用户 "sample_user_1" 的会话
        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C,    //会话类型：单聊
                peer);

        //构造一条消息
        TIMMessage msg = new TIMMessage();

//添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText("a new msg from 159 to 130");

//将elem添加到消息
        if(msg.addElement(elem) != 0) {
            Log.e(tag, "addElement failed");
            return;
        }

//发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                Log.e(tag, "send message failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e(tag, "SendMsg ok");
            }
        });

    }
}
