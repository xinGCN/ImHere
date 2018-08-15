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

import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.xing.imhere.ImHereApplication;
import com.xing.imhere.R;
import com.xing.imhere.fragment.MessageFragment;
import com.xing.imhere.fragment.UserFragment;
import com.xing.imhere.fragment.WhocameFragment;
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

        getFragmentManager().beginTransaction().add(R.id.activity_main_fragment,new WhocameFragment()).commit();
        ctx = this;
        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_navigation_whocame:
                        //T.s(ctx,"Im whocame");
                        if(where != STATE_WHOCAME){
                            getFragmentManager().beginTransaction().replace(R.id.activity_main_fragment,new WhocameFragment()).commit();
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
                            getFragmentManager().beginTransaction().replace(R.id.activity_main_fragment,new MessageFragment()).commit();
                            fab.setVisibility(View.INVISIBLE);
                            where = STATE_USER;
                        }
                        break;
                }
                return false;
            }
        });
        
        initTencent();
    }

    private void initTencent() {
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                //消息的内容解析请参考消息解析
                Log.e(TAG,"onNewMessages");
                for (TIMMessage msg:list) {
                    for(int i = 0; i < msg.getElementCount(); ++i) {
                        TIMElem elem = msg.getElement(i);

                        //获取当前元素的类型
                        TIMElemType elemType = elem.getType();
                        Log.e(TAG, "elem type: " + elemType.name());
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
