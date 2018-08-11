package com.xing.imhere.customcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.xing.imhere.R;
import com.xing.imhere.util.L;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

/**
 * Created by xinG on 2018/8/11 0011.
 */
public class EnsureButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener{
    private OnClickListener onClickListener;
    private static int refreashTime = 60;
    private static final int SECOND = 1000;
    private static final String TAG = "EnsureButton";
    private Timer timer;

    public EnsureButton(Context context) {
        super(context);
        this.setText(R.string.ensure_normal);
        setOnClickListener(this);
    }

    public EnsureButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setText(R.string.ensure_normal);
        setOnClickListener(this);
    }

    public EnsureButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setText(R.string.ensure_normal);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        L.e(TAG,"onClick!");
        if(onClickListener != null)
            onClickListener.onClick(v);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.obtainMessage().sendToTarget();
            }
        },0,1000);
    }

    public interface OnClickListener{
        void onClick(View v);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            EnsureButton.this.setText(refreashTime + "秒");
            EnsureButton.this.setClickable(false);
            refreashTime -= 1;
            if(refreashTime < 0){
                refreashTime = 60;
                timer.cancel();
                EnsureButton.this.setClickable(true);
                EnsureButton.this.setText(R.string.ensure_reset);
            }
        }
    };
}
