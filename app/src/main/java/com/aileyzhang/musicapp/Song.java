package com.aileyzhang.musicapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ailey on 2017-12-15.
 */

public class Song implements Parcelable {
    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel parcel) {
            return new Song(parcel);
        }

        @Override
        public Song[] newArray(int i) {
            return new Song[i];
        }
    };

    public String mPath;
    public String mTitle;
    public String mArtist;
    public String mAlbum;
    public Bitmap mArtwork;

    public Song (String path, String title, String artist, String album, Bitmap artwork) {
        super();
        mPath = path;
        mTitle = title;
        mArtist = artist;
        mAlbum = album;
        mArtwork = artwork;
    }

    public Song(Parcel in) {
        mPath = in.readString();
        mTitle = in.readString();
        mArtist = in.readString();
        mAlbum = in.readString();
        mArtwork = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mPath);
        parcel.writeString(mTitle);
        parcel.writeString(mArtist);
        parcel.writeString(mAlbum);
        parcel.writeParcelable(mArtwork, i);
    }
}
