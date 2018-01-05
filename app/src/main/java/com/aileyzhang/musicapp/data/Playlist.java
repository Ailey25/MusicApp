package com.aileyzhang.musicapp.data;

import java.util.ArrayList;

/**
 * Created by Ailey on 2018-01-03.
 */

public class Playlist {
    public String mPath;
    public String mName;
    public String mID;
    public ArrayList<Song> mSongsInPlaylist;

    Playlist(String path, String name, String id, ArrayList<Song> songs) {
        mPath = path;
        mName = name;
        mID = id;
        mSongsInPlaylist = songs;
    }
}