package com.colinchartier.overture.app.playlist.listeners;

/**
 * This listener is called when a playlist is added or removed to the global list.<br>
 * Currently this is only called through creation or deletion.
 */
public interface OnPlaylistListChangedListener {
    void onPlaylistListChanged();
}
