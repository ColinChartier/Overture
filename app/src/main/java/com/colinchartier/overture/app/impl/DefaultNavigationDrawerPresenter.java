package com.colinchartier.overture.app.impl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import com.colinchartier.overture.app.ContextType;
import com.colinchartier.overture.app.FromContext;
import com.colinchartier.overture.app.activities.LicenseActivity;
import com.colinchartier.overture.app.dialogs.CreatePlaylistDialog;
import com.colinchartier.overture.app.fragments.presenters.NavigationDrawerPresenter;
import com.colinchartier.overture.app.fragments.views.NavigationDrawerView;
import com.colinchartier.overture.app.playlist.Playlist;
import com.colinchartier.overture.app.playlist.PlaylistManager;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class DefaultNavigationDrawerPresenter implements NavigationDrawerPresenter, CreatePlaylistDialog.Listener {
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String LOG_TAG = "navdrawerpres";

    private final NavigationDrawerView view;
    private final Context activityContext;
    private final FragmentManager fragmentManager;
    private final PlaylistManager playlistManager;

    private boolean userLearnedDrawer;

    @Inject
    public DefaultNavigationDrawerPresenter(NavigationDrawerView view, @FromContext(ContextType.ACTIVITY) Context activityContext,
                                            FragmentManager fragmentManager, PlaylistManager playlistManager) {
        this.view = view;
        this.activityContext = activityContext;
        this.fragmentManager = fragmentManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void init() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activityContext);
        userLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);
        Fragment dialog = fragmentManager.findFragmentByTag("dialog");
        if (dialog != null && dialog instanceof CreatePlaylistDialog) {
            ((CreatePlaylistDialog) dialog).setListener(this);
            setDialogExistingPlaylistNames((CreatePlaylistDialog) dialog);
        }
    }

    @Override
    public void onNewPlaylistButtonClicked(View v) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment previousDialog = fragmentManager.findFragmentByTag("dialog");
        if (previousDialog != null) {
            ft.remove(previousDialog);
        }
        ft.addToBackStack(null);
        CreatePlaylistDialog dialog = CreatePlaylistDialog.newInstance();
        dialog.setListener(this);
        setDialogExistingPlaylistNames(dialog);
        dialog.show(ft, "dialog");
    }

    private void setDialogExistingPlaylistNames(CreatePlaylistDialog dialog) {
        Set<String> usedNames = new HashSet<>();
        for (Playlist p : playlistManager.getPlaylists()) {
            usedNames.add(p.getName());
        }
        dialog.setExistingPlaylistNames(usedNames);
    }

    @Override
    public void onPlaylistClicked(String playlistName) {
        Playlist p = playlistManager.getPlaylist(playlistName);
        if (p == null) {
            Log.w(LOG_TAG, String.format("Failed to set playlist to %s. No playlist was found by that name?", playlistName));
            return;
        }
        playlistManager.selectPlaylist(playlistName);
        //setPlaylist(p);
    }

    @Override
    public void onPlaylistCreated(String playlistName) {
        playlistManager.createPlaylist(playlistName);
    }

    @Override
    public void onLicenseInfoButtonClicked(View button) {
        Intent intent = new Intent(activityContext, LicenseActivity.class);
        activityContext.startActivity(intent);
    }
}
