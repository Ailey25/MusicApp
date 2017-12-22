package com.aileyzhang.musicapp;

import android.graphics.Bitmap;

/**
 * Created by Ailey on 2017-12-22.
 */

public class Album {
    public String mAlbumID;
    public String mTitle;
    public String mArtist;
    public int mNumOfSongs;
    public Bitmap mArtwork;

    public Album (String id, String title, String artist, int numOfSongs, Bitmap artwork) {
        mAlbumID = id;
        mTitle = title;
        mArtist = artist;
        mNumOfSongs = numOfSongs;
        mArtwork = artwork;
    }
}
