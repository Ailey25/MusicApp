package com.aileyzhang.musicapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.aileyzhang.musicapp.fragments.AlbumListFragment;
import com.aileyzhang.musicapp.fragments.SongListFragment;

/**
 * Created by Ailey on 2017-12-12.
 */

public class MainActivityContentAdapter extends FragmentPagerAdapter {
    private final int TOTAL_PAGES = 2;

    public MainActivityContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new SongListFragment();
                break;
            case 1:
                fragment = new AlbumListFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TOTAL_PAGES;
    }

}
