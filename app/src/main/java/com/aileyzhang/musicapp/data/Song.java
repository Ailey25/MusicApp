package com.aileyzhang.musicapp.data;

import android.graphics.Bitmap;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Song)) {
            return false;
        }
        Song song = (Song) obj;
        if (mArtwork == null && !(song.mArtwork == null)) return false;
        if (!(mArtwork == null) && song.mArtwork == null) return false;
        return Objects.equals(mPath, song.mPath) &&
                Objects.equals(mAudioID, song.mAudioID) &&
                Objects.equals(mTitle, song.mTitle) &&
                Objects.equals(mArtist, song.mArtist) &&
                Objects.equals(mAlbum, song.mAlbum) &&
                ((mArtwork == null && song.mArtwork == null) || mArtwork.sameAs(song.mArtwork)) &&
                mDuration == song.mDuration;
    }
}
