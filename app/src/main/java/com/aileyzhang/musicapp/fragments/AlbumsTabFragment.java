package com.aileyzhang.musicapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.aileyzhang.musicapp.CustomSwipeViewPager;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.adapters.AlbumGridAdapter;
import com.aileyzhang.musicapp.adapters.AlbumViewPagerAdapter;
import com.aileyzhang.musicapp.adapters.SongListAdapter;
import com.aileyzhang.musicapp.data.Album;
import com.aileyzhang.musicapp.data.AlbumData;
import com.aileyzhang.musicapp.data.SongData;

/**
 * Created by Ailey on 2017-12-19.
 */

public class AlbumsTabFragment extends Fragment implements AlbumGridAdapter.AlbumAdapterListener {

    private View view;
    private CustomSwipeViewPager mAlbumViewPager;
    private GridView albumGridView;
    private ListView albumSongsListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_albums_tab, container, false);
        mAlbumViewPager = view.findViewById(R.id.album_view_pager);
        albumGridView = view.findViewById(R.id.albums_grid_view);
        albumSongsListView = view.findViewById(R.id.album_songs_listview);

        AlbumGridAdapter albumGridAdapter = new AlbumGridAdapter(getContext(), 0,
                AlbumData.getAllAlbums(getContext()));
        albumGridAdapter.setListener(this);
        albumGridView.setAdapter(albumGridAdapter);

        mAlbumViewPager.setAdapter(new AlbumViewPagerAdapter());
        mAlbumViewPager.setCurrentItem(AlbumViewPagerAdapter.ALBUM_GRID_VIEW);
        return view;
    }

    public void onAlbumClicked(Album album) {
        TextView tv = view.findViewById(R.id.album_name_of_songs);
        tv.setText(album.mAlbumTitle);

        SongListAdapter albumSongListAdapter = new SongListAdapter(getContext(), 0,
                SongData.getSongsInAlbum(getContext(), album));
        albumSongListAdapter.setParentTabFragment("Albums");
        albumSongsListView.setAdapter(albumSongListAdapter);

        mAlbumViewPager.setCurrentItem(AlbumViewPagerAdapter.ALBUM_SONG_LIST_VIEW);
        mAlbumViewPager.setIsSwipeEnabled(true);
    }
}
