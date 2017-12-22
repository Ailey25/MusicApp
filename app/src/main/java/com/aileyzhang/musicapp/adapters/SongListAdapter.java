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
import com.aileyzhang.musicapp.Song;
import com.aileyzhang.musicapp.views.SongListItemView;

import java.util.List;

/**
 * Created by Ailey on 2017-12-16.
 */

public class SongListAdapter extends ArrayAdapter<Song> {

    public SongListAdapter(@NonNull Context context, int resource, @NonNull List<Song> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View songListView = convertView;
        if (songListView == null) {
            songListView = new SongListItemView(getContext());
        }
        Song curSong = getItem(position);

        ImageView artwork = songListView.findViewById(R.id.song_list_artwork);
        if (curSong.mArtwork != null) {
            artwork.setImageBitmap(curSong.mArtwork);
        }
        TextView title = songListView.findViewById(R.id.song_list_title);
        if (curSong.mTitle != null && !curSong.mTitle.isEmpty()) {
            title.setText(curSong.mTitle);
        }
        if (curSong.mArtist != null && !curSong.mArtist.isEmpty()) {
            TextView artist = songListView.findViewById(R.id.song_list_artist);
            artist.setText(curSong.mArtist);
        }

        return songListView;
    }
}
