package com.aileyzhang.musicapp.adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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
    static MediaPlayer mediaPlayer = new MediaPlayer();
    public static String mediaPlayerCurrentSongPath;

    public SongListAdapter(@NonNull Context context, int resource, @NonNull List<Song> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        View songListItemView = convertView;
        if (songListItemView == null) {
            songListItemView = new SongListItemView(getContext());
        }
        final Song curSong = getItem(position);

        ImageView artwork = songListItemView.findViewById(R.id.song_list_artwork);
        if (curSong.mArtwork != null) {
            artwork.setImageBitmap(curSong.mArtwork);
        }
        TextView title = songListItemView.findViewById(R.id.song_list_title);
        if (curSong.mTitle != null && !curSong.mTitle.isEmpty()) {
            title.setText(curSong.mTitle);
        }
        if (curSong.mArtist != null && !curSong.mArtist.isEmpty()) {
            TextView artist = songListItemView.findViewById(R.id.song_list_artist);
            artist.setText(curSong.mArtist);
        }

        songListItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSongClick(curSong.mPath);
            }
        });
        return songListItemView;
    }

    public void onSongClick(String songPath) {
        if (songPath == mediaPlayerCurrentSongPath) {   // Clicked on currently playing song. Pause it
            mediaPlayer.pause();
        } else {                                        // Play clicked song
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(songPath);
                mediaPlayerCurrentSongPath = songPath;
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("DEBUGMODE", "MediaPlayer in SongListAdapter exception");
            }
        }
    }

}
