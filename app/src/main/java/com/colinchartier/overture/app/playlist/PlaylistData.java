package com.colinchartier.overture.app.playlist;

import com.google.common.collect.ImmutableList;

public class PlaylistData {
    private final String name;
    private ImmutableList<Long> songIds;

    public PlaylistData(String name, ImmutableList<Long> songIds) {
        this.name = name;
        this.songIds = songIds;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<Long> getSongIds() {
        return songIds;
    }
}
