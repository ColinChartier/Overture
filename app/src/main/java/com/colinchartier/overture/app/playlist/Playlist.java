package com.colinchartier.overture.app.playlist;

import com.colinchartier.overture.app.model.SongListRetriever;
import com.colinchartier.overture.app.song.Song;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Playlist {
    private final String name;
    private final List<Long> songIds;
    private final List<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songIds = new ArrayList<Long>();
        this.songs = new ArrayList<Song>();
    }

    public void addSongId(Long songId) {
        this.songIds.add(songId);
    }

    public void setSongIds(Collection<Long> songIds) {
        this.songIds.clear();
        this.songIds.addAll(songIds);
    }

    private void retrieveSongs(SongListRetriever retriever) {
        final Object lock = new Object();
        retriever.retrieveSongs(new SongListRetriever.OnSongListRetrievedListener() {
            @Override
            public void onSongListRetrieved(List<Song> songList) {
                songs.clear();
                songs.addAll(songList);
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        }, songIds);
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized List<Song> getSongs(SongListRetriever retriever) {
        if (songs.isEmpty()) {
            retrieveSongs(retriever);
        }
        return songs;
    }

    /**
     * Whether or not this playlist is mutable.  If it isn't, calls to setSongIds and other functions will throw an {@link java.lang.UnsupportedOperationException}
     */
    public boolean isMutable() {
        return true;
    }

    public final String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Playlist)) {
            return false;
        }
        return ((Playlist) other).getName().equals(name);
    }

    @Override
    public String toString() {
        return "Playlist " + name + "(" + songs.toString() + ")";
    }
}
