package com.colinchartier.overture.app.fragments.presenters;

import android.view.View;

public interface SongControlsPresenter {
    void init();

    void stop();

    void onPlayPauseButtonClicked(View button);

    void onSkipPreviousButtonClicked(View button);

    void onSkipNextButtonClicked(View button);

    void onFastForwardButtonClicked(View button);

    void onRewindButtonClicked(View button);

    void onRepeatButtonClicked(View button);

    void onPlaybackSpeedButtonClicked(View button);
}
