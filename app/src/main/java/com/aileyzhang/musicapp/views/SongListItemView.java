package com.aileyzhang.musicapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aileyzhang.musicapp.R;

/**
 * Created by Ailey on 2017-12-18.
 */

public class SongListItemView extends View {
    View view;

    public SongListItemView(Context context, ViewGroup parent) {
        super(context);
        view = LayoutInflater.from(getContext()).inflate(R.layout.song_list_item,
                parent, false);
        parent.addView(view);
    }


}
