package com.aileyzhang.musicapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.aileyzhang.musicapp.AudioController;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.activities.MainActivity;
import com.aileyzhang.musicapp.activities.SongItemActivity;
import com.aileyzhang.musicapp.data.Album;
import com.aileyzhang.musicapp.data.Artist;
import com.aileyzhang.musicapp.data.Playlist;
import com.aileyzhang.musicapp.data.PlaylistData;
import com.aileyzhang.musicapp.data.Song;
import com.aileyzhang.musicapp.data.SongData;
import com.aileyzhang.musicapp.views.SongListItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ailey on 2017-12-16.
 */

public class SongListAdapter extends ArrayAdapter<Song> {
    private Context context;
    private String mParentTabFragment;
    private Playlist mPlaylist;


    public SongListAdapter(@NonNull Context context, int resource, @NonNull List<Song> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    public Playlist getPlaylist() {
        return mPlaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AudioController.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        View songListItemView = convertView;
        if (songListItemView == null) {
            songListItemView = new SongListItemView(getContext());
        }
        final Song curSong = getItem(position);

        ImageView artwork = songListItemView.findViewById(R.id.song_list_artwork);
        if (curSong.mArtwork != null) {
            artwork.setImageBitmap(curSong.mArtwork);
        }
        TextView title = songListItemView.findViewById(R.id.song_list_title);
        if (curSong.mTitle != null && !curSong.mTitle.isEmpty()) {
            title.setText(curSong.mTitle);
        }
        TextView artist = songListItemView.findViewById(R.id.song_list_artist);
        if (curSong.mArtist != null && !curSong.mArtist.isEmpty()) {
            artist.setText(curSong.mArtist);
        }

        // Clicked on more option menu of a song
        final ImageView moreOption = songListItemView.findViewById(R.id.song_list_more_option);
        moreOption.setVisibility(View.VISIBLE);
        moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSongMoreOptionClick(getContext(), moreOption, curSong);
            }
        });

        // Clicked on a song in a list of songs
        songListItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).updateSongListBottomBadView(curSong);
                AudioController.currentSong = curSong;
                if (mPlaylist == null) {
                    AudioController.setCurrentSongQueue(getContext());
                } else {
                    AudioController.setCurrentSongQueue(getContext(), mPlaylist.mID);
                }
            }
        });

        // Clicked on the song on the bottom of the screen
        (MainActivity.currentSongLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPersistentCurrentSongClick(view, AudioController.currentSong);
            }
        });

        return songListItemView;
    }

    public void setParentTabFragment(String tabFragmentName) {
        mParentTabFragment = tabFragmentName;
    }

    public void setParentTabFragment(String tabFragmentName, Artist artist) {
        mParentTabFragment = tabFragmentName;
    }

    public void setParentTabFragment(String tabFragmentName, Album album) {
        mParentTabFragment = tabFragmentName;
    }

    public void setParentTabFragment(String tabFragmentName, Playlist playlist) {
        mParentTabFragment = tabFragmentName;
        mPlaylist = playlist;
    }

    private void onSongMoreOptionClick(final Context context, View moreOption, final Song song) {
        PopupMenu popupMenu = new PopupMenu(context, moreOption);
        popupMenu.getMenuInflater().inflate(R.menu.song_item_more_option, popupMenu.getMenu());
        if (mParentTabFragment.equals("Songs")) {
            popupMenu.getMenu().findItem(R.id.add_to_playlist).setVisible(true);
            popupMenu.getMenu().findItem(R.id.delete_song).setVisible(true);
        } else if (mParentTabFragment.equals("Artists")) {
            popupMenu.getMenu().findItem(R.id.add_to_playlist).setVisible(true);
            popupMenu.getMenu().findItem(R.id.delete_song).setVisible(true);
        } else if (mParentTabFragment.equals("Albums")) {
            popupMenu.getMenu().findItem(R.id.add_to_playlist).setVisible(true);
            popupMenu.getMenu().findItem(R.id.delete_song).setVisible(true);
        } else if (mParentTabFragment.equals("Playlists")) {
            popupMenu.getMenu().findItem(R.id.delete_from_playlist).setVisible(true);
        }
        //TODO: remove checks
        for (Song s: SongData.getAllSongs(context)) {
            Log.e("DEBUG", "db before: " + s.mTitle);
        }
        for (Song s: AudioController.currentSongQueue) {
            Log.e("DEBUG","song queue before: " + s.mTitle);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add_to_playlist:
                        addToPlaylist(song);
//                        for (Song s: SongData.getAllSongs(context)) {
//                            Log.e("DEBUG","db add to playlist: " + s.mTitle);
//                        }
//                        for (Song s: AudioController.currentSongQueue) {
//                            Log.e("DEBUG","song queue add to playlist: " + s.mTitle);
//                        }
                        return true;
                    case R.id.delete_from_playlist:
//                        for (Song s: SongData.getSongsInPlaylist(context, mPlaylist.mID)) {
//                            Log.e("DEBUG","playlist before" + s.mTitle);
//                        }
                        deleteSongFromPlaylist(song, mPlaylist);
//                        for (Song s: SongData.getSongsInPlaylist(context, mPlaylist.mID)) {
//                            Log.e("DEBUG","playlist after" + s.mTitle);
//                        }
//                        for (Song s: SongData.getAllSongs(context)) {
//                            Log.e("DEBUG","db deleted from playlist " + s.mTitle);
//                        }
                        return true;
                    case R.id.delete_song:
                        deleteSongFromDatabase(song);
                        for (Song s: SongData.getAllSongs(context)) {
                            Log.e("DEBUG","db deleted song: " + s.mTitle);
                        }
                        for (Song s: AudioController.currentSongQueue) {
                            Log.e("DEBUG","song queue delete song: " + s.mTitle);
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    private void addToPlaylist(final Song song) {
        AlertDialog.Builder pickPlaylistBuilder = new AlertDialog.Builder(getContext());
        final ArrayList<Playlist> playlists = PlaylistData.getAllPlaylist(context);
        ArrayList<String> existentPlaylistNames = new ArrayList<>();
        existentPlaylistNames.add("Create new playlist");
        for (Playlist playlist: playlists) {
            existentPlaylistNames.add(playlist.mName);
        }
        CharSequence[] playlistNames = existentPlaylistNames.toArray(new CharSequence[existentPlaylistNames.size()]);
        pickPlaylistBuilder.setTitle(R.string.choose_a_playlist)
                .setItems(playlistNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which == 0) { // Create new playlist
                            newPlaylist(context, song);
                        } else {
                            // Add song to playlist
                            Long playlistID = Long.parseLong(playlists.get(which - 1).mID);
                            long audioID = Long.parseLong(song.mAudioID);
                            PlaylistData.addSongToPlaylist(context, playlistID, audioID);
                            notifyDataSetChanged();
                            // TODO: add song to currently playing song queue if queue is being played
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Pressed cancel
                    }
                });
        pickPlaylistBuilder.create().show();
    }

    private void deleteSongFromDatabase(Song song) {
        Toast.makeText(context, song.mTitle + " deleted", Toast.LENGTH_SHORT).show();
        SongData.deleteSongFromDatabase(context, song);
        remove(song);
        notifyDataSetChanged();
        AudioController.updateCurrentSongQueue(context);
    }

    private void deleteSongFromPlaylist(Song song, Playlist playlist) {
        Toast.makeText(context, song.mTitle + " deleted from " + playlist.mName, Toast.LENGTH_SHORT).show();
        SongData.deleteSongFromPlaylist(context, song, playlist);
        remove(song);
        notifyDataSetChanged();
        AudioController.updateCurrentSongQueue(context, mPlaylist.mID);
    }

    /**
     * AlertDialog for user to input new playlist name.
     * Checks whether playlist already exists. Creates new playlist if name is not taken.
     * @param context
     */
    private void newPlaylist(final Context context, final Song song) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter new playlist name");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String userPickedPlaylistName = input.getText().toString();
                        long newPlaylistID = PlaylistData.getPlaylistID(getContext(), userPickedPlaylistName);
                        if (newPlaylistID != -1) {
                            // Playlist already exists
                            Toast.makeText(context, "Playlist already exists. Try a new name", Toast.LENGTH_SHORT).show();
                        } else {
                            // Creates new playlist with playlist name and add the song to it
                            newPlaylistID = PlaylistData.createNewPlaylist(getContext(), userPickedPlaylistName);
                            long audioID = Long.parseLong(song.mAudioID);
                            PlaylistData.addSongToPlaylist(context, newPlaylistID, audioID);
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.create().show();
    }

    private void onPersistentCurrentSongClick(View view, Song curSong) {
        Intent songItemIntent = new Intent(view.getContext(), SongItemActivity.class);
        songItemIntent.putExtra("SONG_PATH", curSong.mPath);
        songItemIntent.putExtra("SONG_TITLE", curSong.mTitle);
        songItemIntent.putExtra("SONG_ARTIST", curSong.mArtist);
        songItemIntent.putExtra("SONG_DURATION", curSong.mDuration);
        view.getContext().startActivity(songItemIntent);
    }
}
