package com.aileyzhang.musicapp.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.Song;
import com.aileyzhang.musicapp.SongData;
import com.aileyzhang.musicapp.activities.MainActivity;
import com.aileyzhang.musicapp.adapters.SongListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.aileyzhang.musicapp.adapters.AlbumSongListAdapter.ALBUM_SONG_LIST_VIEW;


/**
 * Created by Ailey on 2017-12-12.
 */

public class SongListFragment extends Fragment {
    private SongData songData;

    public static final SongListFragment newInstance(int showAllSongs, String albumID) {
        SongListFragment songListFragment = new SongListFragment();
        Bundle bundle = new Bundle(2);
        //bundle.putParcelableArrayList("SONGS", songArrayList);
        bundle.putInt("SHOW_ALL_SONGS", showAllSongs);
        bundle.putString("ALBUM_ID", albumID);
        songListFragment.setArguments(bundle);
        return songListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songData = new SongData((getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);
        ListView songListView = view.findViewById(R.id.songs_list_view);
        songListView.setAdapter(new SongListAdapter(getContext(), 0, songData.mSongs));
        return view;
    }
}
