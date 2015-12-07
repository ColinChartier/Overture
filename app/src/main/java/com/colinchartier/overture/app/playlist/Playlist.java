package com.colinchartier.overture.app.playlist;

import com.colinchartier.overture.app.song.Song;

import java.util.List;

public interface Playlist {
    String getName();

    List<Song> getSongs();
}
