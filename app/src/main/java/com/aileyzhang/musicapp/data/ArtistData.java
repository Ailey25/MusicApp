package com.aileyzhang.musicapp.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by Ailey on 2017-12-23.
 */

public class ArtistData {
    private static final Uri ARTISTS_URI = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

    public static ArrayList<Artist> getAllArtists(Context context) {
        ArrayList<Artist> artists = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.ARTIST_KEY,};

        Cursor artistCursor = context.getContentResolver().query(ARTISTS_URI, projection,
                null, null, null);

        try {
            if (artistCursor != null) {
                while (artistCursor.moveToNext()) {
                    String artistName = artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                    int albumsCount = artistCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
                    String artistKey = artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST_KEY));

                    Artist artist = new Artist(artistName, albumsCount, artistKey);
                    artists.add(artist);
                }
            }
            return artists;
        } finally {
            if (artistCursor != null) artistCursor.close();
        }
    }
}
