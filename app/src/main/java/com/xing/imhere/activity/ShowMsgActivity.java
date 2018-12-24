package com.xing.imhere.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xing.imhere.ImHereApplication;
import com.xing.imhere.R;
import com.xing.imhere.adapter.CardAdapter;
import com.xing.imhere.base.CardBase;
import com.xing.imhere.base.Message;
import com.xing.imhere.fragment.UserFragment;
import com.xing.imhere.http.HttpService;
import com.xing.imhere.util.Global;
import com.xing.imhere.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xinG on 2018/8/30 0030.
 */
public class ShowMsgActivity extends AppCompatActivity{
    private static String TAG = "ShowMsgActivity";
    private  HttpService httpService;
    @BindView(R.id.activity_show_msg_recycle)RecyclerView rView;
    RecyclerView.LayoutManager layoutManager;
    List<CardBase> msgs ;
    CardAdapter cAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_msg);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);
        msgs = new ArrayList<>();
        cAdapter = new CardAdapter(msgs);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(cAdapter);

        httpService = ((ImHereApplication) getApplication()).getHttpService();
        int id = getIntent().getIntExtra(UserFragment.MSGKEY,-1);
        switch (id){
            case R.id.fragment_user_mymsg:
                httpService.getMyMsgs(Global.user.getAccount()).enqueue(new Callback<List<Message>>() {
                    @Override
                    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                        L.e(TAG,"getMyMsg success " + response.body().toString());
                        msgs.addAll(Message.toCardBases(response.body()));
                        cAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Message>> call, Throwable t) {
                        L.e(TAG,"getMyMsgs Fail " + t.getMessage());
                    }
                });
                break;
            case R.id.fragment_user_likemsg:
                httpService.getLikeMsgs(Global.user.getAccount()).enqueue(new Callback<List<Message>>() {
                    @Override
                    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                        L.e(TAG,"getLikeMsg success " + response.body().toString());
                        msgs.addAll(Message.toCardBases(response.body()));
                        cAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Message>> call, Throwable t) {
                        L.e(TAG,"getLikeMsg Fail " + t.getMessage());
                    }
                });
                break;
            default:
                    L.e(TAG,"where are you from? im lost...");break;
        }

    }
}
