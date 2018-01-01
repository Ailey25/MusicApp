package com.aileyzhang.musicapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.aileyzhang.musicapp.R;

/**
 * Created by Ailey on 2017-12-18.
 */

public class SongListItemView extends FrameLayout {

    public SongListItemView(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
       inflate(getContext(), R.layout.item_song_in_list, this);
    }
}
