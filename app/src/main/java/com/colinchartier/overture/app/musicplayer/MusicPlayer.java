package com.colinchartier.overture.app.musicplayer;

import android.net.Uri;
import com.colinchartier.overture.app.musicplayer.listeners.OnPlaybackCompletionListener;

import java.io.IOException;

/**
 * A music player is a simple state machine for the playback of music.<br>
 * It starts out in {@link State#UNINITIALIZED}<br>
 * After {@link #setDataSource(Uri)}, the state is set to {@link State#STOPPED}<br>
 * After {@link #start()}, the state is set to {@link State#PLAYING}<br>
 * After {@link #stop()}, the state is set to {@link State#STOPPED}<br>
 * If {@link #start()} is called after the player has been stopped, the player will resume playback from where it was when it was stopped.<br>
 * To restart the player completely, call {@link #seek(long)} with 0 and then call {@link #start()}.
 */
public interface MusicPlayer {
    void setDataSource(Uri dataSource) throws IOException;

    void start();

    void stop();

    void seek(long timeUs);

    /**
     * @return the current playback time in us (microseconds.)
     */
    long getPlaybackTime();

    //Listeners
    void setPlaybackCompletionListener(OnPlaybackCompletionListener listener);

    //State
    boolean isPlaying();

    boolean isInitialized();

    State getState();

    enum State {
        PLAYING,
        STOPPED,
        UNINITIALIZED
    }
}
