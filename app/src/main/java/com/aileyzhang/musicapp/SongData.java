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
    private static final Uri MEDIA_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private Context mContext;

    public SongData(Context context) {
        mContext = context;
        loadAllSongs();
    }

    private void loadAllSongs() {
        String[] projection = new String[]{
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,};

        Cursor songCursor = mContext.getContentResolver().query(MEDIA_URI, projection,
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

    public ArrayList<Song> loadSongsFromAlbum(String albumID) {
        ArrayList<Song> songs = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,};
        String selection = MediaStore.Audio.AudioColumns._ID + "=?";
        String[] selectionArgs = new String[] {"" + albumID};

        Cursor songCursor = mContext.getContentResolver().query(MEDIA_URI, projection,
                selection, selectionArgs, null);

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
                    songs.add(song);
                }
            }
            return songs;
        } finally {
            if (songCursor != null) songCursor.close();
        }
    }
}