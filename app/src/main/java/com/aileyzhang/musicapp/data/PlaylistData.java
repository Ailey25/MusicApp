package com.aileyzhang.musicapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by Ailey on 2018-01-04.
 */

public class PlaylistData {
    private static final Uri PLAYLISTS_URI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
    private static String[] playlistProjection = new String[]{
            MediaStore.Audio.Playlists.DATA,
            MediaStore.Audio.Playlists.NAME,
            MediaStore.Audio.Playlists._ID,};

    public static ArrayList<Playlist> getAllPlaylist(Context context) {
        ArrayList<Playlist> playlists = new ArrayList<>();

        Cursor playlistCursor = context.getContentResolver().query(PLAYLISTS_URI, playlistProjection,
                null, null, null);

        try {
            if (playlistCursor != null) {
                while (playlistCursor.moveToNext()) {
                    String playlistPath = playlistCursor.getString(playlistCursor.getColumnIndex(
                            MediaStore.Audio.Playlists.DATA));
                    String playlistName = playlistCursor.getString(playlistCursor.getColumnIndex(
                            MediaStore.Audio.Playlists.NAME));
                    String playlistID = playlistCursor.getString(playlistCursor.getColumnIndex(
                            MediaStore.Audio.Playlists._ID));

                    Playlist playlist = new Playlist(playlistPath, playlistName, playlistID, null);
                    playlists.add(playlist);
                }
            }
            return playlists;
        } finally {
            if (playlistCursor != null) playlistCursor.close();
        }
    }

    /**
     * Retrieve the id of a playlist given its name. -1 if playlist does not exist.
     * @param context
     * @param playlistName
     * @return
     */
    public static long getPlaylistID(Context context, String playlistName) {
        long id = -1;

        String selection = MediaStore.Audio.Playlists.NAME + "=?";
        String[] selectionArgs = new String[] {playlistName};

        Cursor cursor = context.getContentResolver().query(
                PLAYLISTS_URI, new String[] { MediaStore.Audio.Playlists._ID },
                selection, selectionArgs, null);

        if (cursor != null) {
            if (cursor.moveToNext())
                id = cursor.getLong(0);
            cursor.close();
        }

        return id;
    }

    /** Overwrite (clear) playlist with given playlist name.
     * @param context
     * @param playlistName
     */
    public static void overwritePlaylist(Context context, String playlistName, long playlistID) {
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistID);
        context.getContentResolver().delete(uri, null, null);
    }

    /** Creates new playlist with name that is not taken. Return playlist id.
     * @param context
     * @param playlistName
     */
    public static long createNewPlaylist(Context context, String playlistName) {
        ContentValues values = new ContentValues(1);
        values.put(MediaStore.Audio.Playlists.NAME, playlistName);
        Uri uri = context.getContentResolver().insert(PLAYLISTS_URI, values);
        long playlistID = Long.parseLong(uri.getLastPathSegment());
        return playlistID;
    }

    public static void addSongToPlaylist(Context context, long playlistID, long audioID) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, 0);
        values.put(MediaStore.Audio.Playlists.Members.PLAYLIST_ID, playlistID);
        values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, audioID);
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistID);
        context.getContentResolver().insert(uri, values);
    }
}
