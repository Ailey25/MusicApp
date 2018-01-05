package com.aileyzhang.musicapp.data;

/**
 * Created by Ailey on 2017-12-23.
 */

public class Artist {
    public String mArtist;
    public int mNumOfAlbums;
    public String mArtistKey;

    Artist(String artist, int numOfAlbums, String artistKey) {
        mArtist = artist;
        mNumOfAlbums = numOfAlbums;
        mArtistKey = artistKey;
    }
}
