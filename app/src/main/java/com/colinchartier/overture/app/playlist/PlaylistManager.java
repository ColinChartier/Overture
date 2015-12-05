package com.colinchartier.overture.app.playlist;

import android.support.annotation.Nullable;
import com.colinchartier.overture.app.playlist.listeners.OnPlaylistListChangedListener;

import java.util.List;

public interface PlaylistManager {
    /**
     * Reloads all playlists from the disk.
     */
    void reloadPlaylists();

    @Nullable
    Playlist getPlaylist(String playlistName);

    /**
     * @return an unmodifiable view of all playlists.
     */
    List<Playlist> getPlaylists();

    void createPlaylist(String name);

    boolean deletePlaylist(String name);

    void addPlaylistListRetrievalListener(OnPlaylistListChangedListener retrievalListener);

    void removePlaylistListRetrievalListener(OnPlaylistListChangedListener retrievalListener);

    Playlist getDefaultPlaylist();

    boolean isPlaylistDefault(String name);
}
