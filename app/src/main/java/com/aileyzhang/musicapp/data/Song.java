package com.aileyzhang.musicapp.data;

import android.graphics.Bitmap;

/**
 * Created by Ailey on 2017-12-15.
 */

public class Song {
    public String mPath;
    public String mAudioID;      // MediaStore.Audio.AudioColumns_ID == MediaStore.Audio.Playlists.Members.Audio_id
    public String mTitle;
    public String mArtist;
    public String mAlbum;
    public Bitmap mArtwork;
    public int mDuration;   // song duration in ms

    public Song (String path, String id, String title, String artist, String album, Bitmap artwork, String duration) {
        super();
        mPath = path;
        mAudioID = id;
        mTitle = title;
        mArtist = artist;
        mAlbum = album;
        mArtwork = artwork;
        mDuration = Integer.parseInt(duration);
    }
}
