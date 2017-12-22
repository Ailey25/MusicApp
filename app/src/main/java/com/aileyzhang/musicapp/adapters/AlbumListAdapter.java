package com.aileyzhang.musicapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aileyzhang.musicapp.Album;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.views.AlbumListItemView;

import java.util.List;

/**
 * Created by Ailey on 2017-12-19.
 */

public class AlbumListAdapter extends ArrayAdapter<Album> {
    public AlbumListAdapter(@NonNull Context context, int resource, @NonNull List<Album> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View albumListView = convertView;
        if (albumListView == null) {
            albumListView = new AlbumListItemView(getContext());
        }
        Album curAlbum = getItem(position);

        ImageView artwork = albumListView.findViewById(R.id.album_list_artwork);
        if (curAlbum.mArtwork != null) {
            artwork.setImageBitmap(curAlbum.mArtwork);
        }

        TextView title = albumListView.findViewById(R.id.album_list_title);
        if (curAlbum.mTitle != null && !curAlbum.mTitle.isEmpty()) {
            title.setText(curAlbum.mTitle);
        }

        if (curAlbum.mArtist != null && !curAlbum.mArtist.isEmpty()) {
            TextView artist = albumListView.findViewById(R.id.album_list_artist);
            artist.setText(curAlbum.mArtist);
        }

        TextView numOfSongs = albumListView.findViewById(R.id.album_list_number_of_songs);
        String songsCount = "";
        if (curAlbum.mNumOfSongs == 1){
            songsCount = "1 song";
        } else {
            songsCount = curAlbum.mNumOfSongs + " songs";
        }
        numOfSongs.setText(songsCount);

        return albumListView;
    }
}
