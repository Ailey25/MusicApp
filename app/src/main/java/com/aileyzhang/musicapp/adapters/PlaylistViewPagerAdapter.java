package com.aileyzhang.musicapp.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.aileyzhang.musicapp.R;

/**
 * Created by Ailey on 2018-01-03.
 */

public class PlaylistViewPagerAdapter extends PagerAdapter {

    private final int TOTAL_PAGES = 2;

    public static final int PLAYLIST_LIST_VIEW = 0;
    public static final int PLAYLIST_SONG_LIST_VIEW = 1;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        switch (position) {
            case PLAYLIST_LIST_VIEW:
                return container.findViewById(R.id.playlist_list_page);
            case PLAYLIST_SONG_LIST_VIEW:
                return container.findViewById(R.id.playlist_songs_list_page);
            default:
                return null;
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
