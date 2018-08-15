package com.xing.imhere.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xing.imhere.R;
import com.xing.imhere.adapter.HeaderAndFooterRecyclerViewAdapter;
import com.xing.imhere.adapter.MessageAdapter;
import com.xing.imhere.base.ui.Message;
import com.xing.imhere.util.Global;
import com.xing.imhere.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消息列表
 * Created by xinG on 2018/8/15.
 */
public class MessageFragment extends Fragment {
    @BindView(R.id.fragment_message_recycle) RecyclerView rvMessage = null;
    private LinearLayoutManager mLayoutManager = null;
    private MessageAdapter adapter = null;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this,mView);
        init();
        return mView;
    }

    /**
     * 初始化
     */
    private void init() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvMessage.setLayoutManager(mLayoutManager);
        List<Message> msgs = new ArrayList<>();

        if(Global.user.getFriends()!=null){
            String[] split = Global.user.getFriends().split(",");
            for (String account:split) {
                Message m = new Message();
                m.setDate("星期三");
                m.setInfo("[惬意]");
                m.setNickName(account);
                msgs.add(m);
            }
        }
        adapter = new MessageAdapter(msgs);
        //TODO
        //adapter.setMode(Attributes.Mode.Single);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        rvMessage.setAdapter(mHeaderAndFooterRecyclerViewAdapter);

        View vHeader = View.inflate(getActivity(), R.layout.fragment_message_header, null);
        vHeader.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Util.setHeaderView(rvMessage, vHeader);
    }

}
