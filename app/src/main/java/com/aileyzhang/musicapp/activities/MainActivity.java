package com.aileyzhang.musicapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aileyzhang.musicapp.AudioController;
import com.aileyzhang.musicapp.CustomSwipeViewPager;
import com.aileyzhang.musicapp.R;
import com.aileyzhang.musicapp.adapters.MainActivityContentAdapter;
import com.aileyzhang.musicapp.data.Playlist;
import com.aileyzhang.musicapp.data.Song;
import com.aileyzhang.musicapp.fragments.PlaylistsTabFragment;

import static com.aileyzhang.musicapp.adapters.MainActivityContentAdapter.ALBUMS_PAGE_POSITION;
import static com.aileyzhang.musicapp.adapters.MainActivityContentAdapter.ARTISTS_PAGE_POSITION;
import static com.aileyzhang.musicapp.adapters.MainActivityContentAdapter.PLAYLISTS_PAGE_POSITION;
import static com.aileyzhang.musicapp.adapters.MainActivityContentAdapter.SONGS_PAGE_POSITION;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int SDCARD_PERMISSION_REQUEST_CODE = 1;
    private MainActivityContentAdapter mMainActivityContentAdapter;
    private CustomSwipeViewPager mMainViewPager;
    public static ViewGroup currentSongLayout;
    public Button songOnBottomBarPlayPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_songs);

        // Initialize Current song layout and button on bottom bar
        currentSongLayout = findViewById(R.id.current_song_layout);
        songOnBottomBarPlayPauseButton = (MainActivity.currentSongLayout).findViewById(
                R.id.current_song_play_pause);

        // Initialize ViewPager
        mMainViewPager = findViewById(R.id.main_view_pager);
        mMainViewPager.setOffscreenPageLimit(3);
        mMainActivityContentAdapter = new MainActivityContentAdapter(getSupportFragmentManager());

        // Check for permission and request it if it's not there
        if (getStoragePermission()) {
            mMainViewPager.setAdapter(mMainActivityContentAdapter);
            mMainViewPager.setCurrentItem(SONGS_PAGE_POSITION);
        }
    }

    public Boolean getStoragePermission() {
        Boolean hasPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    SDCARD_PERMISSION_REQUEST_CODE);
        }
        return hasPermission;
    }

    @Override
    public void onRequestPermissionsResult(int reqestCode, String[] permissions, int[] grantResult) {
        switch (reqestCode) {
            case SDCARD_PERMISSION_REQUEST_CODE:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    mMainViewPager.setAdapter(mMainActivityContentAdapter);
                    mMainViewPager.setCurrentItem(SONGS_PAGE_POSITION);
                } else {
                    // Permission denied
                }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_songs) {
            mMainViewPager.setCurrentItem(SONGS_PAGE_POSITION);
        } else if (id == R.id.nav_artists) {
            mMainViewPager.setCurrentItem(ARTISTS_PAGE_POSITION);
        } else if (id == R.id.nav_albums) {
            mMainViewPager.setCurrentItem(ALBUMS_PAGE_POSITION);
        } else if (id == R.id.nav_playlists) {
            PlaylistsTabFragment playlistsTabFragment = mMainActivityContentAdapter.playlistsTabFragment;
            playlistsTabFragment.updatePlaylistList();
            if (playlistsTabFragment.playlistSongListAdapter != null) {
                Playlist playlist = playlistsTabFragment.playlistSongListAdapter.getPlaylist();
                playlistsTabFragment.updateSongsInPlaylist(playlist);
            }
            mMainViewPager.setCurrentItem(PLAYLISTS_PAGE_POSITION);
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_equalizer) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * On activity resume, update bottom song bar with the currently playing song info
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (AudioController.currentSong != null) {
            AudioController.setSongPlayPause(this,songOnBottomBarPlayPauseButton);
        }
    }

    public void updateSongListBottomBadView(final Song curSong) {
        AudioController.playOrPauseInBottomBar(curSong.mPath);

        // Show the currently playing song
        (MainActivity.currentSongLayout).setVisibility(View.VISIBLE);

        ImageView artwork = (MainActivity.currentSongLayout).findViewById(R.id.song_list_artwork);
        TextView title = (MainActivity.currentSongLayout).findViewById(R.id.song_list_title);
        TextView artist = (MainActivity.currentSongLayout).findViewById(R.id.song_list_artist);

        artwork.setImageBitmap(curSong.mArtwork);
        title.setText(curSong.mTitle);
        artist.setText(curSong.mArtist);

        AudioController.setSongPlayPause(this, songOnBottomBarPlayPauseButton);
        songOnBottomBarPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioController.playOrPauseInBottomBar(curSong.mPath);
                AudioController.setSongPlayPause(getApplicationContext(), songOnBottomBarPlayPauseButton);
            }
        });
    }
}
