package com.colinchartier.overture.app.services.presenters;

import com.colinchartier.overture.app.playlist.PlaylistManager;

public interface MusicPresenter {
    boolean isPlaying();

    void setPlaying(boolean playing);

    boolean isLooping();

    void setLooping(boolean looping);

    void skipNext();

    void skipPrevious();

    void seekToPercent(int percent);

    void skipBackward(long timeUs);

    void skipForward(long timeUs);

    PlaylistManager getPlaylistManager();
}
