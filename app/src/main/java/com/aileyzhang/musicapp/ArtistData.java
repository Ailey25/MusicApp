package com.aileyzhang.musicapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by Ailey on 2017-12-23.
 */

public class ArtistData {
    public ArrayList<Artist> mArtists = new ArrayList<>();
    private final Uri ARTISTS_URI = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

    public ArtistData(Context context) {
        loadAllArtists(context);
    }

    private void loadAllArtists(Context context) {
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
                    mArtists.add(artist);
                }
            }
        } finally {
            if (artistCursor != null) artistCursor.close();
        }
    }
}
