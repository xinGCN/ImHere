package com.xing.imhere.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xing.imhere.R;
import com.xing.imhere.base.Comment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xinG on 2018/8/14 0014.
 *
 * 卡片下的评论Adapter
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private List<Comment> cmts;

    public CommentAdapter(List<Comment> cmts){
        this.cmts = cmts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_whocame_item_comment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.message.setText(cmts.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return cmts == null?0:cmts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.activity_whocame_item_comment_item_message)TextView message;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
