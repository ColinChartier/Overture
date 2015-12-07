package com.colinchartier.overture.app.fragments.presenters;

public interface MusicBarsPresenter {
    void init();

    void stop();

    void onMusicBarPress(double percent);
}
