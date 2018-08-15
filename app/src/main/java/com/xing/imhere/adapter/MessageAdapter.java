package com.xing.imhere.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xing.imhere.R;
import com.xing.imhere.base.ui.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xinG on 2018/8/15 0015.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private List<Message> msgs;

    public MessageAdapter(List<Message> msgs){
        this.msgs = msgs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message_recycle_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message msg = msgs.get(position);
        holder.user.setText(msg.getNickName());
    }

    @Override
    public int getItemCount() {
        return msgs == null ? 0 : msgs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.fragment_message_recycle_item_user_image)ImageView userImage;
        @BindView(R.id.fragment_message_recycle_item_top_name)TextView user;
        @BindView(R.id.fragment_message_recycle_item_bottom_info)TextView bottom;
        @BindView(R.id.fragment_message_recycle_item_date)TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
