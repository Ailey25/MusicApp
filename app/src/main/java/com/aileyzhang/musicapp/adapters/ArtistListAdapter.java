package com.aileyzhang.musicapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aileyzhang.musicapp.Artist;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.views.ArtistListItemView;

import java.util.List;

/**
 * Created by Ailey on 2017-12-23.
 */

public class ArtistListAdapter extends ArrayAdapter<Artist> {
    public interface ArtistAdapterListener {
        void onArtistClick(String artistKey);
    }
    ArtistAdapterListener mArtistAdapterListener;

    public ArtistListAdapter(@NonNull Context context, int resource, @NonNull List<Artist> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View artistListView = convertView;
        if (artistListView == null) {
            artistListView = new ArtistListItemView(getContext());
        }
        final Artist curArtist = getItem(position);


        TextView artist = artistListView.findViewById(R.id.artist_list_artist);
        if (curArtist.mArtist != null && !curArtist.mArtist.isEmpty()) {
            artist.setText(curArtist.mArtist);
        }

        TextView numOfSongs = artistListView.findViewById(R.id.artist_list_album_count);
        String albumsCount = "";
        if (curArtist.mNumOfAlbums == 1) {
            albumsCount = "1 album";
        } else {
            albumsCount = curArtist.mNumOfAlbums + " albums";
        }
        numOfSongs.setText(albumsCount);

        artistListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArtistAdapterListener != null) {
                    mArtistAdapterListener.onArtistClick(curArtist.mArtistKey);
                }
            }
        });

        return artistListView;
    }
}
