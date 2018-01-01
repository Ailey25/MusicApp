package com.aileyzhang.musicapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Button;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ailey on 2018-01-03.
 */

public class AudioController {
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static String mediaPlayerCurrentSongPath;

    /**
     * If song is playing, button changes to pause button.
     * If song is paused, button changes to play button.
     * @param context
     * @param button
     */
    public static void setSongPlayPause (Context context, Button button) {
        if (mediaPlayer.isPlaying()) {
            button.setBackground(context.getDrawable(R.drawable.ic_pause_light));
        } else {
            button.setBackground(context.getDrawable(R.drawable.ic_play_light));
        }
    }

    /**
     * If clicked on different song, play clicked song from beginning.
     * If clicked on same song, if it's not playing, resume else if it's playing, pause it.
     * @param curSongPath
     */
    public static void playOrPauseCurrentSong(String curSongPath) {
        if (!curSongPath.equals(mediaPlayerCurrentSongPath)) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(curSongPath);
                mediaPlayerCurrentSongPath = curSongPath;
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
            mediaPlayer.start();
        } else if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public static String toReadableDuration(int duration) {
        long minutes =  TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
}
