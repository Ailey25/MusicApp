package com.aileyzhang.musicapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import static android.graphics.BitmapFactory.decodeFile;

/**
 * Created by Ailey on 2017-12-15.
 */

public class SongData {
    private Context context;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    public ArrayList<Song> mSongs = new ArrayList<>();

    public SongData(Context context) {
        this.context = context;
        loadAllSongs();
    }

    private void loadAllSongs() {
        Uri allSongUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[] {
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.ALBUM_ID};
        Cursor cursor = context.getContentResolver().query(allSongUri, projection,
                null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String albumID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(path);
                InputStream inputStream = null;
                if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                    inputStream = new ByteArrayInputStream(mediaMetadataRetriever.getEmbeddedPicture());
                }
                mediaMetadataRetriever.release();
                Bitmap art = BitmapFactory.decodeStream(inputStream);

                Song song = new Song(path, title, album, artist, art);
                mSongs.add(song);
            }
            cursor.close();
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
