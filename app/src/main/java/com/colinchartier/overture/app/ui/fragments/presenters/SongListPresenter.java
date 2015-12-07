package com.colinchartier.overture.app.ui.fragments.presenters;

import android.view.View;

public interface SongListPresenter {
    void onSongClicked(View view, int position);

    void init();

    void stop();
}
