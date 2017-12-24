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
    public ArrayList<Song> mSongs = new ArrayList<>();

    public SongData(Context context) {
        loadAllSongs(context);
    }

    public SongData(Context context, String albumID) {
        loadSongsFromAlbum(albumID);
    }

    private void loadAllSongs(Context context) {
        Uri songsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,};
        Cursor songCursor = context.getContentResolver().query(songsUri, projection,
                null, null, null);
        try {
            if (songCursor != null) {
                while (songCursor.moveToNext()) {
                    String path = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String artist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String album = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));

                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(path);
                    InputStream inputStream = null;
                    if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                        inputStream = new ByteArrayInputStream(mediaMetadataRetriever.getEmbeddedPicture());
                    }
                    mediaMetadataRetriever.release();
                    Bitmap art = BitmapFactory.decodeStream(inputStream);

                    Song song = new Song(path, title, artist, album, art);
                    mSongs.add(song);
                }
            }
        } finally {
            if (songCursor != null) songCursor.close();
        }
    }

    private void loadSongsFromAlbum(String albumID) {

    }
}