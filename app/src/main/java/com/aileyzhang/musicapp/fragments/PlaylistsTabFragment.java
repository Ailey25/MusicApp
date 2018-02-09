package com.aileyzhang.musicapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aileyzhang.musicapp.CustomSwipeViewPager;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.adapters.PlaylistListAdapter;
import com.aileyzhang.musicapp.adapters.PlaylistViewPagerAdapter;
import com.aileyzhang.musicapp.adapters.SongListAdapter;
import com.aileyzhang.musicapp.data.Playlist;
import com.aileyzhang.musicapp.data.PlaylistData;
import com.aileyzhang.musicapp.data.SongData;

/**
 * Created by Ailey on 2018-01-03.
 */

public class PlaylistsTabFragment extends Fragment implements PlaylistListAdapter.PlaylistAdapterListener {

    private View view;
    private CustomSwipeViewPager mPlaylistViewPager;
    private ListView playlistListView;
    private ListView playlistSongsListView;
    public SongListAdapter playlistSongListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlists_tab, container, false);
        mPlaylistViewPager = view.findViewById(R.id.playlist_view_pager);
        playlistListView = view.findViewById(R.id.playlist_list_view);
        playlistSongsListView = view.findViewById(R.id.playlist_songs_listview);

        PlaylistListAdapter playlistListAdapter = new PlaylistListAdapter(getContext(), 0,
                PlaylistData.getAllPlaylist(getContext()));
        playlistListAdapter.setListener(this);
        playlistListView.setAdapter(playlistListAdapter);

        mPlaylistViewPager.setAdapter(new PlaylistViewPagerAdapter());
        mPlaylistViewPager.setCurrentItem(PlaylistViewPagerAdapter.PLAYLIST_LIST_VIEW);
        return view;
    }

    @Override
    public void onPlaylistClick(Playlist playlist) {
        TextView tv = view.findViewById(R.id.playlist_name_of_songs);
        tv.setText(playlist.mName);

        playlistSongListAdapter = new SongListAdapter(getContext(), 0,
                SongData.getSongsInPlaylist(getContext(), playlist.mID));
        playlistSongListAdapter.setParentTabFragment("Playlists", playlist);
        playlistSongsListView.setAdapter(playlistSongListAdapter);

        mPlaylistViewPager.setCurrentItem(PlaylistViewPagerAdapter.PLAYLIST_SONG_LIST_VIEW);
        mPlaylistViewPager.setIsSwipeEnabled(true);
    }

    public void updateSongsInPlaylist(Playlist playlist) {
        if (playlistSongListAdapter != null) {
            playlistSongListAdapter = new SongListAdapter(getContext(), 0,
                    SongData.getSongsInPlaylist(getContext(), playlist.mID));
            playlistSongListAdapter.setParentTabFragment("Playlists", playlist);
            playlistSongsListView.setAdapter(playlistSongListAdapter);
        }
    }
}
