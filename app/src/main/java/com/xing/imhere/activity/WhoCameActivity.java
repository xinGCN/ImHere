package com.xing.imhere.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.xing.imhere.ImHereApplication;
import com.xing.imhere.R;
import com.xing.imhere.adapter.CardAdapter;
import com.xing.imhere.base.CardBase;
import com.xing.imhere.base.Message;
import com.xing.imhere.http.HttpService;
import com.xing.imhere.http.ImHereHttpUrl;
import com.xing.imhere.service.BDLocationService;
import com.xing.imhere.util.L;
import com.xing.imhere.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xinG on 2018/8/6 0006.
 */
public class WhoCameActivity extends AppCompatActivity {
    @BindView(R.id.activity_whocame_recycleView)
    RecyclerView recycle;
    CardAdapter cardAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    List<CardBase> cards;

    @BindView(R.id.activity_whocame_bottom_navigation)
    BottomNavigationView bottomView;

    private Context ctx;
    private static final String TAG = "WhocameActivity";

    private BDLocationService.Binder binder;
    private HttpService httpService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whocame);
        ButterKnife.bind(this);

        ctx = WhoCameActivity.this;

        binder = ((ImHereApplication)getApplication()).getmBinder();
        httpService = ((ImHereApplication)getApplication()).getHttpService();

        cards = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(mLayoutManager);
        cardAdapter = new CardAdapter(cards);
        recycle.setAdapter(cardAdapter);

        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_navigation_whocame:
                        T.s(ctx,"Im whocame");
                        break;
                    case R.id.bottom_navigation_user:
                        T.s(ctx,"Im user");
                        break;
                }
                return false;
            }
        });

        List<Message> msgs = new ArrayList<>();
        for(int i = 0 ; i < 10 ;i++){
            Message msg = new Message();
            msg.setMessage("im " + i);
            msg.setTime("time" + i);
            msgs.add(msg);
        }
        cards.addAll(Message.toCardBases(msgs));
        cardAdapter.notifyDataSetChanged();

//网络请求刷新数据
//        if(binder != null && binder.isImmediate()){
//            Call<List<Message>> msgsCall = httpService.getMessages(binder.getLastLat(), binder.getLastLon());
//
//            msgsCall.enqueue(new Callback<List<Message>>() {
//                @Override
//                public void onResponse(Call<List<Message>> call, Response<List<Message>> resp) {
//                    L.e(TAG,resp.body()==null?"null":resp.body().toString());
//                    cards.addAll(Message.toCardBases(resp.body()));
//                    cardAdapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onFailure(Call<List<Message>> call, Throwable t) {
//                    L.e(TAG,t.getMessage());
//                }
//            });
//        }

    }

    @OnClick(R.id.activity_whocame_fab)
    public void imHere(){
        startActivity(new Intent(ctx,ImHereActivity.class));
    }

    @Override
    protected void onDestroy() {
        ((ImHereApplication)getApplication()).stopService();
        super.onDestroy();
    }
}
