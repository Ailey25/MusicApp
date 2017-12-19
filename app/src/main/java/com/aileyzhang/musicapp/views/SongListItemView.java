package com.aileyzhang.musicapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import com.aileyzhang.musicapp.R;

/**
 * Created by Ailey on 2017-12-18.
 */

public class SongListItemView extends FrameLayout {
    View view;

    public SongListItemView(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        view = inflate(getContext(), R.layout.song_list_item, null);
        addView(view);
    }
}
