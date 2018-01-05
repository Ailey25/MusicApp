package com.aileyzhang.musicapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.aileyzhang.musicapp.R;

/**
 * Created by Ailey on 2018-01-03.
 */

public class PlaylistListItemView extends FrameLayout {

    public PlaylistListItemView(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.item_playlist_in_list, this);
    }
}