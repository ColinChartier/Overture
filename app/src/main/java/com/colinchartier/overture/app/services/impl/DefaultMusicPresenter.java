package com.colinchartier.overture.app.services.impl;

import android.content.ContentUris;
import com.colinchartier.overture.app.musicplayer.MusicPlayer;
import com.colinchartier.overture.app.musicplayer.listeners.OnPlaybackCompletionListener;
import com.colinchartier.overture.app.playlist.PlaylistManager;
import com.colinchartier.overture.app.services.presenters.MusicPresenter;

import javax.inject.Inject;
import java.io.IOException;

public class DefaultMusicPresenter implements MusicPresenter, OnPlaybackCompletionListener {
    private final PlaylistManager playlistManager;
    private final MusicPlayer player;

    private int currentSongIndex = -1;
    private long selectedSongId = -2;

    private boolean looping = false;

    @Inject
    public DefaultMusicPresenter(PlaylistManager playlistManager, MusicPlayer player) {
        this.playlistManager = playlistManager;
        this.player = player;
        player.setPlaybackCompletionListener(this);
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void setPlaying(boolean toPlaying) {
        if (toPlaying) {
            if (player.isPlaying()) {
                return;
            }
            if (player.isInitialized()) {
                player.start();
                return;
            }
            try {
                player.setDataSource(ContentUris.withAppendedId(
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        getPlaylistManager().getSelectedPlaylist().getSongs().get(currentSongIndex).getId()));
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            player.stop();
        }
    }

    @Override
    public boolean isLooping() {
        return looping;
    }

    @Override
    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    @Override
    public void skipNext() {

    }

    @Override
    public void skipPrevious() {

    }

    @Override
    public void seekToPercent(double percent) {
        //TODO
    }

    @Override
    public void skipBackward(long timeUs) {
        player.seek(Math.max(0, player.getPlaybackTime() - timeUs));
    }

    @Override
    public void skipForward(long timeUs) {
        player.seek(player.getPlaybackTime() + timeUs);
    }

    @Override
    public PlaylistManager getPlaylistManager() {
        return playlistManager;
    }

    @Override
    public void onPlaybackComplete() {
        if (looping) {
            player.start();
        } else {
            try {
                player.setDataSource(null); //TODO start playing next song
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
