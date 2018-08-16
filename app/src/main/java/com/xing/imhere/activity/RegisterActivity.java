package com.xing.imhere.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xing.imhere.R;
import com.xing.imhere.fragment.RegisterFragment;
import com.xing.imhere.fragment.ResetPassFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xinG on 2018/8/10 0010.
 */
public class RegisterActivity extends AppCompatActivity {
    RegisterFragment rFragment;
    ResetPassFragment pFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        rFragment = new RegisterFragment();
        pFragment = new ResetPassFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,rFragment).commit();
    }

}
