package com.colinchartier.overture.app.ui.fragments.presenters;

import android.view.View;

public interface NavigationDrawerPresenter {
    void init();

    void stop();

    void onNewPlaylistButtonClicked(View v);

    void onPlaylistClicked(String playlistName);

    void onLicenseInfoButtonClicked(View button);
}
