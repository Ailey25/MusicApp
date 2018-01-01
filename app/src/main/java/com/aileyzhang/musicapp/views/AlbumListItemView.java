package com.aileyzhang.musicapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.aileyzhang.musicapp.R;

/**
 * Created by Ailey on 2017-12-22.
 */

public class AlbumListItemView extends FrameLayout{

    public AlbumListItemView(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.item_album_in_list, this);
    }
}
