package com.aileyzhang.musicapp.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.aileyzhang.musicapp.R;

/**
 * Created by jiesiren on 2017-12-29.
 */

public class AlbumViewPagerAdapter extends PagerAdapter {

    private final int TOTAL_PAGES = 2;

    public static final int ALBUM_GRID_VIEW = 0;
    public static final int ALBUM_SONG_LIST_VIEW = 1;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        switch (position) {
            case ALBUM_GRID_VIEW:
                return container.findViewById(R.id.album_grid_page);
            case ALBUM_SONG_LIST_VIEW:
                return container.findViewById(R.id.album_song_list_page);
            default:
                return 0;
        }
    }

    @Override
    public int getCount() {
        return TOTAL_PAGES;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
