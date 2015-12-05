package com.colinchartier.overture.app.model;

import com.colinchartier.overture.app.song.Song;

import java.util.List;

public interface SongListRetriever {
    /**
     * Asynchronously reads the song list, and then calls {@link OnSongListRetrievedListener#onSongListRetrieved(List)} on the data.
     */
    void retrieveAllSongs(OnSongListRetrievedListener listener);

    /**
     * Asynchronously retrieves a list of Songs from the specified list of song ids, and then calls {@link OnSongListRetrievedListener#onSongListRetrieved(List)} on the data.
     *
     * @param ids the list of song ids to retrieve
     */
    void retrieveSongs(OnSongListRetrievedListener listener, List<Long> ids);

    interface OnSongListRetrievedListener {
        void onSongListRetrieved(List<Song> songList);
    }
}
