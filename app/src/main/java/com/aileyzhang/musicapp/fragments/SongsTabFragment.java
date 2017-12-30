package com.aileyzhang.musicapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.SongData;
import com.aileyzhang.musicapp.adapters.SongListAdapter;


/**
 * Created by Ailey on 2017-12-12.
 */

public class SongsTabFragment extends Fragment {

    public static final SongsTabFragment newInstance(int showAllSongs, String albumName) {
        SongsTabFragment songsTabFragment = new SongsTabFragment();
        Bundle bundle = new Bundle(2);
        //bundle.putParcelableArrayList("SONGS", songArrayList);
        bundle.putInt("SHOW_ALL_SONGS", showAllSongs);
        bundle.putString("ALBUM_NAME", albumName);
        songsTabFragment.setArguments(bundle);
        return songsTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs_tab, container, false);
        ListView songListView = view.findViewById(R.id.songs_list_view);
        songListView.setAdapter(new SongListAdapter(getContext(), 0, SongData.getAllSongs(getContext())));
        return view;
    }
}
