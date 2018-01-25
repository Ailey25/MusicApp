package com.aileyzhang.musicapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.data.Album;
import com.aileyzhang.musicapp.views.AlbumListItemView;

import java.util.List;

/**
 * Created by Ailey on 2017-12-19.
 */

public class AlbumGridAdapter extends ArrayAdapter<Album> {

    public interface AlbumAdapterListener {
        void onAlbumClicked(Album album);
    }

    private AlbumAdapterListener mAlbumAdapterListener;

    public AlbumGridAdapter(@NonNull Context context, int resource, @NonNull List<Album> objects) {
        super(context, resource, objects);
    }

    public void setListener(AlbumAdapterListener listener) {
        mAlbumAdapterListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        notifyDataSetChanged();
        final Album curAlbum = getItem(position);
        View albumItemView = convertView;
        if (albumItemView == null) {
            albumItemView = new AlbumListItemView(getContext());
        }

        ImageView artwork = albumItemView.findViewById(R.id.album_list_artwork);
        if (curAlbum.mArtwork != null) {
            artwork.setImageBitmap(curAlbum.mArtwork);
        }

        TextView title = albumItemView.findViewById(R.id.album_list_title);
        if (curAlbum.mAlbumTitle != null && !curAlbum.mAlbumTitle.isEmpty()) {
            title.setText(curAlbum.mAlbumTitle);
        }

        if (curAlbum.mArtist != null && !curAlbum.mArtist.isEmpty()) {
            TextView artist = albumItemView.findViewById(R.id.album_list_artist);
            artist.setText(curAlbum.mArtist);
        }

        TextView numOfSongs = albumItemView.findViewById(R.id.album_list_number_of_songs);
        String songsCount;
        if (curAlbum.mNumOfSongs == 1) {
            songsCount = "1 song";
        } else {
            songsCount = curAlbum.mNumOfSongs + " songs";
        }
        numOfSongs.setText(songsCount);

        albumItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAlbumAdapterListener != null) {
                    mAlbumAdapterListener.onAlbumClicked(curAlbum);
                }
            }
        });

        return albumItemView;
    }
}
