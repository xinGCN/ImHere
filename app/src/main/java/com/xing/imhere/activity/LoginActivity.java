package com.xing.imhere.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.xing.imhere.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class LoginActivity extends AppCompatActivity{
    @BindView(R.id.activity_login_usr) EditText usr;
    @BindView(R.id.activity_login_pass) EditText pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_login_submit)
    public void submit(){
        startActivity(new Intent(LoginActivity.this,WhoCameActivity.class));
        finish();
    }
}

