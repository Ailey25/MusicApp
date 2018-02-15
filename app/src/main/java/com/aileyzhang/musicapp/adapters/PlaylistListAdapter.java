package com.aileyzhang.musicapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.aileyzhang.musicapp.AudioController;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.data.Playlist;
import com.aileyzhang.musicapp.data.PlaylistData;
import com.aileyzhang.musicapp.views.PlaylistListItemView;

import java.util.List;

/**
 * Created by Ailey on 2018-01-03.
 */

public class PlaylistListAdapter extends ArrayAdapter<Playlist> {
    private Context context;

    public interface PlaylistAdapterListener {
        void onPlaylistClick(Playlist playlist);
    }

    private PlaylistAdapterListener mPlaylistAdapterListener;

    public PlaylistListAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
        this.context = context;
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

        // Clicked on playlist more options menu of playlist
        final ImageView moreOption = playlistListView.findViewById(R.id.playlist_more_option);
        moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlaylistMoreOptionClick(getContext(), moreOption, curPlaylist);
            }
        });

        // Clicked on playlist in list of playlists
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

    private void onPlaylistMoreOptionClick(final Context context, View moreOption, final Playlist playlist) {
        PopupMenu popupMenu = new PopupMenu(context, moreOption);
        popupMenu.getMenuInflater().inflate(R.menu.playlist_item_more_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.delete_playlist:
                        Toast.makeText(context, playlist.mName + " deleted", Toast.LENGTH_SHORT).show();
                        deletePlaylist(playlist);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    private void deletePlaylist(Playlist playlist) {
        PlaylistData.deletePlaylist(context, playlist.mID);
        remove(playlist);
        notifyDataSetChanged();
        AudioController.updateCurrentSongQueue(context, playlist.mID);
    }
}