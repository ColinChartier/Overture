package com.colinchartier.overture.app.playlist;

import com.colinchartier.overture.app.song.Song;
import com.colinchartier.overture.app.song.SongSortMethod;

import java.util.List;

public interface Playlist {
    String getName();

    List<Song> getSongs();

    void setSortMethod(SongSortMethod sortMethod);
}
