package com.colinchartier.overture.app.playlist.impl;

import android.content.Context;
import com.colinchartier.overture.app.R;
import com.colinchartier.overture.app.model.SongListRetriever;
import com.colinchartier.overture.app.playlist.Playlist;
import com.colinchartier.overture.app.song.Song;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AllSongsPlaylist extends Playlist {
    private final List<Song> songs = new ArrayList<Song>();

    public AllSongsPlaylist(Context ctx) {
        super(ctx.getString(R.string.all_songs_playlist_name));
    }

    @Override
    public void addSongId(Long songId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSongIds(Collection<Long> songIds) {
        throw new UnsupportedOperationException();
    }

    private void retrieveSongs(SongListRetriever retriever) {
        final Object lock = new Object();
        retriever.retrieveAllSongs(new SongListRetriever.OnSongListRetrievedListener() {
            @Override
            public void onSongListRetrieved(List<Song> songList) {
                songs.clear();
                songs.addAll(songList);
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        });
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized List<Song> getSongs(SongListRetriever retriever) {
        if (songs.isEmpty()) {
            retrieveSongs(retriever);
        }
        return songs;
    }

    public boolean isMutable() {
        return false;
    }

    @Override
    public String toString() {
        return "All Songs Playlist [Default]";
    }
}
