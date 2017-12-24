package com.aileyzhang.musicapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.aileyzhang.musicapp.R;

/**
 * Created by Ailey on 2017-12-23.
 */

public class ArtistListItemView extends FrameLayout {

    public ArtistListItemView(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.artist_list_item, this);
    }
}
