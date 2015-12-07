package com.colinchartier.overture.app.impl;

import android.content.Context;
import com.colinchartier.overture.app.ContextType;
import com.colinchartier.overture.app.FromContext;
import com.colinchartier.overture.app.fragments.presenters.MusicBarsPresenter;

import javax.inject.Inject;

public class DefaultMusicBarsPresenter extends MusicBindingPresenter implements MusicBarsPresenter {
    @Inject
    public DefaultMusicBarsPresenter(@FromContext(ContextType.ACTIVITY) Context context) {
        super(context);
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
    public void onMusicBarPress(double percent) {
        getMusicService().seekToPercent(percent);
    }

    @Override
    protected void onMusicServiceConnected() {

    }
}
