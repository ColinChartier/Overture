package com.colinchartier.overture.app.ui.impl;

import android.content.Context;
import com.colinchartier.overture.app.ContextType;
import com.colinchartier.overture.app.FromContext;
import com.colinchartier.overture.app.ui.fragments.presenters.MusicBarsPresenter;

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
        //TODO listen in on music playing state to update seek bars
    }
}
