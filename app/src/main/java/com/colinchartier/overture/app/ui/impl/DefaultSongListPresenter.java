package com.colinchartier.overture.app.ui.impl;

import android.content.Context;
import android.view.View;
import com.colinchartier.overture.app.ContextType;
import com.colinchartier.overture.app.FromContext;
import com.colinchartier.overture.app.ui.fragments.presenters.SongListPresenter;
import com.colinchartier.overture.app.ui.fragments.views.SongListView;

import javax.inject.Inject;

public class DefaultSongListPresenter extends MusicBindingPresenter implements SongListPresenter {
    private final SongListView view;

    @Inject
    public DefaultSongListPresenter(SongListView view, @FromContext(ContextType.ACTIVITY) Context context) {
        super(context);
        this.view = view;
    }

    @Override
    public void onSongClicked(View view, int position) {

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
    protected void onMusicServiceConnected() {

    }
}
