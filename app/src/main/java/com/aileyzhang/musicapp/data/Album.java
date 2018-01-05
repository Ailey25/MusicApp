package com.aileyzhang.musicapp.data;

import android.graphics.Bitmap;

/**
 * Created by Ailey on 2017-12-22.
 */

public class Album {
    public String mAlbumID;
    public String mAlbumTitle;
    public String mArtist;
    public int mNumOfSongs;
    public Bitmap mArtwork;

    public Album (String id, String title, String artist, int numOfSongs, Bitmap artwork) {
        mAlbumID = id;
        mAlbumTitle = title;
        mArtist = artist;
        mNumOfSongs = numOfSongs;
        mArtwork = artwork;
    }
}
