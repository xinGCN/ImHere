package com.xing.imhere.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.xing.imhere.ImHereApplication;
import com.xing.imhere.R;
import com.xing.imhere.base.RegisterResult;
import com.xing.imhere.customcontrol.EnsureButton;
import com.xing.imhere.http.HttpService;
import com.xing.imhere.service.BDLocationService;
import com.xing.imhere.util.L;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xinG on 2018/8/10 0010.
 */
public class RegisterFragment extends Fragment {
    @BindView(R.id.fragment_register_account)EditText account;
    @BindView(R.id.fragment_register_ensureCode)EditText ensureCode;
    @BindView(R.id.fragment_register_register) EnsureButton register;
    @BindString(R.string.ensure_normal)String ensure_normal;
    @BindString(R.string.ensure_reset)String ensure_reset;
    private HttpService httpService;
    private static final String TAG = "RegisterFragment";
    public static final String BUNDLE_KEY="Account";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        ButterKnife.bind(this,view);

        register.setOnClickListener(new EnsureButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((EnsureButton)v).getText().toString().equals(ensure_normal))
                    register();
                else if (((EnsureButton)v).getText().toString().equals(ensure_reset))
                    resetCode();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        httpService = ((ImHereApplication)getActivity().getApplication()).getHttpService();
    }

    public void register(){
        Call<RegisterResult> registerCall = httpService.register(account.getText().toString());
        registerCall.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> resp) {
                L.e(TAG,"register onResponse : " + resp.code());
                RegisterResult body = resp.body();
                switch(body.getCode()){
                    case RegisterResult.FAIL:
                        Snackbar.make(getView(), R.string.register_registered,Snackbar.LENGTH_SHORT).show();
                        break;
                    case RegisterResult.SUCCESS:
                        Snackbar.make(getView(), R.string.register_register_success,Snackbar.LENGTH_SHORT).show();
                        break;
                    default:
                        Snackbar.make(getView(), R.string.register_sth_unknown_happen,Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                L.e(TAG,"register onFailure : " + t.getMessage());
            }
        });
    }

    public void resetCode(){
        Call<RegisterResult> call = httpService.resetEnsureCode(account.getText().toString());
        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> resp) {
                RegisterResult body = resp.body();
                switch (body.getCode()){
                    case RegisterResult.ACCOUNT_NOT_EXIST:
                        Snackbar.make(getView(), R.string.register_account_not_exist,Snackbar.LENGTH_SHORT).show();break;
                    case RegisterResult.FAIL:
                        Snackbar.make(getView(), R.string.register_reset_code_fail,Snackbar.LENGTH_SHORT).show();break;
                    case RegisterResult.SUCCESS:
                        Snackbar.make(getView(), R.string.register_reset_code_success,Snackbar.LENGTH_SHORT).show();break;
                    default:
                        Snackbar.make(getView(), R.string.register_sth_unknown_happen,Snackbar.LENGTH_SHORT).show();break;
                }
            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                L.e(TAG,"resetCode onFailure : " + t.getMessage());
            }
        });
    }

    @OnClick(R.id.fragment_register_confirmEnsureCode)
    public void confirmEnsureCode(){
        Call<RegisterResult> confirmCall = httpService.confirmEnsureCode(account.getText().toString(), ensureCode.getText().toString());
        confirmCall.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> resp) {
                RegisterResult body = resp.body();
                switch(body.getCode()){
                    case RegisterResult.ENSURECODE_OUT_TIME:
                        Snackbar.make(getView(), R.string.register_ensure_out_time,Snackbar.LENGTH_SHORT).show();
                        break;
                    case RegisterResult.ENSURECODE_ERROR:
                        Snackbar.make(getView(), R.string.register_ensure_error,Snackbar.LENGTH_SHORT).show();
                        break;
                    case RegisterResult.FAIL:
                        Snackbar.make(getView(), R.string.register_sth_happen,Snackbar.LENGTH_SHORT).show();
                        break;
                    case RegisterResult.ACCOUNT_NOT_EXIST:
                        Snackbar.make(getView(), R.string.register_not_registering,Snackbar.LENGTH_SHORT).show();
                        break;
                    case RegisterResult.SUCCESS:
                        //注册成功进入重设密码界面
                        ResetPassFragment pFragment = new ResetPassFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(BUNDLE_KEY,account.getText().toString());
                        pFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,pFragment).commit();
                        break;
                    default:break;
                }
            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                L.e(TAG,"confirmEnsureCode onFailure : " + t.getMessage());
            }
        });
    }
}
