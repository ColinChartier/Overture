package com.colinchartier.overture.app.fragments.presenters;

import android.view.View;

public interface NavigationDrawerPresenter {
    void onNewPlaylistButtonClicked(View v);

    void onPlaylistClicked(String playlistName);

    void onLicenseInfoButtonClicked(View button);
}
