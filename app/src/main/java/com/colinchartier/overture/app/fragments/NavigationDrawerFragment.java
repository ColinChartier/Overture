package com.colinchartier.overture.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import com.colinchartier.overture.app.R;
import com.colinchartier.overture.app.fragments.presenters.NavigationDrawerPresenter;
import com.colinchartier.overture.app.fragments.views.NavigationDrawerView;
import com.google.common.base.Preconditions;

public class NavigationDrawerFragment extends Fragment implements NavigationDrawerView {
    private NavigationDrawerPresenter presenter;

    @Bind(R.id.playlist_list)
    ListView playlists;

    public NavigationDrawerFragment() {
        //Required empty constructor
    }

    public void setPresenter(NavigationDrawerPresenter presenter) {
        Preconditions.checkState(presenter == null, "Presenter is already set!");
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        presenter.init();
    }

    @OnItemClick(R.id.playlist_list)
    public void onPlaylistClicked(View view, int position) {
        presenter.onPlaylistClicked(((TextView) view).getText().toString());
    }

    @OnClick(R.id.playlist_new)
    public void onNewPlaylistButtonClicked(View button) {
        presenter.onNewPlaylistButtonClicked(button);
    }

    @OnClick(R.id.license_info)
    public void onLicenseInfoButtonClciked(View button) {
        presenter.onLicenseInfoButtonClicked(button);
    }
}
