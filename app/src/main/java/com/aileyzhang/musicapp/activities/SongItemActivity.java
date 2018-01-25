package com.aileyzhang.musicapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aileyzhang.musicapp.AudioController;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.data.Song;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SongItemActivity extends AppCompatActivity implements Runnable {
    private String mPath;
    private String mTitle;
    private String mArtist;
    private int mDuration;
    private Song currentSong;

    private ImageView songArtwork;
    private TextView totalDuration;
    private TextView songTitle;
    private TextView artist;
    private Button songItemPlayPauseButton;
    private Handler durationThreadHandler = new Handler();
    private SeekBar durationSeekBar;
    private TextView currentDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mPath = intent.getStringExtra("SONG_PATH");
        mTitle = intent.getStringExtra("SONG_TITLE");
        mArtist = intent.getStringExtra("SONG_ARTIST");
        mDuration = intent.getIntExtra("SONG_DURATION", 0);
        setContentView(R.layout.item_song);
        setActivityLayout();
        SongItemActivity.this.runOnUiThread(this);
    }

    private void setActivityLayout() {
        // Album art
        songArtwork = findViewById(R.id.item_song_album_art);
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(mPath);
        InputStream inputStream = null;
        if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
            inputStream = new ByteArrayInputStream(mediaMetadataRetriever.getEmbeddedPicture());
        }
        mediaMetadataRetriever.release();
        Bitmap art = BitmapFactory.decodeStream(inputStream);
        if (art != null) {
            songArtwork.setImageBitmap(art);
        }

        // Duration seek bar
        durationSeekBar = findViewById(R.id.item_song_duration_bar);
        durationSeekBar.setMax(mDuration);
        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar != null && fromUser) {
                    AudioController.mediaPlayer.seekTo(progress);
                    currentDuration.setText(AudioController.toReadableDuration(progress));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Current and total duration
        currentDuration = findViewById(R.id.item_song_current_duration);
        totalDuration = findViewById(R.id.item_song_total_duration);
        totalDuration.setText(AudioController.toReadableDuration(mDuration));

        // Song title
        songTitle = findViewById(R.id.item_song_title);
        if (mTitle != null && !mTitle.isEmpty()) {
            songTitle.setText(mTitle);
        }

        // Song artist
        artist = findViewById(R.id.item_song_artist);
        if (mArtist != null && !mArtist.isEmpty()) {
            artist.setText(mArtist);
        }

        // Rewind song / Play previous song
        Button songItemRewindButton = findViewById(R.id.item_song_rewind);
        songItemRewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioController.onPlayRewindClick(getBaseContext());
            }
        });

        // Play Pause Button
        songItemPlayPauseButton = findViewById(R.id.item_song_play_pause);
        AudioController.setSongPlayPause(getBaseContext(), songItemPlayPauseButton);
        songItemPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioController.playOrPauseInSongItem();
                AudioController.setSongPlayPause(getBaseContext(), songItemPlayPauseButton);
            }
        });

        // Next Song
        Button songItemNextButton = findViewById(R.id.item_song_forward);
        songItemNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioController.onPlayNextClick(getBaseContext());
                updateView();
            }
        });
    }

    @Override
    public void run() {
        if (AudioController.mediaPlayer != null) {
            int currentPosition = AudioController.mediaPlayer.getCurrentPosition();
            durationSeekBar.setProgress(currentPosition);
            currentDuration.setText(AudioController.toReadableDuration(currentPosition));
        }
        durationThreadHandler.postDelayed(this, 200);
    }

    private void updateView() {
        currentSong = AudioController.currentSong;
        Log.e("DEBUG", "progress: " + AudioController.toReadableDuration(0));

        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(currentSong.mPath);
        InputStream inputStream = null;
        if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
            inputStream = new ByteArrayInputStream(mediaMetadataRetriever.getEmbeddedPicture());
        }
        mediaMetadataRetriever.release();
        Bitmap art = BitmapFactory.decodeStream(inputStream);
        if (art != null) {
            songArtwork.setImageBitmap(art);
        }
        totalDuration.setText(AudioController.toReadableDuration(currentSong.mDuration));
        durationSeekBar.setMax(currentSong.mDuration);
        currentDuration.setText(AudioController.toReadableDuration(0));
        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar != null && fromUser) {
                    AudioController.mediaPlayer.seekTo(progress);
                    currentDuration.setText(AudioController.toReadableDuration(progress));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        songTitle.setText(currentSong.mTitle);
        artist.setText(currentSong.mArtist);
    }
}