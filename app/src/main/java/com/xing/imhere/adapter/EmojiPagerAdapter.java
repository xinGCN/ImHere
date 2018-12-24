package com.xing.imhere.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ListView;

import com.xing.imhere.base.Emoji;
import com.xing.imhere.fragment.EmojiFragment;
import com.xing.imhere.util.L;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xinG on 2018/8/16 0016.
 * 一页Emoji的Adapter
 */
public class EmojiPagerAdapter extends FragmentPagerAdapter {
    private List<EmojiFragment> emojiFragments;
    private List<String> emojiNames;

    private static final int numOfEmojiEachPage = 3*8;
    private static final String TAG = "EmojiPagerAdapter";
    public static final String EMOJI_KEY = "emojiList";

    private OnClickListener onClickListener;

    public EmojiPagerAdapter(FragmentManager fm,Context ctx) {
        super(fm);

        try {
            emojiNames = Arrays.asList(ctx.getAssets().list("emojis"));
            if(emojiNames == null) return ;
            int emojiCount = emojiNames.size();
            int fcount = 0;
            emojiFragments = new ArrayList<>();
            while(emojiCount > numOfEmojiEachPage){
                EmojiFragment f = new EmojiFragment();
                Bundle bundle = new Bundle();
                ArrayList<String> al= new ArrayList<>();
                al.addAll(emojiNames.subList(fcount * numOfEmojiEachPage, (fcount + 1) * numOfEmojiEachPage));
                bundle.putStringArrayList(EMOJI_KEY,al);
                fcount += 1;
                emojiCount -= numOfEmojiEachPage;
                f.setArguments(bundle);
                f.setOnClickListener(e->{
                    if(onClickListener!=null) onClickListener.onClick(e);
                });
                emojiFragments.add(f);
            }

            //多余的Emoji部分，大小可能在1~24
            EmojiFragment f = new EmojiFragment();
            Bundle bundle = new Bundle();
            ArrayList<String> al= new ArrayList<>();
            al.addAll(emojiNames.subList(fcount * numOfEmojiEachPage, emojiNames.size()));
            bundle.putStringArrayList(EMOJI_KEY,al);
            f.setArguments(bundle);
            f.setOnClickListener(e->{
                if(onClickListener!=null) onClickListener.onClick(e);
            });
            emojiFragments.add(f);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return emojiFragments==null?null:emojiFragments.get(position);
    }

    @Override
    public int getCount() {
        return emojiFragments==null?0:emojiFragments.size();
    }

    public interface OnClickListener{
        void onClick(String emoji);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
