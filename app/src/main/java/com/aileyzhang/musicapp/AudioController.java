package com.aileyzhang.musicapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Button;

import com.aileyzhang.musicapp.data.Song;
import com.aileyzhang.musicapp.data.SongData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ailey on 2018-01-03.
 */

public class AudioController {
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static Song currentSong;
    public static ArrayList<Song> currentSongQueue = new ArrayList<>();

    /**
     * If null, create a shuffled queue. If queue already exists, remove deleted songs
     * @param context
     */
    public static void setUpSongQueue(Context context) {
        // Only shuffle for now
        if (currentSongQueue == null || currentSongQueue.size() == 0) {
            currentSongQueue = SongData.getAllSongs(context);
            Collections.shuffle(currentSongQueue);
        } else {
            // temp stores the most updated songs in library
            ArrayList<Song> temp = SongData.getAllSongs(context);
            Iterator<Song> it = currentSongQueue.iterator();
            for (int i = 0; i < currentSongQueue.size(); i++) {
                if (!temp.contains(currentSongQueue.get(i))) {
                    currentSongQueue.remove(i);
                }
            }
        }
    }

    /**
     * Plays next song. If currently playing last song, create new shuffled queue and start playing.
     */
    public static void playNext(Context context) {
//        Log.e("DEBUGMODE", "Queue ");
//        for (Song s: currentSongQueue) {
//            Log.e("DEBUGMODE", s.mTitle);
//        }
        Boolean isLastSong = false;
        int curSongInd = currentSongQueue.indexOf(currentSong);
        if (curSongInd == currentSongQueue.size() - 1) {
            isLastSong = true;
        }
        if (!isLastSong) {
            Song nextSong = currentSongQueue.get(curSongInd + 1);
            if (mediaPlayer.isPlaying()) {
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(nextSong.mPath);
                    currentSong = currentSongQueue.get(curSongInd + 1);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                currentSong = currentSongQueue.get(curSongInd + 1);
            }
        } else {
            currentSongQueue.clear();
            currentSongQueue = SongData.getAllSongs(context);
            Collections.shuffle(currentSongQueue);
            if (mediaPlayer.isPlaying()) {
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(currentSongQueue.get(0).mPath);
                    currentSong = currentSongQueue.get(0);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                currentSong = currentSongQueue.get(0);
            }
        }
    }

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

    public static void playOrPauseInBottomBar(String curSongPath) {
        if (currentSong == null || !curSongPath.equals(currentSong.mPath)) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(curSongPath);
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

    public static void playOrPauseInSongItem() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(currentSong.mPath);
                mediaPlayer.prepare();
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Takes an audio's duration and turns it into readable audio duration to be displayed
     * @param duration
     * @return
     */
    public static String toReadableDuration(int duration) {
        long minutes =  TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
}
