package com.colinchartier.overture.app.impl;

import com.colinchartier.overture.app.fragments.presenters.MusicBarsPresenter;

import javax.inject.Inject;

public class DefaultMusicBarsPresenter implements MusicBarsPresenter {
    @Inject
    public DefaultMusicBarsPresenter() {

    }

    @Override
    public void onMusicBarPress(double percent) {
        //TODO stubbed
    }
}
