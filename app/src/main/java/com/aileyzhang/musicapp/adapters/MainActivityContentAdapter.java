package com.aileyzhang.musicapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aileyzhang.musicapp.fragments.AlbumListFragment;
import com.aileyzhang.musicapp.fragments.SongListFragment;

/**
 * Created by Ailey on 2017-12-12.
 */

public class MainActivityContentAdapter extends FragmentPagerAdapter {
    private final int TOTAL_PAGES = 2;
    public static final int SONGS_PAGE_POSITION = 0;
    public static final int ALBUMS_PAGE_POSITION = 1;

    public MainActivityContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SongListFragment();
            case 1:
                return new AlbumListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TOTAL_PAGES;
    }

}
