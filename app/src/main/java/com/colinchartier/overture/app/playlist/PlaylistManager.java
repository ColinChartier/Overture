package com.colinchartier.overture.app.playlist;

import android.support.annotation.Nullable;
import com.colinchartier.overture.app.playlist.listeners.OnPlaylistListChangedListener;
import com.colinchartier.overture.app.playlist.listeners.OnPlaylistSelectedListener;

import java.util.List;

public interface PlaylistManager {
    /**
     * Reloads all playlists from the disk.
     */
    void reloadPlaylists();

    @Nullable
    Playlist getPlaylist(String playlistName);

    /**
     * @return an unmodifiable view of all playlists backed by the internal list.
     */
    List<Playlist> getPlaylists();

    /**
     * @return an unmodifiable view of the names of the playlists, backed by the internal list.
     */
    List<String> getPlaylistNames();

    void createPlaylist(String name);

    boolean deletePlaylist(String name);

    void addPlaylistListRetrievalListener(OnPlaylistListChangedListener retrievalListener);

    void removePlaylistListRetrievalListener(OnPlaylistListChangedListener retrievalListener);

    void addPlaylistSelectedListener(OnPlaylistSelectedListener listener);

    void removePlaylistSelectedLsitener(OnPlaylistSelectedListener listener);

    Playlist getDefaultPlaylist();

    boolean isPlaylistDefault(String name);

    void selectPlaylist(String playlistName);

    /**
     * @return the playlist last selected by {@link #selectPlaylist(String)}, or {@link #getDefaultPlaylist()} if none has been set.
     */
    Playlist getSelectedPlaylist();
}
