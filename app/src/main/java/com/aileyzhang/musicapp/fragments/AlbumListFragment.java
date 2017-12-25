package com.aileyzhang.musicapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.aileyzhang.musicapp.AlbumData;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.activities.MainActivity;
import com.aileyzhang.musicapp.adapters.AlbumListAdapter;
import com.aileyzhang.musicapp.adapters.AlbumSongListAdapter;

import static com.aileyzhang.musicapp.adapters.AlbumSongListAdapter.ALBUM_GRID_VIEW;

/**
 * Created by Ailey on 2017-12-19.
 */

public class AlbumListFragment extends Fragment implements AlbumListAdapter.AlbumAdapterListener {

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
        AlbumListAdapter albumListAdapter = new AlbumListAdapter(getContext(), 0, albumData.mAlbums);
        albumListAdapter.setListener(this);
        albumListView.setAdapter(albumListAdapter);
        // Initialize ViewPager
        ((MainActivity) getActivity()).mAlbumViewPager = view.findViewById(R.id.album_view_pager);
        AlbumSongListAdapter mAlbumSongListAdapter = new AlbumSongListAdapter(getActivity().getSupportFragmentManager());
        ((MainActivity) getActivity()).mAlbumViewPager.setAdapter(mAlbumSongListAdapter);
        ((MainActivity) getActivity()).mAlbumViewPager.setCurrentItem(ALBUM_GRID_VIEW);
        return view;
    }

    public void onAlbumClicked(String albumID) {
        Log.d("DEBUGMODE", "in albumlistfragment id is " + albumID);
//        ((MainActivity) getActivity()).mAlbumViewPager.setCurrentItem(ALBUM_SONG_LIST_VIEW);

    }
}
