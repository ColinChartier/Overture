package com.colinchartier.overture.app.ui.fragments.presenters;

public interface MusicBarsPresenter {
    void init();

    void stop();

    void onMusicBarPress(double percent);
}
