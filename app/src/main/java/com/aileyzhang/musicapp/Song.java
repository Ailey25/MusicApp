package com.aileyzhang.musicapp;

import android.graphics.Bitmap;

/**
 * Created by Ailey on 2017-12-15.
 */

public class Song {
    public String mTitle;
    public String mArtist;
    public String mGenre;
    public Bitmap mArtwork;

    public Song (String title, String artist, String genre, Bitmap artwork) {
        mTitle = title;
        mArtist = artist;
        mGenre = genre;
        mArtwork = artwork;
    }

}
