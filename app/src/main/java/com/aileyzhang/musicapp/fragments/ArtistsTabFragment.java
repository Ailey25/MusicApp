package com.aileyzhang.musicapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aileyzhang.musicapp.CustomSwipeViewPager;
import com.aileyzhang.musicapp.data.ArtistData;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.data.SongData;
import com.aileyzhang.musicapp.adapters.ArtistListAdapter;
import com.aileyzhang.musicapp.adapters.ArtistViewPagerAdapter;
import com.aileyzhang.musicapp.adapters.SongListAdapter;

/**
 * Created by Ailey on 2017-12-23.
 */

public class ArtistsTabFragment extends Fragment implements ArtistListAdapter.ArtistAdapterListener {

    private View view;
    private CustomSwipeViewPager mArtistViewPager;
    private ListView artistListView;
    private ListView artistSongsListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_artists_tab, container, false);
        mArtistViewPager = view.findViewById(R.id.artist_view_pager);
        artistListView = view.findViewById(R.id.artist_list_view);
        artistSongsListView = view.findViewById(R.id.artist_songs_listview);

        ArtistListAdapter artistListAdapter = new ArtistListAdapter(getContext(), 0,
                ArtistData.getAllArtists(getContext()));
        artistListAdapter.setListener(this);
        artistListView.setAdapter(artistListAdapter);

        mArtistViewPager.setAdapter(new ArtistViewPagerAdapter());
        mArtistViewPager.setCurrentItem(ArtistViewPagerAdapter.ARTIST_LIST_VIEW);
        return view;
    }

    @Override
    public void onArtistClick(String artistName) {
        TextView tv = view.findViewById(R.id.artist_name_of_songs);
        tv.setText(artistName);

        artistSongsListView.setAdapter(new SongListAdapter(getContext(), 0,
                SongData.getSongsInArtist(getContext(), artistName)));
        mArtistViewPager.setCurrentItem(ArtistViewPagerAdapter.ARTIST_SONG_LIST_VIEW);

        mArtistViewPager.setIsSwipeEnabled(true);
    }
}
