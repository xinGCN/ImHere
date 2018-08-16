package com.xing.imhere.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.xing.imhere.ImHereApplication;
import com.xing.imhere.R;
import com.xing.imhere.activity.LoginActivity;
import com.xing.imhere.base.RegisterResult;
import com.xing.imhere.http.HttpService;
import com.xing.imhere.util.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xinG on 2018/8/10 0010.
 */
public class ResetPassFragment extends Fragment {
    private String account;
    @BindView(R.id.fragment_reset_pass_pass)
    EditText pass;
    @BindView(R.id.fragment_reset_pass_rpass)
    EditText rpass;
    private HttpService httpService;

    private static final String TAG = "ResetPassFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_reset_pass,container,false);
        account = getArguments().getString(RegisterFragment.BUNDLE_KEY);
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        httpService = ((ImHereApplication)getActivity().getApplication()).getHttpService();
    }

    @OnClick(R.id.fragment_reset_pass_confirm)
    public void confirm(){
        if(pass.getText().toString().equals(rpass.getText().toString())){
            Call<RegisterResult> call = httpService.resetPass(account, pass.getText().toString());
            call.enqueue(new Callback<RegisterResult>() {
                @Override
                public void onResponse(Call<RegisterResult> call, Response<RegisterResult> resp) {
                    RegisterResult body = resp.body();
                    switch (body.getCode()){
                        case RegisterResult.ACCOUNT_NOT_EXIST:
                            Snackbar.make(getView(), R.string.register_account_not_exist,Snackbar.LENGTH_SHORT).show();break;
                        case RegisterResult.SUCCESS:
                            //注册流程结束，转至登录;
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                            break;
                        case RegisterResult.FAIL:
                            Snackbar.make(getView(), R.string.register_reset_pass_fail,Snackbar.LENGTH_SHORT).show();break;
                        default:
                            Snackbar.make(getView(), R.string.register_sth_unknown_happen,Snackbar.LENGTH_SHORT).show();break;
                    }
                }

                @Override
                public void onFailure(Call<RegisterResult> call, Throwable t) {
                    L.e(TAG,"confirm onFailure : " + t.getMessage());
                }
            });
        }else{
            Snackbar.make(getView(), R.string.register_pass_repeat_error,Snackbar.LENGTH_SHORT).show();
        }
    }
}
