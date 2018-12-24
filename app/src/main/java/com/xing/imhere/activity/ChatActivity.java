package com.xing.imhere.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.imcore.TextElem;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.xing.imhere.R;
import com.xing.imhere.adapter.ChatAdapter;
import com.xing.imhere.adapter.EmojiPagerAdapter;
import com.xing.imhere.adapter.MessageAdapter;
import com.xing.imhere.base.ChatBase;
import com.xing.imhere.util.T;
import com.xing.imhere.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.activity_chat_title_tv)TextView title;
    @BindView(R.id.activity_chat_listview)ListView chatList;
    private ChatAdapter chatAdapter;
    private List<ChatBase> chatBases;

    private boolean isSend = false;
    private Context ctx;
    private String account = null;
    private TIMConversation conversation;

    private static final String TAG = "ChatActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        account = getIntent().getStringExtra(MessageAdapter.CHAT_KEY);
        if(account == null) finish();


        ctx = this;

        initChatAbout();
        initNormalLayout();
        initEmojiLayout();
        initTencent();
    }

    private void initChatAbout() {
        chatBases = new ArrayList<>();
        chatAdapter = new ChatAdapter(ctx,chatBases);
        chatList.setAdapter(chatAdapter);
    }

    private void initNormalLayout() {
        title.setText(account);

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

    private void initTencent() {
        conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C,    //会话类型：单聊
                account);

        TIMManager.getInstance().addMessageListener(list -> {
            //消息的内容解析请参考消息解析
            Log.e(TAG,"onNewMessages");
            for (TIMMessage msg:list) {
                for(int i = 0; i < msg.getElementCount(); ++i) {
                    TIMElem elem = msg.getElement(i);

                    //获取当前元素的类型
                    TIMElemType elemType = elem.getType();
                    Log.e(TAG, "elem type: " + elemType.name());
                    if (elemType == TIMElemType.Text) {
                        //处理文本消息
                        chatBases.add(new ChatBase(((TIMTextElem)elem).getText(), ChatBase.Type.TO));
                        chatAdapter.notifyDataSetChanged();
                        T.s(ctx,((TIMTextElem)elem).getText());
                    } else if (elemType == TIMElemType.Image) {
                        //处理图片消息
                    }//...处理更多消息
                }
            }

            return true; //返回 true 将终止回调链，不再调用下一个新消息监听器
        });
    }

    private void sendSaySth(String text){
        //构造一条消息
        TIMMessage msg = new TIMMessage();
        //添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText(text);
        //将elem添加到消息
        if(msg.addElement(elem) != 0) {
            Log.e(TAG, "addElement failed");
            return;
        }
        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                Log.e(TAG, "send message failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e(TAG, "SendMsg ok");
            }
        });
    }

    private void initEmojiLayout() {
        EmojiPagerAdapter emojiPagerAdapter = new EmojiPagerAdapter(getSupportFragmentManager(), ctx);
        emojiPagerAdapter.setOnClickListener(e->{
            //emoji点击时传递出来那个按钮对应的emoji名
            try {
                String s2 = "[" + e + "]";
                SpannableStringBuilder ssb = new SpannableStringBuilder(s2);
                ImageSpan is = new ImageSpan(this, BitmapFactory.decodeStream(getAssets().open("emojis/" + e)));
                ssb.setSpan(is, 0,s2.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                saySth.setText(saySth.getText().append(ssb));
                saySth.setSelection(saySth.getText().length());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        emojiPager.setAdapter(emojiPagerAdapter);
    }

    @OnClick(R.id.activity_chat_sound_btn)
    public void click_bt_send(){
        if (isSend) {
            //发送信息
            sendSaySth(saySth.getText().toString());
            saySth.setText("");
            chatBases.add(new ChatBase(saySth.getText().toString(), ChatBase.Type.FROM));
            chatBases.add(new ChatBase(saySth.getText().toString(), ChatBase.Type.TO));
            chatAdapter.notifyDataSetChanged();
        } else {
            if (saySth.isEnabled()) {
                holdSpeak.setVisibility(View.VISIBLE);
                saySth.setEnabled(false);
                bt_send.setImageDrawable(getResources().getDrawable(R.drawable.chat_keyboard));
                hideEmojiLayout();
            } else {
                saySth.setEnabled(true);
                holdSpeak.setVisibility(View.GONE);
                bt_send.setImageDrawable(getResources().getDrawable(R.drawable.chat_sound_btn));
                Util.showKeyboard(ctx);
                hideEmojiLayout();
            }

        }
    }

    @OnClick(R.id.activity_chat_btn_emoji)
    public void click_bt_emoji(){
        if(emojiLayout.getVisibility() == View.GONE)
            showEmojiLayout();
        else if(emojiLayout.getVisibility() == View.VISIBLE)
            hideEmojiLayout();
    }

    private void hideEmojiLayout(){
        emojiLayout.setVisibility(View.GONE);
    }

    private void showEmojiLayout(){
        emojiLayout.setVisibility(View.VISIBLE);
    }

}
