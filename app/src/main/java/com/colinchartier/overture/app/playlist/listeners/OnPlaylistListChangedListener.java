package com.colinchartier.overture.app.playlist.listeners;

/**
 * This listener is called when a playlist is added or removed to the global list.<br>
 * Currently this is only called through creation or deletion.<br>
 * <br>
 * Created by overcaste on 16/6/2015.
 */
public interface OnPlaylistListChangedListener {
    void onPlaylistListChanged();
}
