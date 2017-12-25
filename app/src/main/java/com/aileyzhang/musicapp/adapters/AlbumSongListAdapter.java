package com.aileyzhang.musicapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aileyzhang.musicapp.fragments.SongListFragment;

/**
 * Created by Ailey on 2017-12-28.
 */

public class AlbumSongListAdapter extends FragmentStatePagerAdapter {
    private String mAlbumID;
    private final int TOTAL_PAGES = 2;
    public static final int ALBUM_GRID_VIEW = 0;
    public static final int ALBUM_SONG_LIST_VIEW = 1;

    public AlbumSongListAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case ALBUM_GRID_VIEW:
                return SongListFragment.newInstance(0, "");
            case ALBUM_SONG_LIST_VIEW:
                return SongListFragment.newInstance(0, "");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TOTAL_PAGES;
    }
}
