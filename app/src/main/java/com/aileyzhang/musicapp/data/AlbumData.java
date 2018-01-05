package com.aileyzhang.musicapp.data;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by Ailey on 2017-12-22.
 */

public class AlbumData {
    private static final Uri ALBUMS_URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

    public static ArrayList<Album> getAllAlbums(Context context) {
        ArrayList<Album> albums = new ArrayList<>();
        String[] projection = new String[] {
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
                MediaStore.Audio.Albums.ALBUM_ART,};

        Cursor albumCursor = context.getContentResolver().query(ALBUMS_URI, projection,
                null, null, null);

        try {
            if (albumCursor != null) {
                while (albumCursor.moveToNext()) {
                    String id = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                    String albumName = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                    String artist = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                    String numOfSongs = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                    String albumArt = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));

                    Bitmap art = BitmapFactory.decodeFile(albumArt);

                    Album album = new Album(id, albumName, artist, Integer.parseInt(numOfSongs), art);
                    albums.add(album);
                }
            }
            return albums;
        } finally {
            if (albumCursor != null) albumCursor.close();
        }
    }
}
