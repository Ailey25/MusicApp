package com.aileyzhang.musicapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.aileyzhang.musicapp.AlbumData;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.adapters.AlbumListAdapter;

/**
 * Created by Ailey on 2017-12-19.
 */

public class AlbumListFragment extends android.support.v4.app.Fragment {
    private AlbumData albumData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize album data
        albumData = new AlbumData(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        GridView albumListView = view.findViewById(R.id.albums_list_view);
        albumListView.setAdapter(new AlbumListAdapter(getContext(), 0, albumData.mAlbums));
        return view;
    }
}
