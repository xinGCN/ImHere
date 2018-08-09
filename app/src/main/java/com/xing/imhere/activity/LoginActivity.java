package com.xing.imhere.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.xing.imhere.ImHereApplication;
import com.xing.imhere.R;
import com.xing.imhere.base.LoginResult;
import com.xing.imhere.base.Message;
import com.xing.imhere.base.User;
import com.xing.imhere.http.HttpService;
import com.xing.imhere.util.L;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
public class LoginActivity extends AppCompatActivity{
    @BindView(R.id.activity_login_account) EditText account;
    @BindView(R.id.activity_login_pass) EditText pass;

    private HttpService httpService;

    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        httpService = ((ImHereApplication)getApplication()).getHttpService();
    }

    @OnClick(R.id.activity_login_submit)
    public void submit(){
        Call<LoginResult> login = httpService.login(account.getText().toString(), pass.getText().toString());
        login.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> resp) {
                if(resp.code() == 200 && resp.body().getCode() == LoginResult.SUCCESS){
                    User user = resp.body().getUser();
                    List<Message> msgs = resp.body().getMsgs();
                }else{
                    L.e(TAG,"onResponse sth happen");
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                L.e(TAG,"onFailure : " + t.getMessage());
            }
        });
    }


}

