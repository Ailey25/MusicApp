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
    private static final Uri MEDIA_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    public static ArrayList<Song> getAllSongs (Context context) {
        ArrayList<Song> songs = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.DURATION,};

        Cursor songCursor = context.getContentResolver().query(MEDIA_URI, projection,
                null, null, null);

        try {
            if (songCursor != null) {
                while (songCursor.moveToNext()) {
                    String path = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String artist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String album = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String duration = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(path);
                    InputStream inputStream = null;
                    if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                        inputStream = new ByteArrayInputStream(mediaMetadataRetriever.getEmbeddedPicture());
                    }
                    mediaMetadataRetriever.release();
                    Bitmap art = BitmapFactory.decodeStream(inputStream);

                    Song song = new Song(path, title, artist, album, art, duration);
                    songs.add(song);
                }
            }
            return songs;
        } finally {
            if (songCursor != null) songCursor.close();
        }
    }

    public static ArrayList<Song> getSongsInAlbum (Context context, String albumName) {
        ArrayList<Song> songs = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.DURATION,};
        String selection = MediaStore.Audio.AudioColumns.ALBUM + "=?";
        String[] selectionArgs = new String[] {albumName};

        Cursor songCursor = context.getContentResolver().query(MEDIA_URI, projection,
                selection, selectionArgs, null);

        try {
            if (songCursor != null) {
                while (songCursor.moveToNext()) {
                    String path = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String artist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String album = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String duration = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(path);
                    InputStream inputStream = null;
                    if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                        inputStream = new ByteArrayInputStream(mediaMetadataRetriever.getEmbeddedPicture());
                    }
                    mediaMetadataRetriever.release();
                    Bitmap art = BitmapFactory.decodeStream(inputStream);

                    Song song = new Song(path, title, artist, album, art, duration);
                    songs.add(song);
                }
            }
            return songs;
        } finally {
            if (songCursor != null) songCursor.close();
        }
    }

    public static ArrayList<Song> getSongsInArtist (Context context, String artistName) {
        ArrayList<Song> songs = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.DURATION,};

        String selection = MediaStore.Audio.AudioColumns.ARTIST + "=?";
        String[] selectionArgs = new String[] {artistName};

        Cursor songCursor = context.getContentResolver().query(MEDIA_URI, projection,
                selection, selectionArgs, null);

        try {
            if (songCursor != null) {
                while (songCursor.moveToNext()) {
                    String path = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String artist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String album = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String duration = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(path);
                    InputStream inputStream = null;
                    if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                        inputStream = new ByteArrayInputStream(mediaMetadataRetriever.getEmbeddedPicture());
                    }
                    mediaMetadataRetriever.release();
                    Bitmap art = BitmapFactory.decodeStream(inputStream);

                    Song song = new Song(path, title, artist, album, art, duration);
                    songs.add(song);
                }
            }
            return songs;
        } finally {
            if (songCursor != null) songCursor.close();
        }
    }
}