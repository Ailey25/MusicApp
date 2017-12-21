package com.aileyzhang.musicapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Ailey on 2017-12-15.
 */

public class SongData {
    private Context context;
    public ArrayList<Song> mSongs = new ArrayList<>();

    public SongData(Context context) {
        this.context = context;
        loadAllSongs();
    }

    private void loadAllSongs() {
        Uri allSongUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,};
        Cursor cursor = context.getContentResolver().query(allSongUri, projection,
                null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));

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
}