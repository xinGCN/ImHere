package com.xing.imhere.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

import com.xing.imhere.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xinG on 2018/8/16 0016.
 */
public class EmojiGridAdapter extends BaseAdapter {
    private List<String> emojis;
    private LayoutInflater mInflater;
    private Context ctx;
    private static final String emojiPath = "emojis";

    private OnClickListener onClickListener;

    public EmojiGridAdapter(List<String> emojis,Context ctx) {
        this.emojis = emojis;
        mInflater = LayoutInflater.from(ctx);
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return emojis==null?0:emojis.size();
    }

    @Override
    public Object getItem(int position) {
        return emojis==null?null:emojis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.fragment_emoji_grid_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.emojib.setImageBitmap(BitmapFactory.decodeStream( ctx.getAssets().open(emojiPath + "/" + emojis.get(position))));
            holder.emojib.setOnClickListener(v -> {
                if(onClickListener!=null) onClickListener.onClick(emojis.get(position));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public interface OnClickListener{
        void onClick(String emoji);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    class ViewHolder{
        @BindView(R.id.fragment_emoji_grid_item_emoji) ImageButton emojib;

        public ViewHolder(View v){
            ButterKnife.bind(this,v);
        }
    }
}
