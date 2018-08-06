package com.xing.imhere.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.xing.imhere.R;
import com.xing.imhere.adapter.CardAdapter;
import com.xing.imhere.base.CardBase;
import com.xing.imhere.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whocame);
        ButterKnife.bind(this);

        ctx = WhoCameActivity.this;

        cards = new ArrayList<>();
        for (int i = 0; i < 100 ; i++)
            cards.add(new CardBase("hello world " + i, "time " + i));

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
    }

    @OnClick(R.id.activity_whocame_fab)
    public void imHere(){
        startActivity(new Intent(ctx,ImHereActivity.class));
    }



}
