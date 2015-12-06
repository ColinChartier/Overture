package com.colinchartier.overture.app.playlist.impl;

import android.content.Context;
import com.colinchartier.overture.app.ContextType;
import com.colinchartier.overture.app.FromContext;
import com.colinchartier.overture.app.playlist.Playlist;
import com.colinchartier.overture.app.playlist.PlaylistDatabaseHelper;
import com.colinchartier.overture.app.playlist.PlaylistManager;
import com.colinchartier.overture.app.playlist.listeners.OnPlaylistListChangedListener;

import javax.inject.Inject;
import java.util.*;


public class DefaultPlaylistManager implements PlaylistManager {
    public static final String LOG_TAG = "DefaultPlaylistManager";

    private final List<Playlist> playlists = new ArrayList<Playlist>();
    private final Map<String, Playlist> playlistNameMap = new HashMap<String, Playlist>();

    private final PlaylistDatabaseHelper databaseHelper;
    private final List<OnPlaylistListChangedListener> playlistListRetrievedListeners = new ArrayList<OnPlaylistListChangedListener>();

    /*Default playlists*/
    final Playlist allSongsPlaylist;


    @Inject
    public DefaultPlaylistManager(@FromContext(ContextType.ACTIVITY) Context ctx, PlaylistDatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;

        this.allSongsPlaylist = new AllSongsPlaylist(ctx);
    }

    @Override
    public synchronized void reloadPlaylists() {
        playlists.clear();
        playlists.add(allSongsPlaylist);
        playlists.addAll(databaseHelper.getAllPlaylists());

        playlistNameMap.clear();
        for (Playlist p : playlists) {
            playlistNameMap.put(p.getName(), p);
        }

        for (OnPlaylistListChangedListener l : playlistListRetrievedListeners) {
            l.onPlaylistListChanged();
        }
    }

    @Override
    public Playlist getPlaylist(String playlistName) {
        return playlistNameMap.get(playlistName);
    }

    @Override
    public List<Playlist> getPlaylists() {
        return Collections.unmodifiableList(playlists);
    }

    @Override
    public void createPlaylist(String name) { //TODO ensure threadsafe
        name = name.trim();
        if (playlistNameMap.containsKey(name)) {
            throw new IllegalArgumentException("Unable to presenter a playlist with the name \"" + name + ".\" That name is already used!");
        }
        databaseHelper.createPlaylist(name);
        reloadPlaylists();
    }

    @Override
    public boolean deletePlaylist(String name) {
        name = name.trim();
        Playlist p = playlistNameMap.remove(name);
        if (p == null) {
            return false;
        }
        databaseHelper.deletePlaylist(name);
        reloadPlaylists();
        return true;
    }

    @Override
    public void addPlaylistListRetrievalListener(OnPlaylistListChangedListener retrievalListener) {
        playlistListRetrievedListeners.add(retrievalListener);
    }

    @Override
    public void removePlaylistListRetrievalListener(OnPlaylistListChangedListener retrievalListener) {
        playlistListRetrievedListeners.remove(retrievalListener);
    }

    @Override
    public Playlist getDefaultPlaylist() {
        return allSongsPlaylist;
    }

    @Override
    public boolean isPlaylistDefault(String name) {
        return name != null && (name.equals(allSongsPlaylist.getName()));
    }

    @Override
    public void selectPlaylist(String playlistName) {
        //TODO
    }
}
