package com.colinchartier.overture.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import com.colinchartier.overture.app.R;
import com.colinchartier.overture.app.fragments.presenters.SongListPresenter;
import com.colinchartier.overture.app.fragments.views.SongListView;
import com.google.common.base.Preconditions;

public class SongListFragment extends Fragment implements SongListView {
    private SongListPresenter presenter;

    @Bind(R.id.song_list)
    ListView songList;

    public SongListFragment() {
        //Required empty constructor
    }

    public void setPresenter(SongListPresenter presenter) {
        Preconditions.checkState(this.presenter == null, "Presenter is already set!");
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    @OnItemClick(R.id.song_list)
    public void onSongClicked(View view, int position) {
        presenter.onSongClicked(view, position);
    }
}
