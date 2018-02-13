package com.aileyzhang.musicapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Button;

import com.aileyzhang.musicapp.data.Song;
import com.aileyzhang.musicapp.data.SongData;

import java.util.ArrayList;
import java.util.Collections;
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
     * Overwrite currentSongQueue with shuffled songs in database or playlist
     */
    public static void setCurrentSongQueue(Context context) {
        currentSongQueue = SongData.getAllSongs(context);
        Collections.shuffle(currentSongQueue);
    }

    public static void setCurrentSongQueue(Context context, String playlistID) {
        currentSongQueue = SongData.getSongsInPlaylist(context, playlistID);
        Collections.shuffle(currentSongQueue);
    }

    /**
     * Remove deleted songs from currentSongQueue by comparing it to the most recent songs
     * in database or in the playlist
     * @param context
     */
    public static void updateCurrentSongQueue(Context context) {
        ArrayList<Song> mostRecentSongs = SongData.getAllSongs(context);
        currentSongQueue.retainAll(mostRecentSongs);
    }

    public static void updateCurrentSongQueue(Context context, String playlistID) {
        ArrayList<Song> mostRecentSongs = SongData.getSongsInPlaylist(context, playlistID);
        currentSongQueue.retainAll(mostRecentSongs);
        // TODO: remove checks
//        for (Song s: mostRecentSongs) {
//            Log.e("DEBUG","recent array: " + s.mTitle);
//        }
//        for (Song s: AudioController.currentSongQueue) {
//            Log.e("DEBUG","new currentSongQueue: " + s.mTitle);
//        }
    }

    /**
     * Play song from beginning
     * @param context
     */
    public static void onPlayRewindClick(Context context) {
        mediaPlayer.seekTo(0);
    }

    /**
     * Plays next song. If currently playing last song, create new shuffled queue and start playing.
     */
    public static void onPlayNextClick(Context context) {
//        Log.e("DEBUGMODE", "Queue ");
//        for (Song s: currentSongQueue) {
//            Log.e("DEBUGMODE", s.mTitle);
//        }
        mediaPlayer.seekTo(0);
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
