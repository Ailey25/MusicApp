package com.aileyzhang.musicapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.aileyzhang.musicapp.AlbumData;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.adapters.AlbumGridAdapter;
import com.aileyzhang.musicapp.adapters.AlbumViewPagerAdapter;

/**
 * Created by Ailey on 2017-12-19.
 */

public class AlbumsTabFragment extends Fragment implements AlbumGridAdapter.AlbumAdapterListener {

    private AlbumData albumData;
    private ViewPager mAlbumViewPager;
    private GridView albumGridView;
    private ListView albumSongsListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize album data
        albumData = new AlbumData(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums_tab, container, false);
        mAlbumViewPager = view.findViewById(R.id.album_view_pager);
        albumGridView = view.findViewById(R.id.albums_grid_view);
        albumSongsListView = view.findViewById(R.id.album_songs_listview);

        AlbumGridAdapter albumGridAdapter = new AlbumGridAdapter(getContext(), 0, albumData.mAlbums);
        albumGridAdapter.setListener(this);
        albumGridView.setAdapter(albumGridAdapter);

        mAlbumViewPager.setAdapter(new AlbumViewPagerAdapter());
        mAlbumViewPager.setCurrentItem(AlbumViewPagerAdapter.ALBUM_GRID_VIEW);
        return view;
    }

    public void onAlbumClicked(String albumID) {
        // Load songs, flip mAlbumViewPager to song list view...
        Log.e("ASDF", "LOAD SONGS, SHOW SONG LIST");
    }
}
