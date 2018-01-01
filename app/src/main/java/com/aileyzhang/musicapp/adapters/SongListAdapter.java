package com.aileyzhang.musicapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aileyzhang.musicapp.AudioController;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.Song;
import com.aileyzhang.musicapp.activities.MainActivity;
import com.aileyzhang.musicapp.activities.SongItemActivity;
import com.aileyzhang.musicapp.views.SongListItemView;

import java.util.List;

/**
 * Created by Ailey on 2017-12-16.
 */

public class SongListAdapter extends ArrayAdapter<Song> {
    public static Song currentSongOnBottomBar;

    public SongListAdapter(@NonNull Context context, int resource, @NonNull List<Song> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AudioController.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
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
        TextView artist = songListItemView.findViewById(R.id.song_list_artist);
        if (curSong.mArtist != null && !curSong.mArtist.isEmpty()) {
            artist.setText(curSong.mArtist);
        }

        songListItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSongInSongListClick(curSong);
                currentSongOnBottomBar = curSong;
            }
        });

        (MainActivity.currentSongLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPersistentCurrentSongClick(view, currentSongOnBottomBar);
            }
        });
        return songListItemView;
    }


    private void onSongInSongListClick(final Song curSong) {
        AudioController.playOrPauseCurrentSong(curSong.mPath);

        // Show the currently playing song
        (MainActivity.currentSongLayout).setVisibility(View.VISIBLE);
        ImageView artwork = (MainActivity.currentSongLayout).findViewById(R.id.song_list_artwork);
        TextView title = (MainActivity.currentSongLayout).findViewById(R.id.song_list_title);
        TextView artist = (MainActivity.currentSongLayout).findViewById(R.id.song_list_artist);
        final Button songOnBottomBarPlayPauseButton = (MainActivity.currentSongLayout).findViewById(
                R.id.current_song_play_pause);

        artwork.setImageBitmap(curSong.mArtwork);
        title.setText(curSong.mTitle);
        artist.setText(curSong.mArtist);

        AudioController.setSongPlayPause(getContext(), songOnBottomBarPlayPauseButton);
        songOnBottomBarPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioController.playOrPauseCurrentSong(curSong.mPath);
                AudioController.setSongPlayPause(getContext(), songOnBottomBarPlayPauseButton);
            }
        });
    }

    private void onPersistentCurrentSongClick(View view, Song curSong) {
        Intent songItemIntent = new Intent(view.getContext(), SongItemActivity.class);
        songItemIntent.putExtra("SONG_PATH", curSong.mPath);
        songItemIntent.putExtra("SONG_TITLE", curSong.mTitle);
        songItemIntent.putExtra("SONG_ARTIST", curSong.mArtist);
        songItemIntent.putExtra("SONG_DURATION", curSong.mDuration);
        view.getContext().startActivity(songItemIntent);
    }
}
