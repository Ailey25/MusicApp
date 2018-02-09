package com.aileyzhang.musicapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aileyzhang.musicapp.fragments.AlbumsTabFragment;
import com.aileyzhang.musicapp.fragments.ArtistsTabFragment;
import com.aileyzhang.musicapp.fragments.PlaylistsTabFragment;
import com.aileyzhang.musicapp.fragments.SongsTabFragment;

/**
 * Created by Ailey on 2017-12-12.
 */

public class MainActivityContentAdapter extends FragmentPagerAdapter {

    private final int TOTAL_PAGES = 4;
    public static final int SONGS_PAGE_POSITION = 0;
    public static final int ARTISTS_PAGE_POSITION = 1;
    public static final int ALBUMS_PAGE_POSITION = 2;
    public static final int PLAYLISTS_PAGE_POSITION = 3;
    public PlaylistsTabFragment playlistsTabFragment;

    public MainActivityContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case SONGS_PAGE_POSITION:
                return SongsTabFragment.newInstance(1, "");
            case ARTISTS_PAGE_POSITION:
                return new ArtistsTabFragment();
            case ALBUMS_PAGE_POSITION:
                return new AlbumsTabFragment();
            case PLAYLISTS_PAGE_POSITION:
                playlistsTabFragment = new PlaylistsTabFragment();
                return playlistsTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TOTAL_PAGES;
    }

}
