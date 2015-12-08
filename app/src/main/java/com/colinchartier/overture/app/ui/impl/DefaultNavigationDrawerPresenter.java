package com.colinchartier.overture.app.ui.impl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.colinchartier.overture.app.ContextType;
import com.colinchartier.overture.app.FromContext;
import com.colinchartier.overture.app.R;
import com.colinchartier.overture.app.playlist.Playlist;
import com.colinchartier.overture.app.playlist.listeners.OnPlaylistListChangedListener;
import com.colinchartier.overture.app.ui.activities.LicenseActivity;
import com.colinchartier.overture.app.ui.dialogs.CreatePlaylistDialog;
import com.colinchartier.overture.app.ui.fragments.presenters.NavigationDrawerPresenter;
import com.colinchartier.overture.app.ui.fragments.views.NavigationDrawerView;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class DefaultNavigationDrawerPresenter extends MusicBindingPresenter implements NavigationDrawerPresenter, CreatePlaylistDialog.Listener, OnPlaylistListChangedListener {
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String LOG_TAG = "navdrawerpres";

    private final NavigationDrawerView view;
    private final Context activityContext;
    private final FragmentManager fragmentManager;
    private final LayoutInflater inflater;

    private boolean userLearnedDrawer;

    private BaseAdapter playlistAdapter;

    @Inject
    public DefaultNavigationDrawerPresenter(NavigationDrawerView view, @FromContext(ContextType.ACTIVITY) Context activityContext,
                                            FragmentManager fragmentManager, LayoutInflater inflater) {
        super(activityContext);
        this.view = view;
        this.activityContext = activityContext;
        this.fragmentManager = fragmentManager;
        this.inflater = inflater;
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
        bindMusicService();
    }

    @Override
    public void stop() {
        getMusicService().getPlaylistManager().removePlaylistListChangedListener(this);
        unbindMusicService();
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
        for (Playlist playlist : getMusicService().getPlaylistManager().getPlaylists()) {
            usedNames.add(playlist.getName());
        }
        dialog.setExistingPlaylistNames(usedNames);
    }

    @Override
    public void onPlaylistClicked(String playlistName) {
        getMusicService().getPlaylistManager().selectPlaylist(playlistName);
    }

    @Override
    public void onPlaylistCreated(String playlistName) {
        getMusicService().getPlaylistManager().createPlaylist(playlistName);
    }

    @Override
    public void onLicenseInfoButtonClicked(View button) {
        Intent intent = new Intent(activityContext, LicenseActivity.class);
        activityContext.startActivity(intent);
    }

    @Override
    protected void onMusicServiceConnected() {
        playlistAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return getMusicService().getPlaylistManager().getPlaylistNames().size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final TextView nameHolder;
                String currPlaylist = getMusicService().getPlaylistManager().getPlaylistNames().get(position);
                if (convertView == null) { // We haven't cached this row's layout yet.
                    convertView = inflater.inflate(R.layout.playlist, parent, false);
                    nameHolder = (TextView) convertView.findViewById(R.id.playlist);
                    convertView.setTag(nameHolder);
                } else { // Playlist is already cached.
                    nameHolder = (TextView) convertView.getTag();
                }
                nameHolder.setText(currPlaylist);
                return convertView;
            }
        };

        getMusicService().getPlaylistManager().addPlaylistListChangedListener(this);
    }

    @Override
    public void onPlaylistListChanged() {
        playlistAdapter.notifyDataSetChanged();
    }
}
