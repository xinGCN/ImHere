package com.xing.imhere.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xing.imhere.R;
import com.xing.imhere.base.CardBase;
import com.xing.imhere.util.L;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xinG on 2018/8/6 0006.
 *
 * 实现卡片点击展示更多的特效动画借鉴了https://github.com/xzfxzf1314/expandedrecycleviewdemo/tree/master，非常感谢
 * 其实只是隐藏View的Gone与Visible的转化，加上了动画显得不突兀，非常简单的实现方式
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private static final String TAG = "CardAdapter";
    private List<CardBase> cards;

    private ValueAnimator mOpenValueAnimator;
    private ValueAnimator mCloseValueAnimator;



    public CardAdapter(List<CardBase> cards) {
        L.e(TAG,cards.toString());
        this.cards = cards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_whocame_item, parent, false);

        //L.e(TAG,"hehe1");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //L.e(TAG,"hehe2" + position);
        final CardBase cardBase = cards.get(position);
        holder.message.setText(cardBase.getMessage());
        holder.time.setText(cardBase.getTime());
        holder.setOnClickListener(new ViewHolder.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.e(TAG,"我被点击了");


                if (cardBase.isExpand())
                    holder.animOpen();
                else
                    holder.animClose();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards == null ? 0 : cards.size();
    }


    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.activity_whocame_item_message) TextView message;
        @BindView(R.id.activity_whocame_item_time) TextView time;
        @BindView(R.id.activity_whocame_item_expand) ImageView expand;
        @BindView(R.id.activity_whocame_item_expand_layout) LinearLayout expandLayout;

        private ValueAnimator mOpenValueAnimator;
        private ValueAnimator mCloseValueAnimator;
        private int mHiddenViewMeasuredHeight = 200;
        private OnClickListener listener;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

//            message.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (text.getVisibility() == View.VISIBLE) {
//                        animClose(text);
//                    } else {
//                        animOpen(text);
//                    }
//                }
//            });
            itemView.setOnClickListener(this);
        }


        private void animOpen(){
            RotateAnimation ra = new RotateAnimation(0,90,50,50);
            ra.setFillAfter(true);
            ra.setDuration(1000);
            expand.startAnimation(ra);

//            View view = expandLayout;
//            view.setVisibility(View.VISIBLE);
//            view.setAlpha(0);
//            if (mOpenValueAnimator == null) {
//                mOpenValueAnimator = createDropAnim(view,0, mHiddenViewMeasuredHeight);
//            }
//            mOpenValueAnimator.start();
        }

        private void animClose(){
            RotateAnimation ra = new RotateAnimation(90,0,50,50);
            ra.setFillAfter(true);
            ra.setDuration(1000);
            expand.startAnimation(ra);

//            final View view = expandLayout;
//            int origHeight = view.getHeight();
//            view.setAlpha(1);
//            if (mCloseValueAnimator == null) {
//                mCloseValueAnimator = createDropAnim(view, origHeight, 0);
//                mCloseValueAnimator.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        view.setVisibility(View.GONE);
//                    }
//                });
//            }
//            mCloseValueAnimator.start();
        }

        /**
         * 使用动画的方式来改变高度解决visible不一闪而过出现
         * @param view
         * @param start 初始状态值
         * @param end 结束状态值
         * @return
         */
        private ValueAnimator createDropAnim(final  View view,int start,int end) {
            final ValueAnimator va = ValueAnimator.ofInt(start, end);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();//根据时间因子的变化系数进行设置高度
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = value;
                    float alpha = ((float)value) / mHiddenViewMeasuredHeight;
                    view.setAlpha(alpha);
                    view.setLayoutParams(layoutParams);//设置高度
                }
            });
            return  va;
        }

        //当Item离开视图时被调用，减少内存开销
        public void onViewDetachedFromWindow() {
            if (mOpenValueAnimator != null) {
                mOpenValueAnimator.removeAllUpdateListeners();
            }
            if (mCloseValueAnimator != null) {
                mCloseValueAnimator.removeAllUpdateListeners();
            }
        }

        public void setOnClickListener(OnClickListener listener){
            this.listener = listener;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private void rotationExpandIcon(float from, float to) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);//属性动画
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        expand.setRotation((Float) valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        }

        interface OnClickListener{
            void onClick(View v);
        }

        @Override
        public void onClick(View v) {
            if(listener != null ) listener.onClick(v);
        }
    }
}
