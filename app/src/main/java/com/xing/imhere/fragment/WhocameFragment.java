package com.xing.imhere.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.xing.imhere.ImHereApplication;
import com.xing.imhere.R;
import com.xing.imhere.activity.ImHereActivity;
import com.xing.imhere.adapter.CardAdapter;
import com.xing.imhere.base.CardBase;
import com.xing.imhere.base.Message;
import com.xing.imhere.http.HttpService;
import com.xing.imhere.service.BDLocationService;
import com.xing.imhere.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xinG on 2018/8/14 0014.
 */
public class WhocameFragment extends Fragment {
    @BindView(R.id.activity_whocame_recycleView)
    RecyclerView recycle;
    CardAdapter cardAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    List<CardBase> cards;

    private Activity ctx;
    private static final String TAG = "WhocameFragment";

    private BDLocationService.Binder binder;
    private HttpService httpService;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_whcame,container,false);
        ButterKnife.bind(this,v);

        ctx = getActivity();

        binder = ((ImHereApplication)ctx.getApplication()).getmBinder();
        httpService = ((ImHereApplication)ctx.getApplication()).getHttpService();

        cards = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(ctx);
        recycle.setLayoutManager(mLayoutManager);
        cardAdapter = new CardAdapter(cards);
        recycle.setAdapter(cardAdapter);

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

        return v;
    }
}
