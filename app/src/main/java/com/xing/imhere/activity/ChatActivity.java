package com.xing.imhere.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xing.imhere.R;
import com.xing.imhere.adapter.EmojiPagerAdapter;
import com.xing.imhere.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.activity_chat_et)EditText saySth;
    @BindView(R.id.activity_chat_sound_btn)ImageView bt_send;
    @BindView(R.id.activity_chat_btn_emoji)CheckBox bt_emoji;
    @BindView(R.id.activity_chat_hold_tv)TextView holdSpeak;
    @BindView(R.id.activity_chat_emoji_rl)RelativeLayout emojiLayout;
    @BindView(R.id.activity_chat_emoji_vp)ViewPager emojiPager;
    private boolean isSend = false;
    private Context ctx;

    private static final String TAG = "ChatActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        ctx = this;
        saySth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    isSend = true;
                    bt_send.setImageResource(R.drawable.chat_send_btn);
                } else if (s.length() == 0) {
                    isSend = false;
                    bt_send.setImageResource(R.drawable.chat_sound_btn);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @OnClick(R.id.activity_chat_sound_btn)
    public void click_bt_send(){
        if (isSend) {
            //发送信息
        } else {
            if (saySth.isEnabled()) {
                holdSpeak.setVisibility(View.VISIBLE);
                saySth.setEnabled(false);
                bt_send.setImageDrawable(getResources().getDrawable(R.drawable.chat_keyboard));
                //TODO hideExpressionLayout();隐藏emoji布局
            } else {
                saySth.setEnabled(true);
                holdSpeak.setVisibility(View.GONE);
                bt_send.setImageDrawable(getResources().getDrawable(R.drawable.chat_sound_btn));
                Util.showKeyboard(ctx);
                //TODO hideExpressionLayout();隐藏emoji布局
            }

        }
    }

    @OnClick(R.id.activity_chat_btn_emoji)
    public void click_bt_emoji(){
        emojiLayout.setVisibility(View.VISIBLE);
        //emojiPager.setAdapter();
        EmojiPagerAdapter emojiPagerAdapter = new EmojiPagerAdapter(getSupportFragmentManager(), ctx);
        emojiPagerAdapter.setOnClickListener(e->{
            //emoji点击时传递出来那个按钮对应的emoji名
            //TODO 处理Emoji点击
        });
        emojiPager.setAdapter(emojiPagerAdapter);
    }

}
