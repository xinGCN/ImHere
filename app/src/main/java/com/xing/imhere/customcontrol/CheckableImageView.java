package com.xing.imhere.customcontrol;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Checkable;
import android.widget.RadioButton;

import com.xing.imhere.util.L;

/**
 * Created by xinG on 2018/8/31 0031.
 *
 * 记录一些问题，在做好一切基本工作后，该控件不能正常的与like.xml(selector)互动，selector的checked属性不能被触发
 * 解决方案：https://blog.csdn.net/bestthinking/article/details/39580053
 * 给出的解释大概为：view的UI效果是check性质的（开和关状态UI效果不同），则使用selector的XML描述将不起作用，必须手写控制代码来切换UI效果
 */
public class CheckableImageView extends android.support.v7.widget.AppCompatImageView implements Checkable{
    private boolean isChecked = false;
    private static String TAG = "CheckableImageView";

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    public CheckableImageView(Context context) {
        super(context);
    }

    public CheckableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        if(checked!=isChecked){
            isChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    @Override
    public boolean performClick() {
        //L.e(TAG,"check state " + isChecked );
        toggle();
        return super.performClick();
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}
