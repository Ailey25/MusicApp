package com.aileyzhang.musicapp.data;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Ailey on 2017-12-15.
 */

public class SongData {
    private static final Uri MEDIA_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static final String[] songProjection = new String[]{
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.DURATION,};

    public static ArrayList<Song> getAllSongs (Context context) {
        ArrayList<Song> songs = new ArrayList<>();

        Cursor songCursor = context.getContentResolver().query(MEDIA_URI, songProjection,
                null, null, null);

        try {
            if (songCursor != null) {
                while (songCursor.moveToNext()) {
                    String path = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String id = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media._ID));
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

                    Song song = new Song(path, id, title, artist, album, art, duration);
                    songs.add(song);
                }
            }
            return songs;
        } finally {
            if (songCursor != null) songCursor.close();
        }
    }

    public static ArrayList<Song> getSongsInAlbum (Context context, Album album) {
        ArrayList<Song> songs = new ArrayList<>();

        String selection = MediaStore.Audio.AudioColumns.ALBUM + "=? AND " +
                            MediaStore.Audio.AudioColumns.ARTIST + "=?";
        String[] selectionArgs = new String[] {album.mAlbumTitle, album.mArtist};

        Cursor songCursor = context.getContentResolver().query(MEDIA_URI, songProjection,
                selection, selectionArgs, null);

        try {
            if (songCursor != null) {
                while (songCursor.moveToNext()) {
                    String path = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String id = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String title = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String artist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String albumName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String duration = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(path);
                    InputStream inputStream = null;
                    if (mediaMetadataRetriever.getEmbeddedPicture() != null) {
                        inputStream = new ByteArrayInputStream(mediaMetadataRetriever.getEmbeddedPicture());
                    }
                    mediaMetadataRetriever.release();
                    Bitmap art = BitmapFactory.decodeStream(inputStream);

                    Song song = new Song(path, id, title, artist, albumName, art, duration);
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

        String selection = MediaStore.Audio.AudioColumns.ARTIST + "=?";
        String[] selectionArgs = new String[] {artistName};

        Cursor songCursor = context.getContentResolver().query(MEDIA_URI, songProjection,
                selection, selectionArgs, null);

        try {
            if (songCursor != null) {
                while (songCursor.moveToNext()) {
                    String path = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String id = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media._ID));
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

                    Song song = new Song(path, id, title, artist, album, art, duration);
                    songs.add(song);
                }
            }
            return songs;
        } finally {
            if (songCursor != null) songCursor.close();
        }
    }

    public static ArrayList<Song> getSongsInPlaylist(Context context, String id) {
        long playlistID = Long.parseLong(id);
        ArrayList<Song> songs = new ArrayList<>();

        String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0 ";
        String[] selectionArgs = null;

        Cursor songCursor = context.getContentResolver().query(
                MediaStore.Audio.Playlists.Members.getContentUri("external", playlistID),
                songProjection, selection, selectionArgs, null);

        try {
            if (songCursor != null) {
                while (songCursor.moveToNext()) {
                    String path = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String audioID = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media._ID));
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

                    Song song = new Song(path, audioID, title, artist, album, art, duration);
                    songs.add(song);
                }
            }
            return songs;
        } finally {
            if (songCursor != null) songCursor.close();
        }
    }

    public static void deleteSongFromDatabase(Context context, Song song) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.MediaColumns.DATA + "='" + song.mPath + "'";
        context.getContentResolver().delete(uri, selection, null);
    }

    public static void deleteSongFromPlaylist(Context context, Song song, Playlist playlist) {
        long playlistID = Long.parseLong(playlist.mID);
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistID);
        String selection = MediaStore.Audio.Playlists.Members._ID + "=?";
        String[] selectionArgs = {song.mAudioID};
        context.getContentResolver().delete(uri, selection, selectionArgs);
    }
}