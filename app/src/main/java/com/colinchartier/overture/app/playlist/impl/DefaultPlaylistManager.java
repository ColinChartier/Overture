package com.colinchartier.overture.app.playlist.impl;

import android.support.annotation.Nullable;
import com.colinchartier.overture.app.playlist.Playlist;
import com.colinchartier.overture.app.playlist.PlaylistManager;
import com.colinchartier.overture.app.playlist.listeners.OnPlaylistListChangedListener;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import javax.inject.Inject;
import java.util.*;

public class DefaultPlaylistManager implements PlaylistManager {
    private String selectedPlaylistName;

    private final Map<String, Playlist> playlistMap = new HashMap<>(32);
    private final List<Playlist> playlists = new ArrayList<>();

    /*Listeners*/
    private final List<OnPlaylistListChangedListener> playlistListChangedListeners = new ArrayList<>();

    @Inject
    public DefaultPlaylistManager() {

    }

    @Override
    public void reloadPlaylists() {
        //TODO
        for (OnPlaylistListChangedListener l : playlistListChangedListeners) {
            l.onPlaylistListChanged();
        }
    }

    @Nullable
    @Override
    public Playlist getPlaylist(String playlistName) {
        return playlistMap.get(playlistName);
    }

    @Override
    public List<Playlist> getPlaylists() {
        return Collections.unmodifiableList(playlists);
    }

    @Override
    public List<String> getPlaylistNames() {
        return Lists.transform(getPlaylists(), new Function<Playlist, String>() {
            @Override
            public String apply(Playlist input) {
                return input.getName();
            }
        });
    }

    @Override
    public void createPlaylist(String name) {

    }

    @Override
    public boolean deletePlaylist(String name) {
        return false;
    }

    @Override
    public Playlist getDefaultPlaylist() {
        return null;
    }

    @Override
    public void selectPlaylist(String playlistName) {
        selectedPlaylistName = playlistName;
    }

    @Override
    public Playlist getSelectedPlaylist() {
        Playlist selectedPlaylist = playlistMap.get(selectedPlaylistName);
        return selectedPlaylist == null ? getDefaultPlaylist() : selectedPlaylist;
    }

    @Override
    public void addPlaylistListChangedListener(OnPlaylistListChangedListener listener) {
        playlistListChangedListeners.add(listener);
    }

    @Override
    public void removePlaylistListChangedListener(OnPlaylistListChangedListener listener) {
        playlistListChangedListeners.remove(listener);
    }
}
