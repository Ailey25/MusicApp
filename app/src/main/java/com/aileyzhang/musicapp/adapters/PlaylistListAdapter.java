package com.aileyzhang.musicapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.data.Playlist;
import com.aileyzhang.musicapp.views.PlaylistListItemView;

import java.util.List;

/**
 * Created by Ailey on 2018-01-03.
 */

public class PlaylistListAdapter extends ArrayAdapter<Playlist> {
    public interface PlaylistAdapterListener {
        void onPlaylistClick(Playlist playlist);
    }

    private PlaylistAdapterListener mPlaylistAdapterListener;

    public PlaylistListAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }

    public void setListener(PlaylistAdapterListener playlistListAdapter) {
        mPlaylistAdapterListener = playlistListAdapter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        notifyDataSetChanged();
        View playlistListView = convertView;
        if (playlistListView == null) {
            playlistListView = new PlaylistListItemView(getContext());
        }
        final Playlist curPlaylist = getItem(position);

        TextView playlistname = playlistListView.findViewById(R.id.playlist_list_name);
        if (curPlaylist.mName != null && !curPlaylist.mName.isEmpty()) {
            playlistname.setText(curPlaylist.mName);
        }

        playlistListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPlaylistAdapterListener != null) {
                    mPlaylistAdapterListener.onPlaylistClick(curPlaylist);
                }
            }
        });

        return playlistListView;
    }
}