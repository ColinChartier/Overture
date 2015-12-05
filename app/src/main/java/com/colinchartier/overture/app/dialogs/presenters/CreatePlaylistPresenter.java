package com.colinchartier.overture.app.dialogs.presenters;

import com.colinchartier.overture.app.playlist.Playlist;

import java.util.List;

public interface CreatePlaylistPresenter {
    void onComplete();

    void onCancel();

    void init();

    /**
     * Called every time the user changes the value in the presenter playlist dialog
     */
    void onPlaylistNameChanged(String newName);

    void setPlaylistList(List<Playlist> playlistList);

}
