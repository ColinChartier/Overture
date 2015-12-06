package com.colinchartier.overture.app.impl;

import android.view.View;
import com.colinchartier.overture.app.fragments.presenters.SongControlsPresenter;

import javax.inject.Inject;

public class DefaultSongControlsPresenter implements SongControlsPresenter {
    @Inject
    public DefaultSongControlsPresenter() {
    }

    @Override
    public void onPlayPauseButtonClicked(View button) {
        //TODO stubbed
    }

    @Override
    public void onSkipPreviousButtonClicked(View button) {

    }

    @Override
    public void onSkipNextButtonClicked(View button) {

    }

    @Override
    public void onFastForwardButtonClicked(View button) {

    }

    @Override
    public void onRewindButtonClicked(View button) {

    }

    @Override
    public void onRepeatButtonClicked(View button) {

    }

    @Override
    public void onPlaybackSpeedButtonClicked(View button) {

    }
}
