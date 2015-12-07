package com.colinchartier.overture.app.ui.impl;

import android.content.Context;
import android.view.View;
import com.colinchartier.overture.app.ContextType;
import com.colinchartier.overture.app.FromContext;
import com.colinchartier.overture.app.ui.fragments.presenters.SongControlsPresenter;
import com.colinchartier.overture.app.ui.fragments.views.SongControlsView;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

public class DefaultSongControlsPresenter extends MusicBindingPresenter implements SongControlsPresenter {
    private final SongControlsView view;

    private long lastPrevSongClickTime = 0L; //You can click once to go to start of song, or twice within x seconds to go the previous song.

    @Inject
    public DefaultSongControlsPresenter(SongControlsView view, @FromContext(ContextType.ACTIVITY) Context context) {
        super(context);
        this.view = view;
    }

    @Override
    public void init() {
        bindMusicService();
    }

    @Override
    public void stop() {
        unbindMusicService();
    }

    @Override
    public void onPlayPauseButtonClicked(View button) {
        getMusicService().setPlaying(!getMusicService().isPlaying());
    }

    @Override
    public void onSkipPreviousButtonClicked(View button) {
        if (System.currentTimeMillis() - lastPrevSongClickTime < 1500L) { //double click within 1.5 seconds
            getMusicService().skipPrevious();
            lastPrevSongClickTime = 0L;
        } else { //Otherwise skip to start of song
            getMusicService().seekToPercent(0);
            lastPrevSongClickTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onSkipNextButtonClicked(View button) {
        getMusicService().skipNext();
    }

    @Override
    public void onRewindButtonClicked(View button) {
        getMusicService().skipBackward((TimeUnit.SECONDS.toMicros(5)));
    }

    @Override
    public void onFastForwardButtonClicked(View button) {
        getMusicService().skipForward((TimeUnit.SECONDS.toMicros(5)));
    }

    @Override
    public void onRepeatButtonClicked(View button) {
        getMusicService().setLooping(!getMusicService().isLooping());
        view.setLoopButtonToggled(getMusicService().isLooping());
    }

    @Override
    public void onPlaybackSpeedButtonClicked(View button) {
        //TODO show playback speed dialog
    }

    @Override
    protected void onMusicServiceConnected() {

    }
}
