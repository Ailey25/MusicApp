package com.aileyzhang.musicapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aileyzhang.musicapp.ArtistData;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.adapters.ArtistListAdapter;

/**
 * Created by Ailey on 2017-12-23.
 */

public class ArtistListFragment extends Fragment implements ArtistListAdapter.ArtistAdapterListener {

    private ArtistData artistData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize album data
        artistData = new ArtistData(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_list, container, false);
        ListView albumListView = view.findViewById(R.id.artists_list_view);
        albumListView.setAdapter(new ArtistListAdapter(getContext(), 0, artistData.mArtists));
        return view;
    }

    @Override
    public void onArtistClick(String artistKey) {

    }
}
