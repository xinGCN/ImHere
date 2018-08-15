package com.xing.imhere.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.xing.imhere.ImHereApplication;
import com.xing.imhere.R;
import com.xing.imhere.fragment.UserFragment;
import com.xing.imhere.fragment.WhocameFragment;
import com.xing.imhere.util.T;

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
                            getFragmentManager().beginTransaction().replace(R.id.activity_main_fragment,new UserFragment()).commit();
                            fab.setVisibility(View.INVISIBLE);
                            where = STATE_USER;
                        }
                        break;
                }
                return false;
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
