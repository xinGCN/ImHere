package com.xing.imhere.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xing.imhere.R;
import com.xing.imhere.base.CardBase;
import com.xing.imhere.util.L;
import com.xing.imhere.util.T;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xinG on 2018/8/6 0006.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private static final String TAG = "CardAdapter";
    private List<CardBase> cards;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //L.e(TAG,"hehe2" + position);
        CardBase cardBase = cards.get(position);
        holder.message.setText(cardBase.getMessage());
        holder.time.setText(cardBase.getTime());
    }

    @Override
    public int getItemCount() {
        return cards == null ? 0 : cards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.activity_whocame_item_message) TextView message;
        @BindView(R.id.activity_whocame_item_time) TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
