package com.colinchartier.overture.app.fragments.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.colinchartier.overture.app.R;
import com.colinchartier.overture.app.fragments.views.NavigationDrawerView;

public class NavigationDrawerFragment extends Fragment implements NavigationDrawerView {
    @Bind(R.id.playlist_list)
    ListView playlists;

    public NavigationDrawerFragment() {
        //Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.playlist_new)
    public void onNewPlaylistClicked(View view) {

    }

    @OnClick(R.id.license_info)
    public void onLicenseInfoClicked(View view) {
    }
}
