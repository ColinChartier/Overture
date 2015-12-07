package com.colinchartier.overture.app.playlist;

import java.util.Collection;
import java.util.List;

public interface PlaylistDatabaseHelper {
    void setPlaylistSongs(String name, Collection<Long> songs);

    List<Long> getPlaylistSongs(String playlistName);

    List<PlaylistData> getAllPlaylists();

    void createPlaylist(String name);

    void deletePlaylist(String name);
}
