package com.aileyzhang.musicapp;

import android.graphics.Bitmap;

/**
 * Created by Ailey on 2017-12-15.
 */

public class Song {
    public String mPath;
    public String mTitle;
    public String mArtist;
    public String mAlbum;
    public Bitmap mArtwork;

    public Song (String path, String title, String artist, String album, Bitmap artwork) {
        mPath = path;
        mTitle = title;
        mArtist = artist;
        mAlbum = album;
        mArtwork = artwork;
    }

}
