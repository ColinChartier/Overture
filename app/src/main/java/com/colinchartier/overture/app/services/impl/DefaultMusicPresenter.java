package com.colinchartier.overture.app.services.impl;

import android.content.ContentUris;
import com.colinchartier.overture.app.musicplayer.MusicPlayer;
import com.colinchartier.overture.app.musicplayer.listeners.OnPlaybackCompletionListener;
import com.colinchartier.overture.app.playlist.PlaylistManager;
import com.colinchartier.overture.app.services.presenters.MusicPresenter;
import com.colinchartier.overture.app.song.Song;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class DefaultMusicPresenter implements MusicPresenter, OnPlaybackCompletionListener {
    private final PlaylistManager playlistManager;
    private final MusicPlayer player;

    private int currentSongIndex = 0;
    private long currentPlayingId = -1;

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
            long currentSongId = getSongs().get(currentSongIndex).getId();
            if (currentSongId == currentPlayingId) {
                if (player.isPlaying()) { //we are currently playing the song that we want to
                    return;
                }
                if (player.isInitialized()) { //the song is selected, but paused/not started yet
                    player.start();
                    return;
                }
            }
            if (player.isPlaying()) { //Song is playing, but not the song we want. Stop it so we can change the data source.
                player.stop();
            }
            try {
                currentPlayingId = currentSongId;
                player.setDataSource(ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currentSongId));
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
        currentSongIndex = (currentSongIndex + 1) % getSongs().size();
    }

    @Override
    public void skipPrevious() {
        if (currentSongIndex == 0) { //java modulo sux
            currentSongIndex = getSongs().size() - 1;
        } else {
            currentSongIndex--;
        }
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

    private List<Song> getSongs() {
        return getPlaylistManager().getSelectedPlaylist().getSongs();
    }
}
