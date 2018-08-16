package com.xing.imhere.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.xing.imhere.R;
import com.xing.imhere.adapter.EmojiGridAdapter;
import com.xing.imhere.adapter.EmojiPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xinG on 2018/8/16 0016.
 */
public class EmojiFragment extends Fragment {
    private List<String> emojis;
    @BindView(R.id.fragment_emoji_grid)GridView emojiGrid;
    private OnClickListener onClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        emojis = getArguments().getStringArrayList(EmojiPagerAdapter.EMOJI_KEY);
        View v = inflater.inflate(R.layout.fragment_emoji,container,false);
        ButterKnife.bind(this,v);

        EmojiGridAdapter emojiGridAdapter = new EmojiGridAdapter(emojis, getContext());
        emojiGridAdapter.setOnClickListener(e -> {
            if(onClickListener != null) onClickListener.onClick(e);
        });
        emojiGrid.setAdapter(emojiGridAdapter);
        return v;
    }

    public interface OnClickListener{
        void onClick(String emoji);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
