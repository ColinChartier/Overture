package com.colinchartier.overture.app.services.impl;

import com.colinchartier.overture.app.playlist.PlaylistManager;
import com.colinchartier.overture.app.services.presenters.MusicPresenter;

import javax.inject.Inject;

public class DefaultMusicPresenter implements MusicPresenter {
    @Inject
    public DefaultMusicPresenter() {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void setPlaying(boolean playing) {

    }

    @Override
    public boolean isLooping() {
        return false;
    }

    @Override
    public void setLooping(boolean looping) {

    }

    @Override
    public void skipNext() {

    }

    @Override
    public void skipPrevious() {

    }

    @Override
    public void seekToPercent(double percent) {

    }

    @Override
    public void skipBackward(long timeUs) {

    }

    @Override
    public void skipForward(long timeUs) {

    }

    @Override
    public PlaylistManager getPlaylistManager() {
        return null;
    }
}
