package com.aileyzhang.musicapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.SongData;

/**
 * Created by Ailey on 2017-12-19.
 */

public class AlbumListFragment extends android.support.v4.app.Fragment {
    private SongData songData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize song data
        songData = new SongData(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
//        ListView songListView = view.findViewById(R.id.songs_list_view);
//        songListView.setAdapter(new SongListAdapter(getContext(), 0, songData.mSongs));
        return view;
    }
}
