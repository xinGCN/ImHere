package com.xing.imhere.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xing.imhere.R;
import com.xing.imhere.activity.ShowMsgActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xinG on 2018/8/14 0014.
 */
public class UserFragment extends Fragment {
    public static final String MSGKEY = "whoClick";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    @OnClick(R.id.fragment_user_mymsg)
    public void myMsgClick(){
        Intent i = new Intent(getContext(), ShowMsgActivity.class);
        i.putExtra(MSGKEY,R.id.fragment_user_mymsg);
        startActivity(i);
    }

    @OnClick(R.id.fragment_user_likemsg)
    public void likeMsgClick(){
        Intent i = new Intent(getContext(), ShowMsgActivity.class);
        i.putExtra(MSGKEY,R.id.fragment_user_likemsg);
        startActivity(i);
    }
}
