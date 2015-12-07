package com.colinchartier.overture.app.song;

import com.google.common.base.Preconditions;

import java.util.*;

public class SongList {
    private final List<Song> songs;
    private Comparator<Song> sortMethod;

    public SongList(Collection<Song> songs, Comparator<Song> sortMethod) {
        this.songs = new ArrayList<Song>(Preconditions.checkNotNull(songs));
        this.sortMethod = sortMethod;

        sortSongs();
    }

    private void sortSongs() {
        Collections.sort(songs, sortMethod);
    }

    public void setSortMethod(Comparator<Song> sortMethod) {
        this.sortMethod = sortMethod;
        sortSongs();
    }
}
