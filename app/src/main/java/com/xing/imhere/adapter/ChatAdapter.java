package com.xing.imhere.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xing.imhere.R;
import com.xing.imhere.base.ChatBase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by xinG on 2018/8/22 0022.
 */
public class ChatAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<ChatBase> chatBases;
    private Context ctx;

    public ChatAdapter(Context ctx,List<ChatBase> chatBases){
        mInflater = LayoutInflater.from(ctx);
        this.ctx = ctx;
        this.chatBases = chatBases;
    }

    @Override
    public int getCount() {
        return chatBases==null?0:chatBases.size();
    }

    @Override
    public Object getItem(int position) {
        return chatBases==null?null:chatBases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatBases.get(position).getType()){
            case FROM:return 0;
            case TO:return 1;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatBase chatMessage = chatBases.get(position);
        ViewHolder viewHolder;
        if (convertView == null)
        {
            if (getItemViewType(position) == 0)
            {
                convertView = mInflater.inflate(R.layout.activity_chat_item_from, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.date = convertView
                        .findViewById(R.id.activity_chat_item_from_date);
                viewHolder.content = convertView
                        .findViewById(R.id.activity_chat_item_from_text);
            } else
            {
                convertView = mInflater.inflate(R.layout.activity_chat_item_to, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.date = convertView
                        .findViewById(R.id.activity_chat_item_to_date);
                viewHolder.content = convertView
                        .findViewById(R.id.activity_chat_item_to_text);
            }
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //viewHolder.date.setText(df.format(chatMessage.getDate()));

        String content = chatMessage.getContent();
        SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        char[] chs = content.toCharArray();
        System.out.println(chs);
        for (int i = 0 ; i < chs.length ; i++){
            //System.out.println("i:" +  i);
            if(chs[i] == '['){
                //System.out.println("match i:" + i);
                for(int j = i + 1 ; j < chs.length ; j++){
                    //System.out.println("this is j:" + j + " and chs[j]:" + chs[j]);
                    if(chs[j] == ']'){
                        // System.out.println("match success : " + i + " " + j);
                        try {
                            String eName = new String(chs, i + 1, j - i - 1);
                            ImageSpan iSpan = new ImageSpan(ctx,BitmapFactory.decodeStream(ctx.getAssets().open("emojis/" + eName)));
                            ssb.setSpan(iSpan,i,j+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        i = j;
                        break;
                    }else if(chs[j] == '['){
                        i = j;
                        break;
                    }
                }
            }
        }
        viewHolder.content.setText(ssb);
        return convertView;
    }

    class ViewHolder{
        TextView date;
        TextView content;
    }

}
