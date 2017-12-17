package com.aileyzhang.musicapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ailey on 2017-12-15.
 */

public class SongData {
    private static final String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() +
            File.separator + "Download" + File.separator;
    private static final String mp3Pattern = ".mp3";
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
    public ArrayList<Song> mSongs = new ArrayList<>();

    public SongData() {
        loadAllSongs();
    }

    private void loadAllSongs() {
        // Set up songs
        File home = new File(MEDIA_PATH);
        File[] listFiles = home.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file: listFiles) {
                if (file.getName().endsWith(mp3Pattern)) {
                    String mp3Name = file.getName();
                    mMediaMetadataRetriever.setDataSource(MEDIA_PATH + mp3Name);
                    String title = mMediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                    String artist = mMediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                    String genre = mMediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
                    byte[] art = mMediaMetadataRetriever.getEmbeddedPicture();
                    Bitmap artwork = null;
                    if (art != null) {
                        artwork = BitmapFactory.decodeByteArray(art, 0, art.length);
                    }
                    Song song = new Song(title, artist, genre, artwork);
                    mSongs.add(song);
                }
            }
        }
    }



//    public void songThumbnailClick(View view) {
//        try {
//            mMediaPlayer.setDataSource(MEDIA_PATH + "test.mp3");
//            mMediaPlayer.prepare();
//            mMediaPlayer.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
